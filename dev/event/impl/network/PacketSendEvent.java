package dev.event.impl.network;

import com.craftrise.lv;

import dev.event.Event;

public class PacketSendEvent extends Event
{
    private lv<?> packet;

    public PacketSendEvent(lv<?> packet)
    {
        this.packet = packet;
    }

    public lv<?> getPacket()
    {
        return packet;
    }

    public void setPacket(lv<?> packet)
    {
        this.packet = packet;
    }
}
