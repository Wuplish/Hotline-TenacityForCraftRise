package dev.client.tenacity.module.impl.combat;


import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.NumberSetting;
import dev.utils.network.PacketUtils;
import dev.utils.time.TimerUtil;

import java.util.UUID;

import com.craftrise.lE;

import cr.launcher.main.a;

@SuppressWarnings("unused")
public final class Criticals extends Module {

    private final ModeSetting modeSetting = new ModeSetting("Mode", "RAC");
    private final NumberSetting delay = new NumberSetting("Delay", 1,20, 0, 1);
    private final TimerUtil timer = new TimerUtil();

    public Criticals() {
        super("Criticals", Category.COMBAT, "Crit attacks");
        this.addSettings(modeSetting, delay);
    }

    private final EventListener<MotionEvent> onMotion = e -> {
        switch (modeSetting.getMode()) {
            case "Watchdog":
                if (KillAura.attacking && e.isOnGround()) {
                    if (KillAura.target.a5 > delay.getValue().intValue()) {
                        for (double offset : new double[]{0.06f, 0.01f}) {
                            a.q.z.a(new lE.a(a.q.bE, a.q.aY + offset + (Math.random() * 0.001), a.q.bH, false),5L);
                        }
                    }
                }
                break;
        }
    };

}
