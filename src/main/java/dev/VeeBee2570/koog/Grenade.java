package dev.VeeBee2570.koog;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Grenade extends Projectile {
    protected LivingEntity owner;
    private final float speed = 2;


    public Grenade(EntityType<? extends Grenade> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public Grenade(Level level,  @Nullable LivingEntity owner, Vec3 deltaMovement, Vec3 pos) {
        super(ExampleMod.GRENADE_TYPE.get(), level);
        this.owner = owner;
        this.setDeltaMovement(deltaMovement.multiply(speed, speed, speed));
        this.setPos(pos);
    }

    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            HitResult hit = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hit.getType() != HitResult.Type.MISS) {
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0f, Level.ExplosionInteraction.TNT);
                this.discard();
            }
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().add(0, -0.04, 0));
        }

    }

    @Override
    public Entity getOwner() {
        return this.owner;
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
    
}
