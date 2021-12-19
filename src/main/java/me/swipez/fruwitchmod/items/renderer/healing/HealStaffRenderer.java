package me.swipez.fruwitchmod.items.renderer.healing;

import me.swipez.fruwitchmod.items.staffs.StaffItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class HealStaffRenderer extends GeoItemRenderer<StaffItem> {

    public HealStaffRenderer() {
        super(new HealStaffModel());
    }
}
