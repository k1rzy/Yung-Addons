package com.encodey.YungAddons.utils.MathUtils;

import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.utils.PlayerUtil;
import com.encodey.YungAddons.utils.Utils;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Rotater implements IRotater{
    public static boolean rotating;
    protected float divider;
    protected long timeElapsed;
    protected float yaw;
    protected float pitch;
    protected float startYaw;
    protected float startPitch;
    protected float goalYaw;
    protected float goalPitch;

    public Rotater(final Vec3 target) {
        this.divider = 200.0f;
        final double diffX = target.xCoord - YungAddons.mc.thePlayer.posX;
        final double diffY = target.yCoord - (YungAddons.mc.thePlayer.posY + PlayerUtil.fastEyeHeight());
        final double diffZ = target.zCoord - YungAddons.mc.thePlayer.posZ;
        this.yaw = (float) MathHelper.wrapAngleTo180_double(MathHelper.atan2(diffZ, diffX) * 57.29577951308232 - 90.0 - YungAddons.mc.thePlayer.rotationYaw);
        this.pitch = (float)MathHelper.wrapAngleTo180_double(-(MathHelper.atan2(diffY, (double)MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ)) * 57.29577951308232) - YungAddons.mc.thePlayer.rotationPitch);
        this.startPitch = YungAddons.mc.thePlayer.rotationPitch;
        this.startYaw = YungAddons.mc.thePlayer.rotationYaw;
        this.goalYaw = this.yaw + YungAddons.mc.thePlayer.rotationYaw;
        this.goalPitch = this.pitch + YungAddons.mc.thePlayer.rotationPitch;
    }

    public Rotater(final float yawIn, final float pitchIn) {
        this.divider = 200.0f;
        this.yaw = yawIn;
        this.pitch = pitchIn;
        this.startPitch = YungAddons.mc.thePlayer.rotationPitch;
        this.startYaw = YungAddons.mc.thePlayer.rotationYaw;
        this.goalYaw = this.yaw + YungAddons.mc.thePlayer.rotationYaw;
        this.goalPitch = this.pitch + YungAddons.mc.thePlayer.rotationPitch;
    }

    public Rotater setPitch(final float pitch) {
        this.pitch = pitch;
        this.goalPitch = pitch + YungAddons.mc.thePlayer.rotationPitch;
        return this;
    }

    public Rotater setYaw(final float yaw) {
        this.yaw = yaw;
        this.goalYaw = yaw + YungAddons.mc.thePlayer.rotationYaw;
        return this;
    }

    public Rotater addPitch(final float pitch) {
        this.pitch += pitch;
        this.goalPitch += pitch;
        return this;
    }

    public Rotater addYaw(final float yaw) {
        this.yaw += yaw;
        this.goalYaw += yaw;
        return this;
    }

    public Rotater randomPitch() {
        this.pitch = MathUtils.randomFloat();
        this.goalPitch = this.pitch + YungAddons.mc.thePlayer.rotationPitch;
        return this;
    }

    public Rotater randomYaw() {
        this.yaw = MathUtils.randomFloat();
        this.goalYaw = this.yaw + YungAddons.mc.thePlayer.rotationYaw;
        return this;
    }

    public Rotater setRotationAmount(final int rotation) {
        this.divider = (float)(rotation * 10);
        return this;
    }

    @Override
    public void rotate() {
        Rotater.rotating = true;
        this.yaw /= this.divider;
        this.pitch /= this.divider;
        YungAddons.rotater = this;
        this.timeElapsed = System.currentTimeMillis();
    }

    @Override
    public void add() {
        final float elapsed = (float)(System.currentTimeMillis() - this.timeElapsed);
        if (elapsed >= this.divider) {
            YungAddons.mc.thePlayer.rotationYaw = this.goalYaw;
            YungAddons.mc.thePlayer.rotationPitch = MathHelper.clamp_float(this.goalPitch, -90.0f, 90.0f);
            YungAddons.rotater = null;
            return;
        }
        YungAddons.mc.thePlayer.rotationYaw = this.startYaw + this.yaw * elapsed;
        YungAddons.mc.thePlayer.rotationPitch = MathHelper.clamp_float(this.startPitch + this.pitch * elapsed, -90.0f, 90.0f);
    }

    public Rotater addRandom() {
        this.yaw += MathUtils.randomFloat();
        this.pitch += MathUtils.randomFloat();
        this.goalYaw = this.yaw + YungAddons.mc.thePlayer.rotationYaw;
        this.goalPitch = this.pitch + YungAddons.mc.thePlayer.rotationPitch;
        return this;
    }

    public Rotater randomYaw(final float min, final float rand, final boolean positive) {
        this.yaw += (positive ? (MathUtils.positiveFloat() * rand + min) : (MathUtils.negativeFloat() * rand - min));
        this.goalYaw = this.yaw + YungAddons.mc.thePlayer.rotationYaw;
        return this;
    }

    public Rotater randomPitch(final float min, final float rand, final boolean positive) {
        final float nextPitch = positive ? (MathUtils.positiveFloat() * rand + min) : (MathUtils.negativeFloat() * rand - min + this.pitch);
        final float nextGoalPitch = nextPitch + YungAddons.mc.thePlayer.rotationPitch;
        if (nextGoalPitch > 90.0f || nextGoalPitch < -90.0f) {
            return this.randomPitch(min, rand, !positive);
        }
        this.pitch += nextPitch;
        this.goalPitch = nextGoalPitch;
        return this;
    }

    public Rotater randomYaw(final float min, final float rand) {
        this.yaw += ((System.currentTimeMillis() % 2L == 0L) ? (MathUtils.positiveFloat() * rand + min) : (MathUtils.negativeFloat() * rand - min));
        this.goalYaw = this.yaw + YungAddons.mc.thePlayer.rotationYaw;
        return this;
    }

    public Rotater randomPitch(final float min, final float rand) {
        this.pitch += ((System.currentTimeMillis() % 2L == 0L) ? (MathUtils.positiveFloat() * rand + min) : (MathUtils.negativeFloat() * rand - min));
        this.goalPitch = this.pitch + YungAddons.mc.thePlayer.rotationPitch;
        return this;
    }

    public Rotater addRandomRotationAmount(final int rand) {
        this.divider += Utils.random.nextInt(rand * 10);
        return this;
    }

    public Rotater antiSus(final float amt) {
        this.divider = (float)((MathUtils.round((MathUtils.abs(this.yaw) + MathUtils.abs(this.pitch) + MathUtils.randomFloat()) / (amt + amt)) + 15) * 10);
        return this;
    }

    public static void legitRotation() {
        new Rotater(0.0f, 0.0f).randomYaw(60.0f, 30.0f).randomPitch(20.0f, 10.0f, false).setRotationAmount(20 + Utils.random.nextInt(4)).rotate();
    }
}
