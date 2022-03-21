package com.encodey.YungAddons.mixins;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ PlayerControllerMP.class })
public class PlayerControllerMixin
{

    private float hardnessPort;

    @Redirect(method = { "onPlayerDamageBlock" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)F"))
    public float onPlayerDamageBlock(Block instance, EntityPlayer playerIn, World worldIn, BlockPos pos) {
        if(Minecraft.getMinecraft().objectMouseOver != null || Minecraft.getMinecraft().objectMouseOver.getBlockPos() != null) {
            pos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
            instance = Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock();
            worldIn = Minecraft.getMinecraft().theWorld;
            float hardness = instance.getPlayerRelativeBlockHardness(playerIn, worldIn, pos);
            if (!Config.fastbreak) {
                hardness *= 2;
                //hardnessPort = hardness;
            }
        }
        return hardnessPort;
    }
}
