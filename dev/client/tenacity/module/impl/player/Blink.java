package dev.client.tenacity.module.impl.player;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.network.PacketSendEvent;
import dev.event.impl.player.MotionEvent;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.NumberSetting;
import dev.utils.network.PacketUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.craftrise.lE;
import com.craftrise.lv;

@SuppressWarnings("unused")
public final class Blink extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Fake Lag", "Fake Lag");
    private final NumberSetting packetCap = new NumberSetting("Packet Cap", 1, 1000, 1, 1);
    private final List<lv<?>> packetsList = new CopyOnWriteArrayList<>();
    private final EventListener<PacketSendEvent> packetSendEventEventListener = event -> {
        if(event.getPacket() instanceof lE){
            if(event.getPacket() instanceof lE.a){
                packetsList.add(event.getPacket());
                event.cancel();
            }
            if(event.getPacket() instanceof lE.b){
                packetsList.add(event.getPacket());
                event.cancel();
            }
            if(event.getPacket() instanceof lE.c){
                packetsList.add(event.getPacket());
                event.cancel();
            }
        }
    };

    private final EventListener<MotionEvent> motionEventEventListener = event -> {
        if(packetsList.size() >= packetCap.getValue().intValue() && !packetsList.isEmpty()){
            for(lv<?> packet : packetsList){
                PacketUtils.sendPacketNoEvent(packet);
                packetsList.remove(packet);
            }
        }
    };

    public Blink() {
        super("Blink", Category.PLAYER, "holds movement packets");
        this.addSettings(mode, packetCap);
    }
}
