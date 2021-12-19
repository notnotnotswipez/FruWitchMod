package me.swipez.fruwitchmod.entity.test;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.world.World;

public class WitchBroomEntity extends StaticEntity{
    public WitchBroomEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }
}
