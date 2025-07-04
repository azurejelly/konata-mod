package dev.azuuure.konata.screen.widget;

import dev.azuuure.konata.KonataMod;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class ChanceSliderWidget extends SliderWidget {

    private static final int MIN_CHANCE = 1;
    private static final int MAX_CHANCE = 10000;
    private int chance;

    public ChanceSliderWidget(int chance) {
        super(
                0, 0,
                150, 20,
                Text.literal("Chance: 1 in " + chance),
                (chance - MIN_CHANCE) / (double) (MAX_CHANCE - MIN_CHANCE)
        );

        this.chance = chance;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Text.literal("Chance: 1 in " + chance));
    }

    @Override
    protected void applyValue() {
        if (!this.active) {
            return;
        }

        this.chance = MIN_CHANCE + (int) (this.value * (MAX_CHANCE - MIN_CHANCE));

        KonataMod.getInstance().getConfig().setChance(chance);
        KonataMod.getInstance().getRenderer().update();
    }
}
