package dev.utils.network;

import dev.utils.Utils;

import com.craftrise.lv;

import cr.launcher.main.a;

public class PacketUtils implements Utils
{
    public static void sendPacket(lv<?> packet, boolean silent)
    {
        if (a.q != null)
        {
          //  mc.getNetHandler().getNetworkManager().sendPacket(packet);
        }
    }

    public static void sendPacketNoEvent(lv packet)
    {
        sendPacket(packet, true);
    }

    public static void sendPacket(lv packet)
    {
        sendPacket(packet, false);
    }
}
