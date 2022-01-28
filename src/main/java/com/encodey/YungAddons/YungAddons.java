package com.encodey.YungAddons;

import com.encodey.YungAddons.events.Overlay;
import com.encodey.YungAddons.events.SkyblockJoin;
import com.encodey.YungAddons.features.Solvers.BlazeSolver;
import com.encodey.YungAddons.features.Solvers.ThreeChestsSolver;
import com.encodey.YungAddons.features.Solvers.tttSolver;
import com.encodey.YungAddons.utils.UtilsDanker;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import com.encodey.YungAddons.commands.*;
import com.encodey.YungAddons.features.*;
import com.encodey.YungAddons.features.ESP.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.*;
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

    public static Configuration config;
    private final static String file = "config/yungaddons/colorconfig.cfg";

    public static final Logger logger = LogManager.getLogger("YungAddons");
    public static String titleText = "";

    public static int tick = 1;
    public static int titleTimer = -1;
    public static int tickAmount = 1;

    public static boolean showTitle;
    public static boolean yungdebug = true;

    public static GuiScreen display = null;
    public static Config configFile = new Config();
    public static ConfigNoQOL configFileOther = new ConfigNoQOL();

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
        configFileOther.initialize();
        ClientCommandHandler.instance.registerCommand(new addonsCommand());
        ClientCommandHandler.instance.registerCommand(new noqolCommand());
        MinecraftForge.EVENT_BUS.register(new OnPlayerJoin());
        MinecraftForge.EVENT_BUS.register(new AutoAPI());
        MinecraftForge.EVENT_BUS.register(new DefaultMobESP());
        MinecraftForge.EVENT_BUS.register(new GemstoneESP());
        MinecraftForge.EVENT_BUS.register(new BeaconESP());
        MinecraftForge.EVENT_BUS.register(new DungeonChestESP());
        MinecraftForge.EVENT_BUS.register(new CarpetESP());
        MinecraftForge.EVENT_BUS.register(new Reparty());
        MinecraftForge.EVENT_BUS.register(new DungeonESPLoad());
        MinecraftForge.EVENT_BUS.register(new BlazeSolver());
        MinecraftForge.EVENT_BUS.register(new tttSolver());
        MinecraftForge.EVENT_BUS.register(new SkyblockJoin());
        MinecraftForge.EVENT_BUS.register(new MessageHider());
        MinecraftForge.EVENT_BUS.register(new ThreeChestsSolver());
    }
    @EventHandler
    public void preInit(FMLInitializationEvent event)
    {

    }
    @SubscribeEvent
    public void renderPlayerInfo(Overlay event) {
        if (showTitle) {
            UtilsDanker.drawTitle(titleText);
        }
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        tickAmount++;
        if (tickAmount % 20 == 0) {
            if(player != null) {
                tickAmount = 0;
            }
        }

        if (titleTimer >= 0) {
            if (titleTimer == 0) {
                showTitle = false;
            }
            titleTimer--;
        }
    }
}
