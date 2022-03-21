package com.encodey.YungAddons.features.Scripts;

import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.utils.MathUtils.IRotater;
import com.encodey.YungAddons.utils.MathUtils.MathUtils;
import com.encodey.YungAddons.utils.MathUtils.Rotater;
import com.encodey.YungAddons.utils.Utils;
import net.minecraft.util.MathHelper;

public class FailsafeRotater implements IRotater {
    protected float divider;
    protected long timeElapsed;
    protected float yaw;
    protected float pitch;
    protected float startYaw;
    protected float startPitch;
    protected float goalYaw;
    protected float goalPitch;
    protected long lastRotation;
    protected int nextRotation;
    private final int duration;
    private long totalTime;

    public FailsafeRotater() {
        this.duration = 3500;
    }

    public FailsafeRotater(final int duration) {
        this.duration = duration;
    }

    @Override
    public void rotate() {
        this.update();
        this.totalTime = System.currentTimeMillis();
        YungAddons.rotater = this;
        Rotater.rotating = true;
    }

    @Override
    public void add() {
        final long millis = System.currentTimeMillis();
        final double elapsed = (double)(millis - this.timeElapsed);
        if (elapsed >= this.divider) {
            if (millis - this.totalTime >= this.duration) {
                this.shutdown();
                return;
            }
            YungAddons.mc.thePlayer.rotationYaw = this.goalYaw;
            YungAddons.mc.thePlayer.rotationPitch = MathHelper.clamp_float(this.goalPitch, -90.0f, 90.0f);
            this.lastRotation = millis;
            this.timeElapsed = millis + millis;
            this.nextRotation = 250 + Utils.random.nextInt(400);
        }
        else {
            if (this.lastRotation != 0L) {
                if (millis - this.lastRotation >= this.nextRotation) {
                    this.update();
                    this.lastRotation = 0L;
                }
                return;
            }
            YungAddons.mc.thePlayer.rotationYaw = (float)(this.startYaw + this.yaw * elapsed);
            YungAddons.mc.thePlayer.rotationPitch = MathHelper.clamp_float((float)(this.startPitch + this.pitch * elapsed), -90.0f, 90.0f);
        }
    }

    private void update() {
        this.yaw = 66.0f + MathUtils.positiveFloat(30.0f);
        this.goalPitch = MathUtils.randomFloat(15.0f) - 5.0f;
        this.pitch = this.goalPitch - YungAddons.mc.thePlayer.rotationPitch;
        this.startPitch = YungAddons.mc.thePlayer.rotationPitch;
        this.startYaw = YungAddons.mc.thePlayer.rotationYaw;
        this.divider = (float)((MathUtils.round(((MathUtils.abs(this.yaw) + MathUtils.abs(this.pitch)) / 2.0f + MathUtils.randomFloat()) / (1.85f * (1.0f + MathUtils.randomFloat() / 5.0f))) + 19) * 10);
        this.goalYaw = this.yaw + YungAddons.mc.thePlayer.rotationYaw;
        this.yaw /= this.divider;
        this.pitch /= this.divider;
        this.timeElapsed = System.currentTimeMillis();
    }
}
