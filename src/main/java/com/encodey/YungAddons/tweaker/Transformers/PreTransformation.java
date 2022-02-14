package com.encodey.YungAddons.tweaker.Transformers;

import gg.essential.loader.stage0.EssentialSetupTweaker;
import net.minecraft.launchwrapper.Launch;

public class PreTransformation {
    public static boolean deobfuscated;
    public static boolean usingNotchMappings;

    public static void runChecks() {
        deobfuscated = false;
        deobfuscated = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
        usingNotchMappings = !deobfuscated;
    }
}
