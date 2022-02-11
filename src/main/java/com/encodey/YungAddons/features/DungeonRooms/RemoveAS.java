package com.encodey.YungAddons.features.DungeonRooms;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.features.ESP.DungeonChestESP;
import com.encodey.YungAddons.utils.RenderUtils;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RemoveAS {

    @SubscribeEvent
    public void as(final RenderWorldLastEvent event) throws Exception {
        try {
            if (Config.remas && DungeonChestESP.dungeonStarted) {
                for (Entity entityas : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
                    if (entityas instanceof EntityArmorStand) {
                        Minecraft.getMinecraft().theWorld.onEntityRemoved(entityas);
                        Minecraft.getMinecraft().theWorld.removeEntity(entityas);
                        entityas.setDead();
                    }
                }
            }
        } catch (Exception e) {
            UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + e + " : Please report bugs on our discord server.");
        }
    }
}
