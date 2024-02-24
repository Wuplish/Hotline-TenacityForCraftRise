package dev.event.impl.network;

import com.craftrise.lv;

import dev.event.Event;

public class PacketReceiveEvent extends Event
{
    private lv<?> packet;

    public PacketReceiveEvent(lv<?> packet)
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
