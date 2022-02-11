package com.encodey.YungAddons;

import com.encodey.YungAddons.Core.Feature;
import com.encodey.YungAddons.lib.ColorCode;
import com.encodey.YungAddons.utils.ColorUtils;
import com.encodey.YungAddons.utils.EnumUtils;
import com.google.gson.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.Logger;

import java.awt.geom.Point2D;
import java.beans.Introspector;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class ConfigValues {

    private static final int CONFIG_VERSION = 9;

    private final static float GUI_SCALE_MINIMUM = 0.5F;
    private final static float GUI_SCALE_MAXIMUM = 5;

    private static final ReentrantLock SAVE_LOCK = new ReentrantLock();

    private final Map<Feature, Float> defaultGuiScales = new EnumMap<>(Feature.class);

    private final File settingsConfigFile;
    private JsonObject loadedConfig = new JsonObject();
    @Getter
    @Setter
    private JsonObject languageConfig = new JsonObject();

    @Getter
    private final Set<Feature> disabledFeatures = EnumSet.noneOf(Feature.class);
    private final Map<Feature, Integer> colors = new HashMap<>();
    private Map<Feature, Float> guiScales = new EnumMap<>(Feature.class);
    private final MutableInt warningSeconds = new MutableInt(4);
    private final Map<String, Set<Integer>> profileLockedSlots = new HashMap<>();
    @Getter
    private final Set<Feature> chromaFeatures = EnumSet.noneOf(Feature.class);
    @Deprecated
    private final MutableFloat oldChromaSpeed = new MutableFloat(0.19354838F); // 2.0
    @Getter
    private final List<String> discordCustomStatuses = new ArrayList<>();
    @Getter
    private final MutableFloat mapZoom = new MutableFloat(0.18478261F); // 1.3
    @Getter
    private final MutableFloat healingCircleOpacity = new MutableFloat(0.4);
    @Setter
    @Getter
    private MutableFloat chromaSize = new MutableFloat(30);
    @Getter
    private final MutableFloat chromaSpeed = new MutableFloat(6);
    @Getter
    private final MutableFloat chromaSaturation = new MutableFloat(0.75F);
    @Getter
    private final MutableFloat chromaBrightness = new MutableFloat(0.9F);

    public ConfigValues(File settingsConfigFile) {
        this.settingsConfigFile = settingsConfigFile;
    }

}
