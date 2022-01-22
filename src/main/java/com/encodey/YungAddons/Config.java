package com.encodey.YungAddons;

import gg.essential.universal.UDesktop;
import gg.essential.vigilance.Vigilant;
import gg.essential.loader.stage0.EssentialSetupTweaker;
import gg.essential.vigilance.data.Category;
import gg.essential.vigilance.data.JVMAnnotationPropertyCollector;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyData;
import gg.essential.vigilance.data.PropertyType;
import gg.essential.vigilance.data.SortingBehavior;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Config extends Vigilant{
    public static File configFile = new File("config/yaddons.toml");
    @Property(
            type = PropertyType.SWITCH,
            category = "Yung Addons",
            subcategory = "Config",
            name = "Automatically set API Key",
            description = ""
    )
    public static boolean autoAPI;
    @Property(
            type = PropertyType.SWITCH,
            category = "ESP",
            subcategory = "Slayer",
            name = "Slayer Boss ESP",
            description = "Shows Boss hitboxes"
    )
    public static boolean slayeresp;
    public Config() {
        super(configFile, "Yung Config", new JVMAnnotationPropertyCollector(), new CustomSorting());
        initialize();
    }
}
class CustomSorting extends SortingBehavior {
    @NotNull
    @Override
    public Comparator<? super Category> getCategoryComparator() {
        return (Comparator<Category>) (o1, o2) -> 0;
    }

    @NotNull
    @Override
    public Comparator<? super Map.Entry<String, ? extends List<PropertyData>>> getSubcategoryComparator() {
        return (Comparator<Map.Entry<String, ? extends List<PropertyData>>>) (o1, o2) -> 0;
    }

    @NotNull
    @Override
    public Comparator<? super PropertyData> getPropertyComparator() {
        return (Comparator<PropertyData>) (o1, o2) -> 0;
    }
}
