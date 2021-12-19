package me.swipez.fruwitchmod.items.renderer.ultimate;

import me.swipez.fruwitchmod.FruWitchMod;
import me.swipez.fruwitchmod.items.staffs.StaffItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UltimateStaffModel extends AnimatedGeoModel<StaffItem> {
    @Override
    public Identifier getModelLocation(StaffItem object) {
        return new Identifier(FruWitchMod.MOD_ID, "geo/ultimate_staff.geo.json");
    }

    @Override
    public Identifier getTextureLocation(StaffItem object) {
        return new Identifier(FruWitchMod.MOD_ID, "textures/ultimate_staff.png");
    }

    @Override
    public Identifier getAnimationFileLocation(StaffItem animatable) {
        return new Identifier(FruWitchMod.MOD_ID, "animations/ultimate_staff.animation.json");
    }
}
