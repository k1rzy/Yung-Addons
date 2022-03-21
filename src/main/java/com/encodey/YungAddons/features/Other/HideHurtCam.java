package com.encodey.YungAddons.features.Other;

import com.encodey.YungAddons.Config;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
/**
 * @author k1rzy (encodey)
 */
public class HideHurtCam {

    @SideOnly(Side.CLIENT)
    public void onTick(TickEvent.PlayerTickEvent event) {
        if(!Config.hidehurt) return;
        if(Config.hidehurt) {
            if (event.player instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) event.player;
                entity.hurtTime = 0;
            }
        }
    }
}
