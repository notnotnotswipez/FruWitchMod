package me.swipez.fruwitchmod;

import me.swipez.fruwitchmod.entity.registry.EntityRegistry;
import me.swipez.fruwitchmod.items.ItemRegistry;
import net.fabricmc.api.ModInitializer;

public class FruWitchMod implements ModInitializer {

    public static String MOD_ID = "witchmod";

    @Override
    public void onInitialize() {
        EntityRegistry.registerAttributes();
        ItemRegistry.register();
    }
}
