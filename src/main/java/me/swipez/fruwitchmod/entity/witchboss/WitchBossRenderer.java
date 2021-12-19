package me.swipez.fruwitchmod.entity.witchboss;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WitchBossRenderer extends GeoEntityRenderer<WitchBossEntity> {
    public WitchBossRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WitchBossModel());
    }
}
