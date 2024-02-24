package dev.client.tenacity.module.impl.misc;

import com.craftrise.gy;
import com.craftrise.lE;

import cr.launcher.main.a;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.network.PacketReceiveEvent;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ModeSetting;
import dev.utils.network.PacketUtils;

public final class NoRotate extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Normal", "Normal", "Cancel");

    private final BooleanSetting fakeUpdate = new BooleanSetting("Fake Update", false);

    private final EventListener<PacketReceiveEvent> packetReceiveEventEventListener = e -> {
        if (e.getPacket() instanceof gy) {
            gy packet = (gy) e.getPacket();

            switch (mode.getMode()) {
                case "Normal":
                	
                    break;
                case "Cancel":
                    e.cancel();
                    break;
            }

            if (fakeUpdate.isEnabled()) {
                PacketUtils.sendPacketNoEvent(new lE.c(a.q.bE,
                        a.q.aY,
                        a.q.bH,
                        a.q.bL,
                        a.q.N,
                        a.q.s.a(5L)));
            }
        }
    };

    public NoRotate() {
        super("NoRotate", Category.MISC, "prevents servers from forcing rotations");
        this.addSettings(fakeUpdate);
    }
}
