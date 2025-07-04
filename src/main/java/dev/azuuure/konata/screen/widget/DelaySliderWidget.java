package dev.azuuure.konata.screen.widget;

import dev.azuuure.konata.KonataMod;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class DelaySliderWidget extends SliderWidget {

    private float delaySeconds;

    public DelaySliderWidget(float delayMillis) {
        // Convert milliseconds to seconds for display and slider value
        super(0, 0, 150, 20, Text.literal("Delay: %.2f s".formatted(delayMillis / 1000f)), delayMillis / 1000f / 30f);
        this.delaySeconds = delayMillis / 1000f;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Text.literal("Delay: %.2f s".formatted(delaySeconds)));
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
