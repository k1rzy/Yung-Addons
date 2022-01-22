package com.encodey.YungAddons.commands;

import com.encodey.YungAddons.YungAddons;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.io.IOException;

public class OnPlayerJoin extends Thread {
    public boolean getData = false;
    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onServerConnect(EntityJoinWorldEvent player) throws NullPointerException {
        try {
            if(!getData && mc.getCurrentServerData().serverIP.contains("hypixel.")) {
                EntityPlayer logged = mc.thePlayer;
                logged.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "================================================" + "\n" + EnumChatFormatting.RED + "[Yung Addons] " + "\n" + EnumChatFormatting.BLUE + "Type /yung to open mod menu. Current mod version: " + YungAddons.VERSION + "\n" + EnumChatFormatting.AQUA + "================================================"));
                getData = true;
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void loggedOut(PlayerEvent playerlogged, ICommandSender sender) {
        EntityPlayer player = playerlogged.player;
        //if(player.onGround) {
            //sender.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + "Type /yung to open mod menu. Current mod version: " + YungAddons.VERSION));
        }
    }

