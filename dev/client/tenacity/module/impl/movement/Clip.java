package dev.client.tenacity.module.impl.movement;

import java.lang.reflect.Method;

import org.lwjgl.input.Keyboard;

import cr.launcher.main.a;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.settings.impl.NumberSetting;

public class Clip extends Module{
    private final NumberSetting block = new NumberSetting("Block", 0, 10, 0, 1);
    
    public static final double PI = 3.14159265358979323846;

    public static double getPosForSetPosX(double val, float RotationYaw) {
        float yaw = RotationYaw * ((float) PI / 180.0f);
        double x = -Math.sin(yaw) * val;
        return x;
    }

    public static double getPosForSetPosZ(double val, float RotationYaw) {
        float yaw = RotationYaw * ((float) PI / 180.0f);
        double z = Math.cos(yaw) * val;
        return z;
    }
    public static double getPosForSetPosY(double val, float RotationYaw) {
        float yaw = RotationYaw * ((float) PI / 180.0f);
        double y = Math.cos(yaw) * val;
        return y;
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

	
	 public Clip() {
	     super("Clip", Category.MOVEMENT, "Clip");
	     this.setKey(Keyboard.KEY_F);
	        this.addSettings(block);
	 }
	 
	   @Override
	    public void onEnable() {
		 double newposX = a.q.bE + getPosForSetPosX(block.getValue()/2,a.q.bL);
		 double newposz = a.q.bH + getPosForSetPosZ(block.getValue()/2,a.q.bL);
		 double newposY = a.q.bH + getPosForSetPosY(block.getValue()/2,a.q.bL);
		 SetPosition(newposX,newposY,newposz);
		 Clip.this.setToggled(false);
	 }

}
