package dev.client.tenacity.ui.clickguis.dropdown.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public abstract class Component
{
    public Minecraft mc = Minecraft.getMinecraft();

    abstract public void initGui();

    public abstract void keyTyped(char typedChar, int keyCode);

    abstract public void drawScreen(int mouseX, int mouseY);

    abstract public void mouseClicked(int mouseX, int mouseY, int button);

    abstract public void mouseReleased(int mouseX, int mouseY, int state);
}
