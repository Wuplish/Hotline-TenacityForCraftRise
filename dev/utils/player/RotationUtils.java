package dev.utils.player;

import com.craftrise.m9;
import com.craftrise.n5;
import com.craftrise.q;

import cr.launcher.Config;
import cr.launcher.main.a;
import dev.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;


public class RotationUtils implements Utils {

    /*
     * Sets the player's head rotations to the given yaw and pitch (visual-only).
     */

    public static void setRotations(float yaw, float pitch) {
    	a.q.a = yaw;
        a.q.ap = pitch;
    }
    public static float[] getFacingRotations2(final int paramInt1, final double d, final int paramInt3) {
        final n5 localEntityPig = new n5(Config.getMinecraft().bu);
        localEntityPig.bE = paramInt1 + 0.5;
        localEntityPig.aY = d + 0.5;
        localEntityPig.bH = paramInt3 + 0.5;
        return getRotationsNeeded(localEntityPig);
    }

    public static float clampRotation() {
        float rotationYaw = a.q.bL;
        float n = 1.0f;
        if (a.q.l.b.a(5L) < 0.0f) {
            rotationYaw += 180.0f;
            n = -0.5f;
        }
        else if (a.q.l.b.a(5L) > 0.0f) {
            n = 0.5f;
        }
        if (a.q.l.c.a(5L) > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (a.q.l.c.a(5L) < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }


    public static float smoothRotation(float from, float to, float speed) {
        float f = MathHelper.wrapAngleTo180_float(to - from);

        if (f > speed) {
            f = speed;
        }

        if (f < -speed) {
            f = -speed;
        }

        return from + f;
    }

    /*
     * Sets the player's head rotations to the given yaw and pitch (visual-only).
     */
    public static void setRotations(float[] rotations) {
        setRotations(rotations[0], rotations[1]);
    }
    public static float getSensitivityMultiplier() {
        float SENSITIVITY = Config.gameSettings.b7 * 0.6F + 0.2F;
        return (SENSITIVITY * SENSITIVITY * SENSITIVITY * 8.0F) * 0.15F;
    }
    public static float getEyeHeight(m9 Entity) {
        return Entity.t * 0.85F;
    }

    public static float[] getRotationsNeeded(final m9 entity) {
        if (entity == null) {
            return null;
        }
        Minecraft mc = Minecraft.getMinecraft();
        final double xSize = entity.bE - a.q.bE;
        final double ySize = entity.aY + a.q.aY +1;
        final double zSize = entity.bH - a.q.bH;
        final double theta = MathHelper.sqrt_double(xSize * xSize + zSize * zSize);
        final float yaw = (float) (Math.atan2(zSize, xSize) * 180 / Math.PI) - 90;
        final float pitch = (float) (-(Math.atan2(ySize, theta) * 180 / Math.PI));
        return new float[]{(a.q.bL + MathHelper.wrapAngleTo180_float(yaw - a.q.bL)) % 360, (a.q.N + MathHelper.wrapAngleTo180_float(pitch - a.q.N)) % 360.0f};
    }


    public static float getYaw(Vec3 to) {
        float x = (float) (to.xCoord - a.q.bE);
        float z = (float) (to.zCoord - a.q.bH);
        float var1 = (float) (StrictMath.atan2(z, x) * 180.0D / StrictMath.PI) - 90.0F;
        float rotationYaw = a.q.bL;
        return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
    }

    public static Vec3 getVecRotations(float yaw, float pitch) {
        double d = Math.cos(Math.toRadians(-yaw) - Math.PI);
        double d1 = Math.sin(Math.toRadians(-yaw) - Math.PI);
        double d2 = -Math.cos(Math.toRadians(-pitch));
        double d3 = Math.sin(Math.toRadians(-pitch));
        return new Vec3(d1 * d2, d3, d * d2);
    }

    public static float[] getRotations(double posX, double posY, double posZ) {
        double x = posX - a.q.bE, z = posZ - a.q.aY, y = posY - a.q.bH;
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (MathHelper.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (-(MathHelper.atan2(y, d3) * 180.0D / Math.PI));
        return new float[]{yaw, pitch};
    }

}
