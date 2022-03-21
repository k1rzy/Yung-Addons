package com.encodey.YungAddons.mixins;

import com.encodey.YungAddons.events.PacketReceivedEvent;
import com.encodey.YungAddons.events.PacketSentEvent;
import com.encodey.YungAddons.utils.PacketUtils;
import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.channel.*;
import net.minecraft.network.play.server.*;

import java.io.*;

@Mixin({ NetworkManager.class })
public abstract class Packets
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacket(final Packet<?> packet, final CallbackInfo callbackInfo) {
        if (!(packet instanceof C03PacketPlayer)) {
        }
        if (!PacketUtils.noEvent.contains(packet) && MinecraftForge.EVENT_BUS.post(new PacketSentEvent(packet))) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("RETURN") }, cancellable = true)
    private void onSendPacketPost(final Packet<?> packet, final CallbackInfo callbackInfo) {
        if (!PacketUtils.noEvent.contains(packet) && MinecraftForge.EVENT_BUS.post(new PacketSentEvent.Post(packet))) {
            callbackInfo.cancel();
        }
        PacketUtils.noEvent.remove(packet);
    }

    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onChannelReadHead(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo callbackInfo) {
        Label_0092: {
                if (!(packet instanceof S13PacketDestroyEntities) && !(packet instanceof S3EPacketTeams)) {
                    if (!(packet instanceof S40PacketDisconnect)) {
                        break Label_0092;
                    }
                }
                if (packet instanceof S40PacketDisconnect) {
                }
        }
        if (!PacketUtils.noEvent.contains(packet) && MinecraftForge.EVENT_BUS.post((Event)new PacketReceivedEvent(packet, context))) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = { "channelRead0" }, at = { @At("RETURN") }, cancellable = true)
    private void onPost(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo callbackInfo) {
        if (!PacketUtils.noEvent.contains(packet) && MinecraftForge.EVENT_BUS.post((Event)new PacketReceivedEvent.Post(packet, context))) {
            callbackInfo.cancel();
        }
        PacketUtils.noEvent.remove(packet);
    }
}

