package com.encodey.YungAddons;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import com.encodey.YungAddons.commands.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.*;
import com.google.gson.*;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = YungAddons.MOD_ID, name = YungAddons.NAME, version = YungAddons.VERSION)
public class YungAddons
{
    public static final String MOD_ID = "yungaddons";
    public static final String NAME = "Yung Addons";
    public static final String VERSION = "1.0";
    public static final Logger logger = LogManager.getLogger("YungAddons");

    public static GuiScreen display = null;
    public static Config configFile = new Config();
    public static GuiScreen displayScreen;
    public static JsonObject response;
    public static KeyBinding[] keyBindings = new KeyBinding[0];


    @Mod.EventHandler
    public void onFMLInitialization(FMLPreInitializationEvent event) {
        File directory = new File(event.getModConfigurationDirectory(), "yaddons");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        configFile.initialize();
        ClientCommandHandler.instance.registerCommand(new addonsCommand());
        MinecraftForge.EVENT_BUS.register(new OnPlayerJoin());
        //keyBindings[0] = new KeyBinding("Open mod menu", 45, "Yung Addons");
        //for(final KeyBinding keyBinding : YungAddons.keyBindings) {
        //    ClientRegistry.registerKeyBinding(keyBinding);
        //}
    }
    @EventHandler
    public void preInit(FMLInitializationEvent event)
    {

    }
    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (display != null) {
            try {
                Minecraft.getMinecraft().displayGuiScreen(display);
            } catch (Exception e) {
                e.printStackTrace();
            }
            display = null;
        }
    }
}
