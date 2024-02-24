package net.minecraft.client.gui;
import com.craftrise.client.dh;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.lwjgl.opengl.GL11;

import cr.launcher.Config;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.utils.render.GLUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import com.craftrise.client.gK;
import com.craftrise.client.ed;
import com.craftrise.client.gO;

public class Gui extends dh{
	public float zLevel = this.d;
    
    public static void drawRect2(double x, double y, double width, double height, int color)
    {
        RenderUtil.resetColor();
        GLUtil.setup2DRendering(() -> GLUtil.render(GL11.GL_QUADS, () ->
        {
            RenderUtil.color(color);
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x, y + height);
            GL11.glVertex2d(x + width, y + height);
            GL11.glVertex2d(x + width, y);
        }));
    }
    public static void drawHorizontalLine(int startX, int endX, int y, int color){
        if (endX < startX)
        {
            int i = startX;
            startX = endX;
            endX = i;
        }

        drawRect(startX, y, endX + 1, y + 1, color);
    }
    public static void drawVerticalLine(int x, int startY, int endY, int color)
    {
        if (endY < startY)
        {
            int i = startY;
            startY = endY;
            endY = i;
        }

        drawRect(x, startY + 1, x + 1, endY, color);
    }

    public static void drawRect(int left, int top, int right, int bottom, int color){
        dh.a(left,top,right,bottom,color,5L);
    }
    public static void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawString(text, (int)(x - getStringWidth(text) / 2), (int)y, color);
    }
    public void drawString(com.craftrise.client.d0 fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.a(text, (float)x, (float)y, color,5L);
    }
    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight){
        dh.a(x,y,u,v,width,height,textureWidth,textureHeight,5L);
    }
    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight){
        dh.a(x,y,u,v,uWidth,vHeight,width,height,tileWidth,tileHeight,5L);
    }
    public static int getStringWidth(String s1) {
        try {
            Class<?> mcjClass = Config.getMinecraft().j.getClass();
            Method aMethod = mcjClass.getMethod("a", String.class);
            Object result = aMethod.invoke(Config.getMinecraft().j, s1);

            if (result instanceof Integer) {
                int intValue = (int) result;
                return intValue;
            } else {
                throw new IllegalStateException("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
