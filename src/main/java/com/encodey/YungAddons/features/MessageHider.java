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
/**
 * @author k1rzy (encodey)
 */
public class MessageHider {

    public static ScaledResolution scaledResolution;

    private static long wormNotifMillis = 0;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        String messageStrip = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (Config.spiritbathide && messageStrip.startsWith("Your Spirit Sceptre hit ")) {
            event.setCanceled(true);
            return;
        }
        if (Config.midasmessagehide && messageStrip.startsWith("Your Molten Wave hit ")) {
            event.setCanceled(true);
            return;
        }
        if (Config.healmessage && (messageStrip.startsWith("You healed ") || messageStrip.contains(" healed you for "))) {
            event.setCanceled(true);
            return;
        }
        if (Config.cdhide && messageStrip.startsWith("This ability is on cooldown for ")) {
            event.setCanceled(true);
            return;
        }
        if (Config.nomanahide && messageStrip.startsWith("You do not have enough mana to do this!")) {
            event.setCanceled(true);
            return;
        }
        if (Config.guildhide && messageStrip.startsWith("Guild > ")) {
            event.setCanceled(true);
            return;
        }
        if (Config.friendhide && messageStrip.startsWith("Friend > ")) {
            event.setCanceled(true);
            return;
        }
        if(Config.hideautopetmessages && messageStrip.contains("Autopet equipped ")) {
            event.setCanceled(true);
            return;
        }
        if (Config.implosionhide) {
            if (messageStrip.startsWith("Your Implosion hit ") || messageStrip.startsWith("There are blocks in the way")) {
                event.setCanceled(true);
                return;
            }
        }
    }
}
