package com.encodey.YungAddons.settings;

public class BooleanSetting
{
    private boolean enabled;

    public BooleanSetting(final boolean enable) {
        this.enabled = enable;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setState(final boolean enable) {
        this.enabled = enable;
    }
}
