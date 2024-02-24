package net.minecraft.client;

import cr.launcher.Config;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderManager;
import com.craftrise.client.gD;
import com.craftrise.client.g8;

import java.lang.reflect.Field;

import com.craftrise.client.be;
import com.craftrise.client.fV;

public class Minecraft{

    private static Minecraft TheMinecraft;
    public FontRenderer fontRendererObj;
    public RenderManager renderManager;
    public int rightClickDelayTimer;

    public Minecraft() {
        this.fontRendererObj = new FontRenderer(Config.getMinecraft().j);
        this.renderManager = new RenderManager(Minecraft.getMinecraft());
    }

    public static Minecraft getMinecraft(){
        return TheMinecraft;
    }
    public static void init() {
    	TheMinecraft = new Minecraft();
    }
    public static g8 getRenderManager() {
    	try {
    		Field fi = com.craftrise.client.S.class.getDeclaredField("a0");
    		fi.setAccessible(true);
    		return (g8)fi.get(Config.getMinecraft());
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public be getFramebuffer(){
    	try {
    		Field fi = com.craftrise.client.S.class.getDeclaredField("ak");
    		fi.setAccessible(true);
    		return (be)fi.get(Config.getMinecraft());
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    public gD getTextureManager() {
        return Config.getTextureManager();
    }
    public fV getResourceManager() {
       return Config.getResourceManager();
    }

    public void displayGuiScreen(GuiScreen guiScreenIn){
        Config.getMinecraft().a(guiScreenIn,5L);
    }

}
