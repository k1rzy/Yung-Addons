package com.encodey.YungAddons.features.Other.Sounds;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.features.ESP.DungeonChestESP;
import com.encodey.YungAddons.features.Mining.CrystalHollows.WormNotifier;
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

public class SoundsSpeedBoost {
    Minecraft mc = Minecraft.getMinecraft();
    public static boolean isReady;

    @SubscribeEvent
    public void key(ClientChatReceivedEvent event) throws Exception {
        String message = event.message.getUnformattedText();
        try {
            if (Config.soundsspeedboost) {
                if (message.startsWith("Mining Speed ") && message.endsWith("available!")) {
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
