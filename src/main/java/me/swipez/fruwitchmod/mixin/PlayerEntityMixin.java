package me.swipez.fruwitchmod.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo ci){
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        ItemStack mainHandStack = playerEntity.getInventory().getMainHandStack();
        if (mainHandStack != null){
            if (mainHandStack.getItem().toString().toLowerCase().contains("heal_staff")){
                playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20, 0, true, false, false));
            }
            if (mainHandStack.getItem().toString().toLowerCase().contains("speed_staff")){
                playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, 0, true, false, false));
            }
            if (mainHandStack.getItem().toString().toLowerCase().contains("jump_staff")){
                playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, 1, true, false, false));
            }
            if (mainHandStack.getItem().toString().toLowerCase().contains("ultimate_staff")){
                playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, 1, true, false, false));
                playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, 0, true, false, false));
                playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20, 0, true, false, false));
            }
        }
    }
}
