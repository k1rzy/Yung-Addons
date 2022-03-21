package com.encodey.YungAddons.features.Scripts;
import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.features.Farming.ProfitCalc;
import com.encodey.YungAddons.mixins.MixinNetHandlerPlayClient;
import gg.essential.universal.UChat;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import com.encodey.YungAddons.listeners.*;

import java.util.*;

public class FastBreak implements LeftClickListener
{
    public static Block currentBlock;
    private float currentDamage;
    private EnumFacing side;
    private int blockHitDelay;
    public static Block id;
    private BlockPos pos;
    public static BlockPos blockpos;

    public float mineSpeed;
    public float maxBlocks;
    private NetHandlerPlayClient netClientHandler;

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if(Config.fastbreakbps == 0) mineSpeed=1.08f;
        if(Config.fastbreakbps == 1) mineSpeed=0.5f;
        if(Config.fastbreakbps == 2) mineSpeed=1.2f;
        if(Config.fastbreakbps == 3) mineSpeed=1.4f;
        if(Config.fastbreakbps == 4) mineSpeed=1.9f;
        try {
            if (Minecraft.getMinecraft().objectMouseOver == null || Minecraft.getMinecraft().objectMouseOver.getBlockPos() == null) {
                return;
            }
            if(YungAddons.displayScreen != null || Minecraft.getMinecraft().currentScreen != null) return;
            if(!Config.fastbreak || Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock().getMaterial() == Material.air || Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock() == Blocks.soul_sand || Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock() == Blocks.farmland || Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock() == Blocks.dirt || Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock() == Blocks.stonebrick || Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock() == Blocks.standing_sign || Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock() == Blocks.ladder) return;

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (Minecraft.getMinecraft().objectMouseOver == null || Minecraft.getMinecraft().objectMouseOver.getBlockPos() == null) {
                return;
            }
            if (blockHitDelay > 0) {
                --blockHitDelay;
                return;
            }
            if(!mc.gameSettings.keyBindAttack.isKeyDown()) {
                return;
            }
            final Block block;
            blockpos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
            this.pos = blockpos;
            if (blockHitDelay > 0) return;
            if (blockpos == null || !this.pos.equals(blockpos)) {
                currentDamage = 0.0f;
            }
            currentBlock = Minecraft.getMinecraft().theWorld.getBlockState(blockpos).getBlock();
            if (currentDamage == 0f) {
                if (blockHitDelay > 0) return;
                currentBlock.onBlockClicked(mc.theWorld, blockpos, mc.thePlayer);
                //this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockpos, Minecraft.getMinecraft().objectMouseOver.sideHit));
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockpos, Minecraft.getMinecraft().objectMouseOver.sideHit));
                if (currentBlock.getPlayerRelativeBlockHardness(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld, blockpos) >= 1.0f) {
                    if(Config.fastbreakbps == 0) blockHitDelay = 25;
                    if(Config.fastbreakbps == 1) blockHitDelay = 100;
                    if(Config.fastbreakbps == 2) blockHitDelay = 16;
                    if(Config.fastbreakbps == 3) blockHitDelay = 4;
                    if(Config.fastbreakbps == 4) blockHitDelay = 2;
                    //this.currentDamage = 0.1f;
                    if (Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock().getMaterial() != Material.air) {
                        //KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), true);
                        //Minecraft.getMinecraft().thePlayer.swingItem();
                    }
                    Minecraft.getMinecraft().playerController.onPlayerDestroyBlock(blockpos, Minecraft.getMinecraft().objectMouseOver.sideHit);
                    return;
                }
            }
            if (Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock().getMaterial() != Material.air) {
                if (blockHitDelay > 0) return;
                block = Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock();
                currentDamage += currentBlock.getPlayerRelativeBlockHardness(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld, blockpos) * ((Config.fastbreak) ? mineSpeed : 1.0f);
            }
            if (Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock().getMaterial() != Material.air) {
                if (blockHitDelay > 0) return;
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            }
            Minecraft.getMinecraft().theWorld.sendBlockBreakProgress(Minecraft.getMinecraft().thePlayer.getEntityId(), blockpos, (int) (currentDamage * 2.55f) - 1);
            if (currentDamage >= 1.0f) {
                if (blockHitDelay > 0) return;
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, blockpos, Minecraft.getMinecraft().objectMouseOver.sideHit));
                //this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockpos, Minecraft.getMinecraft().objectMouseOver.sideHit));
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockpos, Minecraft.getMinecraft().objectMouseOver.sideHit));
                currentDamage = 0;
                if(Config.fastbreakbps == 0) blockHitDelay = 25;
                if(Config.fastbreakbps == 1) blockHitDelay = 100;
                if(Config.fastbreakbps == 2) blockHitDelay = 16;
                if(Config.fastbreakbps == 3) blockHitDelay = 8;
                if(Config.fastbreakbps == 4) blockHitDelay = 2;
            }  else {
                //Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockpos, Minecraft.getMinecraft().objectMouseOver.sideHit));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onLeftClick() {
        if (Minecraft.getMinecraft().objectMouseOver == null || Minecraft.getMinecraft().objectMouseOver.getBlockPos() == null) {
            return;
        }
        if (Config.fastbreak && Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock().getMaterial() != Material.air) {
            id = Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock();
        }
    }
}
