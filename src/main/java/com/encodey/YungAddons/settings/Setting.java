package com.encodey.YungAddons.settings;

import com.google.gson.annotations.*;

public class Setting {
    @Expose
    @SerializedName("name")
    public String name;
    private boolean hidden;

    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return this.hidden;
    }
}
