package dev.azuuure.konata.screen;

import dev.azuuure.konata.KonataMod;
import dev.azuuure.konata.screen.widget.ChanceSliderWidget;
import dev.azuuure.konata.screen.widget.DelaySliderWidget;
import dev.azuuure.konata.util.TextUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

@Environment(EnvType.CLIENT)
public class KonataScreen extends GameOptionsScreen {

    private final Screen parent;

    protected KonataScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, Text.literal("Epic Konata Mod"));

        this.parent = parent;
    }

    public static KonataScreen create(Screen parent) {
        return new KonataScreen(parent);
    }

    @Override
    protected void addOptions() {
        if (body == null) {
            return;
        }

        KonataMod mod = KonataMod.getInstance();
        DelaySliderWidget delaySlider = new DelaySliderWidget(mod.getConfig().getDelay());
        ChanceSliderWidget chanceSlider = new ChanceSliderWidget(mod.getConfig().getChance());

        chanceSlider.active = !mod.getConfig().isRandomizedPosition();

        List<ClickableWidget> widgets = List.of(
                // Button to toggle the mod
                CyclingButtonWidget.builder(TextUtil::genericOnOff)
                        .values(true, false)
                        .initially(mod.getConfig().isEnabled())
                        .build(
                                Text.literal("Render Konata"),
                                (b, v) -> {
                                    mod.getConfig().setEnabled(v);
                                }
                        ),

                // Button to adjust the time in screen for each Konata
                delaySlider,

                // Button to adjust the mode of the mod
                CyclingButtonWidget.builder(TextUtil::modeBool)
                        .values(true, false)
                        .initially(mod.getConfig().isRandomizedPosition())
                        .build(
                                Text.literal("Mode"),
                                (b, v) -> {
                                    mod.getRenderer().update();
                                    mod.getConfig().setRandomizedPosition(v);

                                    chanceSlider.active = !v;
                                }
                        ),

                // Slider to adjust the chance of getting a fullscreen Konata
                chanceSlider
        );

        body.addAll(widgets);
    }

    @Override
    public void close() {
        if (client == null || parent == null) {
            return;
        }

        client.setScreen(parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);

        // something is wrong
        if (client == null) {
            return;
        }

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        // dynamically update the image size so that it's not
        // too big on smaller windows
        int size = (int) (Math.min(width, height) * 0.35f);

        context.drawTexture(
                RenderPipelines.GUI_TEXTURED,
                Identifier.of(KonataMod.MOD_ID, "textures/konata.png"),
                (width - size) / 2, ((height - size) / 2) + 30,
                0, 0,
                size, size,
                size, size
        );
    }
}
