package dev.client.tenacity;

import dev.client.Client;
import dev.client.ClientType;
import dev.client.tenacity.Hooks.GuiIngameHook;
import dev.client.tenacity.config.ConfigManager;
import dev.client.tenacity.config.DragManager;
import dev.client.tenacity.utils.misc.DiscordRP;
import dev.client.tenacity.utils.objects.Dragging;
import dev.event.Event;
import dev.event.EventProtocol;
import dev.event.impl.render.Render2DEvent;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.module.ModuleCollection;
import dev.client.tenacity.ui.notifications.NotificationManager;
import dev.client.tenacity.ui.sidegui.SideGui;
import dev.utils.Utils;
import dev.utils.font.FontUtil;
import dev.utils.font.MinecraftFontRenderer;
import dev.client.tenacity.utils.client.ReleaseType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.craftrise.client.dt;

import cr.launcher.Config;

public enum Tenacity implements Utils
{
    INSTANCE;
	public static long idk = 0;
    public static final String NAME = "Tenacity";
    public static final String VERSION = "4.0";
    public static final ReleaseType RELEASE = ReleaseType.BETA;
    public static final File DIRECTORY = new File(Config.getMinecraft().b2, "Tenacity");

    private final EventProtocol<Event> eventProtocol = new EventProtocol<Event>();
    private final NotificationManager notificationManager = new NotificationManager();
    
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final SideGui sideGui = new SideGui();
    private ModuleCollection moduleCollection;
    public static int isStarted;
    private static boolean isInitialized = false;
    public static ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());

    public void init()
    {
        this.moduleCollection = new ModuleCollection();
    }
    public static void IngameGuiSetter(dt gui)
    {
        Config.getMinecraft().K = gui;
    }
    public static void GuiIngameTransform() {
    	Module.KeyCheckEvent();
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        Client.dispatchEvent(new Render2DEvent(scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight()));
        Tenacity.INSTANCE.getNotificationManager().drawNotifications(scaledresolution);
    }
    public static void TenacityStart()
    {
    	if (!isInitialized) {
        	try {
                File logFile = new File("c:/Tenacity/log.txt");
                if (!logFile.exists()) {
                    logFile.getParentFile().mkdirs();
                    logFile.createNewFile();
                }

                PrintStream fileStream = new PrintStream(new FileOutputStream(logFile));
                System.setOut(fileStream);
                System.setErr(fileStream);
                
                System.out.println("Start Called");
                Minecraft.init();
                Map<String, Font> locationMap = new HashMap<>();
                FontUtil.tenacityBoldFont40_ = FontUtil.getFont(locationMap, "tenacitybold.ttf", 40);
                FontUtil.tenacityBoldFont40 = new MinecraftFontRenderer(FontUtil.tenacityBoldFont40_);
                FontUtil.tenacityFont14 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacity.ttf", 14), true, true);
                FontUtil.tenacityFont16 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacity.ttf", 16), true, true);
                FontUtil.tenacityFont18 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacity.ttf", 18), true, true);
                FontUtil.iconFont40 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "check.ttf", 40), true, true);
                FontUtil.tenacityFont20 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacity.ttf", 20), true, true);
                FontUtil.iconFont35 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "check.ttf", 35), true, true);
                FontUtil.tenacityBoldFont20 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacitybold.ttf", 20), true, true);
                FontUtil.iconFont26 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "check.ttf", 26), true, true);
                FontUtil.tenacityFont40 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacity.ttf", 40), true, true);
                FontUtil.iconFont20 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "check.ttf", 20), true, true);
                FontUtil.iconFont16 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "check.ttf", 20), true, true);
                FontUtil.tenacityFont24 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacity.ttf", 24), true, true);
                FontUtil.tenacityBoldFont26 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacity.ttf", 26), true, true);
                FontUtil.tenacityBoldFont32 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacitybold.ttf", 24), true, true);
                FontUtil.tenacityBoldFont22 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacitybold.ttf", 22), true, true);
                FontUtil.tenacityBoldFont40 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacitybold.ttf", 40), true, true);
                FontUtil.tenacityFont22 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacity.ttf", 22), true, true);
                FontUtil.tenacityBoldFont18 = new MinecraftFontRenderer(FontUtil.getFont(locationMap, "tenacitybold.ttf", 18), true, true);
                Tenacity.INSTANCE.init();
                Client.client = ClientType.TENACITY;
                isInitialized = true;
                System.out.println("Start Called");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    	Initialized();
    	
    }
    public static void Test() {
    	Config.getMinecraft().j.a("Test",20,20,-1,5L);
    }
    
    public static void Initialized() {
        if (isInitialized) {
            Runnable initializationTask = () -> {
                IngameGuiSetter(new GuiIngameHook(Config.getMinecraft().K));
            };

            Thread initializationThread = new Thread(initializationTask);
            initializationThread.start();
        }
    }


    public String getVersion()
    {
        return VERSION + (RELEASE != ReleaseType.PUBLIC ? " (" + RELEASE.getName() + ")" : "");
    }

    public final Color getClientColor()
    {
        return new Color(236, 133, 209);
    }

    public final Color getAlternateClientColor()
    {
        return new Color(28, 167, 222);
    }

    public SideGui getSideGui()
    {
        return sideGui;
    }

    public EventProtocol<Event> getEventProtocol()
    {
        return eventProtocol;
    }

    public NotificationManager getNotificationManager()
    {
        return notificationManager;
    }

    public ModuleCollection getModuleCollection()
    {
        return moduleCollection;
    }


    public ExecutorService getExecutorService()
    {
        return executorService;
    }

    public void setModuleCollection(ModuleCollection moduleCollection)
    {
        this.moduleCollection = moduleCollection;
    }


    public boolean isToggled(Class <? extends Module > c)
    {
        Module m = INSTANCE.moduleCollection.get(c);
        return m != null && m.isToggled();
    }

    public Dragging createDrag(Module module, String name, float x, float y)
    {
        DragManager.draggables.put(name, new Dragging(module, name, x, y));
        return DragManager.draggables.get(name);
    }
}
