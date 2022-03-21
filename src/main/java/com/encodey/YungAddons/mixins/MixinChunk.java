package com.encodey.YungAddons.mixins;
import com.encodey.YungAddons.events.BlockChangeEvent;
import org.spongepowered.asm.mixin.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Chunk.class })
public class MixinChunk
{
    @Inject(method = { "setBlockState" }, at = { @At("HEAD") })
    private void onBlockChange(final BlockPos pos, final IBlockState state, final CallbackInfoReturnable<IBlockState> cir) {
        MinecraftForge.EVENT_BUS.post(new BlockChangeEvent(pos, state));
    }
}
