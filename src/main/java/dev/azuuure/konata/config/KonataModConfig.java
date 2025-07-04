package dev.azuuure.konata.config;

import java.io.*;
import java.util.Properties;

public class KonataModConfig {

    private final File file;
    private final Properties properties;
    private boolean enabled;
    private boolean randomizedPosition;
    private boolean disabledWhenPaused;
    private float delay;
    private int chance;

    public KonataModConfig(File file) {
        this.file = file;
        this.properties = new Properties();
        this.enabled = true;
        this.randomizedPosition = true;
        this.delay = 1500;
        this.chance = 10_000;
    }

    public void load() throws IOException {
        if (!file.exists()) {
            return;
        }

        try (InputStream stream = new FileInputStream(file)) {
            properties.load(stream);
        }

        this.enabled = Boolean.parseBoolean(properties.getProperty("enabled", "true"));
        this.randomizedPosition = Boolean.parseBoolean(properties.getProperty("randomized", "false"));
        this.disabledWhenPaused = Boolean.parseBoolean(properties.getProperty("disable-paused", "false"));
        this.delay = Float.parseFloat(properties.getProperty("delay", "3000"));
        this.chance = Integer.parseInt(properties.getProperty("chance", "10000"));
    }

    public void save() throws IOException {
        this.properties.setProperty("enabled", Boolean.toString(enabled));
        this.properties.setProperty("randomized", Boolean.toString(randomizedPosition));
        this.properties.setProperty("disable-paused", Boolean.toString(disabledWhenPaused));
        this.properties.setProperty("delay", Float.toString(delay));
        this.properties.setProperty("chance", Integer.toString(chance));
        this.properties.store(new FileWriter(file), null);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }

    public boolean isRandomizedPosition() {
        return randomizedPosition;
    }

    public void setRandomizedPosition(boolean randomizedPosition) {
        this.randomizedPosition = randomizedPosition;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public boolean isDisabledWhenPaused() {
        return disabledWhenPaused;
    }

    public void setDisabledWhenPaused(boolean disabledWhenPaused) {
        this.disabledWhenPaused = disabledWhenPaused;
    }
}
