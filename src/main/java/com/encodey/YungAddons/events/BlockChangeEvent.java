package com.encodey.YungAddons.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
/**
 * @author k1rzy (encodey)
 */
public class BlockChangeEvent extends Event
{
    public BlockPos pos;
    public IBlockState state;

    public BlockChangeEvent(final BlockPos pos, final IBlockState state) {
        this.pos = pos;
        this.state = state;
    }
}
