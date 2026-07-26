package dev.VeeBee2570.koog;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GrappleWireRenderer extends EntityRenderer<GrappleWire> {

    public GrappleWireRenderer(Context context) {
        super(context);
        ExampleMod.LOGGER.info("constructor for wire called");
    }

    @Override
    public ResourceLocation getTextureLocation(GrappleWire grappleWire) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/stone.png");
    }

    @Override
    public void render(GrappleWire wire, float rotation, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        ExampleMod.LOGGER.info("rendering wire");

        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(wire)));
        Vec3 startPosition = wire.getStartPosition();
        Vec3 endPosition = wire.getEndPosition();
        Vec3 cameraPosition = entityRenderDispatcher.camera.getPosition();
        Vec3 cameraDirection = new Vec3(entityRenderDispatcher.camera.getLookVector());
        Vec3 relativeStartPosition = startPosition.subtract(wire.position());
        Vec3 relativeEndPosition = endPosition.subtract(wire.position());
        Vec3 offset = (endPosition.subtract(startPosition)).cross(cameraDirection).multiply(1, 0, 1).normalize().scale(0.1);
        float distance = (float)startPosition.subtract(endPosition).length() * 10;

        ExampleMod.LOGGER.info("offset " + offset.toString());

        Vec3 a = relativeStartPosition.add(offset);
        Vec3 b = relativeEndPosition.add(offset);
        Vec3 c = relativeEndPosition.subtract(offset);
        Vec3 d = relativeStartPosition.subtract(offset);

        poseStack.pushPose();

        Pose pose = poseStack.last();

        consumer.vertex(pose.pose(), (float)a.x, (float)a.y, (float)a.z)
            .color(255, 255, 255, 255)
            .uv(0, 0)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(packedLight)
            .normal(pose.normal(),0, 1, 0)
            .endVertex();
        consumer.vertex(pose.pose(), (float)b.x, (float)b.y, (float)b.z)
            .color(255, 255, 255, 255)
            .uv(0, distance)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(packedLight)
            .normal(pose.normal(),0, 1, 0)
            .endVertex();
        consumer.vertex(pose.pose(), (float)c.x, (float)c.y, (float)c.z)
            .color(255, 255, 255, 255)
            .uv(1, distance)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(packedLight)
            .normal(pose.normal(),0, 1, 0)
            .endVertex();
        consumer.vertex(pose.pose(), (float)d.x, (float)d.y, (float)d.z)
            .color(255, 255, 255, 255)
            .uv(1, 0)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(packedLight)
            .normal(pose.normal(),0, 1, 0)
            .endVertex();


        poseStack.popPose();
    }
}
