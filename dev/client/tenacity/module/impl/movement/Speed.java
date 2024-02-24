package dev.client.tenacity.module.impl.movement;

import java.lang.reflect.Method;

import com.craftrise.dR;
import com.craftrise.lE;

import cr.launcher.main.a;
import dev.client.tenacity.hackerdetector.utils.MovementUtils;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.module.impl.combat.TargetStrafe;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.NumberSetting;
import dev.utils.misc.MathUtils;
import dev.utils.time.TimerUtil;
import mapper.ThePlayer;
import dev.client.tenacity.module.impl.movement.timer1;
public class Speed extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Watchdog", "Watchdog", "Matrix");
    private final ModeSetting watchdogMode = new ModeSetting("Watchdog Mode", "Hop", "Hop", "Low Hop", "Ground");
    private final BooleanSetting fastfall = new BooleanSetting("Fast Fall", false);
    private final NumberSetting timer = new NumberSetting("Timer", 1, 5, 1, 0.1);
    private final TimerUtil timerUtil = new TimerUtil();
    private float speed;
    private int stage;
    public timer1 timer1 = new timer1();
    public Speed() {
        super("Speed", Category.MOVEMENT, "Makes you go faster");
        fastfall.addParent(watchdogMode, modeSetting -> modeSetting.is("Hop"));
        this.addSettings(mode, watchdogMode, fastfall, timer);
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
        switch (mode.getMode()) {
        	//arisunem çok güzel kodlarımızı inceliyor <3
            case "Watchdog":
                switch (watchdogMode.getMode()) {
                    case "Hop":
                        if (e.isPre()) {
                            if (a.q.s.a(5L)) {
                                if (MovementUtils.isMoving(a.q)) {
                                    a.q.y(5L);
                                    stage = 0;
                                    speed = 1.10f;
                                }
                            } else {
                                if (fastfall.isEnabled()) {
                                    stage++;
                                    if (stage == 3) {
                                        double motionY = a.q.aT.b(5L);
                                        motionY -= 0.05;
                                        a.q.aT = new dR(motionY);
                                    }
                                    if (stage == 5) {
                                        double motionY = a.q.aT.b(5L);
                                        motionY -= 0.184;
                                        a.q.aT = new dR(motionY);
                                    }
                                }
                                speed -= 0.004;
                                MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * speed);
                            }
                        }
                        break;
                    case "Low Hop":
                        if (e.isPre()) {
                            float moveYaw = MovementUtils.getMoveYaw(e.getYaw()) % 360.0F;
                            e.setYaw(moveYaw + (MathUtils.getRandomInRange(-1, 1)));
                            if (a.q.s.a(5L)) {
                                speed = 1.1f;
                                MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 1.6);
                            } else {
                                speed -= 0.004;
                                MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * speed);
                            }
                        }
                        break;
                    case "Ground":
                        if (e.isPre()) {
                            if (a.q.s.a(5L) && MovementUtils.isMoving(a.q)) {
                                float moveYaw = MovementUtils.getMoveYaw(e.getYaw()) % 360.0F;
                                double yaw = Math.toRadians(moveYaw);
                                double x = e.getX() + (-Math.sin(yaw) * 0.1);
                                double z = e.getZ() + (Math.cos(yaw) * 0.1);
                                SetPosition(x, a.q.aY, z);
                                a.q.z.a(new lE.c(x, e.getY(), z, a.q.bL, a.q.N, true),5L);
                            }
                        }
                        break;
                }
                break;
            case "Matrix":
                if (MovementUtils.isMoving(a.q)) {
                    if (a.q.s.a(5L) && a.q.aT.a(5L) < 0.003) {
                        a.q.y(5L);
                       // mc.timer.timerSpeed = 1.0f;
                    }
                    if (a.q.aT.a(5L) > 0.003) {
                        double motionx,motionz;
                        motionx= a.q.bh.a(5L);
                        motionz = a.q.bf.a(5L);
                        motionx *= speed;
                        motionz *= speed;
                        a.q.bh = new dR(motionx);
                        a.q.bf = new dR(motionz);
                    }
                }
                speed = 1.0012f;
                break;
        }
    };


    @Override
    public void onEnable() {
        speed = 1.1f;
        timerUtil.reset();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
