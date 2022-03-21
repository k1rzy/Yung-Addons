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
        }
    }
}
