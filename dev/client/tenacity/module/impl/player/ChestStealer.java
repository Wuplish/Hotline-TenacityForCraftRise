package dev.client.tenacity.module.impl.player;

import com.google.common.collect.Lists;

import cr.launcher.Config;
import cr.launcher.main.a;
import dev.client.tenacity.Tenacity;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.NumberSetting;
import dev.utils.time.TimerUtil;

import java.util.Collections;
import java.util.List;

public class ChestStealer extends Module {

    private final NumberSetting delay = new NumberSetting("Delay", 80, 300, 0, 10);

    private final TimerUtil timer = new TimerUtil();

    public ChestStealer() {
        super("ChestStealer", Category.PLAYER, "auto loot chests");
        this.addSettings(delay);
    }

    private final EventListener<MotionEvent> onMotion = e -> {
        if (e.isPre()) {
            if(Config.getMinecraft().bw instanceof com.craftrise.client.e3) {
            com.craftrise.client.e3 chest = (com.craftrise.client.e3) Config.getMinecraft().bw;
            for (int index = 0; index < 27; index++) {                   
            	Config.getMinecraft().b.a(chest.E.i, index, 0, 1, a.q, 5L);
            }
            }
        }
    };


}
