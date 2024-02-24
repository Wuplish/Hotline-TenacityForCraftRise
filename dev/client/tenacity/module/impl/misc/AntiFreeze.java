package dev.client.tenacity.module.impl.misc;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.network.PacketReceiveEvent;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.ModeSetting;
import com.craftrise.pT;

import cr.launcher.main.a;
import com.craftrise.pE;

public final class AntiFreeze extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Normal", "Normal", "Teleport");

    private final EventListener<PacketReceiveEvent> packetReceiveEventEventListener = event -> {
        if (event.getPacket() instanceof pE) {
            event.cancel();
        } else if (event.getPacket() instanceof pT
                && ((pT) event.getPacket()).a().getUnformattedText().contains("frozen")) {
            if (mode.is("Teleport")) {
                a.q.aY = -999;
            }
            event.cancel();
        }
    };

    public AntiFreeze() {
        super("AntiFreeze", Category.MISC, "prevents server plugins from freezing you");
    }

}
