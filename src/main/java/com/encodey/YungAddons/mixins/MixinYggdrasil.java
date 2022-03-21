package com.encodey.YungAddons.mixins;

import com.encodey.YungAddons.features.SessionIDProt;
import org.spongepowered.asm.mixin.*;
import com.mojang.authlib.yggdrasil.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { YggdrasilMinecraftSessionService.class }, priority = Integer.MAX_VALUE, remap = false)
public class MixinYggdrasil
{
    // removed
}
