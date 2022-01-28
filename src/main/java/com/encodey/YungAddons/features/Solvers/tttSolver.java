package com.encodey.YungAddons.features.Solvers;

import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.features.*;
import com.encodey.YungAddons.features.ESP.DungeonChestESP;
import com.encodey.YungAddons.utils.UtilsDanker;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

public class tttSolver {

    static AxisAlignedBB correctTicTacToeButton = null;

    public static int getBestMove(char[][] board) {
        HashMap<Integer, Integer> moves = new HashMap<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != '\0') continue;
                board[row][col] = 'O';
                int score = minimax(board, false, 0);
                board[row][col] = '\0';
                moves.put(row * 3 + col + 1, score);
            }
        }
        return Collections.max(moves.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static boolean hasMovesLeft(char[][] board) {
        for (char[] rows : board) {
            for (char col : rows) {
                if (col == '\0') return true;
            }
        }
        return false;
    }

    public static int getBoardRanking(char[][] board) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][0] == board[row][2]) {
                if (board[row][0] == 'X') {
                    return -10;
                } else if (board[row][0] == 'O') {
                    return 10;
                }
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[0][col] == board[2][col]) {
                if (board[0][col] == 'X') {
                    return -10;
                } else if (board[0][col] == 'O') {
                    return 10;
                }
            }
        }

        if (board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            if (board[0][0] == 'X') {
                return -10;
            } else if (board[0][0] == 'O') {
                return 10;
            }
        } else if (board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            if (board[0][2] == 'X') {
                return -10;
            } else if (board[0][2] == 'O') {
                return 10;
            }
        }

        return 0;
    }

    public static int minimax(char[][] board, boolean max, int depth) {
        int score = getBoardRanking(board);
        if (score == 10 || score == -10) return score;
        if (!hasMovesLeft(board)) return 0;

        if (max) {
            int bestScore = -1000;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == '\0') {
                        board[row][col] = 'O';
                        bestScore = Math.max(bestScore, minimax(board, false, depth + 1));
                        board[row][col] = '\0';
                    }
                }
            }
            return bestScore - depth;
        } else {
            int bestScore = 1000;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == '\0') {
                        board[row][col] = 'X';
                        bestScore = Math.min(bestScore, minimax(board, true, depth + 1));
                        board[row][col] = '\0';
                    }
                }
            }
            return bestScore + depth;
        }
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayerSP player = mc.thePlayer;
        if (YungAddons.tickAmount % 20 == 0) {
            if (Config.tttsolver && DungeonChestESP.dungeonStarted && world != null && player != null) {
                correctTicTacToeButton = null;
                AxisAlignedBB aabb = new AxisAlignedBB(player.posX - 6, player.posY - 6, player.posZ - 6, player.posX + 6, player.posY + 6, player.posZ + 6);
                List<EntityItemFrame> itemFrames = world.getEntitiesWithinAABB(EntityItemFrame.class, aabb);
                List<EntityItemFrame> itemFramesWithMaps = new ArrayList<>();
                for (EntityItemFrame itemFrame : itemFrames) {
                    ItemStack item = itemFrame.getDisplayedItem();
                    if (item == null || !(item.getItem() instanceof ItemMap)) continue;
                    MapData mapData = ((ItemMap) item.getItem()).getMapData(item, world);
                    if (mapData == null) continue;

                    itemFramesWithMaps.add(itemFrame);
                }

                // Only run when it's your turn
                if (itemFramesWithMaps.size() != 9 && itemFramesWithMaps.size() % 2 == 1) {
                    char[][] board = new char[3][3];
                    BlockPos leftmostRow = null;
                    int sign = 1;
                    char facing = 'X';
                    for (EntityItemFrame itemFrame : itemFramesWithMaps) {
                        ItemStack map = itemFrame.getDisplayedItem();
                        MapData mapData = ((ItemMap) map.getItem()).getMapData(map, world);

                        // Find position on board
                        // I mixed up row and column here and I'm too lazy to fix it
                        int row = 0;
                        int column;
                        sign = 1;

                        if (itemFrame.facingDirection == EnumFacing.SOUTH || itemFrame.facingDirection == EnumFacing.WEST) {
                            sign = -1;
                        }

                        BlockPos itemFramePos = new BlockPos(itemFrame.posX, Math.floor(itemFrame.posY), itemFrame.posZ);
                        for (int i = 2; i >= 0; i--) {
                            int realI = i * sign;
                            BlockPos blockPos = itemFramePos;
                            if (itemFrame.posX % 0.5 == 0) {
                                blockPos = itemFramePos.add(realI, 0, 0);
                            } else if (itemFrame.posZ % 0.5 == 0) {
                                blockPos = itemFramePos.add(0, 0, realI);
                                facing = 'Z';
                            }
                            Block block = world.getBlockState(blockPos).getBlock();
                            if (block == Blocks.air || block == Blocks.stone_button) {
                                leftmostRow = blockPos;
                                row = i;
                                break;
                            }
                        }

                        if (itemFrame.posY == 72.5) {
                            column = 0;
                        } else if (itemFrame.posY == 71.5) {
                            column = 1;
                        } else if (itemFrame.posY == 70.5) {
                            column = 2;
                        } else {
                            continue;
                        }

                        int colourInt = mapData.colors[8256] & 255;
                        if (colourInt == 114) {
                            board[column][row] = 'X';
                        } else if (colourInt == 33) {
                            board[column][row] = 'O';
                        }
                    }
                    System.out.println("Board: " + Arrays.deepToString(board));
                    int bestMove = getBestMove(board) - 1;
                    System.out.println("Best move slot: " + bestMove);
                    if (leftmostRow != null) {
                        double drawX = facing == 'X' ? leftmostRow.getX() - sign * (bestMove % 3) : leftmostRow.getX();
                        double drawY = 72 - Math.floor(bestMove / 3);
                        double drawZ = facing == 'Z' ? leftmostRow.getZ() - sign * (bestMove % 3) : leftmostRow.getZ();

                        correctTicTacToeButton = new AxisAlignedBB(drawX, drawY, drawZ, drawX + 1, drawY + 1, drawZ + 1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (Config.tttsolver && correctTicTacToeButton != null) {
            UtilsDanker.draw3DBox(correctTicTacToeButton, 0x40FF40, event.partialTicks);
        }
    }
}
