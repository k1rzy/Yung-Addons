package com.encodey.YungAddons.features.Other;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class LegionCounter {
    public static int radius = 30;
    public static int max_entities = 20;
    public static int playersIn;

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        for(Entity players : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
            if(players instanceof EntityPlayer) {
                if(players == null) return;
                double dist = players.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
                if(dist > 0.5f) {
                    if(dist < max_entities) {

                    }
                }
            }
        }
    }
}
