package dev.VeeBee2570.koog;

import java.util.List;
import java.util.logging.Logger;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MachineGun extends Entity implements Fireable {

    private static final EntityDataAccessor<Integer> bulletCount = SynchedEntityData.defineId(
        MachineGun.class, EntityDataSerializers.INT
    );

    private int fractionalBullet = 0;
    private final int ticksPerBullet = 5;
    private int fireCooldown = 0;
    private final int fireCooldownTicks = 10;
    private ProjectileFactory bulletFactory = new BulletFactory();

    public MachineGun(EntityType<MachineGun> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!this.level().isClientSide()) {
            player.startRiding(this);
            ExampleMod.LOGGER.info("Interaction detected");
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            int bulletCount = this.entityData.get(MachineGun.bulletCount);
            List<Entity> passengers = this.getPassengers();
        
            if (passengers.size() == 1) {
                Entity passenger = passengers.get(0);

                // ExampleMod.LOGGER.info("passenger " + passenger.getXRot());

                this.setXRot(passenger.getXRot());
                this.setYRot(passenger.getYRot());
            }

            if (bulletCount < 10) {
                if (fireCooldown == 0) {
                    ExampleMod.LOGGER.info("Reloading bullets " + fractionalBullet + " " + bulletCount);
                    fractionalBullet++;
                    if (fractionalBullet == ticksPerBullet) {
                        this.entityData.set(MachineGun.bulletCount, bulletCount + 1);
                        fractionalBullet = 0;
                    }
                } else {
                    fireCooldown--;
                }
            }
        }


    }

    @Override
    public boolean isPickable() {
        return true;
    }


    @Override
    protected void defineSynchedData() {
        this.entityData.define(bulletCount, 10);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {
    }

    @Override
    public void Fire(Level level, Player player) {
        int bulletCount = this.entityData.get(MachineGun.bulletCount);

        if (bulletCount > 0) {
            Projectile bullet = bulletFactory.get(level, player, this.getLookAngle(), new Vec3(this.getX(), this.getY(), this.getZ()));
            level.addFreshEntity(bullet);
            fireCooldown = fireCooldownTicks;
            this.entityData.set(MachineGun.bulletCount, bulletCount - 1);
        }

    }

    @Override
    public ResourceLocation getBulletIcon() {
        return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/sprites/hud/food_full.png");
    }

    @Override
    public int getBulletCount(Player player) {
        int bulletCount = this.entityData.get(MachineGun.bulletCount);

        ExampleMod.LOGGER.info("bullet count " + bulletCount);
        return bulletCount;
    }
    
}
