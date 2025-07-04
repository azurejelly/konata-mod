package dev.azuuure.konata.config;

public class KonataModConfig {

    private boolean enabled;
    private boolean randomizedPosition;
    private float delay;
    private int chance;

    public KonataModConfig() {
        this.enabled = true;
        this.randomizedPosition = true;
        this.delay = 1500;
        this.chance = 10_000;
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
}
