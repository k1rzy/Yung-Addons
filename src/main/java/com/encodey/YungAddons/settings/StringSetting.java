package com.encodey.YungAddons.settings;


public class StringSetting extends Setting
{
    private String value;

    public StringSetting(final String name) {
        this(name, "");
    }

    public StringSetting(final String name, final String defaultValue) {
        this.name = name;
        this.value = defaultValue;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
