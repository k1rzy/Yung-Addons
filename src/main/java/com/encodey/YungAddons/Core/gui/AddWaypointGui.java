package com.encodey.YungAddons.Core.gui;

import com.encodey.YungAddons.utils.FontUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
/**
 * @author k1rzy (encodey)
 */
public class AddWaypointGui extends GuiScreen {
    String waypointName = "Waypoint";
    int posX;
    int posY;
    int posZ;
    public void initGui() {
        super.initGui();
        this.buttonList.add(new BetterButton(0, width / 2 - 95, height / 4 + 250, 190, 20, "Save waypoint"));
    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        FontUtils.drawBackground(this.width, this.height);
    }
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(new WaypointGui());
                break;
            }
        }
    }
    public boolean doesGuiPauseGame() {
        return false;
    }
}
