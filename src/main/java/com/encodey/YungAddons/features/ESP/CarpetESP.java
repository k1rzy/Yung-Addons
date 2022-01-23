package com.encodey.YungAddons.features.ESP;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import com.encodey.YungAddons.utils.*;
import gg.essential.universal.UChat;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class CarpetESP {

    public static BlockPos woolcarpet;
    public static BlockPos woolcarpetsecond;

    public static boolean inDwarvenMines;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        woolcarpetsecond = null;
        woolcarpet = null;
        inDwarvenMines = false;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Dwarven Mines")) {
                inDwarvenMines = true;
            }
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Dwarven Village")) {
                inDwarvenMines = true;
            }
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Palace Bridge")) {
                inDwarvenMines = true;
            }
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Royal Mines")) {
                inDwarvenMines = true;
            }
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Gates to the Mines")) {
                inDwarvenMines = true;
            }
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Rampart's Quarry")) {
                inDwarvenMines = true;
            }
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Forge Basin")) {
                inDwarvenMines = true;
            }
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Lava Springs")) {
                inDwarvenMines = true;
            }
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Cliffside Veins")) {
                inDwarvenMines = true;
            }
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Upper Mines")) {
                inDwarvenMines = true;
            }
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Royal Palace")) {
                inDwarvenMines = true;
            }
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("The Forge")) {
                inDwarvenMines = true;
            }
        }
    }

    @SubscribeEvent
    public void carpet(RenderWorldLastEvent event) throws NullPointerException {
        Minecraft mc = Minecraft.getMinecraft();
        if (!inDwarvenMines) return;
        if (inDwarvenMines && Config.carpetrecolor) {
            if (Config.carpetrecolor && mc.getCurrentServerData().serverIP.contains("hypixel.")) {
                woolcarpet = closestCarpet();
                woolcarpetsecond = closestCarpetSecond();
                try {
                    if (woolcarpet != null) {
                        RenderUtils.drawBlockBox(woolcarpet, Config.carpetcolor, true, event.partialTicks);
                    }
                    if(woolcarpetsecond != null) {
                        RenderUtils.drawBlockBox(woolcarpetsecond, Config.carpetcolor, true, event.partialTicks);
                    }
                } catch (NullPointerException e) {
                    UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");

                }
            }
        }
    }
    private BlockPos closestCarpet() {
        int r = 6;
        BlockPos playerPos = Minecraft.getMinecraft().thePlayer.getPosition();
        playerPos.add(0, 1, 0);
        Vec3 playerVec = Minecraft.getMinecraft().thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, r, r);
        Vec3i vec3i2 = new Vec3i(r, r, r);
        ArrayList<Vec3> stones = new ArrayList<Vec3>();
        if (playerPos != null) {
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i2))) {
                IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
                //Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(blockState.getBlock().toString()));
                if (blockState.getBlock() == Blocks.carpet) {
                    stones.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
                }
            }
        }
        double smallest = 9999;
        Vec3 closest = null;
        for (Vec3 stone : stones) {
            double dist = stone.distanceTo(playerVec);
            if (dist < smallest) {
                smallest = dist;
                closest = stone;
            }
        }
        if (closest != null && smallest < 5) {
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        }
        return null;
    }
    private BlockPos closestCarpetSecond() {
        int r = 7;
        BlockPos playerPos = Minecraft.getMinecraft().thePlayer.getPosition();
        playerPos.add(0, 1, 0);
        Vec3 playerVec = Minecraft.getMinecraft().thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, r, r);
        Vec3i vec3i2 = new Vec3i(r, r, r);
        ArrayList<Vec3> stones = new ArrayList<Vec3>();
        if (playerPos != null) {
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i2))) {
                IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
                //Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(blockState.getBlock().toString()));
                if (blockState.getBlock() == Blocks.carpet) {
                    stones.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
                }
            }
        }
        double smallest = 9999;
        Vec3 closest = null;
        for (Vec3 stone : stones) {
            double dist = stone.distanceTo(playerVec);
            if (dist < smallest) {
                smallest = dist;
                closest = stone;
            }
        }
        if (closest != null && smallest < 6) {
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        }
        return null;
    }
}
