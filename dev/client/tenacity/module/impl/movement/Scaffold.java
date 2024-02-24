package dev.client.tenacity.module.impl.movement;

import dev.client.tenacity.Tenacity;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.event.impl.player.SafeWalkEvent;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.NumberSetting;
import dev.client.tenacity.utils.player.ScaffoldUtils;
import dev.utils.misc.MathUtils;
import dev.utils.player.RotationUtils;
import dev.utils.time.TimerUtil;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.RandomUtils;

import com.craftrise.ai;
import com.craftrise.v;

import cr.launcher.Config;
import cr.launcher.main.a;

public class Scaffold extends Module {

    private ScaffoldUtils.BlockCache blockCache, lastBlockCache;
    private ModeSetting placetype = new ModeSetting("Place Type", "Post", "Pre", "Post");
    public static NumberSetting extend = new NumberSetting("Extend", 0, 6, 0, 0.01);
    public static BooleanSetting sprint = new BooleanSetting("Sprint", false);
    private BooleanSetting tower = new BooleanSetting("Tower", false);
    private NumberSetting towerTimer = new NumberSetting("Tower Timer Boost", 1.2, 5, 0.1, 0.1);
    private NumberSetting Delay = new NumberSetting("Delay", 1.2, 500, 0.1, 0.1);
    private BooleanSetting swing = new BooleanSetting("Swing", false);
    private float rotations[];
    private TimerUtil timer = new TimerUtil();

    public Scaffold() {
        super("Scaffold", Category.MOVEMENT, "Automatically places blocks under you");
        this.addSettings(placetype, extend,Delay, sprint, tower, towerTimer, swing);
        towerTimer.addParent(tower, mode -> mode.isEnabled());
    }

    private final EventListener<MotionEvent> onMotion = e -> {

        if (e.isPre()) {
        	if(lastBlockCache != null) {
                rotations = RotationUtils.getFacingRotations2(lastBlockCache.getPosition().c(), lastBlockCache.getPosition().b(), lastBlockCache.getPosition().a());
                a.q.a = rotations[0];
                a.q.ap = rotations[0];
                e.setYaw(rotations[0]);
                e.setPitch(81);
            } else {
            	 e.setPitch(81);
                 e.setYaw(a.q.bL + 180);
                 a.q.a = a.q.bL + 180;
                 a.q.ap = a.q.bL + 180;
            }
            // Setting Block Cache
            blockCache = ScaffoldUtils.grab();
            if (blockCache != null) {
                lastBlockCache = ScaffoldUtils.grab();
            } else {
                return;
            }

            // Setting Item Slot (Pre)
            int slot = ScaffoldUtils.grabBlockSlot();
            if (slot == -1) return;

            // Setting Slot
            a.q.z.a(new ai(slot), 5L);

            // Placing Blocks (Pre)
            if (placetype.getMode().equalsIgnoreCase("Pre")) {
                if (blockCache == null) return;
                double value = Delay.getValue();
                if (timer.hasTimeElapsed((long)value)) { // Check if the delay has been reached
                    Config.getMinecraft().b.onPlayerRightClick(a.q, Config.getMinecraft().bu, a.q.J.a(slot), lastBlockCache.position, lastBlockCache.facing, ScaffoldUtils.getHypixelVec3(lastBlockCache));
                    if (swing.isEnabled()) {
                        a.q.G(5L);
                    }
                    a.q.z.a(new v(), 5L);
                    blockCache = null;
                    timer.reset(); // Reset the timer after the action
                }
            }
        } else {

            // Tower
            if (tower.isEnabled()) {
                if (Config.gameSettings.bO.a("null")) {
                    // mc.timer.timerSpeed = towerTimer.getValue().floatValue();
                    if (a.q.aY < 0) {
                        a.q.y(5L);
                    }
                } else {
                    //mc.timer.timerSpeed = 1;
                }
            }

            // Setting Item Slot (Post)
            int slot = ScaffoldUtils.grabBlockSlot();
            if (slot == -1) return;

            // Placing Blocks (Post)
            if (placetype.getMode().equalsIgnoreCase("Post")) {
                if (blockCache == null) return;
                double value = Delay.getValue();
                if (timer.hasTimeElapsed((long)value)) { // Check if the delay has been reached
                    Config.getMinecraft().b.onPlayerRightClick(a.q, Config.getMinecraft().bu, a.q.J.a(slot), lastBlockCache.position, lastBlockCache.facing, ScaffoldUtils.getHypixelVec3(lastBlockCache));
                    if (swing.isEnabled()) {
                        a.q.G(5L);
                    }
                    a.q.z.a(new v(), 5L);
                    blockCache = null;
                    timer.reset(); // Reset the timer after the action
                }
            }
        }
    };


    @Override
    public void onDisable() {
    	a.q.z.a(new ai(a.q.J.c),5L);
        super.onDisable();
    }

    @Override
    public void onEnable() {
        lastBlockCache = null;
        super.onEnable();
    }
}
