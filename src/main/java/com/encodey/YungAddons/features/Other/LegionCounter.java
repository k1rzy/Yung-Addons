package com.encodey.YungAddons.features.Other;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.ScriptAliases;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
/**
 * @author k1rzy (encodey)
 */
public class LegionCounter {
    public static int radius = 30;
    public static int max_entities = 20;
    public static int playersIn;

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if(!Config.legioncounter) return;
        for(Entity players : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
            if(players instanceof EntityPlayer) {
                if(players == null) return;
                double dist = players.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
                if(dist > 0.5f) {
                    if(dist < max_entities) {
                        int playersIn = players.getEntityId();
                        for(int i = 0; i < playersIn; i++) {
                            ScriptAliases.addMessage("player ids: " + i);
                        }
                    }
                }
            }
        }
    }
}
