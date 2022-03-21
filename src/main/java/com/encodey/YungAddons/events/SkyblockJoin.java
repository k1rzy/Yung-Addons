package com.encodey.YungAddons.events;

import com.encodey.YungAddons.handler.ScoreboardHandler;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.nio.charset.MalformedInputException;
/**
 * @author k1rzy (encodey)
 */
public class SkyblockJoin {
    public static boolean inSkyblock;
    public static boolean isInSkyblock() {
        final ScoreObjective scoreboardObj = Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
        if (scoreboardObj == null) {
            return false;
        }
        if (ScoreboardHandler.cleanSB(scoreboardObj.getDisplayName()).startsWith("SKYBLOCK")) {
            return inSkyblock = true;
        }
        inSkyblock = false;
        return false;
    }
}
