package com.encodey.YungAddons.features.Experiments;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.Core.ChestGui;
import com.encodey.YungAddons.utils.UtilsDanker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;


public class ExpSolver {

    static Slot[] clickInOrderSlots = new Slot[36];
    static int lastUltraSequencerClicked = 0;
    public static int ULTRASEQUENCER_NEXT;
    public static int ULTRASEQUENCER_NEXT_TO_NEXT;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (mc.currentScreen instanceof GuiChest) {
            if (player == null) return;
            ContainerChest chest = (ContainerChest) player.openContainer;
            List<Slot> invSlots = ((GuiChest) mc.currentScreen).inventorySlots.inventorySlots;
            String chestName = chest.getLowerChestInventory().getDisplayName().getUnformattedText().trim();

            if (Config.expersolver && chestName.startsWith("Ultrasequencer (")) {
                if (invSlots.get(49).getStack() != null && invSlots.get(49).getStack().getDisplayName().equals("§aRemember the pattern!")) {
                    for (int i = 9; i <= 44; i++) {
                        if (invSlots.get(i) == null || invSlots.get(i).getStack() == null) continue;
                        String itemName = StringUtils.stripControlCodes(invSlots.get(i).getStack().getDisplayName());
                        if (itemName.matches("\\d+")) {
                            int number = Integer.parseInt(itemName);
                            clickInOrderSlots[number - 1] = invSlots.get(i);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiRender(ChestGui event) {
        if (Config.expersolver && event.displayName.startsWith("Ultrasequencer (")) {
            List<Slot> invSlots = event.slots;
            if (invSlots.size() > 48 && invSlots.get(49).getStack() != null) {
                if (invSlots.get(49).getStack().getDisplayName().startsWith("§7Timer: §a")) {
                    lastUltraSequencerClicked = 0;
                    for (Slot slot : clickInOrderSlots) {
                        if (slot != null && slot.getStack() != null && StringUtils.stripControlCodes(slot.getStack().getDisplayName()).matches("\\d+")) {
                            int number = Integer.parseInt(StringUtils.stripControlCodes(slot.getStack().getDisplayName()));
                            if (number > lastUltraSequencerClicked) {
                                lastUltraSequencerClicked = number;
                            }
                        }
                    }
                    if (clickInOrderSlots[lastUltraSequencerClicked] != null) {
                        Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked];
                        UtilsDanker.drawOnSlot(event.chestSize, nextSlot.xDisplayPosition, nextSlot.yDisplayPosition, ULTRASEQUENCER_NEXT + 0xE5000000);
                    }
                    if (lastUltraSequencerClicked + 1 < clickInOrderSlots.length) {
                        if (clickInOrderSlots[lastUltraSequencerClicked + 1] != null) {
                            Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked + 1];
                            UtilsDanker.drawOnSlot(event.chestSize, nextSlot.xDisplayPosition, nextSlot.yDisplayPosition, ULTRASEQUENCER_NEXT_TO_NEXT + 0xD7000000);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        clickInOrderSlots = new Slot[36];
    }
}
