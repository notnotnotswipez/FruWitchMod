package me.swipez.fruwitchmod.entity.witchboss;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WitchBossModel extends AnimatedGeoModel<WitchBossEntity> {
    @Override
    public Identifier getModelLocation(WitchBossEntity object) {
        return new Identifier("witchmod", "geo/witch.geo.json");
    }

    @Override
    public Identifier getTextureLocation(WitchBossEntity object) {
        return new Identifier("witchmod", "textures/witch.png");
    }

    @Override
    public Identifier getAnimationFileLocation(WitchBossEntity animatable) {
        return new Identifier("witchmod", "animations/witch.animation.json");
    }
}
