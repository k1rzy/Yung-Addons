package com.encodey.YungAddons;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

/**
 * @author k1rzy (encodey)
 */
public class ScriptAliases {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static void addMessage(String text) {
        if(text != null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
        }
    }
    public static void sendMessage(String sender) {
        if(sender != null) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage(sender);
        }
    }
    public static void rotateCustomYaw(float yaw, float pitch, float speedYaw, float speedPitch) {
        if(yaw == mc.thePlayer.rotationYaw) {
            mc.thePlayer.rotationYaw = yaw;
            return;
        }
        while(mc.thePlayer.rotationYaw > yaw) {
            mc.thePlayer.rotationYaw -= speedYaw;
            while(mc.thePlayer.rotationPitch < pitch) {
                mc.thePlayer.rotationPitch += speedPitch;
            }
            while(mc.thePlayer.rotationPitch > pitch) {
                mc.thePlayer.rotationPitch -= speedPitch;
            }
        }
        while(mc.thePlayer.rotationYaw < yaw) {
            mc.thePlayer.rotationYaw += speedYaw;
            while(mc.thePlayer.rotationPitch < pitch) {
                mc.thePlayer.rotationPitch += speedPitch;
            }
            while(mc.thePlayer.rotationPitch > pitch) {
                mc.thePlayer.rotationPitch -= speedPitch;
            }
        }
    }
}
