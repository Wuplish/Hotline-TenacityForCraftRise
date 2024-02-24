package dev.client.tenacity.module;


import dev.client.tenacity.Tenacity;
import dev.client.tenacity.config.DragManager;
import dev.client.tenacity.ui.mainmenu.TenacityMainMenu;
import dev.event.EventListener;
import dev.event.impl.game.KeyPressEvent;
import dev.event.impl.game.GameCloseEvent;
import dev.event.impl.game.TickEvent;
import dev.event.impl.player.ChatReceivedEvent;
import dev.utils.Utils;
import cr.launcher.main.a;
import net.minecraft.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class TenacityBackgroundProcess implements Utils
{
    private final EventListener<KeyPressEvent> onKeyPress = e ->
            Tenacity.INSTANCE.getModuleCollection().getModules().stream()
            .filter(m -> m.getKeybind().getCode() == e.getKey()).forEach(Module::toggle);

    private final File dragData = new File(Tenacity.DIRECTORY, "Drag.json");

    private final EventListener<GameCloseEvent> onGameClose = e ->
    {
        //Tenacity.INSTANCE.getConfigManager().saveDefaultConfig();
        DragManager.saveDragData();
    };

    private final EventListener<ChatReceivedEvent> onChatReceived = e ->
    {
        if (a.q == null)
        {
            return;
        }

     //   String message = StringUtils.stripControlCodes(e.message.getUnformattedText());
       
    };

    private final EventListener<TickEvent> onTick = e ->
    {
    };
}
