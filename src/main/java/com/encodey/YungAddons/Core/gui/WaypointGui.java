package com.encodey.YungAddons.Core.gui;

import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.utils.FontUtils;
import com.encodey.YungAddons.utils.UtilsDanker;
import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.components.input.UITextInput;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import scala.swing.Applet;
/**
 * @author k1rzy (encodey)
 */
public class WaypointGui extends GuiScreen {

    public void initGui() {
        super.initGui();
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();
        this.buttonList.add(new BetterButton(0, width / 2 - 0, height / 4 + 250, 190, 20, "Add Waypoint"));
        this.buttonList.add(new BetterButton(1, width / 2 - 212, height / 4 + 250, 190, 20, "Save and exit"));
    }
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtils.drawBackground(this.width, this.height);
    }
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(new AddWaypointGui());
                break;
            }
            case 1: {
                YungAddons.displayScreen = new GuiStart();
                break;
            }
        }
    }
    public boolean doesGuiPauseGame() {
        return false;
    }
}
