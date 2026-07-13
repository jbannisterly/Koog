package dev.VeeBee2570.koog;

import javax.annotation.Nullable;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Bullet extends Projectile {
    protected LivingEntity owner;
    private final float speed = 5;

    public Bullet(EntityType<? extends Bullet> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public Bullet(Level level,  @Nullable LivingEntity owner, Vec3 deltaMovement, Vec3 pos) {
        super(ExampleMod.BULLET_TYPE.get(), level);
        this.owner = owner;
        this.setDeltaMovement(deltaMovement.multiply(speed, speed, speed));
        this.setPos(pos);
    }

    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            HitResult hit = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hit.getType() != HitResult.Type.MISS) {
                if (hit.getType() == HitResult.Type.ENTITY) {
                    EntityHitResult entityHit = (EntityHitResult) hit;
                    entityHit.getEntity().hurt(level().damageSources().playerAttack((Player)owner), 2.0f);
                }
                this.discard();
                return;
            }
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().add(0, 0, 0));
        }

    }

    @Override
    protected void defineSynchedData() {
    }
    
}
