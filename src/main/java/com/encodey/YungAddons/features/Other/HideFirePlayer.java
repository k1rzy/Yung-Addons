package com.encodey.YungAddons.features.Other;

import com.encodey.YungAddons.Config;
import ibxm.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HideFirePlayer {
    @SideOnly(Side.CLIENT)
    public void hidefire(TickEvent.PlayerTickEvent event) {
        if(!Config.hidefire) return;
        if(Config.hidefire) {
            if (event.player instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) event.player;
                entity.setFire(0);
            }
        }
    }
}
