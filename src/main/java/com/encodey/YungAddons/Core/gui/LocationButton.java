package com.encodey.YungAddons.Core.gui;

import com.encodey.YungAddons.utils.FontUtils;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.client.audio.*;

public class LocationButton extends GuiButton
{
    private float x;
    private float y;
    private float x2;
    private float y2;
    public GuiElement element;

    public LocationButton(final GuiElement element) {
        super(-1, 0, 0, (String)null);
        this.element = element;
        this.x = this.element.getActualX() - 5.0f;
        this.y = this.element.getActualY() - 5.0f;
        this.x2 = this.x + this.element.getWidth() + 5.0f;
        this.y2 = this.y + this.element.getHeight() + 5.0f;
    }

    private void refreshLocations() {
        this.x = this.element.getActualX() - 2.0f;
        this.y = this.element.getActualY() - 2.0f;
        this.x2 = this.x + this.element.getWidth() + 4.0f;
        this.y2 = this.y + this.element.getHeight() + 4.0f;
    }

    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        this.refreshLocations();
        this.hovered = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x2 && mouseY < this.y2);
        final Color c = new Color(250, 250, 250, this.hovered ? 120 : 60);
        FontUtils.drawRect(this.x, this.y, this.x2, this.y2, c.getRGB());
        this.element.demoRender();
    }

    public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
        return this.enabled && this.visible && this.hovered;
    }

    public void func_146113_a(final SoundHandler soundHandlerIn) {
    }

    public GuiElement getElement() {
        return this.element;
    }
}

