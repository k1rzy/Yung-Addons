package com.encodey.YungAddons.features;

import com.encodey.YungAddons.events.SkyblockJoin;
import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.utils.RenderText;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MessageHider {

    public static ScaledResolution scaledResolution;

    private static long wormNotifMillis = 0;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();

        if (Config.spiritbathide && message.startsWith("Your Spirit Sceptre hit ")) {
            event.setCanceled(true);
            return;
        }
        if (Config.midasmessagehide && message.startsWith("Your Molten Wave hit ")) {
            event.setCanceled(true);
            return;
        }
        if (Config.healmessage && (message.startsWith("You healed ") || message.contains(" healed you for "))) {
            event.setCanceled(true);
            return;
        }
        if (Config.cdhide && message.startsWith("This ability is on cooldown for ")) {
            event.setCanceled(true);
            return;
        }
        if (Config.nomanahide && message.startsWith("You do not have enough mana to do this!")) {
            wormNotifMillis = System.currentTimeMillis();
            event.setCanceled(true);
            return;
        }
        if (Config.implosionhide) {
            if (message.startsWith("Your Implosion hit ") || message.startsWith("There are blocks in the way")) {
                event.setCanceled(true);
                return;
            }
        }
    }
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (!Config.wormnotifier) {
            return;
        }
        if (Config.wormnotifier) {
            try {
                if (wormNotifMillis <= 0) return;

                int delta = (int) (System.currentTimeMillis() - wormNotifMillis);
                int notifLen = 5000;
                int fadeLen = 500;
                if (delta > 0 && delta < notifLen && event.type == RenderGameOverlayEvent.ElementType.ALL) {
                    ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
                    int width = scaledResolution.getScaledWidth();
                    int height = scaledResolution.getScaledHeight();

                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float) (width / 2), (float) (height / 2), 0.0F);
                    GlStateManager.scale(4.0F, 4.0F, 4.0F);

                    int colour1 = 0x00;
                    int colour2 = 0xee;

                    double factor = (Math.sin(delta * 2 * Math.PI / 1000) + 1) / 2;
                    int colour = (int) (colour1 * factor + colour2 * (1 - factor));

                    int alpha = 255;
                    if (delta < fadeLen) {
                        alpha = delta * 255 / fadeLen;
                    } else if (delta > notifLen - fadeLen) {
                        alpha = (notifLen - delta) * 255 / fadeLen;
                    }

                    if (alpha > 10) {
                        RenderText.drawStringCenteredScaledMaxWidth("Worm spawned!", Minecraft.getMinecraft().fontRendererObj,
                                0, 0, true, width / 4 - 20, colour | (colour << 8) | (colour << 16) | (alpha << 24));
                    }


                    GlStateManager.popMatrix();
                }
            } catch (Exception e) {
                UChat.chat(e);
            }
        }
    }

}
