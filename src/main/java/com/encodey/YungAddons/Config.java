package com.encodey.YungAddons;

import gg.essential.universal.UDesktop;
import gg.essential.vigilance.Vigilant;
import gg.essential.loader.stage0.EssentialSetupTweaker;
import gg.essential.vigilance.data.Category;
import gg.essential.vigilance.data.JVMAnnotationPropertyCollector;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyData;
import gg.essential.vigilance.data.PropertyType;
import gg.essential.vigilance.data.SortingBehavior;
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

public class Config extends Vigilant{
    public static File configFile = new File("config/yungaddons/config.toml");
    @Property(
            type = PropertyType.TEXT,
            category = "Yung Addons",
            subcategory = "Config",
            name = "API Key", protectedText = true,
            description = "Your Hypixel API Key"
    )
    public static String API = "";
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
        UDesktop.browse(URI.create("https://discord.gg/Ear43ZKzZu"));
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
            name = "Gemstone Color ESP",
            description = "Change your stained glass ESP color!"
    )
    public static Color ESPColorgem = new Color(255, 0, 0, 250);
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
            name = "Player ESP",
            description = "Change your player ESP color!"
    )
    public static Color ESPPlayerColor = new Color(255, 0, 0, 250);
    @Property(
            type = PropertyType.COLOR,
            category = "ESP (!QOL!)",
            subcategory = "Colors (!PLEASE USE DIFFERENT COLORS, IF YOU CAN!)",
            name = "Dungeon Chest ESP",
            description = "Change your dungeon chest ESP color!"
    )
    public static Color ESPColorchest = new Color(255, 0, 0, 250);
    @Property(
            type = PropertyType.SWITCH,
            category = "ESP (!QOL!)",
            subcategory = "Full Gemstone Block",
            name = "Gemstone ESP",
            description = "Shows stained glass (gemstones) ESP"
    )
    public static boolean gemesp;
    @Property(type = PropertyType.SWITCH, category = "ESP (!QOL!)", subcategory = "Other Mobs", name = "Wolf ESP", description = "Shows Wolf hitboxes")
    public static boolean wolfesp;
    @Property(type = PropertyType.SWITCH, category = "ESP (!QOL!)", subcategory = "Other Mobs", name = "Spider ESP", description = "Shows Spider hitboxes")
    public static boolean spideresp;
    @Property(type = PropertyType.SWITCH, category = "ESP (!QOL!)", subcategory = "Other Mobs", name = "Enderman ESP", description = "Shows Enderman hitboxes (Disabled in dungeons)")
    public static boolean endermanesp;
    @Property(type = PropertyType.SWITCH, category = "ESP (!QOL!)", subcategory = "Other Mobs", name = "Bat ESP", description = "Shows Bat hitboxes")
    public static boolean batesp;
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
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Other", name = "Things done times", description = "Shows total time for 'things' done (like blood room opened, terras, etc.)")
    public static boolean roomstime;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Other", name = "Blood room clear Message", description = "Pings when blood room is fully cleared")
    public static boolean bloodclearmessage;
    @Property(type = PropertyType.SWITCH, category = "Dungeons", subcategory = "Other", name = "Blood room clear Message, 2", description = "")
    public static String bloodclearmessagestr = "";
    @Property(type = PropertyType.SWITCH, category = "Slayer Helper", subcategory = "Voidgloom Seraph", name = "Beacon ESP", description = "Shows beacon ESP")
    public static boolean beaconesp;
    @Property(type = PropertyType.SWITCH, category = "Slayer Helper", subcategory = "Voidgloom Seraph", name = "Voidgloom Summon Head Things ESP", description = "Shows Head things ESP")
    public static boolean headthingsesp;
    @Property(type = PropertyType.COLOR, category = "Slayer Helper", subcategory = "Voidgloom Seraph", name = "Beacon Recolor", description = "Recolors beacon")
    public static Color beaconcolor = new Color(255, 0, 0, 250);
    @Property(type = PropertyType.SWITCH, category = "Farming", subcategory = "Trackers", name = "Profit Calculator", description = "Calculating profit by farming")
    public static boolean profitfarming;
    @Property(type = PropertyType.SWITCH, category = "Farming", subcategory = "Trackers", name = "Farming Skill Exp Calculator", description = "Calculating farming exp")
    public static boolean farmingexpcalc;
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Wool carpet Recolor (ESP)", description = "ESP annoying wool carpets in dwarven mines")
    public static boolean carpetrecolor;
    @Property(type = PropertyType.COLOR, category = "Mining", subcategory = "Mining", name = "Wool carpet Recolor", description = "Recolors annoying wool carpets in dwarven mines")
    public static Color carpetcolor = new Color(255, 0, 0, 250);
    @Property(type = PropertyType.SWITCH, category = "Mining", subcategory = "Mining", name = "Fuel Tracker", description = "Shows current amount of fuel")
    public static boolean fueltracker;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Trackers", name = "Pet estimated exp", description = "Shows how much exp you need for the next lvl")
    public static boolean estimatedexp;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Trackers", name = "Current Pet", description = "Shows current pet")
    public static boolean currentpet;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Trackers", name = "Exp shared pets", description = "Shows exp shared pets")
    public static boolean expsharedpet;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Highlight", name = "Highlights equiped pet", description = "Highlights equiped pet in a pet menu")
    public static boolean highlighteqpet;
    @Property(type = PropertyType.SWITCH, category = "Pets", subcategory = "Highlight", name = "Highlight Exp Shared pets", description = "Highlights exp shared pets in a pet menu")
    public static boolean highlightexpshared;
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
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Pings", name = "Auction Item Sold Ping", description = "Make sure your API Key is setted")
    public static boolean auctionsoldping;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Pings", name = "Jerry spawn Ping", description = "Works only if jerry is mayor")
    public static boolean jerryping;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Timers", name = "Wither Shield Timer", description = "Timer after using wither blade with Wither Shield")
    public static boolean shieldtimer;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Timers", name = "Voidgloom Beacon Timer", description = "Timer until death.")
    public static boolean beacontimer;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Timers", name = "Mining Speed Boost Timer", description = "100 Seconds Timer")
    public static boolean speedboosttimer;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Timers", name = "Endstone Protector Spawn Timer", description = "Timer until golem spawns")
    public static boolean golemspawntimer;
    @Property(type = PropertyType.SWITCH, category = "Notifications/Misc", subcategory = "Timers", name = "Dragon Egg Spawn Timer", description = "Timer until Dragon Egg spawns, after Dragon Boss was defeated")
    public static boolean dragoneggspawn;
    @Property(type = PropertyType.SWITCH, category = "Times", subcategory = "Total time", name = "Slayer boss slain Time", description = "Tells Slayer boss kill time")
    public static boolean slayerslaintime;
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
    @Property(type = PropertyType.SWITCH, category = "Other", subcategory = "Other", name = "Golden Dragon Bank Balance", description = "Displays current bank balance, if Golden Dragon equipped")
    public static boolean gdragbank;

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
