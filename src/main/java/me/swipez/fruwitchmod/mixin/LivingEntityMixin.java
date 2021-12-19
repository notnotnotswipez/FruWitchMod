package me.swipez.fruwitchmod.mixin;

import me.swipez.fruwitchmod.entity.broom.RideableBroomEntity;
import me.swipez.fruwitchmod.entity.registry.EntityRegistry;
import me.swipez.fruwitchmod.entity.witchboss.WitchBossEntity;
import me.swipez.fruwitchmod.items.ItemRegistry;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {


    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity.getType().equals(EntityRegistry.RIDEABLE_BROOM_ENTITY)){
            cir.cancel();
        }
        if (livingEntity.getVehicle() != null){
            if (livingEntity.getVehicle().getType().equals(EntityRegistry.RIDEABLE_BROOM_ENTITY)){
                cir.cancel();
            }
        }
        if (livingEntity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) livingEntity;
            if (player.getMainHandStack().getItem().equals(ItemRegistry.ULTIMATE_STAFF)){
                if (source.equals(DamageSource.LIGHTNING_BOLT) || source.isFire()){
                    cir.cancel();
                }
            }
        }
    }

    @Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
    public void onDeath(DamageSource source, CallbackInfo ci){
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity.getType().equals(EntityRegistry.WITCH_BOSS)){

            WitchBossEntity witchBossEntity = (WitchBossEntity) livingEntity;

            witchBossEntity.triggerCoolerDeath();

            RideableBroomEntity rideableBroomEntity = EntityRegistry.RIDEABLE_BROOM_ENTITY.create(witchBossEntity.world);

            witchBossEntity.getEntityWorld().spawnEntity(rideableBroomEntity);
            rideableBroomEntity.teleport(witchBossEntity.getX(), witchBossEntity.getY(), witchBossEntity.getZ());
            ci.cancel();
        }
    }
}
