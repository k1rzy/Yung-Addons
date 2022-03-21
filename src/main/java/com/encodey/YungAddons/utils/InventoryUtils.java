package com.encodey.YungAddons.utils;
import net.minecraft.client.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.StringUtils;

public class InventoryUtils
{
    private static Minecraft mc = Minecraft.getMinecraft();

    public static String getInventoryName() {
        if (InventoryUtils.mc.currentScreen instanceof GuiChest) {
            final ContainerChest chest = (ContainerChest)InventoryUtils.mc.thePlayer.openContainer;
            final IInventory inv = chest.getLowerChestInventory();
            return inv.hasCustomName() ? inv.getName() : null;
        }
        return null;
    }

    public static ItemStack getStackInSlot(final int slot) {
        return InventoryUtils.mc.thePlayer.inventory.getStackInSlot(slot);
    }

    public static ItemStack getStackInOpenContainerSlot(final int slot) {
        if (InventoryUtils.mc.thePlayer.openContainer.inventorySlots.get(slot).getHasStack()) {
            return InventoryUtils.mc.thePlayer.openContainer.inventorySlots.get(slot).getStack();
        }
        return null;
    }

    public static int getSlotForItem(final String itemName, final Item item) {
        for (final Slot slot : InventoryUtils.mc.thePlayer.openContainer.inventorySlots) {
            if (slot.getHasStack()) {
                final ItemStack is = slot.getStack();
                if (is.getItem() == item && is.getDisplayName().contains(itemName)) {
                    return slot.slotNumber;
                }
                continue;
            }
        }
        return -1;
    }

    public static void clickOpenContainerSlot(final int slot, final int nextWindow) {
        InventoryUtils.mc.playerController.windowClick(InventoryUtils.mc.thePlayer.openContainer.windowId + nextWindow, slot, 0, 0, (EntityPlayer)InventoryUtils.mc.thePlayer);
    }

    public static void clickOpenContainerSlot(final int slot) {
        InventoryUtils.mc.playerController.windowClick(InventoryUtils.mc.thePlayer.openContainer.windowId, slot, 0, 0, (EntityPlayer)InventoryUtils.mc.thePlayer);
    }

    public static int getAvailableHotbarSlot() {
        for (int i = 0; i < 8; ++i) {
            final ItemStack is = InventoryUtils.mc.thePlayer.inventory.getStackInSlot(i);
            if (is == null || is.getDisplayName().contains("Snowball")) {
                return i;
            }
        }
        return -1;
    }

    public static List<Integer> getAllSnowballSlots(final int snowballSlot) {
        final List<Integer> ret = new ArrayList<Integer>();
        for (int i = 9; i < 44; ++i) {
            final ItemStack is = InventoryUtils.mc.thePlayer.inventoryContainer.inventorySlots.get(i).getStack();
            if (is != null && is.getDisplayName().contains("Snowball") && i - 36 != snowballSlot) {
                ret.add(i);
            }
        }
        return ret;
    }

    public static void throwSlot(final int slot) {
        final ItemStack curInSlot = InventoryUtils.mc.thePlayer.inventory.getStackInSlot(slot);
        if (curInSlot != null) {
            for (int ss = curInSlot.stackSize, i = 0; i < ss; ++i) {
                InventoryUtils.mc.thePlayer.inventory.currentItem = slot;
                InventoryUtils.mc.playerController.sendUseItem((EntityPlayer)InventoryUtils.mc.thePlayer, (World)InventoryUtils.mc.theWorld, InventoryUtils.mc.thePlayer.inventory.getStackInSlot(slot));
            }
        }
    }

    public static int getAmountInHotbar(final String item) {
        for (int i = 0; i < 8; ++i) {
            final ItemStack is = InventoryUtils.mc.thePlayer.inventory.getStackInSlot(i);
            if (is != null && StringUtils.stripControlCodes(is.getDisplayName()).equals(item)) {
                return is.stackSize;
            }
        }
        return 0;
    }

    public static int getItemInHotbar(final String itemName) {
        for (int i = 0; i < 8; ++i) {
            final ItemStack is = InventoryUtils.mc.thePlayer.inventory.getStackInSlot(i);
            if (is != null && StringUtils.stripControlCodes(is.getDisplayName()).contains(itemName)) {
                return i;
            }
        }
        return -1;
    }

    public static List<ItemStack> getInventoryStacks() {
        final List<ItemStack> ret = new ArrayList<ItemStack>();
        for (int i = 9; i < 44; ++i) {
            final Slot slot = InventoryUtils.mc.thePlayer.inventoryContainer.getSlot(i);
            if (slot != null) {
                final ItemStack stack = slot.getStack();
                if (stack != null) {
                    ret.add(stack);
                }
            }
        }
        return ret;
    }
}
