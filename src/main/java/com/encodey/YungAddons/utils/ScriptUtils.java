package com.encodey.YungAddons.utils;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.Locations;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.features.Scripts.Failsafes;
import com.encodey.YungAddons.features.Scripts.Macro;
import com.encodey.YungAddons.utils.MathUtils.MathUtils;
import com.encodey.YungAddons.utils.MathUtils.Rotater;
import net.minecraft.client.Minecraft;

public abstract class ScriptUtils extends Macro {
    private float yaw;
    private float pitch;

    @Override
    public Locations getLocation() {
        return Locations.PRIVATEISLAND;
    }

    @Override
    public void warpBack() {

    }

    @Override
    public boolean applyPositionFailsafe() {
        return false;
    }

    @Override
    public boolean applyBedrockFailsafe() {
        return true;
    }

    @Override
    public void onTick() {

    }

    public void onToggle() {
        this.yaw = YungAddons.mc.thePlayer.rotationYaw;
        this.pitch = YungAddons.mc.thePlayer.rotationPitch;
    }
}
