package dev.client.tenacity.module;

import dev.client.tenacity.module.impl.combat.Aimbot;
import dev.client.tenacity.module.impl.combat.Criticals;
import dev.client.tenacity.module.impl.combat.KillAura;
import dev.client.tenacity.module.impl.combat.TargetStrafe;
import dev.client.tenacity.module.impl.exploit.AntiAura;
import dev.client.tenacity.module.impl.exploit.AntiInvis;
import dev.client.tenacity.module.impl.exploit.Disabler;
import dev.client.tenacity.module.impl.exploit.Regen;
import dev.client.tenacity.module.impl.exploit.ResetVL;
import dev.client.tenacity.module.impl.misc.AntiDesync;
import dev.client.tenacity.module.impl.misc.AntiFreeze;
import dev.client.tenacity.module.impl.misc.AntiTabComplete;
import dev.client.tenacity.module.impl.misc.AutoHypixel;
import dev.client.tenacity.module.impl.misc.AutoRespawn;
import dev.client.tenacity.module.impl.misc.HackerDetector;
import dev.client.tenacity.module.impl.misc.LightningTracker;
import dev.client.tenacity.module.impl.misc.NoRotate;
import dev.client.tenacity.module.impl.misc.Spammer;
import dev.client.tenacity.module.impl.movement.Clip;
import dev.client.tenacity.module.impl.movement.FastLadder;
import dev.client.tenacity.module.impl.movement.Flight;
import dev.client.tenacity.module.impl.movement.Scaffold;
import dev.client.tenacity.module.impl.movement.Speed;
import dev.client.tenacity.module.impl.movement.Sprint;
import dev.client.tenacity.module.impl.player.AntiVoid;

import dev.client.tenacity.module.impl.player.Blink;
import dev.client.tenacity.module.impl.player.ChestStealer;
import dev.client.tenacity.module.impl.player.FastPlace;
import dev.client.tenacity.module.impl.player.NoFall;
import dev.client.tenacity.module.impl.player.Timerr;
import dev.client.tenacity.module.impl.render.Animations;
import dev.client.tenacity.module.impl.render.ArraylistMod;
import dev.client.tenacity.module.impl.render.Brightness;
import dev.client.tenacity.module.impl.render.ChinaHat;
import dev.client.tenacity.module.impl.render.ClickGuiMod;
import dev.client.tenacity.module.impl.render.HudMod;
import dev.client.tenacity.module.impl.render.NotificationsMod;
import dev.client.tenacity.module.impl.render.Radar;
import dev.client.tenacity.module.impl.render.SessionStats;

import org.reflections.Reflections;

import java.util.*;
import java.util.stream.Collectors;

public class ModuleCollection
{
    public static boolean reloadModules;

    private static HashMap < Class <? extends Module > , Module > modules = new HashMap<>();
    private final List < Class <? extends Module >> hiddenModules = new ArrayList<>(Arrays.asList(ArraylistMod.class, NotificationsMod.class));

    public List < Class <? extends Module >> getHiddenModules()
    {
        return hiddenModules;
    }

    public static List<Module> getModules()
    {
        return new ArrayList<>(modules.values());
    }

    public void setModules(HashMap < Class <? extends Module > , Module > modules)
    {
        this.modules = modules;
    }

