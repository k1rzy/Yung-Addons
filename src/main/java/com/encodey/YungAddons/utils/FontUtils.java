package com.encodey.YungAddons.utils;

import net.minecraft.client.gui.*;
import java.awt.*;
import net.minecraft.client.*;

public class FontUtils
{
    private static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    public static void drawString(final String text, final int x, final int y, final int color) {
        FontUtils.fr.drawString(text, (float)x, (float)y, color, true);
    }

    public static void drawVCenteredString(final String text, final int x, final int y, final int color) {
        FontUtils.fr.drawString(text, (float)x, (float)(y - FontUtils.fr.FONT_HEIGHT / 2), color, true);
    }

    public static void drawHVCenteredString(final String text, final int x, final int y, final int color) {
        FontUtils.fr.drawString(text, (float)(x - FontUtils.fr.getStringWidth(text) / 2), (float)(y - FontUtils.fr.FONT_HEIGHT / 2), color, true);
    }

    public static void drawHVCenteredChromaString(final String text, final int x, final int y, final int offset) {
        drawChromaString(text, x - FontUtils.fr.getStringWidth(text) / 2, y - FontUtils.fr.FONT_HEIGHT / 2, offset);
    }

    public static int getStringWidth(final String text) {
        return FontUtils.fr.getStringWidth(text);
    }

    public static int getFontHeight() {
        return FontUtils.fr.FONT_HEIGHT;
    }

    public static void drawChromaString(final String text, final int x, final int y, final int offset) {
        double tmpX = x;
        for (final char tc : text.toCharArray()) {
            final long t = System.currentTimeMillis() - ((int)tmpX * 10L - y - offset * 10L);
            final int i = Color.HSBtoRGB(t % 2000L / 2000.0f, 0.88f, 0.88f);
            final String tmp = String.valueOf(tc);
            FontUtils.fr.drawString(tmp, (float)(int)tmpX, (float)y, i, true);
            tmpX += FontUtils.fr.getCharWidth(tc);
        }
    }
}