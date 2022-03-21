package com.encodey.YungAddons.utils;

import com.encodey.YungAddons.Config;
import net.minecraft.client.*;
import java.awt.*;
import java.net.URL;
import java.io.*;

public class HookExecute {
    public static void send(String title, String name, String value, boolean inline) {
        try {
            final HookUtils webhook = new HookUtils(Config.Webhook);
            webhook.setAvatarUrl("https://cdn.discordapp.com/attachments/939446037934919692/954458870145773608/java-duke-logo.png");
            webhook.setUsername("Script log");
            webhook.setTts(false);
            webhook.addEmbed(new HookUtils.EmbedObject().setTitle(title).addField(name, value, inline));
            webhook.execute();
        }
        catch (Exception ex) {}
    }
}
