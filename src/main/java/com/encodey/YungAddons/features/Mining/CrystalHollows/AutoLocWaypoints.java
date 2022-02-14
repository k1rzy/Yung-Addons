package com.encodey.YungAddons.features.Mining.CrystalHollows;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.features.ESP.DefaultMobESP;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import com.encodey.YungAddons.utils.*;

import java.awt.Color;

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

    public static Color brown = new Color(157, 105, 46, 250);
    public static Color dark_red = new Color(128, 39, 39, 250);

    public static boolean activatedway1;
    public static boolean activatedway2;
    public static boolean activatedway3;
    public static boolean activatedway4;
    public static boolean activatedway5;
    public static boolean activatedway6;
    public static boolean activatedway7;

    public static BlockPos waypoint1 = new BlockPos(0,0,0);
    public static BlockPos waypoint2 = new BlockPos(0,0,0);
    public static BlockPos waypoint3 = new BlockPos(0,0,0);
    public static BlockPos waypoint4 = new BlockPos(0,0,0);
    public static BlockPos waypoint5 = new BlockPos(0,0,0);
    public static BlockPos waypoint6 = new BlockPos(0,0,0);
    public static BlockPos waypoint7 = new BlockPos(0,0,0);

    static Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        inImportantLocation = false;
        posX = 0;
        posY = 0;
        posZ = 0;
        waypoint1 = null;
        waypoint2 = null;
        waypoint3 = null;
        waypoint4 = null;
        waypoint5 = null;
        waypoint6 = null;
        waypoint7 = null;
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
        activatedway1 = false;
        activatedway2 = false;
        activatedway3 = false;
        activatedway4 = false;
        activatedway5 = false;
        activatedway6 = false;
        activatedway7 = false;
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
            double dist1;
            double dist2;
            double dist3;
            double dist4;
            double dist5;
            double dist6;
            double dist7;
            if(jungletemple) {
                activated1 = true;
                activatedway1 = true;
            }
            if(khazaddum) {
                activated2 = true;
                activatedway2 = true;
            }
            if(fairygrotto) {
                activated3 = true;
                activatedway3 = true;
            }
            if(goblinqueen) {
                activated4 = true;
                activatedway4 = true;
            }
            if(dragonslair) {
                activated5 = true;
                activatedway5 = true;
            }
            if(minesofdivans) {
                activated6 = true;
                activatedway6 = true;
            }
            if(preccity) {
                activated7 = true;
                activatedway7 = true;
            }
            if(activatedway1) {
                waypoint1.add(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ);
                dist1 = waypoint1.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                activatedway1 = false;
            }
            if(activatedway2) {
                waypoint2.add(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ);
                dist2 = waypoint2.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                activatedway2 = false;
            }
            if(activatedway3) {
                waypoint3.add(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ);
                dist3 = waypoint3.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                activatedway3 = false;
            }
            if(activatedway4) {
                waypoint4.add(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ);
                dist4 = waypoint4.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                activatedway4 = false;
            }
            if(activatedway5) {
                waypoint5.add(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ);
                dist5 = waypoint5.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                activatedway5 = false;
            }
            if(activatedway6) {
                waypoint6.add(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ);
                dist6 = waypoint6.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                activatedway6 = false;
            }
            if(activatedway7) {
                waypoint7.add(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ);
                dist7 = waypoint7.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                activatedway7 = false;
            }
            if(activated1) {
                BlockPos pos = waypoint1;
                dist1 = waypoint1.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                UtilsDanker.draw3DString(pos, "Jungle Temple: " + dist1 + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(pos, Color.GREEN, true, event.partialTicks);
            }
            if(activated2) {
                BlockPos pos = waypoint2;
                dist2 = waypoint2.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                UtilsDanker.draw3DString(pos, "Khazad-d\u00fbm: " + dist2 + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(pos, Color.RED, true, event.partialTicks);
            }
            if(activated3) {
                BlockPos pos = waypoint3;
                dist3 = waypoint3.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                UtilsDanker.draw3DString(pos, "Fairy Grotto: " + dist3 + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(pos, Color.PINK, true, event.partialTicks);
            }
            if(activated4) {
                BlockPos pos = waypoint4;
                dist4 = waypoint4.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                UtilsDanker.draw3DString(pos, "Goblin Queen's Den: " + dist4 + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(pos, brown, true, event.partialTicks);
            }
            if(activated5) {
                BlockPos pos = waypoint5;
                dist5 = waypoint5.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                UtilsDanker.draw3DString(pos, "Dragon's lair: " + dist5 + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(pos, dark_red, true, event.partialTicks);
            }
            if(activated6) {
                BlockPos pos = waypoint6;
                dist6 = waypoint6.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                UtilsDanker.draw3DString(pos, "Mines of Divan: " + dist6 + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(pos, Color.BLUE, true, event.partialTicks);
            }
            if(activated7) {
                BlockPos pos = waypoint7;
                dist7 = waypoint7.distanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                UtilsDanker.draw3DString(pos, "Lost Precursor City: " + dist7 + "m", 0xD64FC8, event.partialTicks);
                RenderUtils.drawBlockBox(pos, Color.CYAN, true, event.partialTicks);
            }
        }
    }
}
