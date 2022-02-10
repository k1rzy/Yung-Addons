package com.encodey.YungAddons.features.ESP;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.utils.RenderUtils;
import com.encodey.YungAddons.utils.UtilsDanker;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class PlayerESP {

    public static BlockPos blockEntitySpawn;

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(!Config.playeresp) return;
        if(Config.playeresp) {
            for(Entity player : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
                if(player instanceof EntityPlayer) {
                    double dist = player.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 2, Minecraft.getMinecraft().thePlayer.posZ);
                    blockEntitySpawn = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 2, Minecraft.getMinecraft().thePlayer.posZ);
                    RenderUtils.drawEntityBox(player, Color.red, false, event.partialTicks);
                    if(dist < 2 && Minecraft.getMinecraft().thePlayer.getName().contains(Minecraft.getMinecraft().getSession().getUsername())) {
                        return;
                    }
                }
            }
        }
    }
}
