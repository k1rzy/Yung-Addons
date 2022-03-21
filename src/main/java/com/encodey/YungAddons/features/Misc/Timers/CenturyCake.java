package com.encodey.YungAddons.features.Misc.Timers;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.commands.Dankers.MoveCommand;
import com.encodey.YungAddons.commands.Dankers.ScaleCommmand;
import com.encodey.YungAddons.events.Overlay;
import com.encodey.YungAddons.events.SkyblockJoin;
import com.encodey.YungAddons.handler.TextRenderer;
import com.encodey.YungAddons.handler.TimerHandler;
import com.encodey.YungAddons.utils.UtilsDanker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
/**
 * @author k1rzy (encodey)
 */
public class CenturyCake {
    public static double cakeTime;
    public static final ResourceLocation CAKE_ICON = new ResourceLocation("yungaddons", "icons/cake.png");
    public static String CAKE_COLOUR;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();

        if (message.startsWith("Yum! You gain +") && message.endsWith(" for 48 hours!")) {
            cakeTime = System.currentTimeMillis() / 1000 + 172800; // Add 48 hours
            TimerHandler.writeDoubleConfig("misc", "cakeTime", cakeTime);
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (Config.caketimer && event.type == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getMinecraft();
            double scale = ScaleCommmand.cakeTimerScale;
            int scaledWidth = scaledResolution.getScaledWidth();
            double scaleReset = Math.pow(scale, -1);
            GL11.glScaled(scale, scale, scale);

            double timeNow = System.currentTimeMillis() / 1000;
            mc.getTextureManager().bindTexture(CAKE_ICON);
            Gui.drawModalRectWithCustomSizedTexture(MoveCommand.cakeTimerXY[0], MoveCommand.cakeTimerXY[1], 0, 0, 16, 16, 16, 16);

            String cakeText;
            if (cakeTime - timeNow < 0) {
                cakeText = EnumChatFormatting.GOLD + "NONE";
            } else {
                cakeText = CAKE_COLOUR + UtilsDanker.getTimeBetween(timeNow, cakeTime);
            }
            int stringWidth = mc.fontRendererObj.getStringWidth(cakeText);

            if (stringWidth * scale > (scaledWidth * 0.9F)) {
                scale = (scaledWidth * 0.9F) / (float) stringWidth;
            }

            new TextRenderer(mc, EnumChatFormatting.GOLD + "Century Cake: " + cakeText, MoveCommand.cakeTimerXY[0] + 20, MoveCommand.cakeTimerXY[1] + 5, scale);

            GL11.glScaled(scaleReset, scaleReset, scaleReset);
        }
    }

}