    public ModuleCollection()
    {
    	//Combat
    	modules.put(dev.client.tenacity.module.impl.combat.KillAura.class, new KillAura());
    	modules.put(dev.client.tenacity.module.impl.combat.Aimbot.class, new Aimbot());
    	modules.put(dev.client.tenacity.module.impl.combat.Criticals.class, new Criticals());
    	modules.put(dev.client.tenacity.module.impl.combat.TargetStrafe.class, new TargetStrafe());
    	//Movement
    	modules.put(dev.client.tenacity.module.impl.movement.Flight.class, new Flight());
    	modules.put(dev.client.tenacity.module.impl.movement.Scaffold.class, new Scaffold());
    	modules.put(dev.client.tenacity.module.impl.movement.Clip.class, new Clip());
    	modules.put(dev.client.tenacity.module.impl.movement.FastLadder.class, new FastLadder());
    	modules.put(dev.client.tenacity.module.impl.movement.Speed.class, new Speed());
    	modules.put(dev.client.tenacity.module.impl.movement.Sprint.class, new Sprint());
    	//Player
    	modules.put(dev.client.tenacity.module.impl.player.AntiVoid.class, new AntiVoid());
    	modules.put(dev.client.tenacity.module.impl.player.Blink.class, new Blink());
    	modules.put(dev.client.tenacity.module.impl.player.ChestStealer.class, new ChestStealer());
    	modules.put(dev.client.tenacity.module.impl.player.FastPlace.class, new FastPlace());
    	modules.put(dev.client.tenacity.module.impl.player.NoFall.class, new NoFall());
    	modules.put(dev.client.tenacity.module.impl.player.Timerr.class, new Timerr());
    	//Exploits
    	modules.put(dev.client.tenacity.module.impl.exploit.AntiAura.class, new AntiAura());
    	modules.put(dev.client.tenacity.module.impl.exploit.AntiInvis.class, new AntiInvis());
     	modules.put(dev.client.tenacity.module.impl.exploit.Disabler.class, new Disabler());
    	modules.put(dev.client.tenacity.module.impl.exploit.Regen.class, new Regen());
    	modules.put(dev.client.tenacity.module.impl.exploit.ResetVL.class, new ResetVL());
    	//Render
        modules.put(dev.client.tenacity.module.impl.render.ArraylistMod.class, new ArraylistMod());
        modules.put(dev.client.tenacity.module.impl.render.HudMod.class, new HudMod());
        modules.put(dev.client.tenacity.module.impl.render.NotificationsMod.class, new NotificationsMod());
        modules.put(dev.client.tenacity.module.impl.render.ClickGuiMod.class, new ClickGuiMod());
        modules.put(dev.client.tenacity.module.impl.render.SessionStats.class, new SessionStats());
        modules.put(dev.client.tenacity.module.impl.render.Radar.class, new Radar());
        modules.put(dev.client.tenacity.module.impl.render.Animations.class, new Animations());
        modules.put(dev.client.tenacity.module.impl.render.Brightness.class, new Brightness());
        modules.put(dev.client.tenacity.module.impl.render.ChinaHat.class, new ChinaHat());
         //Misc
        modules.put(dev.client.tenacity.module.impl.misc.AntiDesync.class, new AntiDesync());
        modules.put(dev.client.tenacity.module.impl.misc.AntiFreeze.class, new AntiFreeze());
        modules.put(dev.client.tenacity.module.impl.misc.AntiTabComplete.class, new AntiTabComplete());
        modules.put(dev.client.tenacity.module.impl.misc.AutoRespawn.class, new AutoRespawn());
        modules.put(dev.client.tenacity.module.impl.misc.AutoHypixel.class, new AutoHypixel());
        modules.put(dev.client.tenacity.module.impl.misc.HackerDetector.class, new HackerDetector());
        modules.put(dev.client.tenacity.module.impl.misc.LightningTracker.class, new LightningTracker());
        modules.put(dev.client.tenacity.module.impl.misc.NoRotate.class, new NoRotate());         
        modules.put(dev.client.tenacity.module.impl.misc.Spammer.class, new Spammer());
    }

    public List<Module> getModulesInCategory(Category c)
    {
        return this.modules.values().stream().filter(m -> m.getCategory() == c).collect(Collectors.toList());
    }

    public Module get(Class <? extends Module > mod)
    {
        return this.modules.get(mod);
    }

    public Module getModuleByName(String name)
    {
        return this.modules.values().stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Module> getModulesContains(String text)
    {
        return this.modules.values().stream().filter(m -> m.getName().toLowerCase().contains(text.toLowerCase())).collect(Collectors.toList());
    }

    public final List<Module> getToggledModules()
    {
        return this.modules.values().stream().filter(Module::isToggled).collect(Collectors.toList());
    }
}
