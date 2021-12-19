package me.swipez.fruwitchmod.entity.broom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RideableBroomEntity extends PassiveEntity implements IAnimatable {

    AnimationFactory animationFactory = new AnimationFactory(this);

    public RideableBroomEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }


    public void updatePassengerPosition(Entity passenger) {
        super.updatePassengerPosition(passenger);
        if (passenger instanceof MobEntity) {
            MobEntity mobEntity = (MobEntity) passenger;
            this.bodyYaw = mobEntity.bodyYaw;
        }
        passenger.setPosition(this.getX(), this.getY() + this.getMountedHeightOffset() + passenger.getHeightOffset(), this.getZ());
        if (passenger instanceof LivingEntity) {
            ((LivingEntity) passenger).bodyYaw = this.bodyYaw;
        }
    }

    @Override
    public void tickMovement() {
        LivingEntity livingEntity = getMainPassenger();
        if (livingEntity != null){
            setVelocity(getVelocity().add(0,(livingEntity.getPitch()/200)*-1,0));
        }
        super.tickMovement();
    }

    private LivingEntity getMainPassenger(){
        LivingEntity livingEntity = null;
        for (Entity entity : getPassengerList()){
            if (entity instanceof LivingEntity){
                livingEntity = (LivingEntity) entity;
            }
        }
        return livingEntity;
    }

    public void travel(Vec3d movementInput) {
        if (this.isAlive()) {
            if (this.hasPassengers()) {
                LivingEntity livingEntity = getMainPassenger();

                if (livingEntity != null){
                    setNoGravity(true);
                    this.setBodyYaw(livingEntity.getYaw());
                    this.prevYaw = this.getYaw();
                    this.setPitch(livingEntity.getPitch() * 0.5F);
                    this.setRotation(this.bodyYaw, this.getPitch());
                    this.bodyYaw = this.getYaw();
                    this.headYaw = this.bodyYaw;
                    float f = livingEntity.sidewaysSpeed * 0.5F;
                    float g = livingEntity.forwardSpeed;

                    this.flyingSpeed = this.getMovementSpeed() * 0.1F;
                    this.setMovementSpeed((float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    double finalYTravel = movementInput.y;
                    super.travel(new Vec3d((double) f, finalYTravel, (double) g));

                    this.updateLimbs(this, false);
                    this.tryCheckBlockCollision();
                }
            }
            else {
                setNoGravity(false);
                this.flyingSpeed = 0.02F;
                super.travel(movementInput);
            }
        }
    }

    public boolean canBeControlledByRider() {
        return this.getPrimaryPassenger() instanceof LivingEntity;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        putPlayerOnBack(player);
        return super.interactMob(player, hand);
    }

    protected void putPlayerOnBack(PlayerEntity player) {
        if (!this.world.isClient) {
            player.setYaw(this.getYaw());
            player.setPitch(this.getPitch());
            player.startRiding(this);
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
