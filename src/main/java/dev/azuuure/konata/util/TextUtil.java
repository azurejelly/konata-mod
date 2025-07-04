package dev.azuuure.konata.util;

import net.minecraft.text.Text;

public class TextUtil {

    public static Text genericOnOff(boolean bool) {
        return bool
                ? Text.translatable("options.on")
                : Text.translatable("options.off");
    }

    public static Text modeBool(boolean bool) {
        return bool
                ? Text.literal("Random Position")
                : Text.literal("Fullscreen Chance");
    }
}
