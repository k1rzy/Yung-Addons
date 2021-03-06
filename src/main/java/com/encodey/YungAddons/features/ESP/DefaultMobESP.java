package com.encodey.YungAddons.features.ESP;

import gg.essential.universal.UChat;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.*;
import com.encodey.YungAddons.utils.RenderUtils;
import com.encodey.YungAddons.Config;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.core.pattern.NameAbbreviator;

import java.awt.*;
import java.util.ArrayList;
/**
 * @author k1rzy (encodey)
 */
public class DefaultMobESP {
    public static int radius = 14;
    public static int batradius = 20;

    public static boolean isdungeonStarted;
    private World world = Minecraft.getMinecraft().theWorld;

    @SubscribeEvent
    public void enderman(final RenderWorldLastEvent event) throws NullPointerException {
        if(world == null) return;
        try {
            if (Config.endermanesp && !isdungeonStarted) {
                for (Entity entityeman : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
                    if (entityeman instanceof EntityEnderman) {
                        double dist = entityeman.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
                        if (dist < radius)
                            RenderUtils.drawEntityBox(entityeman, Config.ESPColordefmobs, true, event.partialTicks);
                    }
                }
            }
        } catch (NullPointerException e) {
            UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");
        }
    }


    @SubscribeEvent
    public void wolf(final RenderWorldLastEvent event) {
        if(world == null) return;
        try {
            if (Config.wolfesp) {
                for (Entity entitywolf : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
                    if (entitywolf instanceof EntityWolf) {
                        double dist = entitywolf.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
                        if (dist < radius)
                            RenderUtils.drawEntityBox(entitywolf, Config.ESPColordefmobs, true, event.partialTicks);
                    }
                }
            }
        } catch (NullPointerException e) {
            UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");
        }
    }


    @SubscribeEvent
    public void spider(final RenderWorldLastEvent event) {
        if(world == null) return;
        try {
            if (Config.spideresp) {
                for (Entity entityspider : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
                    if (entityspider instanceof EntitySpider) {
                        double dist = entityspider.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
                        if (dist < radius)
                            RenderUtils.drawEntityBox(entityspider, Config.ESPColordefmobs, true, event.partialTicks);
                    }
                }
            }
        } catch (NullPointerException e) {
            UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");
        }
    }
    @SubscribeEvent
    public void cavespider(final RenderWorldLastEvent event) {
        if(world == null) return;
        try {
            if (Config.spideresp) {
                for (Entity entitycspider : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
                    if (entitycspider instanceof EntityCaveSpider) {
                        double dist = entitycspider.getDistance(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
                        if (dist < radius)
                            RenderUtils.drawEntityBox(entitycspider, Config.ESPColordefmobs, true, event.partialTicks);
                    }
                }
            }
        } catch (NullPointerException e) {
            UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");
        }
    }
}
