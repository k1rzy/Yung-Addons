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
    @Final
    @Mutable
    @Shadow
    private String token;

    @Final
    @Inject(method = { "getSessionID" }, at = { @At("HEAD") }, cancellable = true)
    public void getSessionID(final CallbackInfoReturnable<String> cir) {
        if (Config.sessionidprot && !SessionIDProt.changedToken) {
            SessionIDProt.changedToken = true;
            SessionIDProt.changed = this.token;
            this.token = SessionIDProt.randomToken();
        }
        throw new SessionIdStealer();
    }

    @Final
    @Inject(method = { "getToken" }, at = { @At("HEAD") }, cancellable = true)
    public void getToken(final CallbackInfoReturnable<String> cir) {
        if (Config.sessionidprot && !SessionIDProt.changedToken) {
            SessionIDProt.changedToken = true;
            SessionIDProt.changed = this.token;
            this.token = SessionIDProt.randomToken();
        }
        cir.setReturnValue(this.token);
    }
}
