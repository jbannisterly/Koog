package dev.VeeBee2570.koog;

import java.util.function.Supplier;

import com.google.common.graph.Network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.PacketDispatcher;
import net.minecraftforge.network.PacketDistributor.PacketTarget;

public class PacketChangeCharacterServer {

    private final int characterID;

    public PacketChangeCharacterServer(int characterID){
        this.characterID = characterID;
    }

    public void encoder(FriendlyByteBuf buffer) {
        buffer.writeInt(this.characterID);
    }

    public static PacketChangeCharacterServer decoder(FriendlyByteBuf buffer) {
        int characterID = buffer.readInt();
        return new PacketChangeCharacterServer(characterID);
    }

    public void messageConsumer(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ExampleMod.LOGGER.info("Change skin packet received");
            ServerPlayer player = context.get().getSender();
            setSkin(player, String.valueOf(characterID));
            NetworkMessages.channel.send(PacketDistributor.ALL.noArg(), new PacketChangeCharacterClient(characterID, player.getUUID()));
        });
    }

    private void setSkin(Player player, String skinName) {
        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        data.putString("koog:skin", skinName);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
        ExampleMod.LOGGER.info("Set skin to " + skinName);
        ExampleMod.LOGGER.info("Check skin" + player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).getString("koog:skin"));
    }
}
