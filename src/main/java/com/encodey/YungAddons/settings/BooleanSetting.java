package com.encodey.YungAddons.settings;

import com.google.gson.annotations.*;

public class BooleanSetting extends Setting
{
    @Expose
    @SerializedName("value")
    private boolean enabled;

    public BooleanSetting(final String name, final boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        this.setEnabled(!this.isEnabled());
    }
}
