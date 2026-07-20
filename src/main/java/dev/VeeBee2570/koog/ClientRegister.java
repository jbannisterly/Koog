package dev.VeeBee2570.koog;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=ExampleMod.MODID, bus=Mod.EventBusSubscriber.Bus.MOD, value=Dist.CLIENT)
public class ClientRegister {

    public static KeyMapping characterSelect;

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
            ExampleMod.GRENADE_TYPE.get(),
            GrenadeRenderer::new
        );

        event.registerEntityRenderer(
            ExampleMod.BULLET_TYPE.get(),
            BulletRenderer::new
        );

        event.registerEntityRenderer(
            ExampleMod.MACHINE_GUN_TYPE.get(),
            MachineGunRenderer::new 
        );
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        characterSelect = new KeyMapping("character_select", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, "koog");
        event.register(characterSelect);
    }
}
