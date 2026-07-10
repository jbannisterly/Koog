package dev.VeeBee2570.koog;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BallModel<E extends Entity> extends EntityModel<E> {
    protected final ModelPart ball;

    protected BallModel(ModelPart modelPart) {
        this.ball = modelPart.getChild("ball");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("ball", CubeListBuilder.create().texOffs(0, 0).addBox(-8, -8, -8, 16, 16, 16), PartPose.offset(0, 0, 0));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(Entity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_,
            float p_102623_) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int p_103113_, int p_103114_,
            float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
        this.BallParts().forEach((part) -> {
            part.render(poseStack, vertexConsumer, p_103113_, p_103114_);
        });
    }

    protected Iterable<ModelPart> BallParts() {
        return ImmutableList.of(this.ball);
    }
    
}
