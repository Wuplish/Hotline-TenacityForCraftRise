package dev.event.impl.player;

import cr.launcher.IChatComponent;
import dev.event.Event;

public class ChatReceivedEvent extends Event
{
    /**
     * Introduced in 1.8:
     * 0 : Standard Text Message
     * 1 : 'System' message, displayed as standard text.
     * 2 : 'Status' message, displayed above action bar, where song notifications are.
     */
    public final byte type;
    public IChatComponent message;

    public ChatReceivedEvent(byte type, IChatComponent message)
    {
        this.type = type;
        this.message = message;
    }
}
