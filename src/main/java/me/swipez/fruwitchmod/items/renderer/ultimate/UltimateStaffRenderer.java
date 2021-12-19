package me.swipez.fruwitchmod.items.renderer.ultimate;

import me.swipez.fruwitchmod.items.staffs.StaffItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class UltimateStaffRenderer extends GeoItemRenderer<StaffItem> {
    public UltimateStaffRenderer() {
        super(new UltimateStaffModel());
    }
}
