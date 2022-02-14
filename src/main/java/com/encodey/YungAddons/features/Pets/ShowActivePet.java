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

public class ShowActivePet {
    // i had pain inm my fidcuking as while doing thisk oishit
    public static int allPets = 51;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public enum Rarity {
        COMMON(0, 0, 1, EnumChatFormatting.WHITE),
        UNCOMMON(6, 1, 2, EnumChatFormatting.GREEN),
        RARE(11, 2, 3, EnumChatFormatting.BLUE),
        EPIC(16, 3, 4, EnumChatFormatting.DARK_PURPLE),
        LEGENDARY(20, 4, 5, EnumChatFormatting.GOLD),
        MYTHIC(20, 4, 5, EnumChatFormatting.LIGHT_PURPLE);
        public int petOffset;
        public EnumChatFormatting chatFormatting;
        public int petId;
        public int beastcreatMultiplyer;
        Rarity(int petOffset, int petId, int beastcreatMultiplyer, EnumChatFormatting chatFormatting) {
            this.chatFormatting = chatFormatting;
            this.petOffset = petOffset;
            this.petId = petId;
            this.beastcreatMultiplyer = beastcreatMultiplyer;
        }
        public static Rarity getRarityFromColor(EnumChatFormatting chatFormatting) {
            for(int i = 0; i < Rarity.values().length; i++) {
                if(Rarity.values()[i].chatFormatting.equals(chatFormatting))
                    return Rarity.values()[i];
            }
            return COMMON;
        }
    }
    public static class Pet {
        public String petType;
        public Rarity rarity;
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

    public static void saveConfig(File file) {
        try {
            file.createNewFile();
            try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
                writer.write(GSON.toJson(config));
            }
        } catch(Exception ignored) {}
    }
    public static void setCurrentPet(int index) {
        config.selectedPet2 = config.selectedPet;
        xpGainHourSecondPet = xpGainHour;
        xpGainHourLast = xpGainHour;
        xpGainQueue.clear();
        config.selectedPet = index;
    }
    public static Pet getCurrentPet() {
        return config.petMap.get(config.selectedPet);
    }
    public static Pet getCurrentPet2() {
        if(!Config.showactivepet) return null;
        if(config.selectedPet == config.selectedPet2) return null;
        return config.petMap.get(config.selectedPet2);
    }
    public static void clearPet() {
        config.selectedPet = -1;
        config.selectedPet2 = -1;
    }
    private static int getIdForPet(Pet pet) {
        for (Map.Entry<Integer, Pet> entry : config.petMap.entrySet()) {
            if (entry.getValue() == pet) {
                return entry.getKey();
            }
        } return -1;
    }
    private static int getClosestPetIndex (String petType,int petId, String petItem,float petLevel){
        Pet pet = getClosestPet(petType, petId, petItem, petLevel);
        if (pet == null) {
            return -1;
        } else {
            return getIdForPet(pet);
        }
    }

    private static Pet getClosestPet (String petType,int petId, String petItem,float petLevel){
        Set<Pet> pets = new HashSet<>();
        for (Pet pet : config.petMap.values()) {
            if (pet.petType.equals(petType) && pet.rarity.petId == petId) {
                pets.add(pet);
            }
        }

        if (pets == null || pets.isEmpty()) {
                return null;
        }

        if (pets.size() == 1) {
            return pets.iterator().next();
        }

        String searchItem = petItem;

        Set<Pet> itemMatches = new HashSet<>();
        for (Pet pet : pets) {
            if ((searchItem == null && pet.petItem == null) ||
                    (searchItem != null && searchItem.equals(pet.petItem))) {
                itemMatches.add(pet);
                }
            }

            if (itemMatches.size() == 1) {
                return itemMatches.iterator().next();
            }
            if (itemMatches.size() > 1) {
                pets = itemMatches;
            }

            float closestXp = -1;
            Pet closestPet = null;

            for (Pet pet : pets) {
                final val petLevel1 = pet.petLevel;
                float distXp = Math.abs(petLevel1 - petLevel);

                if (closestPet == null || distXp < closestXp) {
                    closestXp = distXp;
                    closestPet = pet;
                }
            }

            if (closestPet != null) {
                return closestPet;
            } else {
                return pets.iterator().next();
            }
        }

        private float interp(float now, float last) {
            float interp = now;
            if (last >= 0 && last != now) {
                float factor = (System.currentTimeMillis() - lastUpdate) / 1000f;
                factor = LerpUtils.clampZeroOne(factor);
                interp = last + (now - last) * factor;
            }
            return interp;
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
