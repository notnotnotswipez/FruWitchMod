package me.swipez.fruwitchmod.entity.test;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class StaticModel extends AnimatedGeoModel<StaticEntity> {

    final String name;

    public StaticModel(String name) {
        this.name = name;
    }

    @Override
    public Identifier getModelLocation(StaticEntity object) {
        return new Identifier("witchmod", "geo/"+name+".geo.json");
    }

    @Override
    public Identifier getTextureLocation(StaticEntity object) {
        return new Identifier("witchmod", "textures/"+name+".png");
    }

    @Override
    public Identifier getAnimationFileLocation(StaticEntity animatable) {
        return new Identifier("witchmod", "animations/"+name+".animation.json");
    }
}
