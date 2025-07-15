package dev.azuuure.konata;

import dev.azuuure.konata.config.KonataModConfig;
import dev.azuuure.konata.renderer.KonataRenderer;
import dev.azuuure.konata.sound.SoundHelper;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class KonataMod implements ClientModInitializer {

    public static final String MOD_ID = "konata";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static KonataMod instance;

    private MinecraftClient client;
    private Random random;
    private KonataModConfig config;
    private KonataRenderer renderer;

    public static KonataMod getInstance() {
        return instance;
    }

    @Override
    public void onInitializeClient() {
        instance = this;

        SoundHelper.init();

        this.client = MinecraftClient.getInstance();
        this.config = new KonataModConfig(new File(client.runDirectory, "konata.properties"));

        try {
            this.config.load();
            LOGGER.info("configuration loaded");
        } catch (RuntimeException | IOException ex) {
            LOGGER.error("failed to load properties", ex);
        }

        this.renderer = new KonataRenderer();
        this.random = new Random();
        this.renderer.init();

        LOGGER.info("finished initializing");
    }

    public KonataModConfig getConfig() {
        return config;
    }

    public Random getRandom() {
        return random;
    }

    public KonataRenderer getRenderer() {
        return renderer;
    }

    public MinecraftClient getClient() {
        return client;
    }
}
