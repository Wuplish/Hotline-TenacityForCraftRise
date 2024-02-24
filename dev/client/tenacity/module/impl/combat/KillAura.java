package dev.client.tenacity.module.impl.combat;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.network.PacketReceiveEvent;
import dev.event.impl.player.MotionEvent;
import dev.settings.ParentAttribute;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.MultipleBoolSetting;
import dev.settings.impl.NumberSetting;
import dev.utils.misc.MathUtils;
import dev.utils.network.PacketUtils;
import dev.utils.player.RotationUtils;
import dev.utils.time.TimerUtil;
import net.minecraft.util.*;
import cr.launcher.BlockPos;
import cr.launcher.Config;
import cr.launcher.main.a;

import com.craftrise.on;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.input.Keyboard;


@SuppressWarnings("unused")
public final class KillAura extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Single", "Single", "Switch");
    private final NumberSetting switchDelay = new NumberSetting("Switch Delay", 100, 1000, 100, 1);
    private final NumberSetting reach = new NumberSetting("Reach", 4, 6, 1, 0.1);
    private final NumberSetting mincps = new NumberSetting("Min CPS", 12, 20, 1, 1);
    private final NumberSetting maxcps = new NumberSetting("Max CPS", 12, 20, 1, 1);
    private final ModeSetting autoBlockMode = new ModeSetting("AutoBlock Mode", "Interaction", "AAC");
    private final BooleanSetting autoblock = new BooleanSetting("Autoblock", true);
    private final MultipleBoolSetting targetsSetting = new MultipleBoolSetting("Targets",
            new BooleanSetting("Players", true),
            new BooleanSetting("Mobs", false),
            new BooleanSetting("Animals", false),
            new BooleanSetting("Invisibles", false));
    private final MultipleBoolSetting rotationsSetting = new MultipleBoolSetting("Rotations",
            new BooleanSetting("Dynamic", false),
            new BooleanSetting("Prediction", false),
            new BooleanSetting("Resolver", false),
            new BooleanSetting("Smooth", false));

    private final MultipleBoolSetting antiBotSettings = new MultipleBoolSetting("Antibot",
            new BooleanSetting("Ticks", true),
            new BooleanSetting("Invisible", false),
            new BooleanSetting("NameTags", false),
            new BooleanSetting("Packet", false));

    private final BooleanSetting matrix = new BooleanSetting("Matrix", false);
    private final BooleanSetting debug = new BooleanSetting("Debug", false);
    public List<com.craftrise.mj> targets = new ArrayList<>();
    public static com.craftrise.mj target;
    public static boolean blocking;
    public static boolean attacking;
    public TimerUtil timer = new TimerUtil(), swtichTimer = new TimerUtil();

    public KillAura() {
        super("KillAura", Category.COMBAT, "Automatically attacks players");
        this.addSettings(mode, switchDelay, reach, mincps, maxcps, autoBlockMode, autoblock, targetsSetting, rotationsSetting, antiBotSettings, matrix, debug);
        switchDelay.addParent(mode, mode -> mode.is("Switch"));
        this.setKey(Keyboard.KEY_R);
        autoBlockMode.addParent(autoblock, ParentAttribute.BOOLEAN_CONDITION);
    }

    private final EventListener<MotionEvent> onMotion = e -> {
        sortTargets();
        this.setSuffix(mode.getMode());
        if (a.q.d)
            return;
        if (!targets.isEmpty()) {
            if (mode.is("Switch") && swtichTimer.hasTimeElapsed(switchDelay.getValue().longValue())) {

                swtichTimer.reset();
            }
            if (e.isPre()) {
                target = targets.get(0);
                float[] rotations = getRotationsToEnt((com.craftrise.m9)target);
                if (rotationsSetting.getSetting("Dynamic").isEnabled()) {
                    rotations[0] += MathUtils.getRandomInRange(1, 5);
                    rotations[1] += MathUtils.getRandomInRange(1, 5);
                }
                if (rotationsSetting.getSetting("Prediction").isEnabled()) {
                    rotations[0] = (float) (rotations[0] + ((Math.abs(target.bE - target.a6) - Math.abs(target.bE - target.G)) * (2 / 3)) * 2);
                    rotations[1] = (float) ((rotations[1] + (Math.abs(target.aY - target.h) - target.h) * (2.0 / 3)) * 2);
                }

                if (rotationsSetting.getSetting("Resolver").isEnabled()) {
                    if (target.aY < 0) {
                        rotations[1] = 1;
                    } else if (target.aY > 255) {
                        rotations[1] = 90;
                    }   
                }

                if (rotationsSetting.getSetting("Smooth").isEnabled()) {
                    float sens = RotationUtils.getSensitivityMultiplier();

                    rotations[0] = RotationUtils.smoothRotation(a.q.bL, rotations[0], 360);
                    rotations[1] = RotationUtils.smoothRotation(a.q.N, rotations[1], 90);

                    rotations[0] = Math.round(rotations[0] / sens) * sens;
                    rotations[1] = Math.round(rotations[1] / sens) * sens;

                }

                if (matrix.isEnabled()) {
                    rotations[0] = rotations[0] + MathUtils.getRandomFloat(1.98f, -1.98f);
                }
                e.setYaw(rotations[0]);
                e.setPitch(rotations[1]);
                RotationUtils.setRotations(rotations);
            }

            if (autoblock.isEnabled()) {
                Config.getMinecraft().b.b(5L);
                blocking = true;
                switch (autoBlockMode.getMode()) {
                    case "Interaction":
                        if (e.isPost()) {
                            if (blocking) {
                                for (com.craftrise.m9 current : targets) {
                                	Config.getMinecraft().b.interactWithEntitySendPacket(a.q, current);
                                }
                            } else {
                                PacketUtils.sendPacket(new com.craftrise.qd(
                                        new BlockPos(-1, -1, -1), 255, null, 0, 0, 0));
                                blocking = false;
                            }
                        }
                        break;
                    case "AAC":
                        if (e.isPost()) {
                            if (blocking) {
                            	Config.getMinecraft().b.sendUseItem(a.q, Config.getMinecraft().bu, null);
                            } else {
                                PacketUtils.sendPacket(new com.craftrise.qd(
                                        new BlockPos(-1, -1, -1), 255, null, 0, 0, 0));
                                blocking = false;
                            }
                        }
                        break;
                }
            }
            if (e.isPre()) {
                attacking = true;
                if (timer.hasTimeElapsed((1000 / MathUtils.getRandomInRange(mincps.getValue().intValue(), maxcps.getValue().intValue())), true)) {
                    a.q.G(5L);
                    Config.getMinecraft().b.a(a.q,target,"sdfjub1bbm", 5L);
                }
            }
        }
        if (targets.isEmpty()) {
            attacking = false;
            blocking = false;
        }
    };

    @Override
    public void onDisable() {
        targets.clear();
        blocking = false;
        attacking = false;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void sortTargets() {
        targets.clear();
        for (com.craftrise.m9 entity : Config.getMinecraft().bu.g) {
            if (entity instanceof com.craftrise.mj) {
            	com.craftrise.mj entLiving = (com.craftrise.mj) entity;
                if (GetDistanceToEntity(entLiving) < reach.getValue() && entLiving != a.q && !entLiving.d &&!entLiving.E()&& a.q.e(entLiving, 5L)) {
                    targets.add(entLiving);
                }
            }
        }
        //targets.sort(Comparator.comparingDouble(thisclass::GetDistanceToEntity));
    }

    public boolean isValid(com.craftrise.mj entLiving) {
        if (entLiving instanceof com.craftrise.mg && targetsSetting.getSetting("Players").isEnabled()&& !entLiving.E()) {
            return true;
        }
        if (entLiving instanceof com.craftrise.mg && targetsSetting.getSetting("Invisibles").isEnabled() && entLiving.E()) {
            return true;
        }
        if (entLiving instanceof com.craftrise.mg) {
        	return true;
        }
        if (antiBotSettings.getSetting("Ticks").isEnabled() && entLiving.Z < 100) {
            return false;
        }

        if (!entLiving.e(a.q,5L) && antiBotSettings.getSetting("Invisible").isEnabled()) {
            return false;
        }
        return targetsSetting.getSetting("Animals").isEnabled();
    }
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

    private float[] getRotationsToEnt(com.craftrise.m9 ent) {
        final double differenceX = ent.bE - a.q.bE;
        final double differenceY = (ent.aY + ent.t) - (a.q.aY + a.q.t) - 0.5;
        final double differenceZ = ent.bH - a.q.bH;
        final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
        final float rotationPitch = (float) (Math.atan2(differenceY,GetDistanceToEntity(ent)) * 180.0D
                / Math.PI);
        final float finishedYaw = a.q.bL
                + MathHelper.wrapAngleTo180_float(rotationYaw - a.q.bL);
        final float finishedPitch = a.q.N
                + MathHelper.wrapAngleTo180_float(rotationPitch - a.q.N);
        return new float[]{finishedYaw, -MathHelper.clamp_float(finishedPitch, -90, 90)};
    }
}