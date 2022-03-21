package com.encodey.YungAddons.listeners;

import com.mojang.authlib.GameProfile;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import net.minecraft.client.Minecraft;

public abstract class GameProfileListener {

    @Getter
    public String Username() {
        return Minecraft.getMinecraft().getSession().getUsername();
    }
    public String UUID() {
        return Minecraft.getMinecraft().getSession().getPlayerID();
    }
    @Setter
    public GameProfile gameProfile() {
        return Minecraft.getMinecraft().getSession().getProfile();
    }
}
