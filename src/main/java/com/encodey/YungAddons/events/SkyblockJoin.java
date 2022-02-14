package com.encodey.YungAddons.events;

import com.encodey.YungAddons.handler.ScoreboardHandler;
import gg.essential.universal.UChat;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkyblockJoin {
    public static boolean inSkyblock;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("SKYBLOCK")) {
                inSkyblock = true;
                UChat.chat("On Skyblock");
            }
            else {
                inSkyblock = false;
            }
            if(message.contains("Welcome to Hypixel Skyblock")) {
                inSkyblock = true;
            }
        }
    }
}
