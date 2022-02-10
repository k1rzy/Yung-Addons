package com.encodey.YungAddons;

import gg.essential.api.EssentialAPI;
import gg.essential.api.config.EssentialConfig;
import gg.essential.api.gui.EssentialGUI;
import gg.essential.universal.UDesktop;
import gg.essential.vigilance.Vigilant;
import gg.essential.loader.stage0.EssentialSetupTweaker;
import gg.essential.vigilance.data.Category;
import gg.essential.vigilance.data.JVMAnnotationPropertyCollector;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyData;
import gg.essential.vigilance.data.PropertyType;
import gg.essential.vigilance.data.SortingBehavior;
import net.java.games.input.Component;
import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import com.encodey.YungAddons.features.AutoAPI;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Config extends Vigilant {

    public static boolean isActiveGUIChange;

    public static int posX;
    public static int posY;

    Keyboard event;

    public static File configFile = new File("config/yungaddons/config.toml");
    @Property(
            type = PropertyType.BUTTON,
            category = "Yung Addons",
            subcategory = "Config",
            name = "Change GUI Buttons locations",
            description = "All overlays",
            placeholder = "Change"
    )
    public void opengui() {
        try {
            EssentialGUI.getCurrentScreen().onGuiClosed();
        }
        catch(NullPointerException e) {
            e.printStackTrace();
        }

        isActiveGUIChange = true;

        if(event.isKeyDown(Component.Identifier.Key.ESCAPE)) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.thePlayer.sendChatMessage("/yung");
        }
    }
    @Property(
            type = PropertyType.TEXT,
            category = "Yung Addons",
            subcategory = "Config",
            name = "API Key", protectedText = true,
            description = "Your Hypixel API Key"
    )
    public static String API = "";
    @Property(
            type = PropertyType.TEXT,
            category = "Yung Addons",
            subcategory = "Config",
            name = "Discord Webhook", protectedText = true,
            description = "Your discord webhook. Uses for Session ID Remover, Auction House Features"
    )
    public static String Webhook = "";
    @Property(
            type = PropertyType.SWITCH,
            category = "Yung Addons",
            subcategory = "Config",
            name = "Automatically set API Key (only after /api new)",
            description = ""
    )
    public static boolean autoAPI;
    @Property(
            type = PropertyType.SWITCH,
            category = "Yung Addons",
            subcategory = "Config",
            name = "Auto Re-party",
            description = "Automatically do /rt"
    )
    public static boolean autoRP;
    @Property(
            type = PropertyType.SWITCH,
            category = "Yung Addons",
            subcategory = "Config",
            name = "TrollDog",
            description = "Watchdog is useless ngl"
    )
    public static boolean fukdog;
    @Property(
            type = PropertyType.SWITCH,
            category = "Yung Addons",
            subcategory = "Config",
            name = "Session ID Protector",
            description = "Tells in chat sus mods (ty pizza)"
    )
    public static boolean sessionidprot;
    @Property(
            type = PropertyType.SELECTOR,
            category = "Yung Addons",
            subcategory = "Config",
            name = "Session ID Remover",
            description = "Tries to remove Session ID after closing game",
            options = { "Minecraft Password", "Minecraft UUID", "Discord Webhook" }
    )
    public static int sessionidoutexit;
    @Property(
            type = PropertyType.TEXT,
            category = "Yung Addons",
            subcategory = "Config",
            name = "TrollDog Text",
            description = ""
    )
    public static String fukdogText = "";
    @Property(
            type = PropertyType.BUTTON,
            category = "Yung Addons",
            subcategory = "Discord",
            name = "Yung Addons discord server",
            description = "Join our official discord server!",
            placeholder = "Join"
    )
    public static void discord() {
        UDesktop.browse(URI.create("https://discord.gg/NsHbtQVSEm"));
    }
    @Property(
            type = PropertyType.BUTTON,
            category = "Yung Addons",
            subcategory = "Github",
            name = "Yung Addons soruce code/all releases",
            description = "Do not download this mod on other repositories!",
            placeholder = "Repository"
    )
    public static void github() {
        UDesktop.browse(URI.create("https://github.com/k1rzy/Yung-Addons"));
    }
    @Property(
            type = PropertyType.COLOR,
            category = "ESP (!QOL!)",
            subcategory = "Colors (!PLEASE USE DIFFERENT COLORS, IF YOU CAN!)",
            name = "Default Mobs Color ESP",
            description = "Change your default mobs/dungeon mobs ESP color!"
    )
    public static Color ESPColordefmobs = new Color(255, 0, 0, 250);
    @Property(
            type = PropertyType.COLOR,
            category = "ESP (!QOL!)",
            subcategory = "Colors (!PLEASE USE DIFFERENT COLORS, IF YOU CAN!)",
            name = "Dungeon Chest ESP",
            description = "Change your dungeon chest ESP color!"
    )
    public static Color ESPColorchest = new Color(255, 0, 0, 250);
    // scuffed code bc lost files sry
    @Property(type = PropertyType.SWITCH, category = "ESP (!QOL!)", subcategory = "Other Mobs", name = "Wolf ESP", description = "Shows Wolf hitboxes")
    public static boolean wolfesp;
    @Property(type = PropertyType.SWITCH, category = "ESP (!QOL!)", subcategory = "Other Mobs", name = "Spider ESP", description = "Shows Spider hitboxes")
    public static boolean spideresp;
    @Property(type = PropertyType.SWITCH, category = "ESP (!QOL!)", subcategory = "Other Mobs", name = "Enderman ESP", description = "Shows Enderman hitboxes (Disabled in dungeons)")
    public static boolean endermanesp;
    @Property(type = PropertyType.SWITCH, category = "ESP (!QOL!)", subcategory = "Other Mobs", name = "Player ESP", description = "Your teammates are now naked!")
    public static boolean playeresp;
    @Property(type = PropertyType.SWITCH, category = "ESP (!QOL!)", subcategory = "Other blocks", name = "Dungeon Chests", description = "Shows Dungeon Chests ESP/cheesing")
    public static boolean dungeonchestesp;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Solvers", name = "Blaze Solver", description = "Shows largest/smallest blaze health")
    public static boolean blazesolver;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Solvers", name = "Tic Tac Toe Solver", description = "Shows Tic Tac Toe 'answers'")
    public static boolean tttsolver;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Solvers", name = "Three chests Solver", description = "Shows right chest")
    public static boolean chestssolver;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Solvers", name = "Creeper Beams Solver", description = "Shows connecting blocks")
    public static boolean creepersolver;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Solvers", name = "Water Board Solver", description = "Shows right levers")
    public static boolean waterboardsolver;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Solvers", name = "Boulder Solver", description = "Shows right boulders")
    public static boolean boulder;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Solvers", name = "Ice Fill Solver", description = "Shows right path")
    public static boolean icefillsolver;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Solvers", name = "Ice Path Solver", description = "Shows right silverfish path")
    public static boolean icepathsolver;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Solvers", name = "Click in Order Terminal Solver", description = "Basically Terminal Solver")
    public static boolean clickordersolver;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Score Calculator", name = "S (270 Score) Message Sender", description = "Automatically sends '270 score' message in p chat/global")
    public static boolean sscore;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Score Calculator", name = "S+ (300 Score) Message Sender", description = "Automatically sends '300 score' message in p chat/global")
    public static boolean s_score;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Score Calculator", name = "Score Calculator", description = "Shows current dungeon run score")
    public static boolean scorecalculator;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Secrets", name = "All Secrets Ping", description = "Pings when all secrets in a room are found")
    public static boolean secretsping;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Teammates", name = "Near Room Locator", description = "Shows players that are stand near blood room/puzzles/trap room/uncleared room")
    public static boolean nearroom;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Teammates", name = "Re-party auto acceptor", description = "Automatically accepts re-party")
    public static boolean rpacceptor;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Other", name = "Blood room clear Warning", description = "Pings when blood room is fully cleared")
    public static boolean bloodclearmessage;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Other", name = "Remove Armor Stands", description = "Removes Armor Stands, which is can improve fps")
    public static boolean remas;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Other", name = "Show Crypts Counter", description = "Shows crypt Counter")
    public static boolean cryptcounter;
    @Property(type = PropertyType.TEXT, category = "Dungeons", subcategory = "Other", name = "Blood room clear Message Text", description = "")
    public static String bloodclearmessagestr = "";
    @Property(type = PropertyType.COLOR, category = "Experiments", subcategory = "Colors", name = "Recolors solver slot", description = "")
    public static Color expcolor = new Color(255, 0, 0, 250);
    @Property(type = PropertyType.SWITCH, category = "Experiments", subcategory = "Solvers", name = "Experiment Solver", description = "You know what it is")
    public static boolean expersolver;
    @Property(type = PropertyType.SWITCH, category = "Slayer Helper", subcategory = "Slayer", name = "Slayer Counter", description = "Counts total killed slayer bosses")
    public static boolean slayercounter;
    @Property(type = PropertyType.SWITCH, category = "Slayer Helper", subcategory = "Slayer", name = "Inactive quest warning", description = "Show Warning when no Active Slayer Quest")
    public static boolean inactivequestwarning;
    @Property(type = PropertyType.SWITCH, category = "Slayer Helper", subcategory = "Slayer", name = "Show boss slain Time", description = "Time that you needed to kill Slayer Boss")
    public static boolean bossslaintime;
    @Property(type = PropertyType.SWITCH, category = "Slayer Helper", subcategory = "Voidgloom Seraph", name = "Beacon ESP", description = "Shows beacon ESP")
    public static boolean beaconesp;
    @Property(type = PropertyType.SWITCH, category = "Slayer Helper", subcategory = "Voidgloom Seraph", name = "Voidgloom Nukekubi ESP", description = "Shows Head things ESP")
    public static boolean headthingsesp;
    @Property(type = PropertyType.COLOR, category = "Slayer Helper", subcategory = "Voidgloom Seraph", name = "Beacon Recolor", description = "Recolors beacon")
    public static Color beaconcolor = new Color(255, 0, 0, 250);
    @Property(type = PropertyType.COLOR, category = "Slayer Helper", subcategory = "Voidgloom Seraph", name = "Nukekubi Recolor", description = "Recolors heads")
    public static Color nukekubicolor = new Color(255, 0, 0, 250);
    @Property(type = PropertyType.SWITCH, category = "Farming", subcategory = "Trackers", name = "Profit Calculator", description = "Calculating profit by farming")
    public static boolean profitfarming;
    @Property(type = PropertyType.SWITCH, category = "Farming", subcategory = "Trackers", name = "Farming Skill Exp Calculator", description = "Calculating farming exp")
    public static boolean farmingexpcalc;
    @Property(type = PropertyType.SWITCH, category = "Farming", subcategory = "Solvers", name = "Treasure Hunter Solver", description = "")
    public static boolean thuntersolver;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Wool carpet Recolor (ESP)", description = "ESP annoying wool carpets in dwarven mines")
    public static boolean carpetrecolor;
    @Property(type = PropertyType.COLOR, category = "Mining", subcategory = "Mining", name = "Wool carpet Recolor", description = "Recolors annoying wool carpets in dwarven mines")
    public static Color carpetcolor = new Color(255, 0, 0, 250);
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Fuel Tracker", description = "Shows current amount of fuel")
    public static boolean fueltracker;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Show Dwarven Mine Map", description = "Dwarven Mines Map with All Locations")
    public static boolean minemap;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Notify Events", description = "Notifies when Mines Event is Starting")
    public static boolean notifyevents;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Mute Wind Changes", description = "Mutes annoying wind sounds")
    public static boolean mutewind;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Raffle Time Warning", description = "Raffle Time")
    public static boolean rafflewarning;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Gourmand Time Warning", description = "Gourmand Time")
    public static boolean gourmandwarning;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Puzzler Solver", description = "Solves Puzzler puzzle")
    public static boolean puzzlesolver;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Show Area Waypoints", description = "All Dwarven Mines Locations")
    public static boolean areawaypoints;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Show Target Waypoints", description = "All Comms locations")
    public static boolean targerwaypoints;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Show Forge Timer", description = "Displays Forge Item end Timer")
    public static boolean forgetimer;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Show Star Cult Timer", description = "who use this lmao")
    public static boolean starculttimer;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Titanium Warning", description = "You know what it is")
    public static boolean titawarning;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Golden Goblin Warning", description = "(golden goblin)")
    public static boolean goblinwarning;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Crystal Hollows", name = "Crystal Hollows Map", description = "Shows Crystal Hollows Map")
    public static boolean chmap;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Crystal Hollows", name = "Crystal Hollows Waypoints", description = "Shows Crystal Hollows Waypoints")
    public static boolean chwaypoints;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Crystal Hollows", name = "Auto Add Location Waypoints", description = "Automatically Adding Important Locations to Waypoints")
    public static boolean chautolocate;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Crystal Hollows", name = "Mining Pass Time", description = "Displays Expiring Pass Date Time")
    public static boolean miningpasstime;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Crystal Hollows", name = "Treasure Chest Pop", description = "Pop")
    public static boolean treaschestpop;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Crystal Hollows", name = "Worm Notifier", description = "Notifies when Worm is Spawning")
    public static boolean wormnotifier;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Trackers", name = "Pet estimated exp", description = "Shows how much exp you need for the next lvl")
    public static boolean estimatedexp;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Trackers", name = "Current Pet", description = "Shows current pet")
    public static boolean currentpet;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Trackers", name = "Exp shared pets", description = "Shows exp shared pets")
    public static boolean expsharedpet;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Highlight", name = "Highlights equipped pet", description = "Highlights equiped pet in a pet menu")
    public static boolean highlighteqpet;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Highlight", name = "Highlight Exp Shared pets", description = "Highlights exp shared pets in a pet menu")
    public static boolean highlightexpshared;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Highlight", name = "Show Active Pet", description = "Displays Active Pet")
    public static boolean showactivepet;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Highlight", name = "Show Information about Active Pet", description = "Displays info about your active Pet")
    public static boolean showinfoabactivepet;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Hiders", name = "Hide Pet Candies", description = "Hides pet candies in Pet Menu")
    public static boolean hidepetcandies;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Hiders", name = "Hide Auto Pet Messages", description = "Hides Auto Pet Rules messages")
    public static boolean hideautopetmessages;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Pings", name = "Magma boss Ping", description = "")
    public static boolean magmabossping;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Pings", name = "Dark Auction Ping", description = "")
    public static boolean daping;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Pings", name = "270 Score Ping", description = "")
    public static boolean sping;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Pings", name = "300 Score Ping", description = "")
    public static boolean s_ping;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Pings", name = "Voidgloom Beacon Ping", description = "")
    public static boolean beaconping;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Pings", name = "Slayer boss spawn Ping", description = "")
    public static boolean slayerspawnping;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Pings", name = "Auction Item Sold Ping", description = "Make sure your API Key is set")
    public static boolean auctionsoldping;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Pings", name = "Jerry spawn Ping", description = "Works only if jerry is mayor")
    public static boolean jerryping;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Pings", name = "Burrow Particles Ping", description = "Pings when you so clost to burrow (particles)")
    public static boolean burrowparticleping;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Notify", name = "Notify Ability Cooldown", description = "Notifies when ability item is ready")
    public static boolean notifyabilcd;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Timers", name = "Wither Shield Timer", description = "Timer after using wither blade with Wither Shield")
    public static boolean shieldtimer;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Timers", name = "Voidgloom Beacon Timer", description = "Timer until death.")
    public static boolean beacontimer;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Timers", name = "Mining Speed Boost Timer", description = "100 Seconds Timer")
    public static boolean speedboosttimer;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Timers", name = "Endstone Protector Spawn Timer", description = "Times until golem spawns")
    public static boolean golemspawntimer;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Timers", name = "Dragon Egg Spawn Timer", description = "Times until Dragon Egg spawns, after Dragon Boss was defeated")
    public static boolean dragoneggspawn;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Timers", name = "Century Cake Timer", description = "Century Cake Expire Time")
    public static boolean caketimer;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Timers", name = "Puzzler Timer", description = "Puzzler Puzzle starts Time")
    public static boolean puzzletimer;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Other", name = "Announce Rare Drops", description = "Pings when some rare drops is dropped")
    public static boolean annraredrop;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Other", name = "Custom Chat Notifications", description = "")
    public static boolean customchat;
    @Property(type = PropertyType.SWITCH, category = "Times", subcategory = "Total time", name = "Things done times", description = "Shows total time for 'things' done (like blood room opened, terras, etc.)")
    public static boolean roomstime;
    @Property(type = PropertyType.SWITCH, category = "Hide Messages", subcategory = "Spam Hider", name = "Teleport Messages", description = "Hides any teleport messages like implosion and 'There are blocks in the way' messages")
    public static boolean implosionhide;
    @Property(type = PropertyType.SWITCH, category = "Hide Messages", subcategory = "Spam Hider", name = "Spirit Sceptre Bat", description = "Hides 'Your Spirit Sceptre hit...' messages")
    public static boolean spiritbathide;
    @Property(type = PropertyType.SWITCH, category = "Hide Messages", subcategory = "Spam Hider", name = "Midas Staff Message", description = "Hides 'Your Molten Wave hit...' messages")
    public static boolean midasmessagehide;
    @Property(type = PropertyType.SWITCH, category = "Hide Messages", subcategory = "Spam Hider", name = "Zombie Sword Heal Message", description = "Hides 'You healed for...' messages")
    public static boolean healmessage;
    @Property(type = PropertyType.SWITCH, category = "Hide Messages", subcategory = "Spam Hider", name = "Ability Cooldown Message", description = "Hides 'This ability is on cooldown for...' messages")
    public static boolean cdhide;
    @Property(type = PropertyType.SWITCH, category = "Hide Messages", subcategory = "Spam Hider", name = "No Mana Message", description = "Hides 'You do not have enough mana to do this!' messages")
    public static boolean nomanahide;
    @Property(type = PropertyType.SWITCH, category = "Hide Messages", subcategory = "Hider", name = "Hide Zero Damage Nametags", description = "Hides Zero Damage")
    public static boolean zerodamagehide;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Golden Dragon Bank Balance", description = "Displays current bank balance and damage boost, if Golden Dragon equipped")
    public static boolean gdragbank;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Show Soul flow Display", description = "Displays current Soul Flow")
    public static boolean soulflowdisplay;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Show Active Items", description = "Displays active items, like item abilities")
    public static boolean activeitems;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Stop Enderman Teleportation", description = "(Client-side) Prevents enderman teleportation")
    public static boolean endermanteleport;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Mute Enderman Sounds", description = "Mutes eman Sounds")
    public static boolean muteendermansound;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Etherwarp Outline", description = "Displays outline")
    public static boolean etherwarpoutline;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Hide Fishing Rods", description = "Hides rods from other players")
    public static boolean hiderods;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Show Expertise Kills", description = "Displays expertise kills on your rod")
    public static boolean expertisekills;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Show Cultivation Counter", description = "Displays Cultivation crops")
    public static boolean cultivcounter;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Show Legion Counter Display", description = "Displays Player Counter")
    public static boolean legioncounter;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Show Percentage in Pet Menu", description = "EXP = %")
    public static boolean percentpetmenu;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Show Item Quality in Perfect Numbers", description = "More info about this feature you can see in my Discord Server")
    public static boolean itemquality;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Quality of Life", name = "Show Milestone", description = "Displays current milestone in dungeons")
    public static boolean milestone;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Quality of Life", name = "Show Current Items On Auction House", description = "Displays items that you have on your Auction House")
    public static boolean currentitems;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Quality of Life", name = "Larger Heads", description = "Increases Head Size (1.9 style)")
    public static boolean largerheads;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Quality of Life", name = "Big Items", description = "Increases dropped items Size")
    public static boolean bigitems;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Quality of Life", name = "Hide Fire On Player", description = "Hides annoying fire")
    public static boolean hidefire;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Quality of Life", name = "Hide Lightning", description = "Hides lightning bolts")
    public static boolean hidelightning;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Quality of Life", name = "Sounds on Class Ability", description = "Plays your custom sound on Class Ability is Ready (Ignoring healing circle). More information in my discord server")
    public static boolean soundsability;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Quality of Life", name = "Sounds on Wither Key", description = "Plays your custom sound when Wither Key/Blood Key is picked up. More information in my discord server")
    public static boolean soundswitherkey;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Quality of Life", name = "Sounds on Mining Speed Boost", description = "Plays your custom sound on Mining Speed Boost is Ready. More information in my discord server")
    public static boolean soundsspeedboost;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Quality of Life", name = "Hide Hurt Effects", description = "Hides hurtcum")
    public static boolean hidehurt;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Trackers", name = "Show Skill Tracker", description = "Displays skill tracker with skills that are you currently grinding")
    public static boolean skilltracker;
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Trackers", name = "Show Exp Rate", description = "Displays skill exp rate")
    public static boolean exprate;
    @Property(type = PropertyType.SWITCH, category = "Chat", subcategory = "Chat", name = "Timestamps", description = "Timestamps in messages in chat")
    public static boolean timestamp;
    @Property(type = PropertyType.SWITCH, category = "Chat", subcategory = "Chat", name = "Blur Chat", description = "Blurs chat when inventory or minecraft menu is opened")
    public static boolean blurchat;
    @Property(type = PropertyType.SWITCH, category = "Other renders", subcategory = "Rarity", name = "Render Item Rarites", description = "Render Rarity Square in Inventory")
    public static boolean renderitemrarity;
    @Property(type = PropertyType.SWITCH, category = "Mythological Event", subcategory = "Counter", name = "Mythos Counter Display", description = "Displays mythological mobs counter")
    public static boolean mythoscounter;
    @Property(type = PropertyType.SWITCH, category = "Mythological Event", subcategory = "Hided Drops", name = "Show Rare Drops", description = "Shows hidden rare drops from mytho creatures")
    public static boolean raredropsmytho;
    @Property(type = PropertyType.SWITCH, category = "Mythological Event", subcategory = "Waypoints", name = "Burrow Waypoints", description = "Shows All burrows in a hub, re-join hub to update API")
    public static boolean burrowwaypoint;
    @Property(type = PropertyType.COLOR, category = "Mythological Event", subcategory = "Waypoints", name = "Burrow Waypoints Recolor", description = "Recolors burrow waypoints")
    public static Color waypointsburrowcolor = new Color(50, 176, 180, 250);

    public Config() {
        super(configFile, "Yung Config", new JVMAnnotationPropertyCollector(), new CustomSorting());
        initialize();
    }
}
class CustomSorting extends SortingBehavior {
    @NotNull
    @Override
    public Comparator<? super Category> getCategoryComparator() {
        return (Comparator<Category>) (o1, o2) -> 0;
    }

    @NotNull
    @Override
    public Comparator<? super Map.Entry<String, ? extends List<PropertyData>>> getSubcategoryComparator() {
        return (Comparator<Map.Entry<String, ? extends List<PropertyData>>>) (o1, o2) -> 0;
    }

    @NotNull
    @Override
    public Comparator<? super PropertyData> getPropertyComparator() {
        return (Comparator<PropertyData>) (o1, o2) -> 0;
    }
}
