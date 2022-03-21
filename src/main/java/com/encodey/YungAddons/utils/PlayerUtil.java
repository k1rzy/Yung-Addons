package com.encodey.YungAddons.utils;

import com.encodey.YungAddons.utils.MathUtils.MathUtils;
import net.minecraft.realms.RealmsMth;
import net.minecraft.util.StringUtils;
import com.encodey.YungAddons.events.SkyblockJoin;
import net.minecraft.block.*;
import net.minecraft.client.*;
import java.lang.reflect.*;
import net.minecraft.client.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import java.util.*;

public class PlayerUtil
{
    public static final Set<Block> secretBlocks = new HashSet<Block>(Arrays.asList((Block)Blocks.chest, Blocks.lever, (Block)Blocks.skull, Blocks.trapped_chest));;
    private static final float STEP_SIZE = 0.1f;

    public static Minecraft mc = Minecraft.getMinecraft();

    public static void rightClick() {
        try {
            final Method method = Minecraft.class.getDeclaredMethod("rightClickMouse", (Class<?>[])new Class[0]);
            method.setAccessible(true);
            method.invoke(mc, new Object[0]);
        }
        catch (Exception ex) {}
    }

    public static void rightClick(final int clickamount) {
        try {
            final Method method = Minecraft.class.getDeclaredMethod("rightClickMouse", (Class<?>[])new Class[0]);
            method.setAccessible(true);
            for (int i = 0; i < clickamount; ++i) {
                method.invoke(mc, new Object[0]);
            }
        }
        catch (Exception ex) {}
    }

    public static void leftClick() {
        try {
            final Method method = Minecraft.class.getDeclaredMethod("clickMouse", (Class<?>[])new Class[0]);
            method.setAccessible(true);
            method.invoke(mc, new Object[0]);
        }
        catch (Exception ex) {}
    }

    public static void leftClick(final int clickAmount) {
        try {
            final Method method = Minecraft.class.getDeclaredMethod("clickMouse", (Class<?>[])new Class[0]);
            method.setAccessible(true);
            for (int i = 0; i < clickAmount; ++i) {
                method.invoke(mc, new Object[0]);
            }
        }
        catch (Exception ex) {}
    }

    public static void forceUpdateController() {
        try {
            final Method method = Minecraft.class.getDeclaredMethod("syncCurrentPlayItem", (Class<?>[])new Class[0]);
            method.setAccessible(true);
            method.invoke(mc, new Object[0]);
        }
        catch (Exception ex) {}
    }

    public static void updatePlayer() {
        try {
            final Method method = EntityPlayerSP.class.getDeclaredMethod("updateEntityActionState", (Class<?>[])new Class[0]);
            method.setAccessible(true);
            method.invoke(mc, new Object[0]);
        }
        catch (Exception ex) {}
    }
    public static int getAdjacentHotbarSlot() {
        if (mc.thePlayer.inventory.currentItem == 0) {
            return 1;
        }
        if (mc.thePlayer.inventory.currentItem > 6) {
            return mc.thePlayer.inventory.currentItem - 1;
        }
        return (System.currentTimeMillis() % 2L == 0L) ? (mc.thePlayer.inventory.currentItem - 1) : (mc.thePlayer.inventory.currentItem + 1);
    }
    public static Iterable<BlockPos> getPlayerBlocks() {
        return (Iterable<BlockPos>)BlockPos.getAllInBox(new BlockPos(mc.thePlayer.posX - 5.0, mc.thePlayer.posY + fastEyeHeight() - 5.0, mc.thePlayer.posZ - 5.0), new BlockPos(mc.thePlayer.posX + 5.0, mc.thePlayer.posY + fastEyeHeight() + 5.0, mc.thePlayer.posZ + 5.0));
    }
    public static Vec3 getPositionEyes() {
        return new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + fastEyeHeight(), mc.thePlayer.posZ);
    }
    public static float fastEyeHeight() {
        return mc.thePlayer.isSneaking() ? 1.54f : 1.62f;
    }
    public static boolean isStandingStill() {
        return (mc.thePlayer.motionX == 0.0 && mc.thePlayer.motionZ == 0.0) && mc.thePlayer.onGround;
    }
    public static boolean isStandingStillStuck() {
        return mc.thePlayer.motionX == 0.0 && mc.thePlayer.motionZ == 0.0;
    }
    public static int getMiningTool() {
        int gauntlet = -1;
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = mc.thePlayer.inventory.mainInventory[i];
            if (stack != null) {
                final String displayName = StringUtils.stripControlCodes(stack.getDisplayName());
                if (stack.getItem() == Items.prismarine_shard) {
                    if (displayName.contains("Drill")) {
                        return i;
                    }
                }
                else if (displayName.contains("Gemstone Gauntlet")) {
                    gauntlet = i;
                }
            }
        }
        if (gauntlet == -1) {
            for (int i = 0; i < 8; ++i) {
                final ItemStack stack = mc.thePlayer.inventory.mainInventory[i];
                if (stack != null && stack.getItem() instanceof ItemPickaxe) {
                    return i;
                }
            }
        }
        return gauntlet;
    }
    public static boolean switchDirection() {
        final double posX = Utils.formatDouble(MathUtils.abs(mc.thePlayer.posX));
        final double posZ = Utils.formatDouble(MathUtils.abs(mc.thePlayer.posZ));
        final double x = Utils.formatDouble(posX - MathUtils.floor(posX));
        if (x == 0.3 || x == 0.7) {
            return true;
        }
        final double z = Utils.formatDouble(posZ - MathUtils.floor(posZ));
        return z == 0.3 || z == 0.7;
    }
    public static boolean isInLimbo() {
        return !SkyblockJoin.inSkyblock && mc.theWorld.getScoreboard() == null && mc.getCurrentServerData().serverIP.contains("hypixel.") && !SkyblockJoin.isInSkyblock() && mc.theWorld != null;
    }
}