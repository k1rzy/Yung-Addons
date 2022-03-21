package com.encodey.YungAddons.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;
import io.netty.channel.*;

@Cancelable
public class PacketReceivedEvent extends Event
{
    public Packet<?> packet;
    public ChannelHandlerContext context;

    public PacketReceivedEvent(final Packet<?> packet, final ChannelHandlerContext context) {
        this.packet = packet;
        this.context = context;
    }

    public static class Post extends Event
    {
        public Packet<?> packet;
        public ChannelHandlerContext context;

        public Post(final Packet<?> packet, final ChannelHandlerContext context) {
            this.packet = packet;
            this.context = context;
        }
    }
}
