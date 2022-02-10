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
    public static boolean slayerActive;
    public static boolean slayerDied;
    Stopwatch stopwatch = Stopwatch.createStarted();
    long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
    public static Double totalTime;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Slay the boss!") && Config.bossslaintime) {
                slayerActive = true;
                TotalTime();
            }
            if (message.contains("NICE! SLAYER BOSS SLAIN!") || message.contains("SLAYER QUEST COMPLETE!") && Config.bossslaintime) {
                if(Config.bossslaintime) {
                    slayerDied = true;
                    TotalTime();
                }
            }
        }
    }
    public void TotalTime() {
        if(!Config.bossslaintime) return;
        if(slayerActive && Config.bossslaintime) {
            slayerDied = false;
            stopwatch.start();
        }
        if(slayerDied && Config.bossslaintime) {
            stopwatch.stop();
            slayerActive = false;
            ChatComponentText msg1 = new ChatComponentText(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + "Boss took: " + stopwatch + "to slain.");
            Minecraft.getMinecraft().thePlayer.addChatMessage(msg1);
        }
    }
}
