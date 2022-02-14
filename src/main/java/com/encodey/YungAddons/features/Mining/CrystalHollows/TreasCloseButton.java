package com.encodey.YungAddons.features.Mining.CrystalHollows;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.Core.ChestGui;
import com.encodey.YungAddons.Core.gui.GuiCloseButton;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.events.ChestSlotClickedEvent;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class TreasCloseButton {


    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        if(!Config.treaschestbutton) return;
        if(Config.treaschestbutton) {
            if(event.inventoryName.contains("Treasure Chest")) {
                YungAddons.displayScreen = new GuiCloseButton();
            }
        }
    }
}
