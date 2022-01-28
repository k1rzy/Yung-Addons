package com.encodey.YungAddons.features.ESP;

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

public class GemstoneESP {

    public static BlockPos gemstone_stainedglass;

    public static boolean activated;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        gemstone_stainedglass = null;
        activated = false;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Crystal Hollows") || cleanedLine.contains("Jungle") || cleanedLine.contains("Jungle Temple") || cleanedLine.contains("Mithril Deposits") || cleanedLine.contains("Mines of Divan") || cleanedLine.contains("Goblin Holdout") || cleanedLine.contains("Goblin Queen's Den") || cleanedLine.contains("Precursor Remnants") || cleanedLine.contains("Lost Precursor City") || cleanedLine.contains("Magma Fields") || cleanedLine.contains("Khazad-dûm") || cleanedLine.contains("Fairy Grotto") || cleanedLine.contains("Dragon's Lair")) {
                activated = true;
            }
        }
    }

    @SubscribeEvent
    public void Gemstone(RenderWorldLastEvent event) throws NullPointerException {
        Minecraft mc = Minecraft.getMinecraft();
        if(!Config.gemesp && !activated) return;
        if(Config.gemesp && mc.getCurrentServerData().serverIP.contains("hypixel.") && activated) {
            gemstone_stainedglass = closestGemstone();
                try {
                    if (gemstone_stainedglass != null)
                        RenderUtils.drawBlockBox(gemstone_stainedglass, Config.ESPColorgem, true, event.partialTicks);
                }
                catch (NullPointerException e) {
                    UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");

            }
        }
    }
    private BlockPos closestGemstone() {
        int r = 6;
        BlockPos playerPos = Minecraft.getMinecraft().thePlayer.getPosition();
        playerPos.add(0, 1, 0);
        Vec3 playerVec = Minecraft.getMinecraft().thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, r, r);
        Vec3i vec3i2 = new Vec3i(r, r, r);
        ArrayList<Vec3> gems = new ArrayList<Vec3>();
        if (playerPos != null) {
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i2))) {
                IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
                if (blockState.getBlock() == Blocks.stained_glass) {
                    gems.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
                    gems.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
                    gems.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
                    gems.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
                }
            }
        }
        double smallest = 9999;
        Vec3 closest = null;
        for (Vec3 gem : gems) {
            double dist = gem.distanceTo(playerVec);
            if (dist < smallest) {
                smallest = dist;
                closest = gem;
            }
        }
        if (closest != null && smallest < 5) {
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        }
        return null;
    }
}


