package com.encodey.YungAddons.events;

import com.encodey.YungAddons.handler.ScoreboardHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkyblockJoin {
    public static boolean inSkyblock;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("SKYBLOCK")) {
                inSkyblock = true;
            }
        }
    }
}
