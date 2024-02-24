package dev.client.tenacity.Hooks;

import org.lwjgl.input.Keyboard;
import com.craftrise.client.dt;

import cr.launcher.Config;
import dev.client.Client;
import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.ui.clickguis.dropdown.DropdownClickGui;
import dev.event.impl.render.Render2DEvent;
import dev.event.impl.render.Render3DEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class GuiIngameHook extends dt
{
    public static ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
    public GuiIngameHook(dt OriginalGui)
    {
        super(Config.getMinecraft());
    }
    public void KeybindCheckEvent()
    {
    }
    public void a(float var1_1, long var2_2) {
    	super.a(var1_1, var2_2);
    	Module.KeyCheckEvent();
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        Client.dispatchEvent(new Render2DEvent(scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight()));
        Tenacity.INSTANCE.getNotificationManager().drawNotifications(scaledresolution);
    }
}
