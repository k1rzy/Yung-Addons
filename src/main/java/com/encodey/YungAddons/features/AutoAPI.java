package com.encodey.YungAddons.features;

import com.encodey.YungAddons.Config;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

public class AutoAPI {
    public String finalAPI;
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void getAPI(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        Minecraft mc = Minecraft.getMinecraft();
        if(!Config.autoAPI) return;
        if(message.startsWith("Your new API key is ") && Config.autoAPI) {
            String key = message.split("key is ")[1];
            Config.API = key;
            UChat.chat(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + "Your new API key is: " + key);
            return;
        }
    }
}
