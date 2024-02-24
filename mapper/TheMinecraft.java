package mapper;
import com.craftrise.client.cf;
import cr.launcher.BlockPos;
import cr.launcher.IChatComponent;
import cr.launcher.main.a;
import cr.launcher.Config;
import dev.client.tenacity.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import com.craftrise.client.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class TheMinecraft {
   
    public static com.craftrise.client.fa GetPlayer(){
        return a.q;
    }
    public static ez GetPlayerControllerMp() {
        return TheMinecraft.GetMinecraft().b;
    }

    public static com.craftrise.client.dG getCurrentScreen() {
        return Config.getMinecraft().bw;
    }

    public static com.craftrise.client.S GetMinecraft(){
        return Config.getMinecraft();
    }
    public static com.craftrise.client.cf GetWorld(){
        return GetMinecraft().bu;
    }

    
    
}
