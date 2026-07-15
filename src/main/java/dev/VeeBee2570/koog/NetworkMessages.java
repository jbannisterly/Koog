package dev.VeeBee2570.koog;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkMessages {
    
    public static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(ExampleMod.MODID, "channel"),
        () -> "1", "1"::equals, "1"::equals);

    public static void register() {
        channel.messageBuilder(PacketFireGun.class, 0)
        .encoder(PacketFireGun::encoder)
        .decoder(PacketFireGun::decoder)
        .consumerMainThread(PacketFireGun::messageConsumer)
        .add();

        channel.messageBuilder(PacketChangeCharacterServer.class, 1)
        .encoder(PacketChangeCharacterServer::encoder)
        .decoder(PacketChangeCharacterServer::decoder)
        .consumerMainThread(PacketChangeCharacterServer::messageConsumer)
        .add();

        channel.messageBuilder(PacketChangeCharacterClient.class, 2)
        .encoder(PacketChangeCharacterClient::encoder)
        .decoder(PacketChangeCharacterClient::decoder)
        .consumerMainThread(PacketChangeCharacterClient::messageConsumer)
        .add();
    }
}