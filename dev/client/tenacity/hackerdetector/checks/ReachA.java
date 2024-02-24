package dev.client.tenacity.hackerdetector.checks;

import com.craftrise.mg;

import dev.client.tenacity.hackerdetector.Category;
import dev.client.tenacity.hackerdetector.Detection;

public class ReachA extends Detection {

    public ReachA() {
        super("Reach A", Category.COMBAT);
    }

    @Override
    public boolean runCheck(mg player) {
        return false;
    }
}
