package com.encodey.YungAddons.features.ESP;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import gg.essential.universal.UChat;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import com.encodey.YungAddons.utils.RenderUtils;
import com.encodey.YungAddons.features.ESP.*;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Locale;

public class DungeonChestESP {
    public static BlockPos chest;
    public static boolean dungeonStarted;
    public static boolean activated;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
	chest = null;
        activated = false;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("The Catacombs")) {
                activated = true;
                dungeonStarted = true;
		DefaultMobESP.isdungeonStarted = true;
            }
        }
    }

    @SubscribeEvent
    public void chatCheck(final ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        if(message.startsWith("Dungeon starts in 1 second.") && activated) {
            dungeonStarted = true;
        }
    }
    @SubscribeEvent
    public void dungeonChest(RenderWorldLastEvent event) throws NullPointerException {
        Minecraft mc = Minecraft.getMinecraft();
        if(Config.dungeonchestesp && mc.getCurrentServerData().serverIP.contains("hypixel.") && dungeonStarted && activated) {
            chest = closestChest();
            try {
                if (chest != null)
                    RenderUtils.drawBlockBox(chest, Config.ESPColorchest, true, event.partialTicks);
            }
            catch (NullPointerException e) {
                UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");

            }
        }
    }
    private BlockPos closestChest() {
        int r = 25;
        BlockPos playerPos = Minecraft.getMinecraft().thePlayer.getPosition();
        playerPos.add(0, 1, 0);
        Vec3 playerVec = Minecraft.getMinecraft().thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, r, r);
        Vec3i vec3i2 = new Vec3i(r, r, r);
        ArrayList<Vec3> stones = new ArrayList<Vec3>();
        if (playerPos != null) {
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i2))) {
                IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
                if (blockState.getBlock() == Blocks.chest) {
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
        if (closest != null && smallest < 35) {
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        }
        return null;
    }
}
