package dev.client.tenacity.module.impl.misc;

import com.craftrise.ai;

import cr.launcher.main.a;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.network.PacketSendEvent;
import dev.event.impl.player.MotionEvent;
import dev.utils.network.PacketUtils;

@SuppressWarnings("unused")
public final class AntiDesync extends Module {

    private int slot;

    public AntiDesync() {
        super("AntiDesync", Category.MISC, "pervents desync client side");
    }

    private final EventListener<PacketSendEvent> packetSendEventEventListener = event -> {
        if (event.getPacket() instanceof ai) {
            slot = ((ai) event.getPacket()).a();
        }
    };

    private final EventListener<MotionEvent> motionEventEventListener = event -> {
        if (slot != a.q.J.c && slot != -1) {
            PacketUtils.sendPacketNoEvent(new ai(a.q.J.c));
        }
    };

}
