package com.encodey.YungAddons.utils;

import com.encodey.YungAddons.YungAddons;
import net.minecraft.client.Minecraft;
import net.minecraft.util.*;
import com.encodey.YungAddons.utils.Utils;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class FontUtils
{
    public static final char[] modMessageChars;
    public static float posX;
    public static float posY;
    public static final int[] CHAR_WIDTH;
    public static final ResourceLocation ascii;
    public static int modMessageColor = 2;

    public static void init() {
        BufferedImage bufferedimage;
        try {
            bufferedimage = Utils.readBufferedImage(FontUtils.ascii);
        }
        catch (IOException ioexception) {
            throw new RuntimeException(ioexception);
        }
        final int i = bufferedimage.getWidth();
        final int j = bufferedimage.getHeight();
        final int[] aint = new int[i * j];
        bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
        final int k = j / 16;
        final int l = i / 16;
        final int i2 = 1;
        final float f = 8.0f / l;
        for (int j2 = 0; j2 < 256; ++j2) {
            final int k2 = j2 % 16;
            final int l2 = j2 / 16;
            if (j2 == 32) {
                FontUtils.CHAR_WIDTH[j2] = 3 + i2;
            }
            int i3;
            for (i3 = l - 1; i3 >= 0; --i3) {
                final int j3 = k2 * l + i3;
                boolean flag = true;
                for (int k3 = 0; k3 < k; ++k3) {
                    final int l3 = (l2 * l + k3) * i;
                    if ((aint[j3 + l3] >> 24 & 0xFF) != 0x0) {
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    break;
                }
            }
            ++i3;
            CHAR_WIDTH[j2] = (int)(0.5 + i3 * f) + i2;
        }
    }

    public static void drawCenteredString(final String text, final float x, final float y, final int color) {
        Minecraft.getMinecraft().fontRendererObj.drawString(text, (int)(x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2.0f), (int)y, color);
    }


    public static void drawString(final String text, final float x, final float y, final int color) {
        Minecraft.getMinecraft().fontRendererObj.drawString(text, (int)x, (int)y, color);
    }

    public static void drawBackground(final int width, final int height) {
        Gui.drawRect(0, 0, width, height, new Color(0, 0, 0, 90).getRGB());
    }

    public static void drawBackground(final int width, final int height, final int alpha) {
        Gui.drawRect(0, 0, width, height, new Color(0, 0, 0, alpha).getRGB());
    }

    public static void drawBackground(final float width, final float height) {
        Gui.drawRect(0, 0, (int)width, (int)height, new Color(0, 0, 0, 90).getRGB());
    }

    public static void drawRect(final double left, final double top, final double right, final double bottom, final int color) {
        Gui.drawRect((int)left, (int)top, (int)right, (int)bottom, color);
    }

    public static void drawRect(final double left, final double top, final double right, final double bottom, final Color color) {
        Gui.drawRect((int)left, (int)top, (int)right, (int)bottom, color.getRGB());
    }
    public static void drawRainbowText(final String input, final float x, final float y) {
        FontUtils.posX = x;
        FontUtils.posY = y;
        for (final char c : input.toCharArray()) {
            final long dif = (long)FontUtils.posX * 10L - (long)FontUtils.posY * 10L;
            final long time = System.currentTimeMillis() - dif;
            final float z = 2000.0f;
            final int color = Color.HSBtoRGB(time % (int)z / z, 0.8f, 0.8f);
            final float red = (color >> 16 & 0xFF) / 255.0f;
            final float blue = (color >> 8 & 0xFF) / 255.0f;
            final float green = (color & 0xFF) / 255.0f;
            final float originPosX = FontUtils.posX;
            final float originPosY = FontUtils.posY;
            final float offset = 1.0f;
            GlStateManager.color(red * 0.19999999f, green * 0.19999999f, blue * 0.19999999f, 1.0f);
            FontUtils.posX = originPosX - offset;
            FontUtils.posY = originPosY;
            renderChar(c);
            FontUtils.posX = originPosX + offset;
            FontUtils.posY = originPosY;
            renderChar(c);
            FontUtils.posX = originPosX;
            FontUtils.posY = originPosY - offset;
            renderChar(c);
            FontUtils.posX = originPosX;
            FontUtils.posY = originPosY + offset;
            renderChar(c);
            FontUtils.posX = originPosX;
            FontUtils.posY = originPosY;
            GlStateManager.color(red * 0.19999999f, green * 0.19999999f, blue * 0.19999999f, 1.0f);
            FontUtils.posX = originPosX + offset;
            FontUtils.posY = originPosY + offset;
            renderChar(c);
            FontUtils.posX = originPosX;
            FontUtils.posY = originPosY;
            GlStateManager.color(red, green, blue, 1.0f);
            FontUtils.posX += renderChar(c);
        }
    }

    public static float drawRainbowModMessage(final float x, final float y) {
        YungAddons.mc.renderEngine.bindTexture(FontUtils.ascii);
        FontUtils.posX = x;
        FontUtils.posY = y;
        final long time = System.currentTimeMillis();
        for (final char c : FontUtils.modMessageChars) {
            final long position = time - ((long)FontUtils.posX * 10L - (long)FontUtils.posY * 10L);
            final int color = Color.HSBtoRGB(position % 3000L / 3000.0f, 1.0f, 1.0f);
            float red = 0.0f;
            float blue = 0.0f;
            float green = 0.0f;
            switch (modMessageColor) {
                case 1: {
                    red = 1.0f;
                    blue = (color >> 8 & 0xFF) / 255.0f;
                    green = (color & 0xFF) / 255.0f;
                    break;
                }
                case 2: {
                    red = (color >> 16 & 0xFF) / 255.0f;
                    blue = 1.0f;
                    green = (color & 0xFF) / 255.0f;
                    break;
                }
                case 3: {
                    red = (color >> 16 & 0xFF) / 255.0f;
                    blue = (color >> 8 & 0xFF) / 255.0f;
                    green = 1.0f;
                    break;
                }
                default: {
                    red = (color >> 16 & 0xFF) / 255.0f;
                    blue = (color >> 8 & 0xFF) / 255.0f;
                    green = (color & 0xFF) / 255.0f;
                    break;
                }
            }
            final float originPosX = FontUtils.posX;
            final float originPosY = FontUtils.posY;
            GL11.glColor4f(0.15f, 0.15f, 0.15f, 0.5f);
            FontUtils.posX = originPosX - 1.0f;
            renderDefaultCharNoReturn(c);
            FontUtils.posX = originPosX + 1.0f;
            renderDefaultCharNoReturn(c);
            FontUtils.posX = originPosX;
            FontUtils.posY = originPosY - 1.0f;
            renderDefaultCharNoReturn(c);
            FontUtils.posY = originPosY + 1.0f;
            renderDefaultCharNoReturn(c);
            FontUtils.posX = originPosX + 1.0f;
            FontUtils.posY = originPosY + 1.0f;
            renderDefaultCharNoReturn(c);
            FontUtils.posX = originPosX;
            FontUtils.posY = originPosY;
            GL11.glColor3f(red, green, blue);
            FontUtils.posX += renderDefaultChar(c);
        }
        FontUtils.posX += 4.0f;
        final char c2 = '>';
        final float originPosX2 = FontUtils.posX;
        final float originPosY2 = FontUtils.posY;
        GL11.glColor4f(0.15f, 0.15f, 0.15f, 0.5f);
        FontUtils.posX = originPosX2 - 1.0f;
        renderDefaultCharNoReturn(c2);
        FontUtils.posX = originPosX2 + 1.0f;
        renderDefaultCharNoReturn(c2);
        FontUtils.posX = originPosX2;
        FontUtils.posY = originPosY2 - 1.0f;
        renderDefaultCharNoReturn(c2);
        FontUtils.posY = originPosY2 + 1.0f;
        renderDefaultCharNoReturn(c2);
        FontUtils.posX = originPosX2 + 1.0f;
        FontUtils.posY = originPosY2 + 1.0f;
        renderDefaultCharNoReturn(c2);
        FontUtils.posX = originPosX2;
        FontUtils.posY = originPosY2;
        GL11.glColor3f(0.0f, 1.0f, 1.0f);
        FontUtils.posX += renderDefaultChar(c2);
        GlStateManager.resetColor();
        return FontUtils.posX + 4.0f;
    }

    public static float renderChar(final char ch) {
        if (ch == ' ') {
            return 4.0f;
        }
        if (ch == ' ') {
            return 4.0f;
        }
        return renderDefaultChar(ch);
    }

    public static void renderCharNoReturn(final char ch) {
        if (ch == ' ') {
            return;
        }
        if (ch == ' ') {
            return;
        }
        renderDefaultCharNoReturn(ch);
    }

    public static float renderDefaultChar(final int ch) {
        final int i = ch % 16 * 8;
        final int j = ch / 16 * 8;
        final int l = FontUtils.CHAR_WIDTH[ch];
        final float f = l - 0.01f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(i / 128.0f, j / 128.0f);
        GL11.glVertex3f(FontUtils.posX, FontUtils.posY, 0.0f);
        GL11.glTexCoord2f(i / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f(FontUtils.posX, FontUtils.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((i + f - 1.0f) / 128.0f, j / 128.0f);
        GL11.glVertex3f(FontUtils.posX + f - 1.0f, FontUtils.posY, 0.0f);
        GL11.glTexCoord2f((i + f - 1.0f) / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f(FontUtils.posX + f - 1.0f, FontUtils.posY + 7.99f, 0.0f);
        GL11.glEnd();
        return (float)l;
    }

    public static void renderDefaultCharNoReturn(final int ch) {
        final int i = ch % 16 * 8;
        final int j = ch / 16 * 8;
        final float f = FontUtils.CHAR_WIDTH[ch] - 0.01f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(i / 128.0f, j / 128.0f);
        GL11.glVertex3f(FontUtils.posX, FontUtils.posY, 0.0f);
        GL11.glTexCoord2f(i / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f(FontUtils.posX, FontUtils.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((i + f - 1.0f) / 128.0f, j / 128.0f);
        GL11.glVertex3f(FontUtils.posX + f - 1.0f, FontUtils.posY, 0.0f);
        GL11.glTexCoord2f((i + f - 1.0f) / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f(FontUtils.posX + f - 1.0f, FontUtils.posY + 7.99f, 0.0f);
        GL11.glEnd();
    }

    static {
        modMessageChars = "YungAddons".toCharArray();
        CHAR_WIDTH = new int[256];
        ascii = new ResourceLocation("textures/font/ascii.png");
    }
}