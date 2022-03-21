package com.encodey.YungAddons.features.Mining.CrystalHollows;

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
public class MiningPassTime {
    public static double passTime;
    public static final ResourceLocation GEMSTONE_ICON = new ResourceLocation("yungaddons", "icons/gem.png");
    public static String PASS_COLOUR;

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (message.contains(" Gwendolyn: Great! ")) {
            passTime = System.currentTimeMillis() / 1000 + 18000; // Add 5 hours
            TimerHandler.writeDoubleConfig("misc", "miningpassTime", passTime);
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (Config.miningpasstime && event.type == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getMinecraft();
            double scale = ScaleCommmand.passTimerScale;
            int scaledWidth = scaledResolution.getScaledWidth();
            double scaleReset = Math.pow(scale, -1);
            GL11.glScaled(scale, scale, scale);

            double timeNow = System.currentTimeMillis() / 1000;
            mc.getTextureManager().bindTexture(GEMSTONE_ICON);
            Gui.drawModalRectWithCustomSizedTexture(MoveCommand.passTimerXY[0], MoveCommand.passTimerXY[1], 0, 0, 16, 16, 16, 16);

            String passText;
            if (passTime - timeNow < 0) {
                passText = EnumChatFormatting.DARK_AQUA + "NONE";
            } else {
                passText = PASS_COLOUR + UtilsDanker.getTimeBetween(timeNow, passTime);
            }
            int stringWidth = mc.fontRendererObj.getStringWidth(passText);

            if (stringWidth * scale > (scaledWidth * 0.9F)) {
                scale = (scaledWidth * 0.9F) / (float) stringWidth;
            }

            new TextRenderer(mc, EnumChatFormatting.DARK_AQUA + "Hollows Pass: " + passText, MoveCommand.passTimerXY[0] + 31, MoveCommand.passTimerXY[1] + 6, scale);

            GL11.glScaled(scaleReset, scaleReset, scaleReset);
        }
    }

}
