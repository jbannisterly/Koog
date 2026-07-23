package dev.VeeBee2570.koog;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class GunRenderer extends EntityRenderer<Grenade> {

    protected Model grenadeModel;

    protected GunRenderer(EntityRendererProvider.Context context) {
        super(context);
        LayerDefinition grenadeLayer = GrenadeModel.createBodyLayer();
        ModelPart grenadePart = grenadeLayer.bakeRoot();

        this.grenadeModel = new GrenadeModel<Grenade>(grenadePart);
    }

    @Override
    public void render(Grenade grenade, float rotation, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(grenade)));

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(grenade.xRotO));
        poseStack.mulPose(Axis.YP.rotationDegrees(grenade.yRotO));

        this.grenadeModel.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    
        poseStack.popPose();
    }


    @Override
    public ResourceLocation getTextureLocation(Grenade grenade) {
        // temp texture
        return ResourceLocation.fromNamespaceAndPath("koog", "textures/entity/bullet/grenade.png");
    }
    
}
