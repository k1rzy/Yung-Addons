/*package com.encodey.YungAddons.features.Mining.Warnings;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.utils.RenderText;
import com.encodey.YungAddons.utils.RenderUtils;
import com.encodey.YungAddons.utils.Utils;
import com.encodey.YungAddons.utils.UtilsFull;
import gg.essential.universal.UChat;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.*;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Map;

public class Titanium {
    private static BlockPos tita;
    private static long titaniumNotifMillis = 0;
    private static Minecraft mc;

    public static boolean spawnedTime;
    public static S23PacketBlockChange packetIn;
    public static IBlockState state = packetIn.getBlockState();

    @SubscribeEvent
    public void carpet(RenderWorldLastEvent event) throws NullPointerException {
        Minecraft mc = Minecraft.getMinecraft();
        if (!Config.titawarning) return;
        if (Config.titawarning) {
            if (Config.titawarning && mc.getCurrentServerData().serverIP.contains("hypixel.")) {
                tita = closestTita();
                try {
                    if (tita != null) {
                        spawnedTime = true;
                    }
                } catch (NullPointerException e) {
                    UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");

                }
            }
        }
    }

    private BlockPos closestTita() {
        int r = 7;
        BlockPos playerPos = Minecraft.getMinecraft().thePlayer.getPosition();
        playerPos.add(0, 1, 0);
        Vec3 playerVec = Minecraft.getMinecraft().thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, r, r);
        Vec3i vec3i2 = new Vec3i(r, r, r);
        ArrayList<Vec3> tita = new ArrayList<Vec3>();
        if (playerPos != null) {
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i2))) {
                IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
                if (state.getBlock() == Blocks.stone && state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.DIORITE_SMOOTH) {
                    tita.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
                }
            }
        }
        double smallest = 9999;
        Vec3 closest = null;
        for (Vec3 stone : tita) {
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

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if(!Config.titawarning) {
            return;
        }
        if(Config.titawarning && spawnedTime) {
            if(titaniumNotifMillis <= 0) return;

            int delta = (int)(System.currentTimeMillis() - titaniumNotifMillis);
            int notifLen = 5000;
            int fadeLen = 500;
            if(delta > 0 && delta < notifLen && event.type == RenderGameOverlayEvent.ElementType.ALL) {
                ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
                int width = scaledResolution.getScaledWidth();
                int height = scaledResolution.getScaledHeight();

                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(width / 2), (float)(height / 2), 0.0F);
                GlStateManager.scale(4.0F, 4.0F, 4.0F);

                int colour1 = 0xcc;
                int colour2 = 0xff;

                double factor = (Math.sin(delta*2*Math.PI/1000)+1)/2;
                int colour = (int)(colour1*factor + colour2*(1-factor));

                int alpha = 255;
                if(delta < fadeLen) {
                    alpha = delta*255/fadeLen;
                } else if(delta > notifLen-fadeLen) {
                    alpha = (notifLen-delta)*255/fadeLen;
                }

                if(alpha > 10) {
                    RenderText.drawStringCenteredScaledMaxWidth("Titanium spawned!", Minecraft.getMinecraft().fontRendererObj,
                            0, 0, false, width/4-20, colour | (colour << 8) | (colour << 16) | (alpha << 24));
                }


                GlStateManager.popMatrix();
                new Thread(() -> {
                    try {
                        Thread.sleep(2000L);
                        spawnedTime = false;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }).start();
        }
        }
    }
}*/
