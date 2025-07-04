package dev.azuuure.konata.sound;

import dev.azuuure.konata.KonataMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundHelper {

    public static SoundEvent JUMPSCARE = registerSound("jumpscare");

    @SuppressWarnings("SameParameterValue")
    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(KonataMod.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void init() {
        KonataMod.LOGGER.info("registering sounds");
    }
}
