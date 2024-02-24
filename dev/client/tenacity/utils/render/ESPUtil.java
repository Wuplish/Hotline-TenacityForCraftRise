package dev.client.tenacity.utils.render;

import dev.utils.Utils;
import dev.utils.misc.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import com.craftrise.client.en;

import cr.launcher.Config;

import com.craftrise.client.A;
import com.craftrise.m9;
import com.craftrise.ah;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class ESPUtil implements Utils {
	private static final A frustum = new A();
    private static final FloatBuffer windPos = BufferUtils.createFloatBuffer(4 * Float.BYTES);
    private static final IntBuffer intBuffer = BufferUtils.createIntBuffer(16 * Integer.BYTES);
    private static final FloatBuffer floatBuffer1  = BufferUtils.createFloatBuffer(16 * Float.BYTES);
    private static final FloatBuffer floatBuffer2  = BufferUtils.createFloatBuffer(16 * Float.BYTES);
    static ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    
    public static FloatBuffer getBufferFloat(int n2) {
    	try {
    		Method m = en.class.getDeclaredMethod("a", int.class);
    		m.setAccessible(true);
    		return (FloatBuffer)m.invoke(Config.getMinecraft(), n2);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return (FloatBuffer)null;
    	}
    }
    public static IntBuffer getBufferInt(int n2) {
    	try {
    		Method m = en.class.getDeclaredMethod("a", int.class);
    		m.setAccessible(true);
    		return (IntBuffer)m.invoke(Config.getMinecraft(), n2);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return (IntBuffer)null;
    	}
    }


    public static Vector3f projectOn2D(float x, float y, float z, int scaleFactor) {
        glGetFloat(GL_MODELVIEW_MATRIX, floatBuffer1);
        glGetFloat(GL_PROJECTION_MATRIX, floatBuffer2);
        glGetInteger(GL_VIEWPORT, intBuffer);
        if (GLU.gluProject(x, y, z, floatBuffer1, floatBuffer2, intBuffer, windPos)) {
            windPos.rewind();
            float winX = windPos.get();
            float winY = windPos.get();
            float winZ = windPos.get();
            return new Vector3f(winX / scaleFactor, (sr.getScaledHeight() - winY) / scaleFactor, winZ);
        }
        return null;
    }
    public static cr.obfuscates.h getTimer(){
        try {
            Field hField = Config.getMinecraft().getClass().getDeclaredField("cl");
            hField.setAccessible(true);
            return (cr.obfuscates.h)hField.get(Config.getMinecraft());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double[] getInterpolatedPos(m9 entity) {
        float ticks = getTimer().a.a();
        return new double[]{
                MathUtils.interpolate(entity.a6, entity.bE, ticks),
                MathUtils.interpolate(entity.h, entity.aY, ticks),
                MathUtils.interpolate(entity.G, entity.bH, ticks)
        };
    }

    public static Vector4f getEntityPositionsOn2D(m9 entity) {
        final double[] renderingEntityPos = getInterpolatedPos(entity);
        final double entityRenderWidth = entity.aw / 1.5;
        final ah bb = new ah(renderingEntityPos[0] - entityRenderWidth,
                renderingEntityPos[1], renderingEntityPos[2] - entityRenderWidth, renderingEntityPos[0] + entityRenderWidth,
                renderingEntityPos[1] + entity.t + (0.18), renderingEntityPos[2] + entityRenderWidth).a(0.15, 0.15, 0.15);

        final List<Vector3f> vectors = Arrays.asList(
                new Vector3f((float) bb.c, (float) bb.a, (float) bb.f),
                new Vector3f((float) bb.c, (float) bb.g, (float) bb.f),
                new Vector3f((float) bb.e, (float) bb.a, (float) bb.f),
                new Vector3f((float) bb.e, (float) bb.g, (float) bb.f),
                new Vector3f((float) bb.c, (float) bb.a, (float) bb.d),
                new Vector3f((float) bb.c, (float) bb.g, (float) bb.d),
                new Vector3f((float) bb.e, (float) bb.a, (float) bb.d),
                new Vector3f((float) bb.e, (float) bb.g, (float) bb.d));

        Vector4f entityPos = new Vector4f(Float.MAX_VALUE, Float.MAX_VALUE, -1.0f, -1.0f);
        ScaledResolution sr = new ScaledResolution(mc);
        for (Vector3f vector3f : vectors) {
            vector3f = projectOn2D(vector3f.x, vector3f.y, vector3f.z, sr.getScaleFactor());
            if (vector3f != null && vector3f.z >= 0.0 && vector3f.z < 1.0) {
                entityPos.x = Math.min(vector3f.x, entityPos.x);
                entityPos.y = Math.min(vector3f.y, entityPos.y);
                entityPos.z = Math.max(vector3f.x, entityPos.z);
                entityPos.w = Math.max(vector3f.y, entityPos.w);
            }
        }
        return entityPos;
    }



}
