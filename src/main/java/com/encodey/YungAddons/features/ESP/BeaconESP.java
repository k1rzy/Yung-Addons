package com.encodey.YungAddons.features.ESP;

import com.encodey.YungAddons.features.Slayer.BossSlainTime;
import com.encodey.YungAddons.utils.Utils;
import gg.essential.universal.UChat;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.*;
import com.encodey.YungAddons.utils.RenderUtils;
import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.handler.*;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.core.pattern.NameAbbreviator;

import java.awt.*;
import java.util.ArrayList;
/**
 * @author k1rzy (encodey)
 */
public class BeaconESP {

    public static BlockPos beacon;
    public static boolean activated;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        activated = false;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
                if(cleanedLine.contains("Slay the boss!")) {
                    activated = true;
                }
        }
        if(message.startsWith("NICE! SLAYER BOSS ") || message.contains("SLAYER QUEST COMPLETE!") || message.contains("SLAYER QUEST FAILED!")) {
            activated = false;
        }
    }

    @SubscribeEvent
    public void BeaconRecolor(RenderWorldLastEvent event) throws NullPointerException {
        Minecraft mc = Minecraft.getMinecraft();
        if(!Config.beaconesp && !activated) return;
        if(Config.beaconesp && mc.getCurrentServerData().serverIP.contains("hypixel.") && activated) {
            beacon = closestBeacon();
            try {
                if(beacon == null) return;
                RenderUtils.drawBlockBox(beacon, Config.beaconcolor, true, event.partialTicks);
            }
            catch (NullPointerException e) {
                UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");

            }
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
                //Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(blockState.getBlock().toString()));
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
        if (closest != null && smallest < 15) {
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        }
        return null;
    }
}
