package com.encodey.YungAddons.tweaker;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.tweaker.Transformers.GUIContainerYung;
import com.encodey.YungAddons.tweaker.Transformers.ITransformer;
import com.encodey.YungAddons.tweaker.Transformers.PreTransformation;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.commons.lang3.mutable.MutableInt;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

public class YungTransformer implements IClassTransformer {
    static {
        PreTransformation.runChecks();
    }

    private final Multimap<String, ITransformer> transformerMap = ArrayListMultimap.create();

    public YungTransformer() {
        registerTransformer(new GUIContainerYung());
    }

    private void registerTransformer(ITransformer transformer) {
        for (String cls : transformer.getClassName()) {
            transformerMap.put(cls, transformer);
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) return null;

        Collection<ITransformer> transformers = transformerMap.get(transformedName);
        if (transformers.isEmpty()) return bytes;

        YungAddons.logger.info("Found {} transformers for {}", transformers.size(), transformedName);

        ClassReader reader = new ClassReader(bytes);
        ClassNode node = new ClassNode();
        reader.accept(node, ClassReader.EXPAND_FRAMES);

        MutableInt classWriterFlags = new MutableInt(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        transformers.forEach(transformer -> {
            YungAddons.logger.info("Applying transformer {} on {}...", transformer.getClass().getName(), transformedName);
            transformer.transform(node, transformedName);
        });

        ClassWriter writer = new ClassWriter(classWriterFlags.getValue());

        try {
            node.accept(writer);
        } catch (Throwable t) {
            YungAddons.logger.error("Exception when transforming " + transformedName + " : " + t.getClass().getSimpleName());
            t.printStackTrace();
            outputBytecode(transformedName, writer);
            return bytes;
        }

        outputBytecode(transformedName, writer);

        return writer.toByteArray();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void outputBytecode(String transformedName, ClassWriter writer) {
        try {
            File bytecodeDirectory = new File("bytecode");
            File bytecodeOutput = new File(bytecodeDirectory, transformedName + ".class");

            if (!bytecodeDirectory.exists()) return;
            if (!bytecodeOutput.exists()) bytecodeOutput.createNewFile();

            FileOutputStream os = new FileOutputStream(bytecodeOutput);
            os.write(writer.toByteArray());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
