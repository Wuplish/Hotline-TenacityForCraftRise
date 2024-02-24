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
import mapper.TheMinecraft;
import mapper.ThePlayer;
import mapper.TheWorld;
import net.minecraft.util.*;
import cr.launcher.BlockPos;
import cr.launcher.Config;
import cr.launcher.main.a;
import com.craftrise.m9;
import com.craftrise.mj;
import com.craftrise.on;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.input.Keyboard;

public class Aimbot extends Module{
	private final NumberSetting Range = new NumberSetting("Range", 4, 6, 1, 0.1);
    public Aimbot() {
        super("Aimbot", Category.COMBAT, "Automatically attacks players");
        this.addSettings(Range);
    }
    private List<mj> getEntities() {
        List<com.craftrise.mj> entities = new ArrayList<>();

        for (final Object o : TheWorld.playerEntities()) {
            final com.craftrise.mj entity = (com.craftrise.mj) o;
            if (entity == null) continue;

            double distance = ThePlayer.GetDistanceToEntity(entity);

            if (!entity.E() && distance <= 7 && TheMinecraft.GetPlayer().e(entity, 5L) && entity != TheMinecraft.GetPlayer()) {
                entities.add(entity);
            }
        }

        return entities;
    }
    public static synchronized void faceEntity(mj entity) {
        final float[] rotations = getRotationsNeeded(entity);

        if (rotations != null) {
            ThePlayer.SetRotationYaw(rotations[0]);
            ThePlayer.SetRotationPitch(rotations[1] + 1.0F);
        }
    }

    public static float[] getRotationsNeeded(m9 entity) {
        double deltaX = entity.bE -ThePlayer.GetPosX();
        double deltaY = entity.aY - ThePlayer.GetPosY();
        double deltaZ = entity.bH - ThePlayer.GetPosZ();

        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        float yaw = (float) Math.toDegrees(-Math.atan2(deltaX, deltaZ));
        float pitch = (float) Math.toDegrees(-Math.atan2(deltaY, distance));

        return new float[]{yaw, pitch};
    }
    private final EventListener<MotionEvent> motionEventEventListener = event -> {
        List<com.craftrise.mj> targetEntities = getEntities();
        if (!targetEntities.isEmpty()) {
            Collections.sort(targetEntities, new Comparator<mj>() {
                @Override
                public int compare(com.craftrise.mj entity1, com.craftrise.mj entity2) {
                    double distance1 = ThePlayer.GetDistanceToEntity(entity1);
                    double distance2 = ThePlayer.GetDistanceToEntity(entity2);
                    return Double.compare(distance1, distance2);
                }
            });

            com.craftrise.mj closestEntity = targetEntities.get(0);

            if (ThePlayer.GetDistanceToEntity(closestEntity) <= Range.getValue()) {
                faceEntity(closestEntity);
            }
        }
    };
}
