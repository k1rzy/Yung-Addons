package com.encodey.YungAddons.features;

import com.encodey.YungAddons.events.SkyblockJoin;
import com.encodey.YungAddons.Config;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MessageHider {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!SkyblockJoin.inSkyblock) return;
        if (message.contains(":")) return;

        if (Config.spiritbathide && message.contains("Your Spirit Sceptre hit ")) {
            event.setCanceled(true);
            return;
        }
        if (Config.midasmessagehide && message.contains("Your Molten Wave hit ")) {
            event.setCanceled(true);
            return;
        }
        if (Config.healmessage && message.contains(" health!") && (message.contains("You healed ") || message.contains(" healed you for "))) {
            event.setCanceled(true);
            return;
        }
        if (Config.cdhide && message.contains("This ability is on cooldown for ")) {
            event.setCanceled(true);
            return;
        }
        if (Config.nomanahide && message.contains("You do not have enough mana to do this!")) {
            event.setCanceled(true);
            return;
        }
        if (Config.implosionhide) {
            if (message.contains("Your Implosion hit ") || message.contains("There are blocks in the way")) {
                event.setCanceled(true);
                return;
            }
        }
    }

}
