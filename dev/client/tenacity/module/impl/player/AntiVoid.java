package dev.client.tenacity.module.impl.player;

import com.craftrise.lE;

import cr.launcher.main.a;
import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.module.impl.movement.Flight;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.ModeSetting;
import dev.utils.misc.MathUtils;
import dev.utils.network.PacketUtils;

public class AntiVoid extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Watchdog", "Watchdog");

    public AntiVoid() {
        super("AntiVoid", Category.PLAYER, "saves you from the void");
        this.addSettings(mode);
    }

    private final EventListener<MotionEvent> onMotion = e -> {
        this.setSuffix(mode.getMode());
        if (Tenacity.INSTANCE.isToggled(Flight.class) || a.q.d) return;
        if (e.isPre()) {
            switch (mode.getMode()) {
                case "Watchdog":
                    PacketUtils.sendPacket(new lE.a(a.q.bE, a.q.aY + MathUtils.getRandomInRange(10, 12), a.q.bH, false));
                    break;
            }
        }
    };

}
