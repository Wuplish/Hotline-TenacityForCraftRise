package dev.client.tenacity.config;

import net.minecraft.client.Minecraft;

import java.io.File;

import cr.launcher.Config;

public class LocalConfig extends Configuration
{
    private final File file;

    public LocalConfig(String name)
    {
        super(name);
        this.file = new File(Config.getMinecraft().b2 + "/Tenacity/Configs/" + name + ".json");
    }

    public File getFile()
    {
        return file;
    }
}
