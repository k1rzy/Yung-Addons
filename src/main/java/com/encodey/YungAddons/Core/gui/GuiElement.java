package com.encodey.YungAddons.Core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
/**
 * @author k1rzy (encodey)
 */
public abstract class GuiElement
{
    public String name;
    public float scale;
    public GuiLocation pos;

    public GuiElement(final String name, final GuiLocation location) {
        this(name, 1.0f, location);
    }

    public GuiElement(final String name, final float scale, final GuiLocation location) {
        this.name = name;
        this.scale = scale;
        this.pos = GuiManager.GUIPOSITIONS.getOrDefault(name, location);
    }

    public abstract void render();

    public abstract void demoRender();

    public abstract boolean getToggled();

    public void setPos(float x, final float y) {
        this.pos.x = x;
        this.pos.y = y;
    }

    public float getActualX() {
        final int maxX = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
        return maxX * this.pos.x;
    }

    public float getActualY() {
        final int maxY = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
        return maxY * this.pos.y;
    }

    public abstract int getHeight();

    public abstract int getWidth();
}
