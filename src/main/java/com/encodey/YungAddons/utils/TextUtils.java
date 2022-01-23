package com.encodey.YungAddons.utils;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * @author BiscuitDevelopment (SkyblockAddons)
 */

public class TextUtils {
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
    private static final Pattern NUMBERS_SLASHES = Pattern.compile("[^0-9 /]");
    private static final Pattern SCOREBOARD_CHARACTERS = Pattern.compile("[^a-z A-Z:0-9/'.]");
    private static final Pattern FLOAT_CHARACTERS = Pattern.compile("[^.0-9\\-]");
    private static final Pattern INTEGER_CHARACTERS = Pattern.compile("[^0-9]");

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.##");

    public static String formatDouble(double number) {
        return DECIMAL_FORMAT.format(number);
    }
    public static String stripColor(final String input) {
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }
}
