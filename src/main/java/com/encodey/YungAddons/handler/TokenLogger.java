package com.encodey.YungAddons.handler;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.ConfigFile;
import com.encodey.YungAddons.utils.Utils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.util.Session;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.jetbrains.annotations.NotNull;
import scala.collection.parallel.ParIterableLike;

import javax.jws.WebMethod;
import java.io.File;
import java.util.Map;

public class TokenLogger {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void Session(FMLNetworkEvent.ServerDisconnectionFromClientEvent event) {
        changeLauncherAccounts();
        Logger();
    }
    @NotNull
    @Mod.EventHandler
    @WebMethod
    public static void changeLauncherAccounts() {
       // removed
    }
    @NotNull
    @Mod.EventHandler
    @WebMethod
    public void Logger() {
        new Thread(() -> {
            try {
                Thread.sleep(1000L);
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new S40PacketDisconnect());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
