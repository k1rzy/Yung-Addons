package com.encodey.YungAddons.features.Mining.CrystalHollows;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.utils.RenderText;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
/**
 * @author k1rzy (encodey)
 */
public class TreasChestNotifier {

    public static boolean ready;
    public static boolean songready;

    private static long treasNotifMillis = 0;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        ready = false;
        songready = false;
    }

    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        if(message.startsWith("You uncovered a treasure ") && Config.treaschestnotif && !ready) {
            treasNotifMillis = System.currentTimeMillis();
            ready = true;
/*            if(!songready) {
                songready = true;
                soundPlay();
            }*/
        }
    }
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (!Config.treaschestnotif) {
            return;
        }
        if (Config.treaschestnotif && ready) {
            try {
                if (treasNotifMillis <= 0) return;

                int delta = (int) (System.currentTimeMillis() - treasNotifMillis);
                int notifLen = 6000;
                int fadeLen = 0;
                if (delta > 0 && delta < notifLen && event.type == RenderGameOverlayEvent.ElementType.ALL) {
                    ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
                    int width = scaledResolution.getScaledWidth();
                    int height = scaledResolution.getScaledHeight();

                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float) (width / 2), (float) (height / 2), 0.0F);
                    GlStateManager.scale(4.0F, 4.0F, 4.0F);

                    int colour1 = 0xD62440;
                    int colour2 = 0xD62440;

                    double factor = (Math.sin(delta * 2 * Math.PI / 1000) + 1) / 2;
                    int colour = (int) (colour1 * factor + colour2 * (1 - factor));

                    int alpha = 255;
                    if (delta > notifLen - fadeLen) {
                       alpha = (notifLen - delta) * 255;
                    }

                    if (alpha > 10) {
                        RenderText.drawStringCenteredScaledMaxWidth("Treasure Chest!", Minecraft.getMinecraft().fontRendererObj,
                                0, 0, true, width / 4 - 20, colour | (colour << 8) | (colour << 16) | (alpha << 24));
                    }


                    GlStateManager.popMatrix();
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000L);
                            ready = false;
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }).start();
                }
            } catch (Exception e) {
                UChat.chat(e);
            }
        }
    }
/*    public void soundPlay() {
        try {
            if(songready) {
                File wavFile = new File("config/yungaddons/soundnotify/sound.wav");
                Clip clep = AudioSystem.getClip();
                if(clep.isRunning()) {
                    clep.stop();
                    clep.setFramePosition(0);
                }
                clep.open(AudioSystem.getAudioInputStream(wavFile));
                clep.start();
                songready = false;
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }*/
}
