package com.encodey.YungAddons.features.Mining.CrystalHollows;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.Core.Feature;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import com.encodey.YungAddons.utils.RenderText;
import gg.essential.universal.UChat;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
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
public class WormNotifier {
    public static int radius;

    private static long wormNotifMillis = 0;

    private Feature subtitleFeature;
    @Getter @Setter private Feature titleFeature;

    public static boolean running;
    public static boolean cliprunning;

    // Checking Location
    public static boolean chlocation;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        chlocation = false;
	    running = false;
    }

    public void processBlockChangePacket(S19PacketEntityStatus event) {

    }
    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
        final String message = event.message.getUnformattedText();
        if (Config.wormnotifier) {
            if (message.startsWith("You hear the sound of something approaching") && !running) {
                wormNotifMillis = System.currentTimeMillis();
                running = true;
                if(!chlocation) {
                    chlocation = true;
                    soundPlay();
                }
            }
        }
    }
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (!Config.wormnotifier) {
            return;
        }
        if (Config.wormnotifier && running) {
            try {
                if (wormNotifMillis <= 0) return;

                int delta = (int) (System.currentTimeMillis() - wormNotifMillis);
                int notifLen = 5000;
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
                    if (delta < fadeLen) {
                        alpha = delta * 255 / fadeLen;
                    } else if (delta > notifLen - fadeLen) {
                        alpha = (notifLen - delta) * 255 / fadeLen;
                    }

                    if (alpha > 10) {
                        RenderText.drawStringCenteredScaledMaxWidth("Worm spawned!", Minecraft.getMinecraft().fontRendererObj,
                                0, 0, true, width / 4 - 20, colour | (colour << 8) | (colour << 16) | (alpha << 24));
                    }


                    GlStateManager.popMatrix();
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000L);
                            running = false;
                            cliprunning = false;
                            chlocation = false;
                            soundPlay();
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
    public void soundPlay() {
        try {
            if(chlocation) {
                File wavFile = new File("config/yungaddons/soundnotify/sound.wav");
                Clip clep = AudioSystem.getClip();
                if(clep.isRunning()) {
                    clep.stop();
                    clep.setFramePosition(0);
                }
                clep.open(AudioSystem.getAudioInputStream(wavFile));
                clep.start();
                cliprunning = false;
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
