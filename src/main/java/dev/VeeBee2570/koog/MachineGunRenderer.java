package dev.VeeBee2570.koog;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class MachineGunRenderer extends EntityRenderer<MachineGun> {

    private Model model = new MachineGunModel<MachineGun>(MachineGunModel.createBodyLayer().bakeRoot());

    protected MachineGunRenderer(Context context) {
        super(context);
    }

    @Override
    public void render(MachineGun machineGun, float rotation, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(machineGun)));

        poseStack.pushPose();

        poseStack.mulPose(Axis.YN.rotationDegrees(machineGun.getYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(machineGun.getXRot()));

        this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    
        poseStack.popPose();
    }


    @Override
    public ResourceLocation getTextureLocation(MachineGun machineGun) {
        // temp texture
        return ResourceLocation.fromNamespaceAndPath("koog", "textures/entity/weapons/machine_gun.png");
    }
    
}
