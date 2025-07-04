package dev.azuuure.konata;

import dev.azuuure.konata.config.KonataModConfig;
import dev.azuuure.konata.renderer.KonataRenderer;
import dev.azuuure.konata.sound.SoundHelper;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class KonataMod implements ClientModInitializer {

	public static final String MOD_ID = "konata";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static KonataMod instance;

	private Random random;
	private KonataModConfig config;
	private KonataRenderer renderer;

    @Override
	public void onInitializeClient() {
		instance = this;

		SoundHelper.init();

		this.renderer = new KonataRenderer();
		this.random = new Random();
		this.config = new KonataModConfig();
		this.renderer.init();
	}

	public static KonataMod getInstance() {
		return instance;
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
}
