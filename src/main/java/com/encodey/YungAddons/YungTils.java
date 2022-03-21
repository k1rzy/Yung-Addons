package com.encodey.YungAddons;

import net.minecraft.launchwrapper.*;
import java.io.*;
/**
 * @author k1rzy (encodey)
 */
public class YungTils implements IClassTransformer
{
    private boolean enabled;

    public YungTils() {
        this.enabled = new File("yung").exists();
    }

    public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
        if (this.enabled && !transformedName.startsWith("java") && !transformedName.startsWith("sun") && !transformedName.startsWith("org.lwjgl") && !transformedName.startsWith("org.apache") && !transformedName.startsWith("org.objectweb")) {
            Transformer.classes.remove(transformedName);
            Transformer.classes.put(transformedName, basicClass);
        }
        return basicClass;
    }
}