package dev.VeeBee2570.koog;

import java.util.UUID;
import java.util.function.Supplier;

import com.google.common.graph.Network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.PacketDispatcher;
import net.minecraftforge.network.PacketDistributor.PacketTarget;

public class PacketChangeCharacterClient {

    private final int characterID;
    private final UUID playerUUID;

    public PacketChangeCharacterClient(int characterID, UUID playerUUID){
        this.characterID = characterID;
        this.playerUUID = playerUUID;
    }

    public void encoder(FriendlyByteBuf buffer) {
        buffer.writeInt(this.characterID);
        buffer.writeUUID(playerUUID);
    }

    public static PacketChangeCharacterClient decoder(FriendlyByteBuf buffer) {
        int characterID = buffer.readInt();
        UUID playerUUID = buffer.readUUID();
        return new PacketChangeCharacterClient(characterID, playerUUID);
    }

    public void messageConsumer(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ExampleMod.LOGGER.info("Change client skin packet received");
            Player player = Minecraft.getInstance().level.getPlayerByUUID(playerUUID);
            setSkin(player, String.valueOf(characterID));
        });
    }

    private void setSkin(Player player, String skinName) {
        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        data.putString("koog:skin", skinName);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
        ExampleMod.LOGGER.info("Set client skin to " + skinName);
        ExampleMod.LOGGER.info("Check client skin" + player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).getString("koog:skin"));
    }
}
