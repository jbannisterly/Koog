package dev.VeeBee2570.koog;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

public class PacketFireGun {

    String message;

    public PacketFireGun(String inMessage){
        this.message = inMessage;
    }

    public void encoder(FriendlyByteBuf buffer) {

    }

    public static PacketFireGun decoder(FriendlyByteBuf buffer) {
        
        return new PacketFireGun("decoder message");
    }

    public void messageConsumer(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            Item currentItem = player.getMainHandItem().getItem();
            Boolean isGun = currentItem instanceof Gun;
            if (isGun) {
                Gun currentGun = (Gun)currentItem;
                currentGun.Fire(player.level(), player);
            }
        });
    }
}
