package dev.VeeBee2570.koog;

import net.minecraftforge.eventbus.api.SubscribeEvent;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;

import java.util.Collections;
import java.util.Map;
import java.util.List;

import org.slf4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value=Dist.CLIENT)
public class PlayerOverride {


    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        ResourceLocation texture =         new ResourceLocation("minecraft", "textures/entity/cow/cow.png");
        VertexConsumer buffer = event.getMultiBufferSource().getBuffer(RenderType.entityCutoutNoCull(texture));

        ExampleMod.LOGGER.info("Intercepted render layer event");
        event.setCanceled(true);

        LayerDefinition cowLayer = CowModel.createBodyLayer();
        ModelPart cowPart = cowLayer.bakeRoot();
        CowModel<Cow> model = new CowModel<Cow>(cowPart);

        int light = 15728880;

        PoseStack pose = event.getPoseStack();
        pose.mulPose(Axis.XP.rotationDegrees(180));
        pose.translate(0, -2, 0);

        model.setupAnim(null, 0.4f, 1f, 190f, 20f, 20f);

        model.renderToBuffer(pose, buffer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

    }

    public static void testMessage() {
        ExampleMod.LOGGER.info("PlayerOverride test message success");
    }

    static  {
        ExampleMod.LOGGER.info("PlayerOverride class initialised");
    }
}
