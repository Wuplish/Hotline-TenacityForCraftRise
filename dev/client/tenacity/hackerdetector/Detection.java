package dev.client.tenacity.hackerdetector;

import com.craftrise.mg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Detection {

    private String name;
    private Category type;
    private long lastViolated;

    public Detection(String name, Category type) {
        this.name = name;
        this.type = type;
    }

    public abstract boolean runCheck(mg player);
}
