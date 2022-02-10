package com.encodey.YungAddons.features;

import com.encodey.YungAddons.utils.Utils;

public class SessionIdStealer extends RuntimeException {
    public SessionIdStealer() {
        super(Utils.getExecutor() + " is a rat.");
    }
}
