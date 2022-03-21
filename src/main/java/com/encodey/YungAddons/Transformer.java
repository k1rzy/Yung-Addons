package com.encodey.YungAddons;


import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.launch.*;
import java.util.*;

@IFMLLoadingPlugin.MCVersion("1.8.9")
public class Transformer implements IFMLLoadingPlugin
{
    public static HashMap<String, byte[]> classes;

    public Transformer() {
        MixinBootstrap.init();
    }

    public String[] getASMTransformerClass() {
        return new String[] { YungTils.class.getName() };
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(final Map<String, Object> data) {
    }

    public String getAccessTransformerClass() {
        return null;
    }

    static {
        Transformer.classes = new HashMap<String, byte[]>();
    }
}