package dev.azuuure.konata.screen.widget;

import dev.azuuure.konata.KonataMod;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class DelaySliderWidget extends SliderWidget {

    private float delaySeconds;

    public DelaySliderWidget(float delayMillis) {
        super(
                0, 0, 150, 20,
                Text.translatable(
                        "konata.settings.delay",
                        String.format("%.1f", delayMillis / 1000f)
                ),
                delayMillis / 1000f / 30f
        );

        this.delaySeconds = delayMillis / 1000f;
        this.setTooltip(Tooltip.of(Text.translatable("konata.settings.delay.tooltip")));
    }

    @Override
    protected void updateMessage() {
        this.setMessage(
                Text.translatable("konata.settings.delay",
                        String.format("%.1f", delaySeconds)
                )
        );
    }

    @Override
    protected void applyValue() {
        if (!this.active) {
            return;
        }

        this.delaySeconds = (float) this.value * 30.0f;
        KonataMod.getInstance().getConfig().setDelay(delaySeconds * 1000f);
    }
}
