package me.swipez.fruwitchmod.items.renderer.speed;

import me.swipez.fruwitchmod.FruWitchMod;
import me.swipez.fruwitchmod.items.staffs.StaffItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpeedStaffModel extends AnimatedGeoModel<StaffItem> {
    @Override
    public Identifier getModelLocation(StaffItem object) {
        return new Identifier(FruWitchMod.MOD_ID, "geo/speed_staff.geo.json");
    }

    @Override
    public Identifier getTextureLocation(StaffItem object) {
        return new Identifier(FruWitchMod.MOD_ID, "textures/speed_staff.png");
    }

    @Override
    public Identifier getAnimationFileLocation(StaffItem animatable) {
        return new Identifier(FruWitchMod.MOD_ID, "animations/speed_staff.animation.json");
    }
}
