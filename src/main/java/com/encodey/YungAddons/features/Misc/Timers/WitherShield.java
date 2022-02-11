package com.encodey.YungAddons.features.Misc.Timers;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.settings.BooleanSetting;
import com.encodey.YungAddons.settings.FloatSetting;
import com.encodey.YungAddons.settings.IntegerSetting;
import com.encodey.YungAddons.utils.FontUtils;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import java.awt.*;

public class WitherShield
{
    private IntegerSetting xPos;
    private IntegerSetting yPos;
    private FloatSetting scale;
    public static long LastShield = 0L;

    public WitherShield() {
        this.xPos = new IntegerSetting(0, 0, 1000);
        this.yPos = new IntegerSetting(0, 0, 1000);
        this.scale = new FloatSetting(1.0f, 0.0f, 2.5f);
    }
}
