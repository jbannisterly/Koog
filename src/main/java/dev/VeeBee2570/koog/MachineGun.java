package dev.VeeBee2570.koog;

import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MachineGun extends Entity {

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

            ExampleMod.LOGGER.info("passender " + passenger.getXRot());

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
    
}
