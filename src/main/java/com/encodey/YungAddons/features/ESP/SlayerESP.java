package com.encodey.YungAddons.features.ESP;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import com.encodey.YungAddons.utils.*;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

/**
 * @author CuzImClicks (stolen from danker's skyblock mod)
 */

public class SlayerESP {

    public static int radius = 6;

    static Entity zombie;
    static Entity spider;
    static Entity wolf;
    static Entity enderman;
    static boolean slayerActive;
    static boolean slayerStarted;
    public static int SLAYER_COLOUR = 0x0EAC35;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        zombie = null;
        spider = null;
        wolf = null;
        enderman = null;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        World world = Minecraft.getMinecraft().theWorld;
        if (world == null) return;
        if (!slayerStarted) return;
        if (zombie != null || spider != null || wolf != null || enderman != null) {
            return;
        }
        slayerActive = true;
        if (YungAddons.tickAmount % 10 == 0 && Config.svenesp || Config.tarantulaesp || Config.voidgloomesp) {
            for (String line : ScoreboardHandler.getSidebarLines()) {
                String cleanedLine = ScoreboardHandler.cleanSB(line);
                if (cleanedLine.contains("Slay the boss!")) {
                    slayerActive = true;
                    List<Entity> entities = world.getLoadedEntityList();
                    for (Entity e : entities) {
                        System.out.println(e.getName());
                         if (e.getName().contains("Tarantula Broodfather")) {
                            spider = e;
                            return;
                        } else if (e.getName().contains("Sven Packmaster")) {
                            wolf = e;
                            return;
                        } else if (e.getName().contains("Voidgloom Seraph")) {
                            enderman = e;
                            return;
                        }

                    }
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Slayer Quest")) {
                slayerStarted = true;
            }
        }
        if (message.contains("SLAYER QUEST STARTED!")) {
            slayerStarted = true;
        }
        if (message.contains("NICE! SLAYER BOSS SLAIN!") || message.contains("SLAYER QUEST COMPLETE!") || message.contains("SLAYER QUEST FAILED!")) {
            slayerActive = false;
            slayerStarted = false;
            zombie = null;
            spider = null;
            wolf = null;
            enderman = null;
        }

    }

    @SubscribeEvent
    public void voidgloom(final RenderWorldLastEvent event) {
        if(!slayerStarted) return;
        if(slayerActive) {
            try {
                if (Config.voidgloomesp) {
                    if(enderman != null) {
                        for (Entity entityeman : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
                            if (entityeman instanceof EntityEnderman) {
                                double dist = entityeman.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
                                if (dist < radius)
                                    RenderUtils.drawEntityBox(entityeman, Config.ESPColorslayer, true, event.partialTicks);
                            }
                        }
                    }
                }
            } catch (NullPointerException e) {
                UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");
            }
        }
    }


    @SubscribeEvent
    public void sven(final RenderWorldLastEvent event) {
        if(!slayerStarted) return;
        if(slayerActive) {
            try {
                if (Config.svenesp) {
                    if(wolf != null) {
                        for (Entity entitywolf : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
                            if (entitywolf instanceof EntityWolf) {
                                double dist = entitywolf.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
                                if (dist < radius)
                                    RenderUtils.drawEntityBox(entitywolf, Config.ESPColorslayer, true, event.partialTicks);
                            }
                        }
                    }
                }
            } catch (NullPointerException e) {
                UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");
            }
        }
    }


    @SubscribeEvent
    public void spider(final RenderWorldLastEvent event) {
        if(!slayerStarted) return;
        if(slayerActive ) {
            try {
                if (Config.tarantulaesp) {
                    if(spider != null) {
                        for (Entity entityspider : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
                            if (entityspider instanceof EntitySpider) {
                                double dist = entityspider.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
                                if (dist < radius)
                                    RenderUtils.drawEntityBox(entityspider, Config.ESPColorslayer, true, event.partialTicks);
                            }
                        }
                    }
                }
            } catch (NullPointerException e) {
                UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");
            }
        }
    }
}
