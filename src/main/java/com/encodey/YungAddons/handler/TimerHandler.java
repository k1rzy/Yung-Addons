package com.encodey.YungAddons.handler;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.commands.Dankers.MoveCommand;
import com.encodey.YungAddons.commands.Dankers.ScaleCommmand;
import com.encodey.YungAddons.features.Experiments.ChronomatronSolver;
import com.encodey.YungAddons.features.Experiments.ExpSolver;
import com.encodey.YungAddons.features.Farming.ProfitCalc;
import com.encodey.YungAddons.features.Mining.CrystalHollows.MiningPassTime;
import com.encodey.YungAddons.features.Misc.Timers.CenturyCake;
import com.encodey.YungAddons.features.Solvers.BlazeSolver;
import com.encodey.YungAddons.features.Solvers.BoulderSolver;
import com.encodey.YungAddons.features.Solvers.TriviaSolver;
import com.encodey.YungAddons.scripts.ActualScript;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.awt.*;
import java.io.File;

public class TimerHandler {
    public static Configuration config;
    private final static String file = "config/yunggui.cfg";

    public static void init() {
        config = new Configuration(new File(file));
        try {
            config.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static int getInt(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0).getInt();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return 0;
    }

    public static double getDouble(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0D).getDouble();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return 0D;
    }

    public static String getString(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, "").getString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return "";
    }

    public static boolean getBoolean(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, false).getBoolean();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return true;
    }

    public static void writeIntConfig(String category, String key, int value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            int set = config.get(category, key, value).getInt();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static void writeDoubleConfig(String category, String key, double value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            double set = config.get(category, key, value).getDouble();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static void writeStringConfig(String category, String key, String value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            String set = config.get(category, key, value).getString();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static void writeBooleanConfig(String category, String key, boolean value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            boolean set = config.get(category, key, value).getBoolean();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static boolean hasKey(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (!config.hasCategory(category)) return false;
            return config.getCategory(category).containsKey(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return false;
    }

    public static void deleteCategory(String category) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.hasCategory(category)) {
                config.removeCategory(new ConfigCategory(category));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static int initInt(String category, String key, int defaultValue) {
        if (!hasKey(category, key)) {
            writeIntConfig(category, key, defaultValue);
            return defaultValue;
        } else {
            return getInt(category, key);
        }
    }

    public static double initDouble(String category, String key, double defaultValue) {
        if (!hasKey(category, key)) {
            writeDoubleConfig(category, key, defaultValue);
            return defaultValue;
        } else {
            return getDouble(category, key);
        }
    }

    public static String initString(String category, String key, String defaultValue) {
        if (!hasKey(category, key)) {
            writeStringConfig(category, key, defaultValue);
            return defaultValue;
        } else {
            return getString(category, key);
        }
    }

    public static boolean initBoolean(String category, String key, boolean defaultValue) {
        if (!hasKey(category, key)) {
            writeBooleanConfig(category, key, defaultValue);
            return defaultValue;
        } else {
            return getBoolean(category, key);
        }
    }
       public static void reloadConfig() {
        CenturyCake.cakeTime = initDouble("misc", "cakeTime", 0);
        MiningPassTime.passTime = initDouble("misc", "miningpassTime", 0);

        // Locations
        ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
        int height = scaled.getScaledHeight();
        ActualScript.workedSessions = initInt("script", "sessions", 0);
        MoveCommand.farmingTextXY[0] = initInt("locations", "farmingTextX", 40);
        MoveCommand.farmingTextXY[1] = initInt("locations", "farmingTextY", 80);
        MoveCommand.cakeTimerXY[0] = initInt("locations", "cakeTimerX", 40);
        MoveCommand.cakeTimerXY[1] = initInt("locations", "cakeTimerY", 30);
        MoveCommand.waterAnswerXY[0] = initInt("locations", "waterAnswerX", 70);
        MoveCommand.waterAnswerXY[1] = initInt("locations", "waterAnswerY", 60);
        MoveCommand.passTimerXY[0] = initInt("locations", "miningPassTimerX", 100);
        MoveCommand.passTimerXY[1] = initInt("locations", "miningPassTimerY", 100);

        // Scales
        ScaleCommmand.cakeTimerScale = initDouble("scales", "cakeTimerScale", 1);
        ScaleCommmand.passTimerScale = initDouble("scales", "passTimerScale", 1);
        ScaleCommmand.waterAnswerScale = initDouble("scales", "waterAnswerScale", 1);
        ScaleCommmand.farmingTextScale = initDouble("scales", "farmingTextScale", 1);

        // Colours
        CenturyCake.CAKE_COLOUR = initString("colors", "cakeDisplay", EnumChatFormatting.GOLD.toString());
        MiningPassTime.PASS_COLOUR = initString("colors", "passDisplay", EnumChatFormatting.DARK_AQUA.toString());
        ProfitCalc.FARMING_OVERLAY_COLOR = initString("colors", "profitcalc", EnumChatFormatting.AQUA.toString());
        TriviaSolver.TRIVIA_WRONG_ANSWER_COLOUR = initString("colors", "triviaWrongAnswer", EnumChatFormatting.RED.toString());
        BlazeSolver.LOWEST_BLAZE_COLOUR = initInt("colors", "blazeLowest", 0xFF0000);
        BlazeSolver.HIGHEST_BLAZE_COLOUR = initInt("colors", "blazeHighest", 0x40FF40);
        BoulderSolver.BOULDER_COLOUR = initInt("colors", "boulder", 0x197F19);
        ChronomatronSolver.CHRONOMATRON_NEXT = initInt("colors", "chronomatronNext", 0x40FF40);
        ChronomatronSolver.CHRONOMATRON_NEXT_TO_NEXT = initInt("colors", "chronomatronNextToNext", 0x40DAE6);
        ExpSolver.ULTRASEQUENCER_NEXT = initInt("colors", "ultrasequencerNext", 0x40FF40);
        ExpSolver.ULTRASEQUENCER_NEXT = initInt("colors", "ultrasequencerNextToNext", 0x40DAE6);
        // Commands
        if (!hasKey("commands", "reparty")) writeBooleanConfig("commands", "reparty", false);
    }
}
