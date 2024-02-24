package dev.client.tenacity.module.impl.misc;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.game.TickEvent;
import dev.utils.network.PacketUtils;
import com.craftrise.lD;

import cr.launcher.main.a;

public final class AutoRespawn extends Module {

    private final EventListener<TickEvent> tickEventEventListener = event -> {
        if(a.q.d){
            PacketUtils.sendPacketNoEvent(new lD(lD.a.PERFORM_RESPAWN));
        }
    };

    public AutoRespawn() {
        super("AutoRespawn", Category.MISC, "automatically respawn");
    }
}
