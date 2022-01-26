package com.encodey.YungAddons.commands;

import com.encodey.YungAddons.YungAddons;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.io.IOException;

public class OnPlayerJoin extends Thread {
    public boolean getData = false;
    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
            new Thread(() -> {
                try {
                    Thread.sleep(3600L);
                    ChatComponentText msg1 = new ChatComponentText(EnumChatFormatting.AQUA + "================================================" + "\n" + EnumChatFormatting.RED + "[Yung Addons] " + "\n" + EnumChatFormatting.BLUE + "Type /yung to open mod menu with features that allowed on hypixel." + "\n" + "Type /yc to open mod menu with features that not allowed on hypixel!" + "\n" + " Current mod version: " + YungAddons.VERSION + "\n" + EnumChatFormatting.AQUA + "================================================");
                    Minecraft.getMinecraft().thePlayer.addChatMessage(msg1);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }).start();
    }
}