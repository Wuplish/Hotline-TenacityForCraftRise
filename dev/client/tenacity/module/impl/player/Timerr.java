package dev.client.tenacity.module.impl.player;

import java.lang.reflect.Field;

import cr.e;
import cr.D;

import cr.launcher.Config;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.NumberSetting;

@SuppressWarnings("unused")
public final class Timerr extends Module {
	private static cr.g d;

    private final NumberSetting amount = new NumberSetting("Amount", 1, 10, 0.1, 0.1);
    
    public static cr.g getgField() {
    	try {
    		Field hField = Config.getMinecraft().cl.getClass().getDeclaredField("d");
    		hField.setAccessible(true);
    		return (cr.g)hField.get(Config.getMinecraft().cl);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public static void setTimerSpeed(float value){
        try {
            Field hField = Config.getMinecraft().cl.getClass().getDeclaredField("a");
            hField.setAccessible(true);
            hField.set(Config.getMinecraft().cl, new e(value,getgField().a()));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private final EventListener<MotionEvent> motionEventEventListener = event -> {
    	setTimerSpeed(amount.getValue().floatValue()*6);
    };

    public Timerr() {
        super("Timer", Category.PLAYER, "changes game speed");
        this.addSettings(amount);
    }

}
