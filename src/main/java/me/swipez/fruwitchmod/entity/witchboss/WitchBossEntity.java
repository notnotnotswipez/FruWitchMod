package me.swipez.fruwitchmod.entity.witchboss;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class WitchBossEntity extends HostileEntity implements IAnimatable{

    AnimationFactory animationFactory = new AnimationFactory(this);
    Entity attackTarget = null;
    private final ServerBossBar bossBar;

    public WitchBossEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.bossBar = (ServerBossBar)(new ServerBossBar(this.getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.PROGRESS)).setDarkenSky(true);
        initDataTrackers();
    }

    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public void initGoals(){
        this.targetSelector.add(3, new WanderAroundFarGoal(this,getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
        this.targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(1, new FollowTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(2, new MeleeAttackGoal(this, getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED), false));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
    }

    // Data trackers
    public static final TrackedData<Integer> STATE = DataTracker.registerData(WitchBossEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    public static final TrackedData<Integer> TICKS_UNTIL_RESET = DataTracker.registerData(WitchBossEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    public static final TrackedData<Integer> TICKS_UNTIL_REMOVAL = DataTracker.registerData(WitchBossEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    public static final TrackedData<Integer> TICKS_UNTIL_LAUNCH = DataTracker.registerData(WitchBossEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    public static final TrackedData<Integer> TICKS_SINCE_LAST_SMASH = DataTracker.registerData(WitchBossEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    public static final TrackedData<Integer> DAMAGE_DELAY = DataTracker.registerData(WitchBossEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    private void initDataTrackers(){
        dataTracker.startTracking(STATE, 0);
        dataTracker.startTracking(TICKS_UNTIL_RESET, 0);
        dataTracker.startTracking(TICKS_UNTIL_REMOVAL, 0);
        dataTracker.startTracking(DAMAGE_DELAY, 0);
        dataTracker.startTracking(TICKS_SINCE_LAST_SMASH, 0);
        dataTracker.startTracking(TICKS_UNTIL_LAUNCH, 0);
    }

    // Ticking
    @Override
    public void tick(){
        super.tick();
        if (dataTracker.get(TICKS_UNTIL_RESET) > 0) {
            dataTracker.set(TICKS_UNTIL_RESET, dataTracker.get(TICKS_UNTIL_RESET)-1);
            if (dataTracker.get(TICKS_UNTIL_RESET) == 0) {
                if (dataTracker.get(STATE) == 5){
                    remove(RemovalReason.KILLED);
                }
                else {
                    dataTracker.set(STATE, 0);
                    if (isAiDisabled()) {
                        setAiDisabled(false);
                    }
                }
            }
        }
        if (dataTracker.get(TICKS_UNTIL_LAUNCH) > 0){
            dataTracker.set(TICKS_UNTIL_LAUNCH, dataTracker.get(TICKS_UNTIL_LAUNCH)-1);
            if (dataTracker.get(TICKS_UNTIL_LAUNCH) == 0) {
                getClosestPlayer().setVelocity(getClosestPlayer().getVelocity().add(0,1.5,0));
                getClosestPlayer().playSound(SoundEvents.ENTITY_IRON_GOLEM_DAMAGE, 1, 1);
            }
        }
        if (dataTracker.get(TICKS_UNTIL_REMOVAL) > 0) {
            dataTracker.set(TICKS_UNTIL_REMOVAL, dataTracker.get(TICKS_UNTIL_REMOVAL)-1);
            if (dataTracker.get(TICKS_UNTIL_REMOVAL) == 0) {
                remove(Entity.RemovalReason.DISCARDED);
            }
        }
        if (dataTracker.get(TICKS_SINCE_LAST_SMASH) > 0) {
            dataTracker.set(TICKS_SINCE_LAST_SMASH, dataTracker.get(TICKS_SINCE_LAST_SMASH)-1);
        }
        if (dataTracker.get(DAMAGE_DELAY) > 0){
            dataTracker.set(DAMAGE_DELAY, dataTracker.get(DAMAGE_DELAY)-1);
            if (dataTracker.get(DAMAGE_DELAY) == 0){
                if (attackTarget != null){
                    boolean bl = attackTarget.damage(DamageSource.mob(this), (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
                    float g = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
                    if (bl) {
                        if (g> 0.0F && attackTarget instanceof LivingEntity) {
                            ((LivingEntity)attackTarget).takeKnockback(g * 0.5F, (double) MathHelper.sin(bodyYaw * 0.017453292F), (double)(-MathHelper.cos(bodyYaw * 0.017453292F)));
                            this.setVelocity(this.getVelocity().multiply(0.6D, 1.0D, 0.6D));
                        }

                        if (attackTarget instanceof PlayerEntity) {
                            PlayerEntity playerEntity = (PlayerEntity)attackTarget;
                            this.disablePlayerShield(playerEntity, this.getMainHandStack(), playerEntity.isUsingItem() ? playerEntity.getActiveItem() : ItemStack.EMPTY);
                        }
                    }
                }
                attackTarget = null;
            }
        }
        if (dataTracker.get(STATE) == 0){
            if (canSmashAttack()){
                triggerSmash();
            }
        }
    }

    public void triggerCoolerDeath(){
        setStateAndTicks(5, 60, true);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (dataTracker.get(STATE) == 0){
            lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, Vec3d.ofCenter(getClosestPlayer().getBlockPos()));
            float f = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            float g = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
            if (target instanceof LivingEntity) {
                f += EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity)target).getGroup());
                g += (float)EnchantmentHelper.getKnockback(this);
            }

            int i = EnchantmentHelper.getFireAspect(this);
            if (i > 0) {
                target.setOnFireFor(i * 4);
            }

            int chosenState = random.nextInt(2)+1;
            int damageDelayTicks = 0;
            int tickLength = 0;
            switch (chosenState){
                case 1:
                    damageDelayTicks = 11;
                    tickLength = 23;
                    break;
                case 2:
                    damageDelayTicks = 10;
                    tickLength = 23;
                    break;
            }
            dataTracker.set(DAMAGE_DELAY, damageDelayTicks);
            attackTarget = target;
            boolean bl = target.damage(DamageSource.mob(this), 0);
            if (bl) {
                if (g > 0.0F && target instanceof LivingEntity) {
                    ((LivingEntity)target).takeKnockback(g * 0.5F, (double) MathHelper.sin(bodyYaw * 0.017453292F), (double)(-MathHelper.cos(bodyYaw * 0.017453292F)));
                    this.setVelocity(this.getVelocity().multiply(0.6D, 1.0D, 0.6D));
                }

                if (target instanceof PlayerEntity) {
                    PlayerEntity playerEntity = (PlayerEntity)target;
                    this.disablePlayerShield(playerEntity, this.getMainHandStack(), playerEntity.isUsingItem() ? playerEntity.getActiveItem() : ItemStack.EMPTY);
                }
            }
            setStateAndTicks(chosenState, tickLength, false);
            return bl;
        }
        return false;
    }

    // Predicate
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (dataTracker.get(STATE) == 0){
            if (event.isMoving()){
                event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
            }
            else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
            }
        }
        else if (dataTracker.get(STATE) == 1){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack1", true));
        }
        else if (dataTracker.get(STATE) == 2){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack2", true));
        }
        else if (dataTracker.get(STATE) == 3){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack3", true));
        }
        else if (dataTracker.get(STATE) == 5){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("death", true));
        }
        return PlayState.CONTINUE;
    }

    private boolean canSmashAttack(){
        if (dataTracker.get(TICKS_SINCE_LAST_SMASH) == 0) {
            if (getTarget() != null) {
                return getTarget().getBlockPos().getSquaredDistance(getBlockPos()) > 40;
            }
        }
        return false;
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }

    private void triggerSmash(){
        dataTracker.set(TICKS_SINCE_LAST_SMASH, 200);
        setStateAndTicks(3, 39, true);
        dataTracker.set(TICKS_UNTIL_LAUNCH, 19);
    }

    public PlayerEntity getClosestPlayer(){
        double closestDistance = 0;
        PlayerEntity closestPlayer = null;
        for (PlayerEntity player : getEntityWorld().getPlayers()){
            double currentDistance = player.getBlockPos().getSquaredDistance(getBlockPos());
            if (closestDistance == 0 || currentDistance < closestDistance){
                closestDistance = currentDistance;
                closestPlayer = player;
            }
        }
        return closestPlayer;
    }

    private void disablePlayerShield(PlayerEntity player, ItemStack mobStack, ItemStack playerStack) {
        if (!mobStack.isEmpty() && !playerStack.isEmpty() && mobStack.getItem() instanceof AxeItem && playerStack.getItem() == Items.SHIELD) {
            float f = 0.25F + (float)EnchantmentHelper.getEfficiency(this) * 0.05F;
            if (this.random.nextFloat() < f) {
                player.getItemCooldownManager().set(Items.SHIELD, 100);
                this.world.sendEntityStatus(player, (byte)30);
            }
        }
    }

    public void setStateAndTicks(int state, int ticks, boolean disableUntilFinished){
        dataTracker.set(STATE, state);
        dataTracker.set(TICKS_UNTIL_RESET, ticks);
        setAiDisabled(disableUntilFinished);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }
}
