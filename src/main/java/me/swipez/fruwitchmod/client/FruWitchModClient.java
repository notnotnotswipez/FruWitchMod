package me.swipez.fruwitchmod.client;

import me.swipez.fruwitchmod.entity.registry.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class FruWitchModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRegistry.registerRenderers();
    }
}
