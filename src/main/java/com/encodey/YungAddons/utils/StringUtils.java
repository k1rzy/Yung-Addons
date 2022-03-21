package com.encodey.YungAddons.utils;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.*;
import net.minecraft.util.*;

import java.util.Set;

import com.encodey.YungAddons.utils.ChatColor;
/**
 * @author k1rzy (encodey)
 */
public class StringUtils {
    public static final Set<String> PROTOCOLS = Sets.newHashSet("http", "https");

    public static String cleanColour(String in) {
        return in.replaceAll("(?i)\\u00A7.", "");
    }

    public static String cleanColourNotModifiers(String in) {
        return in.replaceAll("(?i)\\u00A7[0-9a-f]", "\u00A7r");
    }

    private static final String PREFIX = ChatColor.format(" ");;

    public static void send(String text, final String... args) {
        if (Minecraft.getMinecraft().thePlayer == null) {
            return;
        }
        text = String.format(text, (Object[])args);
        final StringBuilder messageBuilder = new StringBuilder();
        for (String word : text.split(" ")) {
            word = ChatColor.format(ChatColor.getLastColors(text) + word);
            messageBuilder.append(word).append(" ");
        }
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(PREFIX + ChatColor.format(messageBuilder.toString().trim())));
    }
    public static String trimToWidth(String str, int len) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        String trim = fr.trimStringToWidth(str, len);

        if(str.length() != trim.length() && !trim.endsWith(" ")) {
            char next = str.charAt(trim.length());
            if(next != ' ') {
                String[] split = trim.split(" ");
                String last = split[split.length-1];
                if(last.length() < 8) {
                    trim = trim.substring(0, trim.length()-last.length());
                }
            }
        }

        return trim;
    }
}
