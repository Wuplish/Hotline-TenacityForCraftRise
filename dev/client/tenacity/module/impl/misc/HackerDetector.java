package dev.client.tenacity.module.impl.misc;

import com.craftrise.m9;
import com.craftrise.mg;

import cr.launcher.Config;
import cr.launcher.main.a;
import dev.client.tenacity.hackerdetector.Detection;
import dev.client.tenacity.hackerdetector.DetectionManager;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.ui.notifications.NotificationManager;
import dev.client.tenacity.ui.notifications.NotificationType;
import dev.client.tenacity.utils.player.ChatUtils;
import dev.event.EventListener;
import dev.event.impl.game.TickEvent;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.MultipleBoolSetting;
import dev.utils.misc.MathUtils;
import dev.utils.time.TimerUtil;

public class HackerDetector extends Module {

    private DetectionManager detectionManager = new DetectionManager();
    private TimerUtil timer = new TimerUtil();
    private final MultipleBoolSetting detections = new MultipleBoolSetting("Detections",
            new BooleanSetting("Flight A", true),
            new BooleanSetting("Flight B", true),
            new BooleanSetting("Reach A", true));

    public HackerDetector() {
        super("HackerDetector", Category.MISC, "Detects people using cheats inside your game");
        this.addSettings(detections);
    }

    private final EventListener<TickEvent> onTick = e -> {
        if(Config.getMinecraft().bu == null || a.q == null) return;
        for(m9 entity : Config.getMinecraft().bu.g) {
            if(entity instanceof mg) {
                mg entityPlayer = (mg) entity;
                if(entityPlayer != a.q) {
                    for(Detection d : detectionManager.getDetections()) {
                    }
                }
            }
        }
    };
}
