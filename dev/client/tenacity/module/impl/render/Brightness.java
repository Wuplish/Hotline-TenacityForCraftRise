package dev.client.tenacity.module.impl.render;

import cr.launcher.Config;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;

public final class Brightness extends Module {
    private final EventListener<MotionEvent> motionEventEventListener = event -> {
        Config.gameSettings.y = 100;
    };

    @Override
    public void onDisable(){
    	Config.gameSettings.y = 0;
        super.onDisable();
    }

    public Brightness() {
        super("Brightness", Category.RENDER, "changes the game brightness");
    }
}
