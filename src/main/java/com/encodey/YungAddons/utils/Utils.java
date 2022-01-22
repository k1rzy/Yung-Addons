package com.encodey.YungAddons.utils;

import com.google.common.collect.Sets;
import gg.essential.universal.UChat;
import gg.essential.universal.wrappers.message.UTextComponent;
import com.encodey.YungAddons.YungAddons;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.Set;
import java.util.concurrent.Callable;

public class Utils {
    //Stolen from Mindlessly and he stoled it from Biscut & Moulberry and used for detecting whether in skyblock
    private static final Set<String> SKYBLOCK_IN_ALL_LANGUAGES = Sets.newHashSet("SKYBLOCK", "\u7A7A\u5C9B\u751F\u5B58", "\u7A7A\u5CF6\u751F\u5B58");
    private static boolean hasSkyblockScoreboard;

    public static void sendMessageWithPrefix(String message) {
        UChat.chat(EnumChatFormatting.RED + ("[Yung Addons] ") + message.replaceAll("&", "§"));
    }

    public static void sendMessageWithPrefix(String message, ClickEvent clickEvent) {
        UTextComponent result = new UTextComponent(EnumChatFormatting.RED + ("[Yung ADdons] ") + message.replaceAll("&", "§"));
        result.setChatStyle(new ChatStyle().setChatClickEvent(clickEvent));
        UChat.chat(result);
    }

    public static boolean isOnSkyblock() {
        return hasSkyblockScoreboard();
    }

    public static boolean hasSkyblockScoreboard() {
        return hasSkyblockScoreboard;
    }

    public static void updateSkyblockScoreboard() { // Thanks to NEU
        Minecraft mc = Minecraft.getMinecraft();

        if (mc != null && mc.theWorld != null && mc.thePlayer != null) {
            if (mc.isSingleplayer() || mc.thePlayer.getClientBrand() == null ||
                    !mc.thePlayer.getClientBrand().toLowerCase().contains("hypixel")) {
                hasSkyblockScoreboard = false;
                return;
            }

            Scoreboard scoreboard = mc.theWorld.getScoreboard();
            ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
            if (sidebarObjective != null) {
                String objectiveName = sidebarObjective.getDisplayName().replaceAll("(?i)\\u00A7.", "");
                for (String skyblock : SKYBLOCK_IN_ALL_LANGUAGES) {
                    if (objectiveName.contains(skyblock)) {
                        hasSkyblockScoreboard = true;
                        return;
                    }
                }
            }
            hasSkyblockScoreboard = false;
        }
    }

    public static void runInAThread(Callable<Void> callable) {
        new Thread(() -> {
            try {
                callable.call();
            } catch (Exception e) {
                YungAddons.logger.error(e.getMessage(), e);
            }
        }).start();
    }

    public static String removeColorCodes(String in) {
        return in.replaceAll("(?i)\\u00A7.", "");
    }

    public static EnumChatFormatting getColorCodeFromRarity(String rarity) {
        switch (rarity) {
            case "COMMON":
                return EnumChatFormatting.WHITE;
            case "UNCOMMON":
                return EnumChatFormatting.GREEN;
            case "RARE":
                return EnumChatFormatting.BLUE;
            case "EPIC":
                return EnumChatFormatting.DARK_PURPLE;
            case "LEGENDARY":
                return EnumChatFormatting.GOLD;
            case "MYTHIC":
                return EnumChatFormatting.LIGHT_PURPLE;
            case "DIVINE":
                return EnumChatFormatting.AQUA;
            case "SPECIAL":
            case "VERY_SPECIAL":
                return EnumChatFormatting.RED;
            default:
                return EnumChatFormatting.WHITE;
        }
    }
}
