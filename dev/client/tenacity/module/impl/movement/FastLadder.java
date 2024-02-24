package dev.client.tenacity.module.impl.movement;

import java.lang.reflect.Method;

import com.craftrise.dR;
import com.craftrise.lE;

import cr.launcher.main.a;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.NumberSetting;

public class FastLadder extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Motion", "Motion", "Position");
    private final NumberSetting speed = new NumberSetting("Speed", 1.5, 5, 0.1, 0.01);

    public FastLadder() {
        super("FastLadder", Category.MOVEMENT, "Climbs up ladders faster than normal");
        this.addSettings(mode, speed);
    }
    public static void SetPosition(double x, double y, double z){
        try{
            Class<?> Entity = com.craftrise.m9.class;
            Method setPosition = Entity.getMethod("b",double.class,double.class,double.class,long.class);
            setPosition.invoke(a.q,x,y,z,5L);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private final EventListener<MotionEvent> onMotion = e -> {
        if(a.q.N()) {
            switch(mode.getMode()) {
                case "Motion":
                    a.q.aT = new dR(speed.getValue());
                    break;
                case "Position":
                    a.q.z.a(new lE.c(a.q.bE, a.q.aY + speed.getValue(), a.q.bH, a.q.bL, a.q.N, false),5L);
                    SetPosition(a.q.bE, a.q.aY + speed.getValue(), a.q.bH);
                    break;
            }
        }else{
            //mc.timer.timerSpeed = 1;
        }
    };

    @Override
    public void onDisable() {
       // mc.timer.timerSpeed = 1;
        super.onDisable();
    }
}
