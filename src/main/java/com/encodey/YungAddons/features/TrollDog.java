package com.encodey.YungAddons.features;

import com.encodey.YungAddons.Config;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TrollDog {
    @SubscribeEvent
    public void chatCheck(final ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        if(message.startsWith("[WATCHDOG ANNOUNCEMENT]") && Config.fukdog) {
            if(Config.fukdogText == "") return;
            Minecraft.getMinecraft().thePlayer.sendChatMessage(Config.fukdogText);
        }
    }
}
