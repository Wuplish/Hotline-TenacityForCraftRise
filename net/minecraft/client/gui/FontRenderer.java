package net.minecraft.client.gui;
import com.craftrise.client.cz;
import com.craftrise.client.d0;
import com.craftrise.client.gD;

import cr.launcher.Config;
import cr.launcher.ResourceLocation;

public class FontRenderer{
    public int FONT_HEIGHT;

    private d0 FontRendererObj;
    public FontRenderer(d0 fontRendererObj) {
        this.FontRendererObj = fontRendererObj;
        this.FONT_HEIGHT = FontRendererObj.i;
    }
    public FontRenderer(cz ap, ResourceLocation resourceLocation, gD textureManager, boolean b) {
        super();
    }
	public int drawStringWithShadow(String text, float x, float y, int color)
    {
        return FontRendererObj.a(text, x, y, color, true,5L);
    }
    public int drawString(String text, int x, int y, int color)
    {
        return FontRendererObj.a(text, (float)x, (float)y, color, false,5L);
    }
    public int drawString(String text, float x, float y, int color, boolean dropShadow){
        return FontRendererObj.a(text,x,y,color,dropShadow,5L);
    }
    public int getStringWidth(String text){
        return FontRendererObj.b(text,5L);
    }
    public int getCharWidth(char character){
        return FontRendererObj.a(character,5L);
    }

}
