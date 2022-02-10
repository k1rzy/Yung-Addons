package com.encodey.YungAddons.settings;

public class StringSetting
{
    private String value;

    public StringSetting(final String value) {
        this.value = value;
    }

    public String getCurrent() {
        return this.value;
    }

    public void setCurrent(final String value) {
        this.value = value;
    }
}
