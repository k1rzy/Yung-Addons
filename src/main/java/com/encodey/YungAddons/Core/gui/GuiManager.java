package com.encodey.YungAddons.Core.gui;

import java.io.*;

import com.encodey.YungAddons.ConfigFile;
import com.encodey.YungAddons.YungAddons;
import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;


public class GuiManager extends ConfigFile
{
    public static final Map<String, GuiLocation> GUIPOSITIONS = new HashMap<String, GuiLocation>();;
    public static final List<GuiElement> elements = new ArrayList<GuiElement>();
    public static final File configFile = new File(YungAddons.modDir, "guipositions.json");

    public GuiManager() {
        super(GuiManager.configFile);
    }

    public static void registerElement(final GuiElement e) {
        GuiManager.elements.add(e);
    }

    @Override
    public void read() {
        try (final FileReader in = new FileReader(GuiManager.configFile)) {
            final JsonObject file = (JsonObject)YungAddons.gson.fromJson((Reader)in, (Class)JsonObject.class);
            for (final Map.Entry<String, JsonElement> e : file.entrySet()) {
                GuiManager.GUIPOSITIONS.put(e.getKey(), new GuiLocation(e.getValue().getAsJsonObject().get("x").getAsFloat(), e.getValue().getAsJsonObject().get("y").getAsFloat()));
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void saveConfig() {
        for (final GuiElement e : GuiManager.elements) {
            GuiManager.GUIPOSITIONS.put(e.name, e.pos);
        }
        ConfigFile.write(GuiManager.GUIPOSITIONS, GuiManager.configFile);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
        if (Minecraft.getMinecraft().theWorld == null || !(Minecraft.getMinecraft().ingameGUI instanceof GuiIngameForge) || Minecraft.getMinecraft().currentScreen instanceof LocationEditGui || event.type != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        for (final GuiElement e : GuiManager.elements) {
            e.render();
        }
    }
}

