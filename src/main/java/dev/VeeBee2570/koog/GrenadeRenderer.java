package dev.VeeBee2570.koog;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class GrenadeRenderer extends EntityRenderer<Grenade> {

    protected Model grenadeModel;

    protected GrenadeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.grenadeModel = new MinecartModel<>(context.bakeLayer(ModelLayers.MINECART));
    }

    @Override
    public void render(Grenade grenade, float rotation, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(grenade)));
        this.grenadeModel.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }


    @Override
    public ResourceLocation getTextureLocation(Grenade grenade) {
        // temp texture
        return ResourceLocation.fromNamespaceAndPath("minecraft", "entity/ball/ball.png");
    }
    
}
