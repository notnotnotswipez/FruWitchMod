package me.swipez.fruwitchmod.entity.registry;

import me.swipez.fruwitchmod.FruWitchMod;
import me.swipez.fruwitchmod.entity.broom.RideableBroomEntity;
import me.swipez.fruwitchmod.entity.broom.RideableBroomEntityRenderer;
import me.swipez.fruwitchmod.entity.test.StaticRenderer;
import me.swipez.fruwitchmod.entity.test.WitchBroomEntity;
import me.swipez.fruwitchmod.entity.test.WitchStaffEntity;
import me.swipez.fruwitchmod.entity.witchboss.WitchBossEntity;
import me.swipez.fruwitchmod.entity.witchboss.WitchBossRenderer;
import me.swipez.fruwitchmod.items.ItemRegistry;
import me.swipez.fruwitchmod.items.renderer.healing.HealStaffRenderer;
import me.swipez.fruwitchmod.items.renderer.jump.JumpStaffRenderer;
import me.swipez.fruwitchmod.items.renderer.speed.SpeedStaffRenderer;
import me.swipez.fruwitchmod.items.renderer.ultimate.UltimateStaffRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class EntityRegistry {

    public static final EntityType<RideableBroomEntity> RIDEABLE_BROOM_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(FruWitchMod.MOD_ID, "rideable_broom"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RideableBroomEntity::new).dimensions(EntityDimensions.fixed(2f, 1f)).build()
    );

    public static final EntityType<WitchBossEntity> WITCH_BOSS = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(FruWitchMod.MOD_ID, "witch_boss"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WitchBossEntity::new).dimensions(EntityDimensions.fixed(2f, 4f)).build()
    );

    public static final EntityType<WitchBroomEntity> STATIC_WITCH_BROOM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(FruWitchMod.MOD_ID, "static_witch_broom"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WitchBroomEntity::new).dimensions(EntityDimensions.fixed(1f, 1f)).build()
    );

    public static final EntityType<WitchStaffEntity> STATIC_WITCH_STAFF = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(FruWitchMod.MOD_ID, "static_witch_staff"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WitchStaffEntity::new).dimensions(EntityDimensions.fixed(1f, 1f)).build()
    );

    public static void registerAttributes(){
        FabricDefaultAttributeRegistry.register(RIDEABLE_BROOM_ENTITY, RideableBroomEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1)
                        .add(EntityAttributes.GENERIC_FLYING_SPEED, 1)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
        );

        FabricDefaultAttributeRegistry.register(WITCH_BOSS, WitchBossEntity.createHostileAttributes().add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 300)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55)
        );

        FabricDefaultAttributeRegistry.register(STATIC_WITCH_BROOM, WitchBossEntity.createHostileAttributes().add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55)
        );

        FabricDefaultAttributeRegistry.register(STATIC_WITCH_STAFF, WitchBossEntity.createHostileAttributes().add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55)
        );
    }

    public static void registerRenderers(){
        EntityRendererRegistry.register(RIDEABLE_BROOM_ENTITY, RideableBroomEntityRenderer::new);
        EntityRendererRegistry.register(WITCH_BOSS, WitchBossRenderer::new);

        EntityRendererRegistry.register(STATIC_WITCH_BROOM, ctx -> new StaticRenderer(ctx, "witchstatic"));
        EntityRendererRegistry.register(STATIC_WITCH_STAFF, ctx -> new StaticRenderer(ctx, "witchstaffstatic"));

        GeoItemRenderer.registerItemRenderer(ItemRegistry.HEALING_STAFF, new HealStaffRenderer());
        GeoItemRenderer.registerItemRenderer(ItemRegistry.JUMP_STAFF, new JumpStaffRenderer());
        GeoItemRenderer.registerItemRenderer(ItemRegistry.SPEED_STAFF, new SpeedStaffRenderer());
        GeoItemRenderer.registerItemRenderer(ItemRegistry.ULTIMATE_STAFF, new UltimateStaffRenderer());
    }
}
