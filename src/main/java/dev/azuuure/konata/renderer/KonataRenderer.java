package dev.azuuure.konata.renderer;

import dev.azuuure.konata.KonataMod;
import dev.azuuure.konata.sound.SoundHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.util.Identifier;

public class KonataRenderer {

    private static final long UNLUCKY_UPDATE_DELAY = 1000L;
    private static final Identifier TEXTURE = Identifier.of(KonataMod.MOD_ID, "textures/konata.png");

    private final MinecraftClient client;
    private final KonataMod mod;

    private boolean lucky;
    private long lastUpdateTime;
    private int width;
    private int height;
    private int x;
    private int y;

    public KonataRenderer() {
        this.client = MinecraftClient.getInstance();
        this.mod = KonataMod.getInstance();
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public void init() {
        HudElementRegistry.addLast(TEXTURE, this::render);
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

            if (lucky) {
                client.getSoundManager().play(
                        PositionedSoundInstance.master(SoundHelper.VINE, 1f)
                );
            }
        }

        if (!mod.getConfig().isRandomizedPosition() && !lucky) {
            return;
        }

        context.drawTexture(
                RenderPipelines.GUI_TEXTURED,
                TEXTURE,
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
        int maxWidth = client.getWindow().getScaledWidth();
        int maxHeight = client.getWindow().getScaledHeight();

        if (mod.getConfig().isRandomizedPosition()) {
            width = mod.getRandom().nextInt(maxWidth + 1);
            height = mod.getRandom().nextInt(maxHeight + 1);

            x = mod.getRandom().nextInt(maxWidth - width + 1);
            y = mod.getRandom().nextInt(maxHeight - height + 1);

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
            width = client.getWindow().getScaledWidth();
            height = client.getWindow().getScaledHeight();
        }
    }

    private boolean roll() {
        int chance = mod.getConfig().getChance();
        return chance > 0 && mod.getRandom().nextInt(chance) == 0;
    }
}
