package com.encodey.YungAddons.utils;

import com.google.common.collect.Sets;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gg.essential.universal.UChat;
import gg.essential.universal.wrappers.message.UTextComponent;
import com.encodey.YungAddons.YungAddons;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;


public class Utils {
    private static final Set<String> SKYBLOCK_IN_ALL_LANGUAGES = Sets.newHashSet("SKYBLOCK", "\u7A7A\u5C9B\u751F\u5B58", "\u7A7A\u5CF6\u751F\u5B58");
    private static final Set<String> CH_IN_ALL_LANGUAGES = Sets.newHashSet("CRYSTAL HOLLOWS", "\u7A7A\u5C9B\u751F\u5B58", "\u7A7A\u5CF6\u751F\u5B58");
    public static boolean isSkyblockSkyboard;
    public static boolean isCrystalHollows;

    public static JsonElement getJson(String jsonUrl) throws IOException {
        URL url = new URL(jsonUrl);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("Connection", "close");
        conn.setRequestProperty("User-Agent", "YungAddons/1.0");
        return new JsonParser().parse(new InputStreamReader(conn.getInputStream()));
    }

    public static boolean isOnSkyblock() {
        return isSkyblockSkyboard;
    }
    public static boolean isSkyblockSkyboard() {
        return isSkyblockSkyboard;
    }
    public static boolean isCrystalHollows() {
        return isCrystalHollows;
    }
    public static void updateSkyblockScoreboard() { // Thanks to MINDDLESSLY HAHAHAH
        Minecraft mc = Minecraft.getMinecraft();

        if (mc != null && mc.theWorld != null && mc.thePlayer != null) {
            if (mc.isSingleplayer() || mc.thePlayer.getClientBrand() == null ||
                    !mc.thePlayer.getClientBrand().toLowerCase().contains("hypixel")) {
                isSkyblockSkyboard = false;
                isCrystalHollows = false;
                return;
            }

            Scoreboard scoreboard = mc.theWorld.getScoreboard();
            ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
            if (sidebarObjective != null) {
                String objectiveName = sidebarObjective.getDisplayName().replaceAll("(?i)\\u00A7.", "");
                for (String skyblock : SKYBLOCK_IN_ALL_LANGUAGES) {
                    if (objectiveName.contains(skyblock)) {
                        isSkyblockSkyboard = true;
                        return;
                    }
                }
                for (String crystalhollows : CH_IN_ALL_LANGUAGES) {
                    if (objectiveName.contains(crystalhollows)) {
                        isCrystalHollows = true;
                        return;
                    }
                }
            }
            isSkyblockSkyboard = false;
        }
    }
}
