package dev.client.tenacity.hackerdetector.checks;

import com.craftrise.mg;

import dev.client.tenacity.hackerdetector.Category;
import dev.client.tenacity.hackerdetector.Detection;
import dev.client.tenacity.hackerdetector.utils.MovementUtils;

public class FlightA extends Detection {

    public FlightA() {
        super("Flight A", Category.MOVEMENT);
    }

    @Override
    public boolean runCheck(mg player) {
        return  player.aT.b(5L) == 0 && MovementUtils.isMoving(player);
    }
}
