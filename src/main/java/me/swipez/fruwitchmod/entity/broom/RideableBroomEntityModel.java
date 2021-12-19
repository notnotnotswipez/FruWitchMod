package me.swipez.fruwitchmod.entity.broom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RideableBroomEntityModel extends AnimatedGeoModel<RideableBroomEntity> {
    @Override
    public Identifier getModelLocation(RideableBroomEntity object) {
        return new Identifier("witchmod", "geo/broom.geo.json");
    }

    @Override
    public Identifier getTextureLocation(RideableBroomEntity object) {
        return new Identifier("witchmod", "textures/broom.png");
    }

    @Override
    public Identifier getAnimationFileLocation(RideableBroomEntity animatable) {
        return new Identifier("witchmod", "animations/broom.animation.json");
    }
}
