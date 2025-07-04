package dev.azuuure.konata.util;

import net.minecraft.text.Text;

public class TextUtil {

    public static Text genericOnOff(boolean bool) {
        return bool
                ? Text.translatable("options.on")
                : Text.translatable("options.off");
    }

    public static Text modeFromBool(boolean bool) {
        return bool
                ? Text.translatable("konata.mode.random")
                : Text.translatable("konata.mode.jumpscare");
    }
}
