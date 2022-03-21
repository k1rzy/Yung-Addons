package com.encodey.YungAddons.proxy;

import com.encodey.YungAddons.inventory.items.ItemList;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenders() {
        ItemList.registerRenders();
    }
}
