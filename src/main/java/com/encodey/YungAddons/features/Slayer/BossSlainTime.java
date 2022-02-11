package com.encodey.YungAddons.features.Slayer;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import com.google.common.base.Stopwatch;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.Sys;

import javax.management.timer.Timer;
import java.sql.Time;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BossSlainTime {
    public static boolean activated;

    public static boolean running;

    public static long LastActive = 0L;
    public static long DeadTransf;

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
        if(message.startsWith("NICE! SLAYER BOSS " ) || message.contains("SLAYER QUEST COMPLETE!") || message.contains("SLAYER QUEST FAILED!")) {
            activated = false;
            TotalTime();
        }
    }

    public void TotalTime() {
        if(!Config.bossslaintime) return;
        if(activated && Config.bossslaintime) {
            if(!running) {
                new Thread(() -> {
                    try {
                        Thread.sleep(1000L);
                        running = true;
                        DeadTransf++;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }).start();
            }
            if(running) running = false;
        }
        if(!activated && Config.bossslaintime) {
            if(DeadTransf < 1) {
                ChatComponentText msg1 = new ChatComponentText(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + "Boss took: " + EnumChatFormatting.BLUE + "You Killed Boss so Fast!");
                Minecraft.getMinecraft().thePlayer.addChatMessage(msg1);
            }
            else if(DeadTransf >= 1) {
                ChatComponentText msg1 = new ChatComponentText(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + "Boss took: " + EnumChatFormatting.LIGHT_PURPLE + DeadTransf + "s" + EnumChatFormatting.BLUE + " to slain.");
                Minecraft.getMinecraft().thePlayer.addChatMessage(msg1);
            }
            DeadTransf = 0L;
        }
    }
}
