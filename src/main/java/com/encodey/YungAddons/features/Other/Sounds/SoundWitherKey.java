package com.encodey.YungAddons.features.Other.Sounds;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.features.ESP.DungeonChestESP;
import com.encodey.YungAddons.utils.RenderUtils;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
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
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import javax.sound.sampled.*;

public class SoundWitherKey {
    Minecraft mc = Minecraft.getMinecraft();
    public static boolean isDropped;
    public static boolean finalSoundKey;
    private AudioInputStream audioInputStream;

    @SubscribeEvent
    public void key(ClientChatReceivedEvent event) throws Exception {
        String message = event.message.getUnformattedText();
        try {
            if (Config.soundswitherkey && DungeonChestESP.dungeonStarted) {
                    if(message.endsWith("Wither Key!") || message.startsWith("A Wither Key")) {
                        isDropped = true;
                        soundPlayWither();
                        ChatComponentText msg1 = new ChatComponentText(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + "Wither key is Dropped!");
                        mc.thePlayer.addChatMessage(msg1);
                    }
                    if(message.endsWith("Blood Key!") || message.startsWith("A Blood Key")) {
                        isDropped = true;
                        soundPlayBlood();
                        ChatComponentText msg1 = new ChatComponentText(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + "Blood key is Dropped!");
                        mc.thePlayer.addChatMessage(msg1);
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void soundPlayWither() {
        if(isDropped) {
            try {
                File wavFile = new File("config/yungaddons/soundwither/sound.wav");
                Clip clep = AudioSystem.getClip();
                if(clep.isRunning()) {
                    clep.stop();
                    clep.setFramePosition(0);
                }
                clep.open(AudioSystem.getAudioInputStream(wavFile));
                clep.start();
                isDropped = false;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
    public void soundPlayBlood() {
        if(isDropped) {
            try {
                File wavFile = new File("config/yungaddons/soundblood/sound.wav");
                Clip clep = AudioSystem.getClip();
                if(clep.isRunning()) {
                    clep.stop();
                    clep.setFramePosition(0);
                }
                clep.open(AudioSystem.getAudioInputStream(wavFile));
                clep.start();
                isDropped = false;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}
