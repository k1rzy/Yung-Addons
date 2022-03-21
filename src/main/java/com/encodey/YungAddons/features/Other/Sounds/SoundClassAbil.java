package com.encodey.YungAddons.features.Other.Sounds;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.features.ESP.DungeonChestESP;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
/**
 * @author k1rzy (encodey)
 */
public class SoundClassAbil {
    Minecraft mc = Minecraft.getMinecraft();
    public static boolean isDropped;
    public static boolean isUsed;
    public static boolean isUsed2;
    public static boolean finalSoundKey;

    private AudioInputStream audioInputStream;
    private Clip clip;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        isUsed = false;
        isUsed2 = false;
    }

    @SubscribeEvent
    public void key(ClientChatReceivedEvent event) throws Exception {
        String message = event.message.getUnformattedText();
        try {
            if (Config.soundswitherkey && DungeonChestESP.dungeonStarted) {
                if(message.startsWith("Wish is ") || message.startsWith("Thunderstorm is ") || message.startsWith("Ragnarok is ") || message.startsWith("Rapid Fire is ") || message.startsWith("Castle of Stone is ") || message.startsWith("Your Archer ULTIMATE ") || message.startsWith("Your Mage ULTIMATE ") || message.startsWith("Your Berserk ULTIMATE ") || message.startsWith("Your Healer ULTIMATE ") || message.startsWith("Your Tank ULTIMATE ")) {
                    if(!isUsed2) {
                        isDropped = true;
                        soundPlayOther();
                    }
                }
                if(message.startsWith("Guided Sheep is ") || message.startsWith("Throwing Axe is ") || message.startsWith("Seismic Wave is ")) {
                    if(!isUsed) {
                        isDropped = true;
                        soundPlay();
                    }
                }
                if(message.startsWith("Explosive Shot is ") && !isUsed) {
                    if(!isUsed) {
                        new Thread(() -> {
                            try {
                                Thread.sleep(12300L);
                                isDropped = true;
                                soundPlay();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }).start();
                    }
                }
                if(message.startsWith("Used ") ) {
                    isUsed = false;
                    isUsed2 = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void soundPlay() {
        if(isDropped) {
            try {
                File wavFile = new File("config/yungaddons/soundability/sound.wav");
                Clip clep = AudioSystem.getClip();
                if(clep.isRunning()) {
                    clep.stop();
                    clep.setFramePosition(0);
                }
                clep.open(AudioSystem.getAudioInputStream(wavFile));
                clep.start();
                activeThread();
                isDropped = false;
                isUsed = true;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
    public void soundPlayOther() {
        if(isDropped) {
            try {
                File wavFile = new File("config/yungaddons/soundability/sound.wav");
                Clip clep = AudioSystem.getClip();
                if(clep.isRunning()) {
                    clep.stop();
                    clep.setFramePosition(0);
                }
                clep.open(AudioSystem.getAudioInputStream(wavFile));
                clep.start();
                activeThread();
                isDropped = false;
                isUsed2 = true;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
    public void activeThread() {
        if(isUsed) {
            new Thread(() -> {
                try {
                    Thread.sleep(40000L);
                    isUsed = false;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }).start();
        }
        if(isUsed2) {
            new Thread(() -> {
                try {
                    Thread.sleep(40000L);
                    isUsed2 = false;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }).start();
        }
    }
}
