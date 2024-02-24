package dev.client.tenacity.module.impl.render;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.event.EventListener;
import dev.event.impl.render.Render3DEvent;
import dev.settings.impl.BooleanSetting;
import dev.utils.misc.MathUtils;
import net.minecraft.client.renderer.GlStateManager;
import cr.launcher.Config;
import cr.launcher.main.a;
import net.minecraft.util.MathHelper;

import static org.lwjgl.opengl.GL11.*;

import java.lang.reflect.Field;

import org.lwjgl.opengl.GL11;

import com.craftrise.m9;

public class ChinaHat extends Module {

    private final BooleanSetting firstPerson = new BooleanSetting("Show in first person", false);

    public ChinaHat() {
        super("ChinaHat", Category.RENDER, "epic hat");
        this.addSettings(firstPerson);
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
    public static com.craftrise.ah getEntityBoundingBox(m9 Entity){
        try {
            Field boundingbox = com.craftrise.m9.class.getDeclaredField("aW");
            boundingbox.setAccessible(true);
            return (com.craftrise.ah)boundingbox.get(Entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private final EventListener<Render3DEvent> onRender3D = e -> {
        if (a.q == null || Config.getMinecraft().bu == null || a.q.d) return;
        if (!firstPerson.isEnabled() && Config.gameSettings.a0 == 0) return;

        double posX = a.q.a6 + (a.q.bE - a.q.a6) * getTimer().a.a() - mc.getRenderManager().j,
                posY = a.q.h + (a.q.aY - a.q.h) * getTimer().a.a() - mc.getRenderManager().t,
                posZ = a.q.G + (a.q.bH - a.q.G) * getTimer().a.a() - mc.getRenderManager().c;

        com.craftrise.ah axisalignedbb = getEntityBoundingBox(a.q);
        double height = axisalignedbb.g - axisalignedbb.a + 0.02,
                radius = axisalignedbb.e - axisalignedbb.c;

        glPushMatrix();
        GlStateManager.disableCull();
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glDisable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);
        glEnable(GL_BLEND);
        GlStateManager.disableLighting();
        GlStateManager.color(1, 1, 1, 1);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        float yaw = MathUtils.interpolate(a.q.C, a.q.bL, getTimer().a.a()).floatValue();
        float pitchInterpolate = MathUtils.interpolate(a.q.t, a.q.aG, getTimer().a.a()).floatValue();

        glTranslated(posX, posY, posZ);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glRotated(yaw, 0, -1, 0);
        glRotated(pitchInterpolate / 3.0, 0, 0, 0);
        glTranslatef(0, 0, pitchInterpolate / 270.0F);
        glLineWidth(2);
        glBegin(GL_LINE_LOOP);

        // outline/border or whatever you call it
        for (int i = 0; i <= 180; i++) {
            int color1 = ColorUtil.rainbow(7, i * 4, 1, 1, .5f).getRGB();
            GlStateManager.color(1, 1, 1, 1);
            RenderUtil.color(color1);
            glVertex3d(
                    posX - Math.sin(i * MathHelper.PI2 / 90) * radius,
                    posY + height - (0.23) - 0.002,
                    posZ + Math.cos(i * MathHelper.PI2 / 90) * radius
            );
        }
        glEnd();

        glBegin(GL_TRIANGLE_FAN);
        int color12 = ColorUtil.rainbow(7, 4, 1, 1, .7f).getRGB();
        RenderUtil.color(color12);
        glVertex3d(posX, posY + height + 0.3 - (0.23), posZ);

        // draw hat
        for (int i = 0; i <= 180; i++) {
            int color1 = ColorUtil.rainbow(7, i * 4, 1, 1, .2f).getRGB();
            GlStateManager.color(1, 1, 1, 1);
            RenderUtil.color(color1);
            glVertex3d(posX - Math.sin(i * MathHelper.PI2 / 90) * radius,
                    posY + height - (0.23F),
                    posZ + Math.cos(i * MathHelper.PI2 / 90) * radius
            );

        }
        glVertex3d(posX, posY + height + 0.3 - (0.23), posZ);
        glEnd();


        glPopMatrix();

        glEnable(GL_CULL_FACE);
        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_FLAT);
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
    };

}
