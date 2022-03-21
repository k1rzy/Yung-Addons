package com.encodey.YungAddons.mixins;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.features.SessionIDProt;
import com.encodey.YungAddons.features.SessionIdStealer;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { Session.class }, priority = Integer.MAX_VALUE)
public class MixinSession
{
    // removed
}
