package com.encodey.YungAddons.settings;

import com.google.gson.annotations.*;
import org.lwjgl.input.*;
import java.util.*;

import com.google.gson.annotations.*;
import org.lwjgl.input.*;
import java.util.*;

public class Module
{
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("toggled")
    private boolean toggled;
    @Expose
    @SerializedName("keyCode")
    private int keycode;
    private Category category;
    public boolean extended;
    @Expose
    @SerializedName("settings")
    private boolean devOnly;
    public List<Setting> settings;

    public Module(final String name, final int keycode, final Category category) {
        this.settings = new ArrayList<Setting>();
        this.name = name;
        this.keycode = keycode;
        this.category = category;
    }

    public Module(final String name, final Category category) {
        this.settings = new ArrayList<Setting>();
        this.name = name;
        this.keycode = 0;
        this.category = category;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
    }

    public void onEnable() {
    }

    public boolean isKeybind() {
        return false;
    }

    public void addSetting(final Setting setting) {
        this.getSettings().add(setting);
    }

    public void addSettings(final Setting... settings) {
        for (final Setting setting : settings) {
            this.addSetting(setting);
        }
    }

    public Category getCategory() {
        return this.category;
    }

    public String getName() {
        return this.name;
    }

    public boolean isPressed() {
        return this.keycode != 0 && Keyboard.isKeyDown(this.keycode) && this.isKeybind();
    }

    public int getKeycode() {
        return this.keycode;
    }

    public void setKeycode(final int keycode) {
        this.keycode = keycode;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }


    public void setToggled(final boolean toggled) {
        this.toggled = toggled;
    }

    public void onDisable() {
    }

    public void setDevOnly(final boolean devOnly) {
        this.devOnly = devOnly;
    }

    public boolean isDevOnly() {
        return this.devOnly;
    }

    public enum Category
    {
        COMBAT("Combat"),
        Skyblock("Skyblock"),
        RENDER("Render"),
        PLAYER("Player"),
        MACRO("Macros"),
        OTHER("Other");

        public String name;

        private Category(final String name) {
            this.name = name;
        }
    }
}
