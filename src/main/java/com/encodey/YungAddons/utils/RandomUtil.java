package com.encodey.YungAddons.utils;

import java.util.*;
import net.minecraft.util.*;

public class RandomUtil
{
    private static Random rand = new Random();

    public static Vec3 randomVec() {
        return new Vec3(RandomUtil.rand.nextDouble(), RandomUtil.rand.nextDouble(), RandomUtil.rand.nextDouble());
    }

    public static int randBetween(final int a, final int b) {
        return RandomUtil.rand.nextInt(b - a + 1) + a;
    }

    public static double randBetween(final double a, final double b) {
        return RandomUtil.rand.nextDouble() * (b - a) + a;
    }

    public static float randBetween(final float a, final float b) {
        return RandomUtil.rand.nextFloat() * (b - a) + a;
    }

    public static int nextInt(final int yep) {
        return RandomUtil.rand.nextInt(yep);
    }
}
