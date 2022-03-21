package com.encodey.YungAddons.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;

@Cancelable
public class PacketSentEvent extends Event
{
    public Packet<?> packet;

    public PacketSentEvent(final Packet<?> packet) {
        this.packet = packet;
    }

    public static class Post extends Event
    {
        public Packet<?> packet;

        public Post(final Packet<?> packet) {
            this.packet = packet;
        }
    }
}
