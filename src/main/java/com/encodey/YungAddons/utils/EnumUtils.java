package com.encodey.YungAddons.utils;

import com.encodey.YungAddons.features.Mining.CrystalHollows.WormNotifier;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;

import java.net.URI;
import java.net.URISyntaxException;

public class EnumUtils {

    public enum TextStyle {;

        public TextStyle getNextType() {
            int nextType = ordinal() + 1;
            if (nextType > values().length - 1) {
                nextType = 0;
            }
            return values()[nextType];
        }
    }

    /** Different detection methods of the magma boss are more accurate than others, display how accurate the time is. */
    @Getter
    public enum MagmaTimerAccuracy {
        NO_DATA("N/A"),
        SPAWNED("NOW"),
        SPAWNED_PREDICTION("NOW"),
        EXACTLY(""),
        ABOUT("");

        private final String symbol;

        MagmaTimerAccuracy(String symbol) {
            this.symbol = symbol;
        }
    }

    @Getter
    public enum MagmaEvent {
        MAGMA_WAVE("magma"),
        BLAZE_WAVE("blaze"),
        BOSS_SPAWN("spawn"),
        BOSS_DEATH("death"),

        // Not actually an event
        PING("ping");

        // The event name used by InventiveTalent's API
        private final String inventiveTalentEvent;

        MagmaEvent(String inventiveTalentEvent) {
            this.inventiveTalentEvent = inventiveTalentEvent;
        }
    }

    public enum GuiTab {
        MAIN, GENERAL_SETTINGS
    }

    public enum DrawType {
        SKELETON_BAR,
        BAR,
        TEXT,
        PICKUP_LOG,
        DEFENCE_ICON,
        REVENANT_PROGRESS,
        POWER_ORB_DISPLAY,
        TICKER,
        BAIT_LIST_DISPLAY,
        TAB_EFFECT_TIMERS,
        DUNGEONS_MAP,
        SLAYER_TRACKERS,
        DRAGON_STATS_TRACKER,
        PROXIMITY_INDICATOR
    }

    @Getter
    public enum Social {
        YOUTUBE("youtube", "https://www.youtube.com/channel/UCYmE9-052frn0wQwqa6i8_Q"),
        DISCORD("discord", "https://biscuit.codes/discord"),
        GITHUB("github", "https://github.com/BiscuitDevelopment/SkyblockAddons");
        // Patreon removed due to ending of private betas
        // PATREON("patreon", "https://www.patreon.com/biscuitdev");

        private final ResourceLocation resourceLocation;
        private URI url;

        Social(String resourcePath, String url) {
            this.resourceLocation = new ResourceLocation("skyblockaddons", "gui/" + resourcePath + ".png");
            try {
                this.url = new URI(url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public enum GUIType {
        MAIN,
        EDIT_LOCATIONS,
        SETTINGS,
        WARP
    }

    @Getter
    public enum DiscordStatusEntry {
        DETAILS(0),
        STATE(1);

        private final int id;

        DiscordStatusEntry(int id) {
            this.id = id;
        }
    }

    public enum SlayerQuest {
        REVENANT_HORROR("Revenant Horror"),
        TARANTULA_BROODFATHER("Tarantula Broodfather"),
        SVEN_PACKMASTER("Sven Packmaster"),
        VOIDGLOOM_SERAPH("Voidgloom Seraph");

        private final String scoreboardName;

        SlayerQuest(String scoreboardName) {
            this.scoreboardName = scoreboardName;
        }

        public static SlayerQuest fromName(String scoreboardName) {
            for (SlayerQuest slayerQuest : SlayerQuest.values()) {
                if (slayerQuest.scoreboardName.equals(scoreboardName)) {
                    return slayerQuest;
                }
            }

            return null;
        }
    }
}
