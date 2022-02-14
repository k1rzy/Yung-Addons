package com.encodey.YungAddons.Core.gui;

import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.utils.FontUtils;
import gg.essential.api.EssentialAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class GuiCloseButton extends GuiScreen {
    public void initGui() {
        super.initGui();
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();
        this.buttonList.add(new BetterButton(0, width / 2 - 95, height / 4 + 75, 190, 20, "Lazy button"));
    }

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (final GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }
    }

    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                YungAddons.displayScreen = new GuiStart();
                break;
            }
        }
    }
    public boolean doesGuiPauseGame() {
        return false;
    }
}
