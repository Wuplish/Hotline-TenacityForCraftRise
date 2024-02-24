package net.minecraft.client.gui;
import com.craftrise.client.dG;
import com.craftrise.client.et;

import net.minecraft.client.Minecraft;

import java.io.IOException;

public class GuiScreen extends dG{
    public int width = this.u;
    public int height = this.A;
    public static dG GuiScreen;
    public Minecraft mc = Minecraft.getMinecraft();

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.a(typedChar,keyCode);
    }
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        super.mouseClicked(mouseX,mouseY,mouseButton);
    }
    protected void actionPerformed(et et2) throws IOException {
        super.actionPerformed(et2);
    }
    protected void mouseReleased(int mouseX, int mouseY, int state){
        super.a(mouseX, mouseY, state);
    }
    public void initGui() {
        super.initGui();
    }
    public void updateScreen() {
        super.updateScreen();
    }
    public void onGuiClosed(){
        super.onGuiClosed();
    }
    public boolean doesGuiPauseGame() {
		return true;
    }
    public static boolean isKeyComboCtrlA(int keyID){
        try{
            return (boolean)com.craftrise.client.dG.class.getMethod("c",int.class).invoke(GuiScreen);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isKeyComboCtrlC(int keyID){
        try{
            return (boolean)com.craftrise.client.dG.class.getMethod("b",int.class).invoke(GuiScreen);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isKeyComboCtrlV(int keyID){
        try{
            return (boolean)com.craftrise.client.dG.class.getMethod("a",int.class).invoke(GuiScreen);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isKeyComboCtrlX(int keyID){
        try{
            return (boolean)com.craftrise.client.dG.class.getMethod("d",int.class).invoke(GuiScreen);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isCtrlKeyDown(){
        try{
            return (boolean)com.craftrise.client.dG.class.getMethod("f").invoke(GuiScreen);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isAltKeyDown(){
        try{
            return (boolean)com.craftrise.client.dG.class.getMethod("b").invoke(GuiScreen);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isShiftKeyDown(){
        try{
            return (boolean)com.craftrise.client.dG.class.getMethod("a").invoke(GuiScreen);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
