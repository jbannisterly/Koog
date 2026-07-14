package dev.VeeBee2570.koog;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
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
            if (isGun) {
                ExampleMod.LOGGER.info("Fire gun event");
                event.setCanceled(true);
                NetworkMessages.channel.sendToServer(new PacketFireGun("client data"));
            }
        }
    }
}
