package dev.client.tenacity.utils.player;

import cr.launcher.ChatComponentText;
import cr.launcher.EnumChatFormatting;
import cr.launcher.main.a;
import dev.utils.Utils;


public class ChatUtils implements Utils {

    public static void print(boolean prefix, String message) {
        if (a.q != null) {
            if (prefix) message = "§7[§d§lTENACITY§r§7] " + message;
            a.q.a(new ChatComponentText(message),5L);
        }
    }

    public static void print(String prefix, EnumChatFormatting color, String message) {
        if (a.q != null) {
            message = "§7[§" +"§l" + prefix.toUpperCase() + "§r§7]§r §" + message;
            a.q.a(new ChatComponentText(message),5L);
        }
    }

    public static void print(Object o) {
        print(true, String.valueOf(o));
    }

    public static void send(String message) {
        if (a.q != null) {
            a.q.a(message,5L);
        }
    }

}
