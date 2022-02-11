package com.encodey.YungAddons.features.Solvers;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.features.ESP.DefaultMobESP;
import com.encodey.YungAddons.features.ESP.DungeonChestESP;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import com.encodey.YungAddons.utils.RenderUtils;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class BlazeSolver {

    public static int radius = 80;

    ClientChatReceivedEvent eventchat;

    public static boolean setPositionHighest;
    public static boolean setPositionLowest;
    public static boolean setPosition;

    public static Color testmessage = new Color(210, 45, 111, 250);

    public static Entity highestBlaze;
    public static Entity lowestBlaze;
    public static int LOWEST_BLAZE_COLOUR = 0xD62440;
    public static int HIGHEST_BLAZE_COLOUR = 0x0EAC35;

    public void getPos() {
        if(setPosition) {
            for (String line : ScoreboardHandler.getSidebarLines()) {
                String cleanedLine = ScoreboardHandler.cleanSB(line);
                if (cleanedLine.contains("The Catacombs")) {
                    setPosition = true;
                    setPositionLowest = true;
                    setPositionHighest = true;
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        lowestBlaze = null;
        highestBlaze = null;
        setPositionHighest = false;
        setPositionLowest = false;
        setPosition = false;
    }

    @SubscribeEvent
    public void onTick(RenderWorldLastEvent event) {

        World world = Minecraft.getMinecraft().theWorld;
            if (Config.blazesolver && DungeonChestESP.dungeonStarted && world != null) {
                int highestHealth = 0;
                highestBlaze = null;
                int lowestHealth = 99999999;
                lowestBlaze = null;
                        for (Entity entity : world.loadedEntityList) {
                            if (entity.getName().contains("Blaze") && entity.getName().contains("/")) {
                                String blazeName = StringUtils.stripControlCodes(entity.getName());
                                try {
                                    int health = Integer.parseInt(blazeName.substring(blazeName.indexOf("/") + 1, blazeName.length() - 1));
                                    if (health > highestHealth) {
                                        highestHealth = health;
                                        highestBlaze = entity;
                                    }
                                    if (health < lowestHealth) {
                                        lowestHealth = health;
                                        lowestBlaze = entity;
                                    }
                                    if (Config.blazesolver && DungeonChestESP.dungeonStarted) {
                                        if (lowestBlaze != null) {
                                            for(Entity Lowest : world.loadedEntityList)
                                                if(Lowest instanceof EntityBlaze) {
                                                    double dist = lowestBlaze.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
                                                    Lowest = lowestBlaze;
                                                    if (dist < radius) {
                                                        RenderUtils.drawEntityBox(Lowest, Color.RED, true, event.partialTicks);
                                                    }
                                                }
                                        }
                                        if (highestBlaze != null) {
                                            for (Entity Highest : world.loadedEntityList)
                                                if (Highest instanceof EntityBlaze) {
                                                    double dist = highestBlaze.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
                                                    Highest = highestBlaze;
                                                    if (dist < radius) {
                                                        RenderUtils.drawEntityBox(Highest, Color.GREEN, true, event.partialTicks);
                                                    }
                                                }

                                        }
                                    }
                                } catch (NumberFormatException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
            }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
    }
}
