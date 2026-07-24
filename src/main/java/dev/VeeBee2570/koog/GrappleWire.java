package dev.VeeBee2570.koog;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GrappleWire extends Entity {
    
    private Player player;
    private Entity target = null;
    private Vec3 targetPosition;

    public GrappleWire(EntityType<? extends GrappleWire> entityType, Level level) {
        super(entityType, level);
    }

    public GrappleWire(Level level, Player player, Entity target) {
        super(ExampleMod.GRAPPLE_WIRE_TYPE.get(), level);

        this.player = player;
        this.target = target;
    }

    public GrappleWire(Level level, Player player, Vec3 targetPosition) {
        super(ExampleMod.GRAPPLE_WIRE_TYPE.get(), level);

        this.player = player;
        this.targetPosition = targetPosition;
    }

    @Override
    public void tick() {
        super.tick();

        if (player != null) {
            Vec3 startPosition = this.player.position();
            Vec3 endPosition;
            if (target == null) {
                endPosition = this.targetPosition;
            } else {
                endPosition = this.target.position();
            }

            this.player.setDeltaMovement(calculateStartVelocity(startPosition, endPosition));
            this.player.hurtMarked = true;

            if (target != null) {
                this.target.setDeltaMovement(calculateEndVelocity(startPosition, endPosition));
                this.target.hurtMarked = true;
            }
        
            ExampleMod.LOGGER.info("set player velocity " + this.player.getDeltaMovement());
        }

    }

    private Vec3 calculateStartVelocity(Vec3 startPosition, Vec3 endPosition) {
        return endPosition.subtract(startPosition).scale(0.1);
    }

    private Vec3 calculateEndVelocity(Vec3 startPosition, Vec3 endPosition) {
        return startPosition.subtract(endPosition).scale(0.1);
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
