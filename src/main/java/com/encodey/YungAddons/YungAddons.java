package com.encodey.YungAddons;

import gg.essential.universal.UChat;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import com.encodey.YungAddons.commands.*;
import com.encodey.YungAddons.features.*;
import com.encodey.YungAddons.features.ESP.*;
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
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.io.File;

@Mod(modid = YungAddons.MOD_ID, name = YungAddons.NAME, version = YungAddons.VERSION)
public class YungAddons
{
    public static final String MOD_ID = "yungaddons";
    public static final String NAME = "Yung Addons";
    public static final String VERSION = "1.0";

    public static String MAIN_COLOUR;
    public static String ERROR_COLOUR;
    public static String DELIMITER_COLOUR;

    public static final Logger logger = LogManager.getLogger("YungAddons");

    public static int tick = 1;
    public static int titleTimer = -1;
    public static int tickAmount = 1;

    public static boolean showTitle;
    public static boolean yungdebug = true;

    public static GuiScreen display = null;
    public static Config configFile = new Config();


    @Mod.EventHandler
    public void onFMLInitialization(FMLPreInitializationEvent event) {
        File directory = new File(event.getModConfigurationDirectory(), "yungaddons");
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
        MinecraftForge.EVENT_BUS.register(new AutoAPI());
        MinecraftForge.EVENT_BUS.register(new DefaultMobESP());
        MinecraftForge.EVENT_BUS.register(new GemstoneESP());
        MinecraftForge.EVENT_BUS.register(new BeaconESP());
        MinecraftForge.EVENT_BUS.register(new DungeonChestESP());
        MinecraftForge.EVENT_BUS.register(new SlayerESP());
        MinecraftForge.EVENT_BUS.register(new CarpetESP());
        MinecraftForge.EVENT_BUS.register(new Reparty());
        MinecraftForge.EVENT_BUS.register(new DungeonESPLoad());
    }
    @EventHandler
    public void preInit(FMLInitializationEvent event)
    {

    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        tickAmount++;

        if (titleTimer >= 0) {
            if (titleTimer == 0) {
                showTitle = false;
            }
            titleTimer--;
        }
    }
}
