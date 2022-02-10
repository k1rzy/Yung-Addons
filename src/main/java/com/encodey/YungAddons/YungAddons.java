package com.encodey.YungAddons;

import com.encodey.YungAddons.Mining.FuelTracker;
import com.encodey.YungAddons.Mining.RaffleTimeWarning;
import com.encodey.YungAddons.events.Overlay;
import com.encodey.YungAddons.events.SkyblockJoin;
import com.encodey.YungAddons.features.DungeonRooms.BloodClear;
import com.encodey.YungAddons.features.Experiments.ExpSolver;
import com.encodey.YungAddons.features.Mining.CrystalHollows.AutoLocWaypoints;
import com.encodey.YungAddons.features.Mining.CrystalHollows.MiningPassTime;
import com.encodey.YungAddons.features.Mining.CrystalHollows.WormNotifier;
import com.encodey.YungAddons.features.Mining.Warnings.GoldenGoblin;
//import com.encodey.YungAddons.features.Mining.Warnings.Titanium;
import com.encodey.YungAddons.features.Misc.Timers.Beacon;
import com.encodey.YungAddons.features.Misc.Timers.WitherShield;
import com.encodey.YungAddons.features.Other.Sounds.SoundClassAbil;
import com.encodey.YungAddons.features.Other.Sounds.SoundWitherKey;
import com.encodey.YungAddons.features.Other.Sounds.SoundsSpeedBoost;
import com.encodey.YungAddons.features.Slayer.BossSlainTime;
import com.encodey.YungAddons.features.Solvers.BlazeSolver;
import com.encodey.YungAddons.features.Solvers.PuzzlerSolver;
import com.encodey.YungAddons.features.Solvers.ThreeChestsSolver;
import com.encodey.YungAddons.features.Solvers.tttSolver;
import com.encodey.YungAddons.features.Timers.StarCultTimer;
import com.encodey.YungAddons.features.Waypoints.AreaWaypoints;
import com.encodey.YungAddons.utils.Utils;
import com.encodey.YungAddons.utils.UtilsDanker;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gg.essential.universal.UChat;
import ibxm.Player;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.sound.SoundEvent;
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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.*;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

@Mod(modid = YungAddons.MOD_ID, name = YungAddons.NAME, version = YungAddons.VERSION)
public class YungAddons
{
    public static final String MOD_ID = "yungaddons";
    public static final String NAME = "Yung Addons";
    public static final String VERSION = "1.1";

    public static final String modMessage = EnumChatFormatting.AQUA + "[Yung Addons] ";;
    public static Configuration config;
    private final static String file = "config/yungaddons/colorconfig.cfg";

    public static final Logger logger = LogManager.getLogger("YungAddons");
    public static String titleText = "";

    public static int tick = 1;
    public static int titleTimer = -1;
    public static int tickAmount = 1;
    private static int size;

    public static ConfigValues configValues;

    public static YungAddons INSTANCE = null;

    public static boolean showTitle;
    public static boolean yungdebug = true;

    private static boolean finalSoundKey = true;

    public static GuiScreen display = null;
    public static Config configFile = new Config();
    public static ConfigNoQOL configFileOther = new ConfigNoQOL();

    public static JsonObject response;

    @Mod.EventHandler
    public void onFMLInitialization(FMLPreInitializationEvent event) {
        File directory = new File(event.getModConfigurationDirectory(), "yungaddons");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            response = Utils.getJson("https://gist.githubusercontent.com/k1rzy/31d21b85b85456901ae6ce13aff10d4c/raw/0f99624e08a65c2bd49adce5123f15ef9b45aced/yungaddons").getAsJsonObject();
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        File sound1 = new File("config/yungaddons/soundwither");
        if(!sound1.exists()) {
            sound1.mkdirs();
        }
        File sound2 = new File("config/yungaddons/soundability");
        if(!sound2.exists()) {
            sound2.mkdirs();
        }
        File sound3 = new File("config/yungaddons/soundspeedboost");
        if(!sound3.exists()) {
            sound3.mkdirs();
        }
        File sound4 = new File("config/yungaddons/soundblood");
        if(!sound4.exists()) {
            sound4.mkdirs();
        }
        File sound5 = new File("config/yungaddons/soundnotify");
        if(!sound5.exists()) {
            sound5.mkdirs();
        }
        configFile.initialize();
        configFileOther.initialize();
        ClientCommandHandler.instance.registerCommand(new addonsCommand());
        ClientCommandHandler.instance.registerCommand(new noqolCommand());
        MinecraftForge.EVENT_BUS.register(new OnPlayerJoin());
        MinecraftForge.EVENT_BUS.register(new AutoAPI());
        MinecraftForge.EVENT_BUS.register(new DefaultMobESP());
        MinecraftForge.EVENT_BUS.register(new BeaconESP());
        MinecraftForge.EVENT_BUS.register(new DungeonChestESP());
        MinecraftForge.EVENT_BUS.register(new CarpetESP());
        MinecraftForge.EVENT_BUS.register(new Reparty());
        MinecraftForge.EVENT_BUS.register(new DungeonESPLoad());
        MinecraftForge.EVENT_BUS.register(new BlazeSolver());
        MinecraftForge.EVENT_BUS.register(new tttSolver());
        MinecraftForge.EVENT_BUS.register(new SkyblockJoin());
        MinecraftForge.EVENT_BUS.register(new MessageHider());
        MinecraftForge.EVENT_BUS.register(new MiningPassTime());
        MinecraftForge.EVENT_BUS.register(new StarCultTimer());
        MinecraftForge.EVENT_BUS.register(new AreaWaypoints());
        MinecraftForge.EVENT_BUS.register(new GoldenGoblin());
        MinecraftForge.EVENT_BUS.register(new FuelTracker());
        MinecraftForge.EVENT_BUS.register(new NukekubiESP());
        MinecraftForge.EVENT_BUS.register(new ExpSolver());
        MinecraftForge.EVENT_BUS.register(new RaffleTimeWarning());
	    MinecraftForge.EVENT_BUS.register(new SoundWitherKey());
        MinecraftForge.EVENT_BUS.register(new SoundClassAbil());
        MinecraftForge.EVENT_BUS.register(new SoundsSpeedBoost());
        MinecraftForge.EVENT_BUS.register(new TrollDog());
        MinecraftForge.EVENT_BUS.register(new BossSlainTime());
        MinecraftForge.EVENT_BUS.register(new WormNotifier());
        MinecraftForge.EVENT_BUS.register(new PuzzlerSolver());
        MinecraftForge.EVENT_BUS.register(new WitherShield());
        MinecraftForge.EVENT_BUS.register(new BloodClear());
        MinecraftForge.EVENT_BUS.register(new AutoLocWaypoints());
        MinecraftForge.EVENT_BUS.register(new Beacon());
        MinecraftForge.EVENT_BUS.register(new PlayerESP());
    }
    @EventHandler
    public void preInit(FMLInitializationEvent event)
    {
        INSTANCE = this;
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
