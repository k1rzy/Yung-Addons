package com.encodey.YungAddons.Core.gui;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.Core.gui.*;
import com.encodey.YungAddons.features.Misc.Timers.WitherShield;
import com.encodey.YungAddons.utils.FontUtils;
import gg.essential.api.EssentialAPI;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.util.*;

public class GuiStart extends GuiScreen
{
    public void initGui() {
        super.initGui();
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();
        this.buttonList.add(new BetterButton(0, width / 2 - 95, height / 4 + 110, 190, 20, "Config"));
        this.buttonList.add(new BetterButton(1, width / 2 - 95, height / 4 + 135, 190, 20, "Edit Locations"));
        this.buttonList.add(new BetterButton(2, width / 2 - 95, height / 4 + 160, 190, 20, "Waypoints"));
        this.buttonList.add(new BetterButton(3, width / 2 - 95, height / 4 + 185, 190, 20, "Close menu"));
    }

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        String yung = "Yung Addons";
        String ver = YungAddons.VERSION;
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();
        super.drawScreen(mouseX, mouseY, partialTicks);
        FontUtils.drawBackground(this.width, this.height);
        float scale = 10.0f;
        int stringWidth = mc.fontRendererObj.getStringWidth(yung);

        if (stringWidth * scale > (width * 0.9F)) {
            scale = (width * 0.9F) / (float) stringWidth;
        }
        GlStateManager.scale(scale, scale, 0.0f);
        FontUtils.drawCenteredString(yung, width/ 2.0f / scale, (height / 4.0f - 75.0f) / scale, 0xD62440);
        FontUtils.drawCenteredString(ver, width/ 2.0f / scale, (height / 2.7f - 60.0f) / scale, 0xD62440);
        GlStateManager.scale(1.0f / scale, 1.0f / scale, 0.0f);
        for (final GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }
    }

    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                EssentialAPI.getGuiUtil().openScreen(YungAddons.configFile.gui());
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(new LocationEditGui());
                break;
            }
            case 2: {
                this.mc.displayGuiScreen(new WaypointGui());
                break;
            }
            case 3: {
                YungAddons.displayScreen = null;
                break;
            }
        }
    }
    public boolean doesGuiPauseGame() {
        return false;
    }
}
