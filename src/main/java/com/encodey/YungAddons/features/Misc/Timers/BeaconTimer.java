package com.encodey.YungAddons.features.Misc.Timers;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import com.encodey.YungAddons.utils.FontUtils;
import com.encodey.YungAddons.utils.UtilsDanker;
import gg.essential.universal.UChat;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @author k1rzy (encodey)
 */
public class BeaconTimer {
    public static BlockPos beacon;
    public static boolean activated;
    public static boolean running;
    public static boolean holdingbeacon;
    public static boolean runningact;
    public static boolean beaconPlaced;

    public static double beaconTime;

    public static String textbeac;

    public static long LastBeacon = 0L;
    public static long totalBeacon = 5000L;

    private static long beaNotifMillis = 0;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        activated = false;
        running = false;
        runningact = false;
        holdingbeacon = false;
        beaconTime = 0;
    }

    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if(cleanedLine.contains("Slay the boss!")) {
                activated = true;
                runningact = true;
            }
        }
        if(message.contains("NICE! SLAYER BOSS ") || message.contains("SLAYER QUEST FAILED!")) {
            activated = false;
            running = false;
            runningact = false;
            holdingbeacon = false;
            beacon = null;
            beaconTime = 0;
        }
    }

    @SubscribeEvent
    public void BeaconPing(RenderWorldLastEvent event) throws NullPointerException {
        Minecraft mc = Minecraft.getMinecraft();
        if(!Config.beacontimer && !activated || !runningact) return;
        if(Config.beacontimer && mc.getCurrentServerData().serverIP.contains("hypixel.") && activated && runningact) {
            beacon = closestBeacon();
            try {
                if(beacon == null) {
                    beaconPlaced = false;
                }
                if (beacon != null) {
                    new Thread(() -> {
                        try {
                            beaconTime = System.currentTimeMillis() / 1000 + 5;
                            LessTime();
                            Thread.sleep(5000);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }).start();
                    beaNotifMillis = System.currentTimeMillis();
                    beaconPlaced = true;
                }
            }
            catch (NullPointerException e) {
                UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");

            }
        }
    }
    public void LessTime() {
        totalBeacon -= 700L;
        if(totalBeacon <= 0L) {
            totalBeacon = totalBeacon / 2;
            totalBeacon -= 1000L;
        }
    }
    private BlockPos closestBeacon() {
        int r = 15;
        BlockPos playerPos = Minecraft.getMinecraft().thePlayer.getPosition();
        playerPos.add(0, 1, 0);
        Vec3 playerVec = Minecraft.getMinecraft().thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, r, r);
        Vec3i vec3i2 = new Vec3i(r, r, r);
        ArrayList<Vec3> beacon = new ArrayList<Vec3>();
        if (playerPos != null) {
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i2))) {
                IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
                if (blockState.getBlock() == Blocks.beacon) {
                    beacon.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
                }
            }
        }
        double smallest = 9999;
        Vec3 closest = null;
        for (Vec3 stone : beacon) {
            double dist = stone.distanceTo(playerVec);
            if (dist < smallest) {
                smallest = dist;
                closest = stone;
            }
        }
        if (closest != null && smallest < 30) {
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        }
        return null;
    }
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (!Config.beacontimer) {
            return;
        }
        if (Config.beacontimer && runningact) {
            try {
                if(event.type == RenderGameOverlayEvent.ElementType.ALL) {
                    ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
                    int width = scaledResolution.getScaledWidth();
                    int height = scaledResolution.getScaledHeight();
                    final float scale = 1.5f;
                    GlStateManager.scale(scale, scale, 0.0f);
                    if(!runningact) return;
                    double timeNow = (double) System.currentTimeMillis() / 1000;
                    if (!beaconPlaced || beaconTime - timeNow < 0.0) {
                        if(runningact) {
                            FontUtils.drawCenteredString("Beacon: No Beacon", width/ 2.0f / scale, (height / 3.0f - 35.0f) / scale, 0xD62440);
                        }
                    }
                    else if(beaconPlaced && beaconTime - timeNow > 0.0) {
                        if(runningact) {
                            textbeac = UtilsDanker.getTimeBetween(timeNow, beaconTime);
                            FontUtils.drawCenteredString("Beacon: " + EnumChatFormatting.LIGHT_PURPLE + "Placed !!!", width/ 2.0f / scale, (height / 3.0f - 35.0f) / scale, 0xD62440);
                            FontUtils.drawCenteredString("Beacon: " + textbeac.replace("0m",""), width/ 2.0f / scale, (height / 2.8f - 15.0f) / scale, 0xD62440);
                            new Thread(() -> {
                                try {
                                    Thread.sleep(5000);
                                    beaconPlaced = false;
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return;
                            }).start();
                        }
                    }
                    GlStateManager.scale(1.0f / scale, 1.0f / scale, 0.0f);
                }
            } catch (Exception e) {
                UChat.chat(e);
            }
        }
    }
}
