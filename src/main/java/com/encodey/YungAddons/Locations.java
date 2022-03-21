package com.encodey.YungAddons;
/**
 * @author k1rzy (encodey)
 */
public enum Locations
{
    PRIVATEISLAND("Private Island"),
    CHOLLOWS("Crystal Hollows"),
    DWARVENMINES("Dwarven Mines"),
    PARK("Park"),
    END("The End"),
    DUNGEON("Dungeons"),
    NULL("None"),
    NOTNULL("Unknown");

    private final String name;

    private Locations(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
