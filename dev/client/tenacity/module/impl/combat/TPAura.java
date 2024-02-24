package dev.client.tenacity.module.impl.combat;

import java.util.ArrayList;
import java.util.List;

import com.craftrise.m9;
import com.craftrise.mj;

import cr.launcher.Config;
import cr.launcher.main.a;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.NumberSetting;
import dev.utils.time.TimerUtil;
import net.minecraft.util.Vec3;

public class TPAura extends Module{
	private final TimerUtil delayTimer = new TimerUtil();
    private final NumberSetting Distance = new NumberSetting("Distance", 10, 50, 5, 1);
    private final NumberSetting Targets = new NumberSetting("Targets", 1, 5, 1, 1);
    private final NumberSetting delay = new NumberSetting("Delay", 100, 1000, 10, 1);
    private List<Vec3> points = new ArrayList<>();

	public TPAura() {
		super("TPAura", Category.COMBAT, "Sonsuz Vuruþ Hakký");
	}
	@Override
    public void onEnable() {
		delayTimer.reset();
	}
	 private final EventListener<MotionEvent> motionEventEventListener = event -> {
		 if(delayTimer.hasTimeElapsed(delay.getValue().longValue())) {
			 
		 }
	 };
	 
	 public static double GetDistanceToEntity(com.craftrise.m9 m92) {
	        try {
	            double f2 = (a.q.bE- m92.bE);
	            double f3 = (a.q.aY - m92.aY);
	            double f4 = (a.q.bH - m92.bH);
	            double result = Math.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
	            return result;
	        }
	        catch (Exception e){
	            e.printStackTrace();
	            return -1;
	        }

	    }
	 
	 
	 private void doTpAura() {
	        List<mj> targets = new ArrayList<>();
	        for (Object entity : Config.getMinecraft().bu.g) {
	            if (entity instanceof mj &&
	                GetDistanceToEntity((m9) entity) < Distance.getValue().longValue()) {
	                    targets.add((mj) entity);
	            }
	        }

	        if (targets.isEmpty()) return;

	        targets.sort((e1, e2) -> Double.compare(GetDistanceToEntity(e1), GetDistanceToEntity(e2)));

	        int count = 0;
	        for (mj entity : targets) {
	            //if (hit(entity)) {
	              //  count++;
	            //}
	            if (count > Targets.getValue().longValue()) break;
	        }
	    }
	 
	 
	@Override
    public void onDisable() {
		
	}

}
