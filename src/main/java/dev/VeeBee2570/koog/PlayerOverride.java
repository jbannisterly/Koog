package dev.VeeBee2570.koog;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value=Dist.CLIENT)
public class PlayerOverride {


    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        ExampleMod.LOGGER.info("Intercepted render player event");
        event.setCanceled(true);
    }

    public static void testMessage() {
        ExampleMod.LOGGER.info("PlayerOverride test message success");
    }

    static  {
        ExampleMod.LOGGER.info("PlayerOverride class initialised");
    }
}
