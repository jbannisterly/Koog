package dev.VeeBee2570.koog;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.PacketDispatcher;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {
    
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        int characterID = Integer.parseInt(player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).getString("koog:skin"));
        UUID playerID = player.getUUID();

        ExampleMod.LOGGER.info("Sending join value " + characterID);

        NetworkMessages.channel.send(PacketDistributor.ALL.noArg(), new PacketChangeCharacterClient(characterID, playerID));

        for (Player existingPlayer: player.getServer().getPlayerList().getPlayers()) {
            UUID otherPlayerID = existingPlayer.getUUID();
            if (otherPlayerID != playerID) {
                int otherCharacterID = Integer.parseInt(existingPlayer.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).getString("koog:skin"));
                NetworkMessages.channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)player), new PacketChangeCharacterClient(otherCharacterID, otherPlayerID));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        ServerPlayer player = (ServerPlayer)event.getEntity();
        int characterID = Integer.parseInt(player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).getString("koog:skin"));
        UUID playerID = player.getUUID();

        ExampleMod.LOGGER.info("Sending respawn value " + characterID);

        NetworkMessages.channel.send(PacketDistributor.PLAYER.with(() -> player), new PacketChangeCharacterClient(characterID, playerID));
    }
}
