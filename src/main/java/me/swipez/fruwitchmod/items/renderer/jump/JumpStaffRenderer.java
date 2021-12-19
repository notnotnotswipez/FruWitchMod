package me.swipez.fruwitchmod.items.renderer.jump;

import me.swipez.fruwitchmod.items.staffs.StaffItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class JumpStaffRenderer extends GeoItemRenderer<StaffItem> {

    public JumpStaffRenderer() {
        super(new JumpStaffModel());
    }
}
