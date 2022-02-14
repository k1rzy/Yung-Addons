package com.encodey.YungAddons.features.Slayer;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import com.encodey.YungAddons.utils.UtilsDanker;
import com.google.common.base.Stopwatch;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.Sys;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class BossSlainTime {
    public static boolean activated;

    public static boolean running;

    public static long LastActive = 0L;
    public static double DeadTransf;
    double timeNow = System.currentTimeMillis() / 1000;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        activated = false;
        DeadTransf = 0L;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if(cleanedLine.contains("Slay the boss!")) {
                activated = true;
                TotalTime();
            }
        }
        if(message.contains("NICE! SLAYER BOSS ") || message.contains("SLAYER QUEST FAILED!")) {
            activated = false;
            TotalTime();
        }
    }

    public void TotalTime() {
        if(!Config.bossslaintime) return;
        if(activated && Config.bossslaintime) {
            DeadTransf = System.currentTimeMillis() / 1000 + 1;
        }
        if(!activated && Config.bossslaintime) {
            String time = EnumChatFormatting.LIGHT_PURPLE + UtilsDanker.getTimeBetween(timeNow, DeadTransf);
            if(DeadTransf < 1) {
                DeadTransf = DeadTransf + DeadTransf;
                ChatComponentText msg1 = new ChatComponentText(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + "Boss took: " + EnumChatFormatting.BLUE + "So fast!");
                Minecraft.getMinecraft().thePlayer.addChatMessage(msg1);
                DeadTransf = 0L;
            }
            else if(DeadTransf >= 1) {
                DeadTransf = DeadTransf + DeadTransf;
                DeadTransf = DeadTransf / 2;
                DeadTransf += 1;
                ChatComponentText msg1 = new ChatComponentText(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + "Boss took: " + EnumChatFormatting.LIGHT_PURPLE + "" + time + "s" + EnumChatFormatting.BLUE + " to slain.");
                Minecraft.getMinecraft().thePlayer.addChatMessage(msg1);
                DeadTransf = 0L;
            }
            DeadTransf = 0L;
        }
    }
    public void AddTime() {
        DeadTransf += 1;
    }
}
