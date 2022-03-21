package com.encodey.YungAddons.features.ESP;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import com.encodey.YungAddons.utils.RenderUtils;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/**
 * @author k1rzy (encodey)
 */
public class NukekubiESP {
    public static int radius = 25;
    public static boolean slayerActive;

    @SubscribeEvent
    public void onScoreboard(ClientChatReceivedEvent event) {
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Slay the boss!") && Config.headthingsesp) {
                slayerActive = true;
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if(!slayerActive) return;
        if(Config.headthingsesp && slayerActive) {
            for(Entity nukekubi : (Minecraft.getMinecraft().theWorld.loadedEntityList)) {
                if(nukekubi instanceof EntityArmorStand) {
                    if(((EntityArmorStand) nukekubi).hasNoGravity() && nukekubi.getName().contains("")) {
                        if(nukekubi != null) {
                            try {
                                double dist = nukekubi.getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                                if(dist < radius) {
                                    RenderUtils.drawEntityBox(nukekubi, Config.nukekubicolor, true, event.partialTicks);
                                }
                            }
                            catch (Exception e) {
                                YungAddons.logger.debug(e);
                            }
                        }
                    }
                }
            }
        }
    }
}
