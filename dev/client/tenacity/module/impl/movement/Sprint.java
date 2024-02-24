package dev.client.tenacity.module.impl.movement;

import cr.launcher.Config;
import cr.launcher.main.a;
import dev.client.tenacity.Tenacity;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.settings.impl.BooleanSetting;

public class Sprint extends Module {

    private final BooleanSetting omniSprint = new BooleanSetting("Omni Sprint", false);

    public Sprint() {
        super("Sprint", Category.MOVEMENT, "Sprints automatically");
        this.addSettings(omniSprint);
    }

    private final EventListener<MotionEvent> onMotion = e -> {
        a.q.c(true);
    };

    @Override
    public void onDisable() {
    	a.q.c(false);
    	super.onDisable();
    }

}
