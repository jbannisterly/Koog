package dev.VeeBee2570.koog;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BulletRenderer extends EntityRenderer<Bullet> {

    protected Model bulletModel;

    protected BulletRenderer(EntityRendererProvider.Context context) {
        super(context);
        LayerDefinition bulletLayer = BulletModel.createBodyLayer();
        ModelPart bulletPart = bulletLayer.bakeRoot();

        this.bulletModel = new BulletModel<Bullet>(bulletPart);
    }

    @Override
    public void render(Bullet grenade, float rotation, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(grenade)));
        this.bulletModel.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }


    @Override
    public ResourceLocation getTextureLocation(Bullet grenade) {
        // temp texture
        return ResourceLocation.fromNamespaceAndPath("koog", "textures/entity/ball/ball.png");
    }
    
}
