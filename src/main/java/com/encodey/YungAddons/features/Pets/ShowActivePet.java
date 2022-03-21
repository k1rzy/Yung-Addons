package com.encodey.YungAddons.features.Pets;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.utils.MathUtils.LerpUtils;
import com.encodey.YungAddons.utils.RenderUtils;
import lombok.val;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.google.gson.*;
/**
 * @author k1rzy (encodey)
 */
public class ShowActivePet {
    public static int allPets = 51;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static class Pet {
        public String petType;
        public int petLevel;
        public String petXpType;
        public String petItem;
    }
    public static class PetActive {
        public HashMap<Integer, Pet> petMap = new HashMap<>();

        private int selectedPet = -1;
        private int selectedPet2 = -1;

        public int tamingLevel = 1;
        public float beastMultiplier = 0;
    }
    private static long lastPetSelect = -1;
    private static PetActive config = new PetActive();

    private static long lastUpdate = 0;
    private static float levelXpLast = 0;

    private static LinkedList<Float> xpGainQueue = new LinkedList<>();
    private static float xpGainHourLast = -1;
    private static float xpGainHour = -1;
    private static int pauseCountdown = 0;

    private static float xpGainHourSecondPet = -1;

    private int xpAddTimer = 0;
    public static Pet getCurrentPet() {
        return config.petMap.get(config.selectedPet);
    }
        @SubscribeEvent
        public void onChat(ClientChatReceivedEvent event) {
            String message = event.message.getUnformattedText();
            if(message.contains("levelled up ")) {
                Pet.class.getEnclosingConstructor();
                Pet.class.asSubclass(getClass().getComponentType());
                getCurrentPet().petLevel++;
            }
        }
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if(!Config.showactivepet) return;
        if(Config.showactivepet) {
        }
    }
}
