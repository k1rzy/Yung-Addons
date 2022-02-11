package com.encodey.YungAddons.features.Mining.CrystalHollows;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.features.ESP.DefaultMobESP;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import com.encodey.YungAddons.utils.RenderUtils;
import java.awt.Color;

import com.encodey.YungAddons.utils.UtilsDanker;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoLocWaypoints {
    public static double posX;
    public static double posY;
    public static double posZ;

    public static boolean inImportantLocation;

    public static boolean jungletemple;
    public static boolean minesofdivans;
    public static boolean preccity;
    public static boolean khazaddum;
    public static boolean dragonslair;
    public static boolean goblinqueen;
    public static boolean fairygrotto;
    public static boolean activated1;
    public static boolean activated2;
    public static boolean activated3;
    public static boolean activated4;
    public static boolean activated5;
    public static boolean activated6;
    public static boolean activated7;

    public static BlockPos waypoint = new BlockPos(0,0,0);

    static Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        inImportantLocation = false;
        posX = 0;
        posY = 0;
        posZ = 0;
        waypoint = null;
        jungletemple = false;
        minesofdivans = false;
        preccity = false;
        khazaddum = false;
        dragonslair = false;
        goblinqueen = false;
        fairygrotto = false;
        activated1 = false;
        activated2 = false;
        activated3 = false;
        activated4 = false;
        activated5 = false;
        activated6 = false;
        activated7 = false;
    }

    @SubscribeEvent
    public void inImpLoc(ClientChatReceivedEvent event) {
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if(Config.chautolocate) {
                if (cleanedLine.contains("Jungle Temple")) {
                    jungletemple = true;
                    inImportantLocation = true;
                }
                if(cleanedLine.contains("Mines of Divan")) {
                    inImportantLocation = true;
                    minesofdivans = true;
                }
                if(cleanedLine.contains("Goblin Queen's Den")) {
                    goblinqueen = true;
                    inImportantLocation = true;
                }
                if(cleanedLine.contains("Lost Precursor City")) {
                    preccity = true;
                    inImportantLocation = true;
                }
                if(cleanedLine.contains("Khazad-d\u00fbm")) {
                    khazaddum = true;
                    inImportantLocation = true;
                }
                if(cleanedLine.contains("Fairy Grotto")) {
                    fairygrotto = true;
                    inImportantLocation = true;
                }
                if(cleanedLine.contains("Dragon's Lair")) {
                    dragonslair = true;
                    inImportantLocation = true;
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(Config.chautolocate) {
            waypoint = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ);
            double dist = waypoint.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            if(jungletemple && !activated1) {
                UtilsDanker.draw3DString(waypoint, "Jungle Temple: " + dist + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(waypoint, Color.PINK, true, event.partialTicks);
                activated1 = true;
            }
            if(khazaddum && !activated2) {
                UtilsDanker.draw3DString(waypoint, "Khazad-d\u00fbm: " + dist + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(waypoint, Color.PINK, true, event.partialTicks);
                activated2 = true;
            }
            if(fairygrotto && !activated3) {
                UtilsDanker.draw3DString(waypoint, "Fairy Grotto: " + dist + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(waypoint, Color.PINK, true, event.partialTicks);
                activated3 = true;
            }
            if(goblinqueen && !activated4) {
                UtilsDanker.draw3DString(waypoint, "Goblin Queen's Den: " + dist + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(waypoint, Color.PINK, true, event.partialTicks);
                activated4 = true;
            }
            if(dragonslair && !activated5) {
                UtilsDanker.draw3DString(waypoint, "Dragon's lair: " + dist + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(waypoint, Color.PINK, true, event.partialTicks);
                activated5 = true;
            }
            if(minesofdivans && !activated6) {
                UtilsDanker.draw3DString(waypoint, "Mines of Divan: " + dist + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(waypoint, Color.PINK, true, event.partialTicks);
                activated6 = true;
            }
            if(preccity && !activated7) {
                UtilsDanker.draw3DString(waypoint, "Lost Precursor City: " + dist + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(waypoint, Color.PINK, true, event.partialTicks);
                activated7 = true;
            }
        }
    }
}
