package com.encodey.YungAddons.features.ESP;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import gg.essential.universal.UChat;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/**
 * @author k1rzy (encodey)
 */
public class DungeonESPLoad {

    public static boolean oneMessage;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        oneMessage = false;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("The Catacombs")) {
                if(Config.dungeonchestesp && !oneMessage) {
                    UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + "ESP Loaded: Dungeon Chest");
                    oneMessage = true;
                }
            }
        }
    }
}
