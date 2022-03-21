package com.encodey.YungAddons.utils;
import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.network.*;
import java.lang.reflect.*;

public class PacketUtils
{
    public static ArrayList<Packet<?>> noEvent;

    public static void sendPacketNoEvent(final Packet<?> packet) {
        PacketUtils.noEvent.add(packet);
        Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(packet);
    }

    public static String packetToString(final Packet<?> packet) {
        final StringBuilder postfix = new StringBuilder();
        boolean first = true;
        for (final Field field : packet.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                postfix.append(first ? "" : ", ").append(field.get(packet));
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            first = false;
        }
        return packet.getClass().getSimpleName() + String.format("{%s}", postfix);
    }

    static {
        PacketUtils.noEvent = new ArrayList<Packet<?>>();
    }
}
