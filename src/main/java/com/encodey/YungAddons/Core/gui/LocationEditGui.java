package com.encodey.YungAddons.Core.gui;

import java.io.IOException;
import java.util.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public class LocationEditGui extends GuiScreen
{
    private float xOffset;
    private float yOffset;
    private GuiElement dragging;
    private final Map<GuiElement, LocationButton> locationButtons;

    public LocationEditGui() {
        this.locationButtons = new HashMap<GuiElement, LocationButton>();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void initGui() {
        super.initGui();
        for (final GuiElement e : GuiManager.elements) {
            final LocationButton lb = new LocationButton(e);
            this.buttonList.add(lb);
            this.locationButtons.put(e, lb);
        }
    }

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.onMouseMove();
        this.drawDefaultBackground();
        for (final GuiButton button : this.buttonList) {
            if (button instanceof LocationButton) {
                if (!((LocationButton)button).element.getToggled()) {
                    continue;
                }
                button.drawButton(this.mc, mouseX, mouseY);
            }
            else {
                button.drawButton(this.mc, mouseX, mouseY);
            }
        }
    }
    public void actionPerformed(final GuiButton button) {
        if (button instanceof LocationButton) {
            final LocationButton lb = (LocationButton)button;
            this.dragging = lb.getElement();
            final ScaledResolution sr = new ScaledResolution(this.mc);
            final float minecraftScale = (float)sr.getScaleFactor();
            final float floatMouseX = Mouse.getX() / minecraftScale;
            final float floatMouseY = (this.mc.displayHeight - Mouse.getY()) / minecraftScale;
            this.xOffset = floatMouseX - this.dragging.getActualX();
            this.yOffset = floatMouseY - this.dragging.getActualY();
        }
    }

    protected void onMouseMove() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        final float minecraftScale = (float)sr.getScaleFactor();
        final float floatMouseX = Mouse.getX() / minecraftScale;
        final float floatMouseY = (Display.getHeight() - Mouse.getY()) / minecraftScale;
        if (this.dragging != null) {
            final LocationButton lb = this.locationButtons.get(this.dragging);
            if (lb == null) {
                return;
            }
            final float x = (floatMouseX - this.xOffset) / sr.getScaledWidth();
            final float y = (floatMouseY - this.yOffset) / sr.getScaledHeight();
            this.dragging.setPos(x, y);
        }
    }

    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.dragging = null;
    }

    public void onGuiClosed() {
        GuiManager.saveConfig();
    }
}
