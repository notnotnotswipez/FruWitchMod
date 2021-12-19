package me.swipez.fruwitchmod.entity.test;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class StaticRenderer extends GeoEntityRenderer<StaticEntity> {
    public StaticRenderer(EntityRendererFactory.Context ctx, String name) {
        super(ctx, new StaticModel(name));
    }
}
