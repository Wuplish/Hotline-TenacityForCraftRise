package dev.client.tenacity.hackerdetector.utils;

import org.lwjgl.util.vector.Vector2f;

import com.craftrise.dR;
import com.craftrise.mg;

import cr.launcher.main.a;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;


public class MovementUtils {

    public static boolean isMoving(mg player) {
        return player.aZ.a(5L) != 0F || player.cm.a(5L) != 0F;
    }

    public static boolean isFalseFlaggable(mg player) {
        return player.z(5L) || player.Z < 10;
    }
    public static float getMoveYaw(float yaw) {
        Vector2f from = new Vector2f((float) a.q.a6, (float) a.q.G),
                to = new Vector2f((float) a.q.bE, (float) a.q.bH),
                diff = new Vector2f(to.x - from.x, to.y - from.y);

        double x = diff.x, z = diff.y;
        if (x != 0 || z != 0) {
            yaw = (float) Math.toDegrees((Math.atan2(-x, z) + MathHelper.PI2) % MathHelper.PI2);
        }
        return yaw;
    }

    public static void setSpeed(double moveSpeed, float yaw, double strafe, double forward) {
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        a.q.bh = new dR(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        a.q.bf = new dR(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }
    public static double getBaseMoveSpeed() {
        double baseSpeed = a.q.S.b(5L) * 2.873;
        return baseSpeed;
    }

    public static void setSpeed(double moveSpeed) {
        setSpeed(moveSpeed, a.q.bL, a.q.l.c.a(5L), a.q.l.b.a(5L));
    }

}
