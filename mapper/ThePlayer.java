package mapper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.craftrise.dR;
public class ThePlayer {
    public static double GetPosX() {
        return TheMinecraft.GetPlayer().bE;
    }

    public static double GetPosY() {
        return TheMinecraft.GetPlayer().aY;
    }

    public static double GetPosZ() {
        return TheMinecraft.GetPlayer().bH;
    }
    public static boolean onGround(){
        return TheMinecraft.GetPlayer().s.a(522424);
    }
    public static double GetprevPosZ() {
        return TheMinecraft.GetPlayer().at;
    }
    public static boolean isMoving() {
        return TheMinecraft.GetPlayer() != null && (TheMinecraft.GetPlayer().l.b.a(5L) != 0.0f || TheMinecraft.GetPlayer().l.c.a(5L) != 0.0f);
    }
    public static com.craftrise.lU getInventory(final com.craftrise.mg entityPlayer) {
        return entityPlayer.J;
    }
    public static com.craftrise.qJ getInventoryContainer(final com.craftrise.mg entityPlayer) {
        return entityPlayer.b1;
    }
    public static void SetRotationYaw(float value){
        TheMinecraft.GetPlayer().bL = value;
    }

    public static void SetRotationPitch(float value){
        TheMinecraft.GetPlayer().N = value;
    }
    public static com.craftrise.qJ getInventoryContainer() {
        return getInventoryContainer(TheMinecraft.GetPlayer());
    }
    public static double GetDistanceToEntity(com.craftrise.m9 m92) {
        try {
            double f2 = (TheMinecraft.GetPlayer().bE- m92.bE);
            double f3 = (TheMinecraft.GetPlayer().aY - m92.aY);
            double f4 = (TheMinecraft.GetPlayer().bH - m92.bH);
            double result = Math.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }

    }
    public static void jump() {
        try {
            Class<?> clazz = Class.forName("com.craftrise.mg");
            Method method = clazz.getMethod("y", Long.TYPE);
            method.setAccessible(true);
            method.invoke((Object)TheMinecraft.GetPlayer(), 5L);
        }
        catch (Exception exception) {

        }
    }
    public static void Strafe(float f) {
        if (!ThePlayer.isMoving()) {
            return;
        }
        ThePlayer.SetMotionX(-Math.sin(ThePlayer.Direction()) * (double)f);
        ThePlayer.SetMotionZ(Math.cos(ThePlayer.Direction()) * (double)f);
    }
    public static void SetMotionX(double d) {
        TheMinecraft.GetPlayer().bh = new dR(d);
    }

    public static void SetMotionY(double d) {
    	TheMinecraft.GetPlayer().aT = new dR(d);
    }

    public static void SetMotionZ(double d) {
    	TheMinecraft.GetPlayer().bf = new dR(d);
    }
    public static float GetrotationYaw() {
        return TheMinecraft.GetPlayer().bL;
    }
    public static double GetMotionX() {
        return TheMinecraft.GetPlayer().bh.b(5L);
    }

    public static double GetMotionY() {
        return TheMinecraft.GetPlayer().aT.b(5L);
    }

    public static double GetMotionZ() {
        return TheMinecraft.GetPlayer().bf.b(5L);
    }

    public static double Direction() {
        float f = ThePlayer.GetrotationYaw();
        if (TheMinecraft.GetPlayer().l.b.a(5L) < 0.0f) {
            f += 180.0f;
        }
        float f2 = 1.0f;
        if (TheMinecraft.GetPlayer().l.b.a(5L) < 0.0f) {
            f2 = -0.5f;
        } else if (TheMinecraft.GetPlayer().l.b.a(5L) > 0.0f) {
            f2 = 0.5f;
        }
        if (TheMinecraft.GetPlayer().l.c.a(5L) > 0.0f) {
            f -= 90.0f * f2;
        }
        if (TheMinecraft.GetPlayer().l.c.a(5L) < 0.0f) {
            f += 90.0f * f2;
        }
        return Math.toRadians(f);
    }
}

