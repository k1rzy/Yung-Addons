package com.encodey.YungAddons.mixins;

import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.utils.FontUtils;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ FontRenderer.class })
public class MixinFontRenderer
{
    @Inject(method = { "drawStringWithShadow" }, at = { @At("HEAD") }, cancellable = true)
    private void onDrawShadowString(final String text, final float x, final float y, final int color, final CallbackInfoReturnable<Integer> cir) {
        if (text.startsWith("§rYung")) {
            YungAddons.mc.fontRendererObj.drawString(text.substring(9), FontUtils.drawRainbowModMessage(x, y), y, color, true);
            cir.setReturnValue(0);
        }
    }
}
