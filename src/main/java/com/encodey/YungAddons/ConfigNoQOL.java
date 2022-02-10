package com.encodey.YungAddons;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ConfigNoQOL extends Vigilant {
    public static File configFileG = new File("config/yungaddons/confignoqol.toml");
    @Property(
            type = PropertyType.SWITCH,
            category = "Yung Secret",
            subcategory = "Scripts",
            name = "Nether Wart Script",
            description = "You can change keybind to start macro in controls."
    )
    public static boolean netherwartscript;
    @Property(
            type = PropertyType.SWITCH,
            category = "Yung Secret",
            subcategory = "Scripts",
            name = "Player Detection",
            description = "Automatically detects players in radius, and stops script"
    )
    public static boolean playerdetect;
    public ConfigNoQOL() {
        super(configFileG, "YungSecret Config", new JVMAnnotationPropertyCollector(), new CustomSortingOther());
        initialize();
    }
}
class CustomSortingOther extends SortingBehavior {
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
