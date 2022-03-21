package com.encodey.YungAddons.utils.MathUtils;

import com.encodey.YungAddons.utils.Utils;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class MathUtils {
    public static final float PI = 3.1415927f;
    public static final float PI_180 = 0.017453292f;
    public static final double ROTATION_NUMBER = 57.29577951308232;
    public static final float ROTATION_NUMBER_F = 57.29578f;
    public static final float PLAYER_REACH_SQ = 20.25f;

    public static int floor(final float value) {
        final int i = (int)value;
        return (value < i) ? (i - 1) : i;
    }

    public static int floor(final double value) {
        final int i = (int)value;
        return (value < i) ? (i - 1) : i;
    }

    public static long floor_long(final double value) {
        final long i = (long)value;
        return (value < i) ? (i - 1L) : i;
    }

    public static int round(final float value) {
        final int floor = floor(value);
        return (value >= floor + 0.5) ? (floor + 1) : floor;
    }

    public static int round(final double value) {
        final int floor = floor(value);
        return (value >= floor + 0.5) ? (floor + 1) : floor;
    }
    public static int roundUp(final float value) {
        final int floor = floor(value);
        return (value >= floor + 0.5) ? (floor + 1) : floor;
    }

    public static int roundUp(final double value) {
        final int floor = floor(value);
        return (value >= floor + 0.5) ? (floor + 1) : floor;
    }
    public static int roundDown(final float value) {
        final int floor = floor(value);
        return (value > floor + 0.5) ? (floor + 1) : floor;
    }

    public static int roundDown(final double value) {
        final int floor = floor(value);
        return (value > floor + 0.5) ? (floor + 1) : floor;
    }

    public static int ceil(final float value) {
        final int i = (int)value;
        return (value > i) ? (i + 1) : i;
    }

    public static int ceil(final double value) {
        final int i = (int)value;
        return (value > i) ? (i + 1) : i;
    }

    public static int abs(final int value) {
        return (value >= 0) ? value : (-value);
    }

    public static float abs(final float value) {
        return (value >= 0.0f) ? value : (-value);
    }

    public static double abs(final double value) {
        return (value >= 0.0) ? value : (-value);
    }

    public static double randomDouble() {
        return (System.currentTimeMillis() % 2L == 0L) ? Utils.random.nextDouble() : (-Utils.random.nextDouble());
    }

    public static float randomFloat() {
        return (System.currentTimeMillis() % 2L == 0L) ? Utils.random.nextFloat() : (-Utils.random.nextFloat());
    }

    public static float positiveFloat() {
        return Utils.random.nextFloat();
    }

    public static float negativeFloat() {
        return -Utils.random.nextFloat();
    }

    public static float randomFloat(final float multiplier) {
        return (System.currentTimeMillis() % 2L == 0L) ? (Utils.random.nextFloat() * multiplier) : (-Utils.random.nextFloat() * multiplier);
    }

    public static float positiveFloat(final float multiplier) {
        return Utils.random.nextFloat() * multiplier;
    }

    public static float negativeFloat(final float multiplier) {
        return -Utils.random.nextFloat() * multiplier;
    }

    public static int randomNumber(final int bound) {
        return (System.currentTimeMillis() % 2L == 0L) ? Utils.random.nextInt(bound) : (-Utils.random.nextInt(bound));
    }

    public static boolean inBetween(final float value, final float i, final float i2) {
        return max(i, i2) >= value && min(i, i2) <= value;
    }

    public static boolean inBetween(final double value, final float i, final float i2) {
        return max(i, i2) >= value && min(i, i2) <= value;
    }

    public static boolean inBetween(final double value, final double i, final double i2) {
        return max(i, i2) >= value && min(i, i2) <= value;
    }

    public static Vec3 randomVec() {
        return new Vec3(Utils.random.nextDouble(), Utils.random.nextDouble(), Utils.random.nextDouble());
    }

    public static double min(final double a, final double b) {
        return (a < b) ? a : b;
    }

    public static float min(final float a, final float b) {
        return (a < b) ? a : b;
    }

    public static long min(final long a, final long b) {
        return (a < b) ? a : b;
    }

    public static int min(final int a, final int b) {
        return (a < b) ? a : b;
    }

    public static double max(final double a, final double b) {
        return (a >= b) ? a : b;
    }

    public static float max(final float a, final float b) {
        return (a >= b) ? a : b;
    }

    public static int max(final int a, final int b) {
        return (a >= b) ? a : b;
    }

    public static double max(final Vec3 vec) {
        return max(max(vec.xCoord, vec.yCoord), max(vec.yCoord, vec.zCoord));
    }

    public static double min(final Vec3 vec) {
        return min(min(vec.xCoord, vec.yCoord), min(vec.yCoord, vec.zCoord));
    }

    public static float getRandomInBetween(final float i, final float i2) {
        final float min = min(i, i2);
        return Utils.random.nextFloat() * (max(i, i2) - min) + min;
    }

    public static double getRandomInBetween(final double i, final double i2) {
        final double min = min(i, i2);
        return Utils.random.nextDouble() * (max(i, i2) - min) + min;
    }

    public static float randomNegative(final float numb) {
        return (System.currentTimeMillis() % 2L == 0L) ? numb : (-numb);
    }

    public static double randomNegative(final double numb) {
        return (System.currentTimeMillis() % 2L == 0L) ? numb : (-numb);
    }
}
