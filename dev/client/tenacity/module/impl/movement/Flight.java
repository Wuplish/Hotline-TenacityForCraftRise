package dev.client.tenacity.module.impl.movement;


import dev.event.EventListener;
import dev.event.impl.network.PacketReceiveEvent;
import dev.event.impl.network.PacketSendEvent;
import dev.event.impl.player.MotionEvent;
import dev.client.tenacity.hackerdetector.utils.MovementUtils;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.module.impl.player.Timerr;
import dev.client.tenacity.ui.notifications.NotificationManager;
import dev.client.tenacity.ui.notifications.NotificationType;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.NumberSetting;
import dev.utils.network.PacketUtils;
import cr.launcher.eb;
import cr.launcher.main.a;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.lwjgl.input.Keyboard;

import com.craftrise.dR;
import com.craftrise.jS;
import com.craftrise.lE;
import com.craftrise.lv;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public final class Flight extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Watchdog", "Watchdog", "AirWalk","Vanilla","Rac");
    private final NumberSetting speed = new NumberSetting("Speed", 2, 5, 0, 0.1);
    private float stage;
    private int ticks;
    private boolean doFly;
    private double x, y, z;
    private ArrayList<lv> packets = new ArrayList<>();
    private boolean hasClipped;
    private double speedStage;
    public double teleport=0;
    
    private double firstposX;
    
    private double posX,posY,posZ;
    private ScheduledExecutorService executorService;

    public Flight() {
        super("Flight", Category.MOVEMENT, "Hovers you in the air");
        speed.addParent(mode, m -> m.is("Vanilla"));
        this.addSettings(mode, speed);
    }
    public static void SetflySpeed(float value){
        try {
            Class<?> thePlayerClass = com.craftrise.mg.class;
            Field sField = thePlayerClass.getDeclaredField("S");
            sField.setAccessible(true);
            Object sObject = sField.get(a.q);
            Field hField = sObject.getClass().getDeclaredField("c");
            hField.setAccessible(true);
            hField.set(sObject, new com.craftrise.de(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public static void SetisFlying(boolean value) {
        try {
            Class<?> thePlayerClass = com.craftrise.mg.class;
            Field sField = thePlayerClass.getDeclaredField("S");
            sField.setAccessible(true);
            Object sObject = sField.get(a.q);
            Field hField = sObject.getClass().getDeclaredField("h");
            hField.setAccessible(true);
            hField.set(sObject, new eb(value, cr.launcher.main.a.m));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final EventListener<MotionEvent> onMotion = e -> {
        switch (mode.getMode()) {
            case "Watchdog":
            	a.q.aY = y;
                if (a.q.s.a(5L) && stage == 0) {
                    a.q.aT = new dR(0.09);
                }
                stage++;
                if (a.q.s.a(5L) && stage > 2 && !hasClipped) {
                	a.q.z.a(new lE.a(a.q.bE,a.q.aY - 0.15,a.q.bH,false),5L);
                	a.q.z.a(new lE.a(a.q.bE,a.q.aY + 0.15,a.q.bH,true),5L);
                    hasClipped = true;
                }
                if (doFly) {
                	a.q.aT = new dR(0);
                	a.q.s = new eb(true,cr.launcher.main.a.m);
                   // mc.timer.timerSpeed = 2;
                } else {
                    //MovementUtils.setSpeed(0);
                    //mc.timer.timerSpeed = 5;
                }
                break;
            case "AirWalk":
            	a.q.aT = new dR(0);
            	a.q.s = new eb(true,cr.launcher.main.a.m);
                MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 0.6);
                break;
            case "Vanilla":
            	SetisFlying(true);
            	SetflySpeed(0.2f);
                break;
                
            case "Rac":
            	SetisFlying(true);
            	SetflySpeed(0.3f);
            	posX = a.q.bE;
            	posY = a.q.aY;
            	posZ = a.q.bH;
            	break;
           
        }
    };

    private final EventListener<PacketSendEvent> onPacketSend = e -> {
    	switch (mode.getMode()) {
   	     case "Rac":
   		 if (e.getPacket() instanceof lE) {
   			 if(cr.launcher.main.a.q.Z % 1 == 0){
                    e.cancel();
                }
   		 }
            break;
   	}
    };

   

    @Override
    public void onEnable() {
        doFly = false;
        ticks = 0;
        stage = 0;
        x = a.q.bE;
        y = a.q.aY;
        z = a.q.bH;
        hasClipped = false;
        packets.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
      //  mc.timer.timerSpeed = 1;
    	if(mode.getMode() =="Vanilla") {
    		SetisFlying(false);
    	}else if(mode.getMode() == "Rac") {
    		NotificationManager.post(NotificationType.INFO, "Flight", "Please Wait A 5 Secons");
    		executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(this::performTeleport, 0, 100, TimeUnit.MILLISECONDS);
    	}else if(mode.getMode() == "AirWalk") {
    		Timerr.setTimerSpeed(0.05f);
    	}
        super.onDisable();
    }
    private void performTeleport() {
    	if(a.q.bE == posX) {
            teleport++;
            if(teleport >= 20) {
            	NotificationManager.post(NotificationType.INFO, "Flight", "Position Updated!!");
            }
		}
        if (teleport <25) {
            SetPosition(posX, posY, posZ);
            a.q.z.a(new lE.a(posX, posY, posZ, true), 5L);
        } else {
        	teleport = 0;
            executorService.shutdown();
        }
    }

}
