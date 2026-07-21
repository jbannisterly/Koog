package dev.VeeBee2570.koog;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class MachineGunModel<E extends Entity> extends EntityModel<E> {
    protected final ModelPart machineGun;
    
    public MachineGunModel(ModelPart root) {
		this.machineGun = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 23).addBox(-2.0F, -8.0F, 4.0F, 4.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -10.0F, -13.0F, 6.0F, 6.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(35, 10).addBox(-1.0F, -4.0F, -11.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 29).addBox(-1.0F, -9.0F, -18.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -5.0F, -18.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.GunParts().forEach(
            (part) -> {
                part.render(poseStack, vertexConsumer, packedLight, packedOverlay);
            }
        );
	}

    protected Iterable<ModelPart> GunParts() {
        return ImmutableList.of(this.machineGun);
    }
}
