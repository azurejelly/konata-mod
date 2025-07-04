package dev.azuuure.konata.mixin;

import dev.azuuure.konata.KonataMod;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {

    @Inject(method = "write", at = @At("TAIL"))
    public void onWrite(CallbackInfo ci) {
        try {
            KonataMod.getInstance().getConfig().save();
            KonataMod.LOGGER.info("configuration saved");
        } catch (RuntimeException | IOException ex) {
            KonataMod.LOGGER.error("failed to write properties", ex);
        }
    }
}
