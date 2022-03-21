package com.encodey.YungAddons.features.ESP;
import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.utils.RenderUtils;
import com.encodey.YungAddons.utils.UtilsDanker;
import java.awt.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/**
 * @author k1rzy (encodey)
 */
public class BlockOutline {
    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if(!Config.blockoutline) return;
        if(Minecraft.getMinecraft().objectMouseOver == null || Minecraft.getMinecraft().objectMouseOver.getBlockPos() == null) { return; }
        if(mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() == Blocks.air) return;
        BlockPos pos = mc.objectMouseOver.getBlockPos();
        AxisAlignedBB aabb = new AxisAlignedBB(pos.getX()-0.5, pos.getY() + 1, pos.getZ()-0.5, pos.getX(), pos.getY(), pos.getZ());
        RenderUtils.drawBlockBox(pos, Color.CYAN, true, event.partialTicks);
    }
}
