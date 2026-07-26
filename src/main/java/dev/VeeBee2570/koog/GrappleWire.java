package dev.VeeBee2570.koog;

import java.util.Optional;
import java.util.UUID;

import org.joml.Vector3f;
import org.openjdk.nashorn.internal.runtime.regexp.joni.Option;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class GrappleWire extends Entity {

    private static final EntityDataAccessor<Optional<UUID>> player = SynchedEntityData.defineId(
        GrappleWire.class, EntityDataSerializers.OPTIONAL_UUID
    );

    private static final EntityDataAccessor<Integer> target = SynchedEntityData.defineId(
        GrappleWire.class, EntityDataSerializers.INT
    );

    private static final EntityDataAccessor<Vector3f> targetPosition = SynchedEntityData.defineId(
        GrappleWire.class, EntityDataSerializers.VECTOR3
    );
    public GrappleWire(EntityType<? extends GrappleWire> entityType, Level level) {
        super(entityType, level);

        ExampleMod.LOGGER.info("default constructor grapplewire");
        ExampleMod.LOGGER.info("grapple type constructed " + entityType);
    }

    public GrappleWire(Level level, Player player, Entity target) {
        super(ExampleMod.GRAPPLE_WIRE_TYPE.get(), level);

        this.setPos(player.position());

        this.entityData.set(GrappleWire.player, Optional.of(player.getUUID()));
        this.entityData.set(GrappleWire.target, target.getId());

        ExampleMod.LOGGER.info("custom constructor grapplewire");

    }

    public GrappleWire(Level level, Player player, Vec3 targetPosition) {
        super(ExampleMod.GRAPPLE_WIRE_TYPE.get(), level);

        this.setPos(player.position());


        this.entityData.set(GrappleWire.player, Optional.of(player.getUUID()));
        this.entityData.set(GrappleWire.targetPosition, new Vector3f((float)targetPosition.x, (float)targetPosition.y, (float)targetPosition.z));
        this.entityData.set(GrappleWire.target, -1);

        ExampleMod.LOGGER.info("custom constructor grapplewire");

    }

    public void delete() {
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();

        Optional<UUID> playerUUID = this.entityData.get(GrappleWire.player);

        if (!playerUUID.isEmpty()) {
            Player player = level().getPlayerByUUID(playerUUID.get());

            player.fallDistance = 0;

            if (player.isRemoved()) {
                this.delete();
                return;
            }

            Vec3 startPosition = player.position();
            Vec3 endPosition;

            Integer targetEntityID = this.entityData.get(GrappleWire.target);
            if (targetEntityID == -1) {
                endPosition = new Vec3(this.entityData.get(GrappleWire.targetPosition));
            } else {
                Entity target = level().getEntity(targetEntityID);

                endPosition = target.position();
                target.setDeltaMovement(target.getDeltaMovement().add(calculateEndVelocity(startPosition, endPosition).scale(0.5)).scale(0.7));
                target.hurtMarked = true;

            }

            player.setDeltaMovement(player.getDeltaMovement().add(calculateStartVelocity(startPosition, endPosition).scale(0.5)).scale(0.7));
            player.hurtMarked = true;

            // ExampleMod.LOGGER.info("set player velocity " + this.player.getDeltaMovement());
        } else {
            this.delete();
        }

    }

    private Vec3 calculateStartVelocity(Vec3 startPosition, Vec3 endPosition) {
        return endPosition.subtract(startPosition).scale(0.1);
    }

    private Vec3 calculateEndVelocity(Vec3 startPosition, Vec3 endPosition) {
        return startPosition.subtract(endPosition).scale(0.1);
    }


    public Vec3 getStartPosition() {
        Optional<UUID> playerUUID = this.entityData.get(GrappleWire.player);

        if (!playerUUID.isEmpty()) {
            Player player = level().getPlayerByUUID(playerUUID.get());

            return player.position();

        }

        return new Vec3(0,0,0);

    }

    public Vec3 getEndPosition() {
        Integer targetID = this.entityData.get(GrappleWire.target);

        if (targetID == -1) {
            return new Vec3(this.entityData.get(GrappleWire.targetPosition));
        } else {
            Entity targetEntity = level().getEntity(targetID);

            return targetEntity.position();
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(GrappleWire.player, Optional.empty());
        this.entityData.define(GrappleWire.target, 0);
        this.entityData.define(GrappleWire.targetPosition, new Vector3f(0));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {
    }
    
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        ExampleMod.LOGGER.info("spawn packet request");

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        Vec3 start = getStartPosition();
        Vec3 end = getEndPosition();

        return new AABB(start, end).inflate(1);
    }
}
