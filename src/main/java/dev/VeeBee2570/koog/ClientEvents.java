package dev.VeeBee2570.koog;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=ExampleMod.MODID, bus=Mod.EventBusSubscriber.Bus.FORGE, value=Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onLeftClick(InputEvent.InteractionKeyMappingTriggered event) {
        if (event.isAttack()) {
            ExampleMod.LOGGER.info("intercepted left click event");
        }
    }
}
