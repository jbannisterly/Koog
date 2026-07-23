package dev.VeeBee2570.koog;

import java.util.List;

import net.minecraft.nbt.CompoundTag;
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

    ProjectileFactory bulletFactory = new BulletFactory();

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

        List<Entity> passengers = this.getPassengers();
        
        if (passengers.size() == 1) {
            Entity passenger = passengers.get(0);

            ExampleMod.LOGGER.info("passenger " + passenger.getXRot());

            this.setXRot(passenger.getXRot());
            this.setYRot(passenger.getYRot());
        }

    }

    @Override
    public boolean isPickable() {
        return true;
    }


    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {
    }

    @Override
    public void Fire(Level level, Player player) {
        Projectile bullet = bulletFactory.get(level, player, this.getLookAngle(), new Vec3(this.getX(), this.getY(), this.getZ()));
        level.addFreshEntity(bullet);
    }

    @Override
    public ResourceLocation getBulletIcon() {
        return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/sprites/hud/food_full.png");
    }

    @Override
    public int getBulletCount() {
        return 5;
    }
    
}
