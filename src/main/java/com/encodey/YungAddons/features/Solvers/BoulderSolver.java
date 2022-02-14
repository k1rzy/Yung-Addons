package com.encodey.YungAddons.features.Solvers;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.features.ESP.DungeonChestESP;
import com.encodey.YungAddons.utils.MathUtils.bouldTils;
import com.encodey.YungAddons.utils.UtilsDanker;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoulderSolver {

    static boolean prevInBoulderRoom = false;
    static boolean inBoulderRoom = false;
    static BlockPos chest = null;
    static String boulderRoomDirection = null;
    public static List<int[]> route = new ArrayList<>();
    static int currentStep = 0;
    public static int BOULDER_COLOUR = 0xD64FC8;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        reset();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        World world = mc.theWorld;
            if (Config.boulder && DungeonChestESP.dungeonStarted && world != null && player != null) {
                // multi thread block checking
                new Thread(() -> {
                    prevInBoulderRoom = inBoulderRoom;
                    int quartzBlocksFound = 0;
                    int barriersFound = 0;
                    BlockPos plusPlusQuartz = null;
                    BlockPos minusMinusQuartz = null;
                    Iterable<BlockPos> blocks = BlockPos.getAllInBox(new BlockPos(player.posX - 25, 68, player.posZ - 25), new BlockPos(player.posX + 25, 68, player.posZ + 25));
                    // Detect boulder room
                    for (BlockPos blockPos : blocks) {
                        if (world.getBlockState(blockPos).getBlock() == Blocks.quartz_block) {
                            quartzBlocksFound++;
                            if (plusPlusQuartz == null || (blockPos.getX() >= plusPlusQuartz.getX() && blockPos.getZ() >= plusPlusQuartz.getZ())) {
                                plusPlusQuartz = blockPos;
                            }
                            if (minusMinusQuartz == null || (blockPos.getX() <= minusMinusQuartz.getX() && blockPos.getZ() <= minusMinusQuartz.getZ())) {
                                minusMinusQuartz = blockPos;
                            }
                            if (quartzBlocksFound == 8) {
                                break;
                            }
                        } else if (world.getBlockState(blockPos).getBlock() == Blocks.barrier) {
                            barriersFound++;
                        }
                    }

                    if (quartzBlocksFound == 8 && barriersFound >= 350) {
                        inBoulderRoom = true;
                        if (!prevInBoulderRoom) {
                            // Get boulder locations
                            char[][] board = new char[7][7];
                            for (int x = plusPlusQuartz.getX() - 1, iterationX = 0; iterationX < 7; x -= 3, iterationX++) {
                                for (int z = plusPlusQuartz.getZ() - 1, iterationZ = 0; iterationZ < 7; z -= 3, iterationZ++) {
                                    BlockPos boulderPos = new BlockPos(x, 66, z);
                                    if (world.getBlockState(boulderPos).getBlock() == Blocks.planks) {
                                        board[iterationX][iterationZ] = 'X';
                                    }
                                }
                            }

                            // Detect rotation of room
                            BlockPos northChest = minusMinusQuartz.add(11, -2, -2);
                            BlockPos eastChest = plusPlusQuartz.add(2, -2, -11);
                            BlockPos southChest = plusPlusQuartz.add(-11, -2, 2);
                            BlockPos westChest = minusMinusQuartz.add(-2, -2, 11);
                            if (world.getBlockState(northChest).getBlock() == Blocks.chest) {
                                boulderRoomDirection = "north";
                                chest = northChest;
                                board = bouldTils.flipVertically(bouldTils.rotateClockwise(board));
                            } else if (world.getBlockState(eastChest).getBlock() == Blocks.chest) {
                                boulderRoomDirection = "east";
                                chest = eastChest;
                                board = bouldTils.flipHorizontally(board);
                            } else if (world.getBlockState(southChest).getBlock() == Blocks.chest) {
                                boulderRoomDirection = "south";
                                chest = southChest;
                                board = bouldTils.flipHorizontally(bouldTils.rotateClockwise(board));
                            } else if (world.getBlockState(westChest).getBlock() == Blocks.chest) {
                                boulderRoomDirection = "west";
                                chest = westChest;
                                board = bouldTils.flipVertically(board);
                            } else {
                                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Solve attempt failed. Trying to solve again..."));
                                YungAddons.logger.debug("Cant solve");
                                return;
                            }
                            board = bouldTils.removeFirstRow(board);
                            if (bouldTils.hasOpenPath(board)) return;

                            long startTime = System.currentTimeMillis();
                            reset();
                            int length = bouldTils.findSolution(board, 0, route);
                            long endTime = System.currentTimeMillis();
                            System.out.println("Time elapsed: " + (endTime - startTime) + "ms, " + bouldTils.iterations + " iterations.");
                            if (length == 10) {
                                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[Yung Addons]" + EnumChatFormatting.BLUE + "No solution to puzzle found, most likely puzzle failed."));
                            } else {
                                System.out.println("Solved " + boulderRoomDirection + " facing boulder room in " + length + " steps. Path:");
                                for (int[] block : route) {
                                    System.out.println(Arrays.toString(block));
                                }
                            }
                        }
                    } else {
                        chest = null;
                        boulderRoomDirection = null;
                        inBoulderRoom = false;
                    }
                }).start();
            }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (Config.boulder && DungeonChestESP.dungeonStarted && route.size() > 0 && currentStep < route.size() && chest != null) {
            int[] currentBlockArray = route.get(currentStep);
            AxisAlignedBB currentBoulder = bouldTils.getBoulder(currentBlockArray[0], currentBlockArray[1], chest, boulderRoomDirection);
            if (currentBoulder == null) return;
            UtilsDanker.drawFilled3DBox(currentBoulder, BOULDER_COLOUR, true, false, event.partialTicks);
            char direction;
            switch (currentBlockArray[2]) {
                case 1:
                    direction = 'u';
                    break;
                case 2:
                    direction = 'd';
                    break;
                case 3:
                    direction = 'l';
                    break;
                case 4:
                    direction = 'r';
                    break;
                default:
                    return;
            }
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (Minecraft.getMinecraft().thePlayer != event.entityPlayer || !inBoulderRoom || !Config.boulder) return;

        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(event.pos).getBlock();
            if (block == Blocks.stone_button) currentStep++;
        }
    }

    static void reset() {
        route.clear();
        currentStep = 0;
        bouldTils.seenBoardStates.clear();
        bouldTils.iterations = 0;
        bouldTils.fastestSolution = 10;
    }

}