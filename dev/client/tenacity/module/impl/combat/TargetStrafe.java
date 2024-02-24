package dev.client.tenacity.module.impl.combat;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.module.impl.movement.Flight;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.event.impl.render.Render3DEvent;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ColorSetting;
import dev.settings.impl.MultipleBoolSetting;
import dev.settings.impl.NumberSetting;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import cr.launcher.Config;
import cr.launcher.main.a;

import java.awt.*;
import java.lang.reflect.Field;

import static org.lwjgl.opengl.GL11.*;

public final class TargetStrafe extends Module {

    public static final BooleanSetting space = new BooleanSetting("Require space key", true);
    public static final BooleanSetting auto3rdPerson = new BooleanSetting("Auto 3rd Person", false);
    public static final NumberSetting radius = new NumberSetting("Radius", 1.5, 8, 0.5, 0.5);
    private static final MultipleBoolSetting adaptiveSettings = new MultipleBoolSetting("Adaptive",
            new BooleanSetting("Edges", false),
            new BooleanSetting("Behind", false),
            new BooleanSetting("Liquids", false),
            new BooleanSetting("Controllable", true)
    );
    private static int strafe = 1;
    private final BooleanSetting circle = new BooleanSetting("Draw circle", true);
    private final ColorSetting color = new ColorSetting("Color", new Color(-16711712));
    private final DecelerateAnimation animation = new DecelerateAnimation(250, radius.getValue(), Direction.FORWARDS);

    public TargetStrafe() {
        super("TargetStrafe", Category.COMBAT, "strafe around targets");
        this.addSettings(adaptiveSettings, auto3rdPerson, space, circle, radius, color);
    }

    private final EventListener<MotionEvent> onMotion = e -> {
        if (canStrafe()) {
            if (auto3rdPerson.isEnabled() &&Config.gameSettings.a0 == 0) {
            	Config.gameSettings.a0 = 1;
            }
            if (a.q.ax) {
                strafe = -strafe;
            } else {
                if (adaptiveSettings.getSetting("Controllable").isEnabled()) {
                    if (Config.gameSettings.bz.a() != null) strafe = 1;
                    if (Config.gameSettings.D.a() != null) strafe = -1;
                }
                if (adaptiveSettings.getSetting("Edges").isEnabled()) {
                    strafe = -strafe;
                }
                if (adaptiveSettings.getSetting("Liquids").isEnabled()) {
                    strafe = -strafe;
                }
            }
        } else if (auto3rdPerson.isEnabled() && Config.gameSettings.a0 != 0) {
        	Config.gameSettings.a0 = 0;
        }
    };

    private final EventListener<Render3DEvent> onRender3D = e -> {
        if (circle.isEnabled()) {
            if (animation.getEndPoint() != radius.getValue()) animation.setEndPoint(radius.getValue());
            boolean canStrafe = canStrafe();
            animation.setDirection(canStrafe ? Direction.FORWARDS : Direction.BACKWARDS);
            if (canStrafe || !animation.isDone()) {
                drawCircle(5, 0xFF000000);
                drawCircle(3, color.getColor().getRGB());
            }
        }
    };

    

    public static boolean canStrafe() {
        if (!Tenacity.INSTANCE.isToggled(TargetStrafe.class) || (space.isEnabled() && !Keyboard.isKeyDown(Keyboard.KEY_SPACE))) {
            return false;
        }
        return Tenacity.INSTANCE.isToggled(KillAura.class)
                && ((KillAura) (Tenacity.INSTANCE.getModuleCollection().get(KillAura.class))).isValid(KillAura.target)
                && Tenacity.INSTANCE.isToggled(TargetStrafe.class)
                && !KillAura.target.d;
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

   

    private void drawCircle(float lineWidth, int color) {
        com.craftrise.mj entity = KillAura.target;
        if (entity == null) return;
        double x = entity.a6 + (entity.bE - entity.a6) *getTimer().a.a() - mc.getRenderManager().h;
        double y = entity.h + (entity.aY - entity.h) * getTimer().a.a() - mc.getRenderManager().n;
        double z = entity.G + (entity.bH - entity.G) * getTimer().a.a() - mc.getRenderManager().g;


        glPushMatrix();
        RenderUtil.color(color, (float) ((animation.getOutput() / radius.getValue()) / 2F));
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glLineWidth(lineWidth);
        glEnable(GL_BLEND);
        glEnable(GL_LINE_SMOOTH);

        glBegin(GL_LINE_STRIP);
        double pi2 = Math.PI * 2.0;
        for (int i = 0; i <= 90; ++i) {
            glVertex3d(x + animation.getOutput() * Math.cos(i * pi2 / 45.0), y, z + animation.getOutput() * Math.sin(i * pi2 / 45.0));
        }
        glEnd();

        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glColor4f(1, 1, 1, 1);
        glPopMatrix();
    }


}
