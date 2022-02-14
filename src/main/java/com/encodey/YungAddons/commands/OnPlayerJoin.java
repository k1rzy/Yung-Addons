package com.encodey.YungAddons.commands;

import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.features.Slayer.BossSlainTime;
import com.encodey.YungAddons.lib.ColorCode;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class OnPlayerJoin extends Thread {
    public boolean getData = false;
    Minecraft mc = Minecraft.getMinecraft();

    ScaledResolution scaledResolution;

    @SubscribeEvent
    public void onConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
            new Thread(() -> {
                try {
                    Thread.sleep(3600L);
                    ChatComponentText msg1 = new ChatComponentText(EnumChatFormatting.AQUA + "================================================" + "\n" + EnumChatFormatting.RED + "[Yung Addons] " + "\n" + EnumChatFormatting.BLUE + "Type /yung to open mod menu with features that allowed on hypixel." + "\n" + EnumChatFormatting.BLUE + "\n" + EnumChatFormatting.BLUE + " Current mod version: " + YungAddons.VERSION + "\n" + EnumChatFormatting.AQUA + "================================================");
                    Minecraft.getMinecraft().thePlayer.addChatMessage(msg1);
                    BossSlainTime.activated = true;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }).start();
    }
}