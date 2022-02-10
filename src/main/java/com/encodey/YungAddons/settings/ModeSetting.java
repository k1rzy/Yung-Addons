package com.encodey.YungAddons.settings;

import java.util.*;

public class ModeSetting
{
    private String current;
    private final List<String> modes;

    public ModeSetting(final String current, final List<String> modes) {
        this.current = current;
        this.modes = modes;
    }

    public String getCurrent() {
        return this.current;
    }

    public void setCurrent(final String current) {
        this.current = current;
    }

    public List<String> getModes() {
        return this.modes;
    }
}
