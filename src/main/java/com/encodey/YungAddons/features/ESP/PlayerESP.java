package com.encodey.YungAddons.features.ESP;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.handler.TextRenderer;
import com.encodey.YungAddons.utils.RenderUtils;
import com.encodey.YungAddons.utils.TextUtils;
import com.encodey.YungAddons.utils.UtilsDanker;
import com.encodey.YungAddons.utils.UtilsFull;
import javafx.scene.chart.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
/**
 * @author k1rzy (encodey)
 */
public class PlayerESP {

    public static BlockPos blockEntitySpawn;

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(!Config.playeresp) return;
        if(Config.playeresp) {
            for(Entity player : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
                if(player instanceof EntityPlayer) {
                    if(player != null) {
                        double dist = player.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
                        blockEntitySpawn = new BlockPos(player.posX + 1.5, player.posY + 3, player.posZ + 1.5);
                        AxisAlignedBB aabb = new AxisAlignedBB(player.posX - 0.5, player.posY + 2, player.posZ - 0.5, player.posX + 0.5, player.posY, player.posZ + 0.5);
                        if(dist > 1) {UtilsDanker.draw3DBox(aabb, 0xD64FC8, event.partialTicks);}
                    }
                }
            }
        }
    }
}
