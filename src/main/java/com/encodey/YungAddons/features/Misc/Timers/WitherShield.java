package com.encodey.YungAddons.features.Misc.Timers;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.Core.gui.LocationEditGui;
import com.encodey.YungAddons.lib.ColorCode;
import com.encodey.YungAddons.settings.BooleanSetting;
import com.encodey.YungAddons.settings.FloatSetting;
import com.encodey.YungAddons.settings.IntegerSetting;
import com.encodey.YungAddons.utils.FontUtils;
import com.encodey.YungAddons.utils.RenderText;
import gg.essential.universal.UChat;
import net.minecraft.client.*;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WitherShield extends MouseAdapter {
    public static float x;
    public static float y;
    public static long LastShield = 0L;
    public static long totalShield = 0L;
    public static boolean activeAbility;
    public static boolean locationgui;
    Minecraft mc = Minecraft.getMinecraft();

    private boolean leftWasDown;

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!Config.shieldtimer) return;
        if (Config.shieldtimer) {
            final ItemStack item = mc.thePlayer.inventory.getCurrentItem();
            if (item.getItem() == Items.iron_sword) {
                final String displayName = item.getDisplayName();
                if (displayName.contains("Hyperion") || displayName.contains("Scylla") || displayName.contains("Astraea") || displayName.contains("Valkyrie")) {
                    if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
                        final long timeDiffy = System.currentTimeMillis() - LastShield;
                        totalShield = timeDiffy;
                        activeAbility = true;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(final RenderGameOverlayEvent.Post event) {
        if (!Config.shieldtimer) return;
        if (Config.shieldtimer || locationgui) {
            try {
                if(event.type == RenderGameOverlayEvent.ElementType.ALL) {
                    ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
                    int width = scaledResolution.getScaledWidth();
                    int height = scaledResolution.getScaledHeight();
                    final float scale = 5.0f;
                    GlStateManager.scale(scale, scale, 0.0f);
                    if (totalShield >= 5000L) {
                        FontUtils.drawCenteredString("Shield: READY!", width/ 2.0f / scale, (height / 4.0f - 75.0f) / scale, 0xD64FC8);
                    } else {
                        FontUtils.drawCenteredString(String.format("Shield: %.3fs", (5000L - totalShield) / 1000f), width/ 2.0f / scale, (height / 4.0f - 75.0f) / scale, 0xD64FC8);
                    }
                    GlStateManager.scale(1.0f / scale, 1.0f / scale, 0.0f);
                }
            } catch (Exception e) {
                UChat.chat(e);
            }
        }
    }
}