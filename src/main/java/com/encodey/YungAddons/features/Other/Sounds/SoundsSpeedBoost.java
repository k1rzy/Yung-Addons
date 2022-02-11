package com.encodey.YungAddons.features.Other.Sounds;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.features.ESP.DungeonChestESP;
import com.encodey.YungAddons.features.Mining.CrystalHollows.WormNotifier;
import com.encodey.YungAddons.utils.RenderText;
import com.encodey.YungAddons.utils.RenderUtils;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import java.applet.Applet;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import javax.sound.sampled.*;

public class SoundsSpeedBoost {
    Minecraft mc = Minecraft.getMinecraft();
    public static boolean isReady;

    public static boolean running;

    private static long beaNotifMillis = 0;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        running = false;
    }

    @SubscribeEvent
    public void key(ClientChatReceivedEvent event) throws Exception {
        String message = event.message.getUnformattedText();
        try {
            if (Config.soundsspeedboost) {
                if (message.startsWith("Mining Speed ") && message.endsWith("available!")) {
                    if(Config.speedboostnotif && !running) {
                        beaNotifMillis = System.currentTimeMillis();
                        running = true;
                    }
                    isReady = true;
                    soundPlay();
                }
            }
        }
        catch(Exception e) {
            ChatComponentText msg2 = new ChatComponentText(EnumChatFormatting.RED + "[Yung Addons] " + e + EnumChatFormatting.BLUE + " : Please report bugs in our discord server");
            mc.thePlayer.addChatMessage(msg2);
        }
    }
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (!Config.speedboostnotif) {
            return;
        }
        if (Config.speedboostnotif && running) {
            try {
                if (beaNotifMillis <= 0) return;

                int delta = (int) (System.currentTimeMillis() - beaNotifMillis);
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
                        RenderText.drawStringCenteredScaledMaxWidth("SPEED BOOST!", Minecraft.getMinecraft().fontRendererObj,
                                0, 0, true, width / 4 - 20, colour | (colour << 8) | (colour << 16) | (alpha << 24));
                    }


                    GlStateManager.popMatrix();
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000L);
                            running = false;
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
        if(isReady) {
            try {
                File wavFile = new File("config/yungaddons/soundspeedboost/sound.wav");
                Clip clep = AudioSystem.getClip();
                if(clep.isRunning()) {
                    clep.stop();
                    clep.setFramePosition(0);
                }
                clep.open(AudioSystem.getAudioInputStream(wavFile));
                clep.start();
                isReady = false;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}
