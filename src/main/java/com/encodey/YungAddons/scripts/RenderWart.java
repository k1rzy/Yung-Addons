package com.encodey.YungAddons.scripts;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.utils.RenderUtils;
import com.encodey.YungAddons.utils.UtilsDanker;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class RenderWart {
    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        if(world == null) return;
        if(Minecraft.getMinecraft().objectMouseOver == null || Minecraft.getMinecraft().objectMouseOver.getBlockPos() == null) { return; }
        if(!Config.wartesp) return;
        if(Config.nwscript || Config.fastbreak) {
            BlockPos pos1block = mc.objectMouseOver.getBlockPos();
            @NotNull BlockPos pos2block = mc.objectMouseOver.getBlockPos();
            @NotNull BlockPos pos3block = mc.objectMouseOver.getBlockPos();
            @NotNull BlockPos pos4block = mc.objectMouseOver.getBlockPos();

            if(mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() != Blocks.nether_wart) return;
            RenderUtils.drawBlockBox(pos1block, Color.CYAN, true, event.partialTicks);
            BlockPos blocks2;
            BlockPos blocks3;
            BlockPos blocks4;
            EnumFacing facing = mc.thePlayer.getHorizontalFacing();
            switch (ActualScript.facingRow) {
                case WEST: {
                    pos2block = new BlockPos(pos2block.getX() - 1, pos2block.getY(), pos2block.getZ());
                    pos3block = new BlockPos(pos3block.getX() - 2, pos3block.getY(), pos3block.getZ());
                    pos4block = new BlockPos(pos4block.getX() - 3, pos4block.getY(), pos3block.getZ());
                    if(pos2block != null && mc.theWorld.getBlockState(pos2block).getBlock() == Blocks.nether_wart) {
                        RenderUtils.drawBlockBox(pos2block, Color.BLUE, true, event.partialTicks);
                    }
                    if(pos3block != null && mc.theWorld.getBlockState(pos3block).getBlock() == Blocks.nether_wart) {
                        RenderUtils.drawBlockBox(pos3block, Color.BLUE, true, event.partialTicks);
                    }
                    if(pos4block != null && mc.theWorld.getBlockState(pos4block).getBlock() == Blocks.nether_wart) {
                        RenderUtils.drawBlockBox(pos4block, Color.BLUE, true, event.partialTicks);
                    }
                    break;
                }
                case EAST: {
                    pos2block = new BlockPos(pos2block.getX() + 1, pos2block.getY(), pos2block.getZ());
                    pos3block = new BlockPos(pos3block.getX() + 2, pos3block.getY(), pos3block.getZ());
                    pos4block = new BlockPos(pos4block.getX() + 3, pos4block.getY(), pos4block.getZ());
                    if(pos2block != null && mc.theWorld.getBlockState(pos2block).getBlock() == Blocks.nether_wart) {
                        RenderUtils.drawBlockBox(pos2block, Color.BLUE, true, event.partialTicks);
                    }
                    if(pos3block != null && mc.theWorld.getBlockState(pos3block).getBlock() == Blocks.nether_wart) {
                        RenderUtils.drawBlockBox(pos3block, Color.BLUE, true, event.partialTicks);
                    }
                    if(pos4block != null && mc.theWorld.getBlockState(pos4block).getBlock() == Blocks.nether_wart) {
                        RenderUtils.drawBlockBox(pos4block, Color.BLUE, true, event.partialTicks);
                    }
                    break;
                }
                case NORTH: {
                    pos2block = new BlockPos(pos2block.getX(), pos2block.getY(), pos2block.getZ() - 1);
                    pos3block = new BlockPos(pos3block.getX(), pos3block.getY(), pos3block.getZ() - 2);
                    pos4block = new BlockPos(pos4block.getX(), pos4block.getY(), pos4block.getZ() - 3);
                    if(pos2block != null && mc.theWorld.getBlockState(pos2block).getBlock() == Blocks.nether_wart) {
                        RenderUtils.drawBlockBox(pos2block, Color.BLUE, true, event.partialTicks);
                    }
                    if(pos3block != null && mc.theWorld.getBlockState(pos3block).getBlock() == Blocks.nether_wart) {
                        RenderUtils.drawBlockBox(pos3block, Color.BLUE, true, event.partialTicks);
                    }
                    if(pos4block != null && mc.theWorld.getBlockState(pos4block).getBlock() == Blocks.nether_wart) {
                        RenderUtils.drawBlockBox(pos4block, Color.BLUE, true, event.partialTicks);
                    }
                    break;
                }
                case SOUTH: {
                    pos2block = new BlockPos(pos2block.getX(), pos2block.getY(), pos2block.getZ() + 1);
                    pos3block = new BlockPos(pos3block.getX(), pos3block.getY(), pos3block.getZ() + 2);
                    pos4block = new BlockPos(pos4block.getX(), pos4block.getY(), pos4block.getZ() + 3);
                    if(pos2block != null && mc.theWorld.getBlockState(pos2block).getBlock() == Blocks.nether_wart) {
                        RenderUtils.drawBlockBox(pos2block, Color.BLUE, true, event.partialTicks);
                    }
                    if(pos3block != null && mc.theWorld.getBlockState(pos3block).getBlock() == Blocks.nether_wart) {
                        RenderUtils.drawBlockBox(pos3block, Color.BLUE, true, event.partialTicks);
                    }
                    if(pos4block != null && mc.theWorld.getBlockState(pos4block).getBlock() == Blocks.nether_wart) {
                        RenderUtils.drawBlockBox(pos4block, Color.BLUE, true, event.partialTicks);
                    }
                    break;
                }
            }
        }
    }
}
