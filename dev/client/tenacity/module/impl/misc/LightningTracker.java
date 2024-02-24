package dev.client.tenacity.module.impl.misc;

import com.craftrise.gP;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.utils.player.ChatUtils;
import dev.event.EventListener;
import dev.event.impl.network.PacketReceiveEvent;

public final class LightningTracker extends Module {

    public LightningTracker() {
        super("LightningTracker", Category.MISC, "detects lightning");
    }

    private final EventListener<PacketReceiveEvent> onPacketReceive = e -> {
        if (e.getPacket() instanceof gP) {
            gP soundPacket = ((gP) e.getPacket());
            if (soundPacket.a().equals("ambient.weather.thunder")) {
                ChatUtils.print(String.format("Lightning detected at (%s, %s, %s)", (int) soundPacket.c(), (int) soundPacket.b(), (int) 0));
            }
        }
    };

}
