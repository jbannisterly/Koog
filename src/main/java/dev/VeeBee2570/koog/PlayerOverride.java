package dev.VeeBee2570.koog;

import net.minecraftforge.eventbus.api.SubscribeEvent;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value=Dist.CLIENT)
public class PlayerOverride {

    private static BallModel<LivingEntity> model;
    private static ItemInHandRenderer inHandRenderer = null;

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        if (inHandRenderer == null) {
           PlayerOverride.inHandRenderer = Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer();
        }

        ExampleMod.LOGGER.info("Intercepted render layer event");
        event.setCanceled(true);

        int light = 15728880;

        PoseStack pose = event.getPoseStack();
        Player player = event.getEntity();

        pose.pushPose();
        pose.mulPose(Axis.XP.rotationDegrees(180));
        pose.mulPose(Axis.YP.rotationDegrees(player.getYRot()));
        pose.translate(0, -0.5, 0);

        PlayerOverride.model.setupAnim(player, player.tickCount + event.getPartialTick(), 1f, 0, 0, player.getXRot());
        
        
        ResourceLocation bodyTexture = new ResourceLocation("minecraft", "textures/entity/ball/default_atlas.png");
        VertexConsumer bodyBuffer = event.getMultiBufferSource().getBuffer(RenderType.entityCutoutNoCull(bodyTexture));
        PlayerOverride.model.renderToBuffer(pose, bodyBuffer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        
        ResourceLocation eyeTexture = new ResourceLocation("minecraft", "textures/entity/ball/eye.png");
        VertexConsumer eyeBuffer = event.getMultiBufferSource().getBuffer(RenderType.entityCutoutNoCull(eyeTexture));
        PlayerOverride.model.renderEyes(pose, eyeBuffer, light, OverlayTexture.NO_OVERLAY);

        pose.popPose();
        pose.pushPose();

        pose.mulPose(Axis.YN.rotationDegrees(player.getYRot()));
        pose.translate(-0.8, 0.5, 0);

        inHandRenderer.renderItem(player, player.getMainHandItem(), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, pose, event.getMultiBufferSource(), light);
    
        pose.popPose();

    }

    public static void testMessage() {
        ExampleMod.LOGGER.info("PlayerOverride test message success");
    }

    static  {
        ExampleMod.LOGGER.info("PlayerOverride class initialised");
        LayerDefinition ballLayer = BallModel.createBodyLayer();
        ModelPart ballPart = ballLayer.bakeRoot();
        PlayerOverride.model = new BallModel<LivingEntity>(ballPart);
    }
}
