package com.encodey.YungAddons.inventory;

import com.encodey.YungAddons.ScriptAliases;
import com.encodey.YungAddons.commands.NewProfileCommand;
import com.encodey.YungAddons.inventory.items.ItemList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkyblockReplacement {

    @SubscribeEvent
    private void onWorldRender(RenderWorldLastEvent event) {
        if(NewProfileCommand.ReplacingMenu) {
            ScriptAliases.mc.thePlayer.replaceItemInInventory(9, new ItemStack(ItemList.skyblock_menu));
        }
    }
}
