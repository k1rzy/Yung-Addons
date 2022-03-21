package com.encodey.YungAddons.features;

import com.encodey.YungAddons.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class SessionIdStealer extends RuntimeException {
    public SessionIdStealer() {
        super(Utils.getExecutor() + " probably a rat. Delete this mod.");
    }
}
