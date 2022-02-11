package com.encodey.YungAddons.Core.gui;

import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.Core.gui.*;
import com.encodey.YungAddons.utils.FontUtils;
import gg.essential.universal.UChat;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.util.*;

public class GuiStart extends GuiScreen
{

    public void initGui() {
        super.initGui();
        this.buttonList.add(new BetterButton(0, this.width / 2 - 95, this.height / 4 + 100, 190, 20, "Config"));
        this.buttonList.add(new BetterButton(1, this.width / 2 - 95, this.height / 4 + 125, 190, 20, "Edit Locations"));
    }

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        FontUtils.drawBackground(this.width, this.height);
        final float scale = 10.0f;
        GlStateManager.scale(scale, scale, 0.0f);
        FontUtils.drawCenteredString("Yung Addons", this.width/ 2.0f / scale, (this.height / 4.0f - 75.0f) / scale, 65535);
        GlStateManager.scale(1.0f / scale, 1.0f / scale, 0.0f);
        for (final GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }
    }

    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(YungAddons.configgui.gui());
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(new LocationEditGui());
                break;
            }
        }
    }
    public boolean doesGuiPauseGame() {
        return false;
    }
}
