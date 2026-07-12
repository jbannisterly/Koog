package dev.VeeBee2570.koog;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Grenade extends Entity implements TraceableEntity {
    protected LivingEntity owner;


    public Grenade(EntityType<? extends Grenade> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public Grenade(Level level,  @Nullable LivingEntity owner, Vec3 deltaMovement, Vec3 pos) {
        super(ExampleMod.GRENADE_TYPE.get(), level);
        this.owner = owner;
        this.setDeltaMovement(deltaMovement);
        this.setPos(pos);
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
