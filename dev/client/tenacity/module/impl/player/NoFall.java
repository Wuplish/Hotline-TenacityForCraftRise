package dev.client.tenacity.module.impl.player;

import com.craftrise.de;

import cr.launcher.main.a;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.utils.Wrapper;
import dev.client.tenacity.utils.player.ChatUtils;
import dev.event.EventListener;
import dev.event.impl.network.PacketSendEvent;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ModeSetting;
import dev.utils.network.PacketUtils;
import io.netty.util.NetUtil;

import com.craftrise.lE;
@SuppressWarnings("unused")
public final class NoFall extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "Packet", "Edit");

    private final EventListener<MotionEvent> motionEventEventListener = event -> {
        if (event.isPre()) {
            if (a.q.a8.a(5L) > 3.0) {
                switch (mode.getMode()) {
                    case "Vanilla":
                        event.setOnGround(true);
                        break;
                    case "Packet":
                        PacketUtils.sendPacket(new lE(true));
                        break;
                }
                a.q.a8 = new de(0);
            }
        }
    };

    private final EventListener<PacketSendEvent> packetSendEventEventListener = event -> {
    };

    public NoFall() {
        super("NoFall", Category.PLAYER, "pervents fall damage");
        this.addSettings(mode);
    }
}
