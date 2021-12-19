package me.swipez.fruwitchmod.items.staffs;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class UltimateStaffItem extends StaffItem{
    public UltimateStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.getServer() != null){
            Vec3d pos = null;
            boolean foundBlock = false;
            for (int i = 0; i < 60; i++){
                pos = rayCast(user.getPos(), new Vec3d(0.0, 1.0, i), new Vec2f(user.getPitch(), user.getYaw()));
                if (!world.getBlockState(new BlockPos(pos)).getBlock().equals(Blocks.AIR)){
                    foundBlock = true;
                    break;
                }
            }
            if (foundBlock){
                LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
                lightningEntity.teleport(pos.x, pos.y, pos.z);
                world.spawnEntity(lightningEntity);
                user.world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.HOSTILE,1, 1);
            }
        }
        return super.use(world, user, hand);
    }

    public Vec3d rayCast(final Vec3d src, final Vec3d rel, final Vec2f rot) {
        final float f1 = MathHelper.cos((rot.y + 90.0F) * 0.017453292F);
        final float f2 = MathHelper.sin((rot.y + 90.0F) * 0.017453292F);
        final float f3 = MathHelper.cos(-rot.x * 0.017453292F);
        final float f4 = MathHelper.sin(-rot.x * 0.017453292F);
        final float f5 = MathHelper.cos((-rot.x + 90.0F) * 0.017453292F);
        final float f6 = MathHelper.sin((-rot.x + 90.0F) * 0.017453292F);
        final Vec3d dx = new Vec3d(f1 * f3, f4, f2 * f3);
        final Vec3d dy = new Vec3d(f1 * f5, f6, f2 * f5);
        final Vec3d dz = dx.crossProduct(dy).multiply(-1.0);
        final double ox = dx.x * rel.z + dy.x * rel.y + dz.x * rel.x;
        final double oy = dx.y * rel.z + dy.y * rel.y + dz.y * rel.x;
        final double oz = dx.z * rel.z + dy.z * rel.y + dz.z * rel.x;
        return new Vec3d(src.x + ox, src.y + oy, src.z + oz);
    }
}
