package me.swipez.fruwitchmod.entity.broom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RideableBroomEntityRenderer extends GeoEntityRenderer<RideableBroomEntity> {
    public RideableBroomEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RideableBroomEntityModel());
    }
}
