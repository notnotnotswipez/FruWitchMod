package me.swipez.fruwitchmod.items.renderer.speed;

import me.swipez.fruwitchmod.items.staffs.StaffItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SpeedStaffRenderer extends GeoItemRenderer<StaffItem> {

    public SpeedStaffRenderer() {
        super(new SpeedStaffModel());
    }
}
