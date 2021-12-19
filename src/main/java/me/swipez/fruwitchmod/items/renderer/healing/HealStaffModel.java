package me.swipez.fruwitchmod.items.renderer.healing;

import me.swipez.fruwitchmod.FruWitchMod;
import me.swipez.fruwitchmod.items.staffs.StaffItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HealStaffModel extends AnimatedGeoModel<StaffItem> {
    @Override
    public Identifier getModelLocation(StaffItem object) {
        return new Identifier(FruWitchMod.MOD_ID, "geo/heal_staff.geo.json");
    }

    @Override
    public Identifier getTextureLocation(StaffItem object) {
        return new Identifier(FruWitchMod.MOD_ID, "textures/heal_staff.png");
    }

    @Override
    public Identifier getAnimationFileLocation(StaffItem animatable) {
        return new Identifier(FruWitchMod.MOD_ID, "animations/heal_staff.animation.json");
    }
}
