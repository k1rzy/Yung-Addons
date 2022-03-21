package com.encodey.YungAddons.utils.MathUtils;

import com.encodey.YungAddons.YungAddons;

public interface IRotater
{
    void rotate();

    void add();

    default void shutdown() {
        YungAddons.rotater = null;
    }
}
