package com.encodey.YungAddons.inventory.items;

import com.encodey.YungAddons.ScriptAliases;
import com.encodey.YungAddons.YungAddons;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemList {

    public static Item skyblock_menu;

    public static void init() {
        skyblock_menu = new Item().setUnlocalizedName("rat");
    }
    public static void register() {
        registerItem(skyblock_menu);
    }
    public static void registerRenders() {
        registerRender(skyblock_menu);
    }
    public static void registerItem(Item item) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
        YungAddons.logger.debug("Registered nigger");
    }
    public static void registerRender(Item item) {
        ScriptAliases.mc.getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(YungAddons.MOD_ID + ":textures/items/skyblock_menu.png", "inventory"));
    }
}
