package dev.VeeBee2570.koog;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=ExampleMod.MODID, bus=Mod.EventBusSubscriber.Bus.FORGE, value=Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onLeftClick(InputEvent.InteractionKeyMappingTriggered event) {
        if (event.isAttack()) {
            LocalPlayer player = Minecraft.getInstance().player;
            Item currentItem = player.getMainHandItem().getItem();
            Boolean isGun = currentItem instanceof Gun;
            Boolean ridingMachineGun = player.getVehicle() instanceof MachineGun;
            if (isGun || ridingMachineGun) {
                // ExampleMod.LOGGER.info("Fire gun event");
                event.setCanceled(true);
                NetworkMessages.channel.sendToServer(new PacketFireGun("client data"));
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        while (ClientRegister.characterSelect != null && ClientRegister.characterSelect.consumeClick()) {
            Minecraft.getInstance().setScreen(new CharacterScreen(Component.literal("Character Select")));
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.getVehicle() instanceof MachineGun && Minecraft.getInstance().options.keyAttack.isDown()) {
                NetworkMessages.channel.sendToServer(new PacketFireGun("client data"));
            }
        }
    }
}
