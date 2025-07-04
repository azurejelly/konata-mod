package dev.azuuure.konata.renderer;

import dev.azuuure.konata.KonataMod;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

import java.util.Random;

public class KonataRenderer {

    private static final long UNLUCKY_UPDATE_DELAY = 1000L;

    private final KonataMod mod;
    private final Identifier texture;

    private boolean lucky;
    private long lastUpdateTime;
    private int width;
    private int height;
    private int x;
    private int y;

    public KonataRenderer() {
        this.mod = KonataMod.getInstance();
        this.texture = Identifier.of(KonataMod.MOD_ID, "textures/konata.png");
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public void init() {
        HudElementRegistry.addLast(texture, this::render);
    }

    public boolean shouldRender() {
        return mod.getConfig().isEnabled();
    }

    public void render(DrawContext context, RenderTickCounter tickCounter) {
        if (!shouldRender()) {
            return;
        }

        if (shouldUpdate()) {
            update();
        }

        if (!mod.getConfig().isRandomizedPosition() && !lucky) {
            return;
        }

        context.drawTexture(
                RenderPipelines.GUI_TEXTURED,
                texture,
                x, y,
                0, 0,
                width, height,
                width, height
        );
    }

    public boolean shouldUpdate() {
        long now = System.currentTimeMillis();

        // when the roll is unlucky and we're using the fullscreen chance
        // mode, check every 1 second instead of the delay. if the roll is
        // successful, then wait the original delay that the user specified.
        if (!mod.getConfig().isRandomizedPosition() && !lucky) {
            return now - lastUpdateTime > UNLUCKY_UPDATE_DELAY;
        } else {
            return now - lastUpdateTime > mod.getConfig().getDelay();
        }
    }

    public void update() {
        MinecraftClient client = MinecraftClient.getInstance();

        int maxWidth = client.getWindow().getScaledWidth();
        int maxHeight = client.getWindow().getScaledHeight();

        if (mod.getConfig().isRandomizedPosition()) {
            Random random = mod.getRandom();

            width = random.nextInt(maxWidth + 1);
            height = random.nextInt(maxHeight + 1);

            x = random.nextInt(maxWidth - width + 1);
            y = random.nextInt(maxHeight - height + 1);

            lucky = false;
        } else {
            x = 0;
            y = 0;
            width = maxWidth;
            height = maxHeight;
            lucky = roll();
        }

        lastUpdateTime = System.currentTimeMillis();
    }

    public void handleWindowResize() {
        if (!mod.getConfig().isRandomizedPosition()) {
            MinecraftClient client = MinecraftClient.getInstance();
            width = client.getWindow().getScaledWidth();
            height = client.getWindow().getScaledHeight();
        }
    }

    private boolean roll() {
        int chance = mod.getConfig().getChance();
        return chance > 0 && mod.getRandom().nextInt(chance) == 0;
    }
}
