package com.encodey.YungAddons.features.Scripts;

import com.encodey.YungAddons.Locations;

public abstract class Macro
{
    public Thread miscThread;

    public Macro() {
        this.miscThread = new Thread(() -> {});
    }

    public void addToggleMessage(final String name) {

    }

    public abstract void onTick();

    public abstract void onToggle(final boolean p0);

    public void onRender() {
    }

    public void onDisable() {
    }

    public void onMove() {
    }

    public void onChat(final String msg) {
    }

    public void warpBack() {

    }

    public void writeToWebhook(final String content) {

    }

    public boolean applyFailsafes() {
        return true;
    }

    public Locations getLocation() {
        return null;
    }

    public boolean applyPositionFailsafe() {
        return false;
    }

    public boolean applyBedrockFailsafe() {
        return true;
    }

    public void enqueueThread(final Runnable runnable) {
        (this.miscThread = new Thread(runnable)).start();
    }
}
