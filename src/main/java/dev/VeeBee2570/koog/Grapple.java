package dev.VeeBee2570.koog;

import org.apache.commons.compress.archivers.zip.PKWareExtraHeader.HashAlgorithm;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Grapple extends Item {

    private static final float MaxWidth = 20;

    public Grapple(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity player, int timeLeft) {        
        if (!level.isClientSide()) {
            CompoundTag currentWireTag = itemStack.getOrCreateTag();

            removeExistingWire(level, currentWireTag);
        }
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 3600;
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);

        if (!level.isClientSide()) {
            Double blockDistance = Double.POSITIVE_INFINITY;
            Double entityDistance = Double.POSITIVE_INFINITY;

            Vec3 start = player.getEyePosition();
            Vec3 end = player.getLookAngle().multiply(Grapple.MaxWidth, Grapple.MaxWidth, Grapple.MaxWidth).add(start);
            HitResult blockCollision = level.clip(
                new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player)   
            );
            HitResult entityCollision = ProjectileUtil.getEntityHitResult(
                level, player, start, end, 
                player.getBoundingBox().expandTowards(player.getLookAngle().scale(Grapple.MaxWidth + 2)), entity -> true
            );
            
            if (blockCollision != null && blockCollision.getType() == HitResult.Type.BLOCK) {
                blockDistance = blockCollision.distanceTo(player);
            } 
            if (entityCollision != null && entityCollision.getType() == HitResult.Type.ENTITY) {
                entityDistance = entityCollision.distanceTo(player);
            }

            if (blockDistance != Double.POSITIVE_INFINITY || entityDistance != Double.POSITIVE_INFINITY) {
                GrappleWire grappleWire;
                CompoundTag currentWireTag = itemStack.getOrCreateTag();

                if (blockDistance < entityDistance) {
                    grappleWire = new GrappleWire(level, player, blockCollision.getLocation());
                } else {
                    grappleWire = new GrappleWire(level, player, ((EntityHitResult)entityCollision).getEntity());
                }

                removeExistingWire(level, currentWireTag);

                level.addFreshEntity(grappleWire);
                currentWireTag.putInt("wire_id", grappleWire.getId());

                player.startUsingItem(interactionHand);
            }

        }
        
        return InteractionResultHolder.consume(itemStack);
    }

    private void removeExistingWire(Level level,  CompoundTag currentWireTag) {
        Entity existing = level.getEntity(currentWireTag.getInt("wire_id"));
        if (existing != null && existing instanceof GrappleWire) {
            ((GrappleWire)existing).delete();
        }

    }


}
