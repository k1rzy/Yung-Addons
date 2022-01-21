package com.encodey.YungAddons;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import com.encodey.YungAddons.commands.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.*;
import com.google.gson.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.text.JTextComponent;

@Mod(modid = YungAddons.MOD_ID, name = YungAddons.NAME, version = YungAddons.VERSION)
public class YungAddons
{
    public static final String MOD_ID = "yungaddons";
    public static final String NAME = "Yung Addons";
    public static final String VERSION = "1.0";

    public static Config config = new Config();
    public static final Gson gson;
    public static final Minecraft mc;
    public static final String modMessage;
    public static GuiScreen displayScreen;
    public static JsonObject response;
    public static KeyBinding keyBindings;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new OnPlayerJoin());
        //YungAddons.keyBindings = new KeyBinding("Open mod menu", 45, "Yung Addons");
        //for(final KeyBinding keyBinding : YungAddons.keyBindings) {
        //    ClientRegistry.registerKeyBinding(keyBinding);
        //}
    }
    @EventHandler
    public void preInit(FMLInitializationEvent event)
    {

    }
    @SubscribeEvent
    public void processCommand(PlayerEvent.PlayerLoggedInEvent log) {
        EntityPlayer player = log.player;
        player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "[Yung Addons] " + EnumChatFormatting.BLUE + "Type /yung to open mod menu. Current mod version: " + YungAddons.VERSION));
    }
    public void registerCommand(final ICommand command) {
        ClientCommandHandler.instance.registerCommand(command);
    }
    static {
        gson = new GsonBuilder().setPrettyPrinting().create();
        mc = Minecraft.getMinecraft();
        //keyBindings = new KeyBinding[0];
        modMessage = EnumChatFormatting.RED + "Yung Addons ";
    }
}
