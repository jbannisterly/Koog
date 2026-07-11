package dev.VeeBee2570.koog;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
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
        partDefinition.addOrReplaceChild("ball", CubeListBuilder.create()
.texOffs(0,0).addBox(-1,-3,-8,2,6,16, new CubeDeformation(0.0f))
.texOffs(0,22).addBox(-1,-5,-7,2,10,14, new CubeDeformation(0.001f))
.texOffs(0,46).addBox(-1,-7,-5,2,14,10, new CubeDeformation(0.002f))
.texOffs(0,70).addBox(-1,-8,-3,2,16,6, new CubeDeformation(0.003f))
.texOffs(0,92).addBox(-2,-2,-8,4,4,16, new CubeDeformation(0.004f))
.texOffs(0,112).addBox(-2,-6,-6,4,12,12, new CubeDeformation(0.005f))
.texOffs(0,136).addBox(-2,-8,-2,4,16,4, new CubeDeformation(0.006f))
.texOffs(0,156).addBox(-3,-1,-8,6,2,16, new CubeDeformation(0.007f))
.texOffs(0,174).addBox(-3,-4,-7,6,8,14, new CubeDeformation(0.008f))
.texOffs(0,196).addBox(-3,-7,-4,6,14,8, new CubeDeformation(0.009000000000000001f))
.texOffs(0,218).addBox(-3,-8,-1,6,16,2, new CubeDeformation(0.01f))
.texOffs(0,236).addBox(-4,-3,-7,8,6,14, new CubeDeformation(0.011f))
.texOffs(44,0).addBox(-4,-5,-6,8,10,12, new CubeDeformation(0.012f))
.texOffs(44,22).addBox(-4,-6,-5,8,12,10, new CubeDeformation(0.013000000000000001f))
.texOffs(44,44).addBox(-4,-7,-3,8,14,6, new CubeDeformation(0.014f))
.texOffs(44,64).addBox(-5,-1,-7,10,2,14, new CubeDeformation(0.015f))
.texOffs(44,80).addBox(-5,-4,-6,10,8,12, new CubeDeformation(0.016f))
.texOffs(44,100).addBox(-5,-5,-5,10,10,10, new CubeDeformation(0.017f))
.texOffs(44,120).addBox(-5,-6,-4,10,12,8, new CubeDeformation(0.018000000000000002f))
.texOffs(44,140).addBox(-5,-7,-1,10,14,2, new CubeDeformation(0.019f))
.texOffs(44,156).addBox(-6,-2,-6,12,4,12, new CubeDeformation(0.02f))
.texOffs(44,172).addBox(-6,-4,-5,12,8,10, new CubeDeformation(0.021f))
.texOffs(44,190).addBox(-6,-5,-4,12,10,8, new CubeDeformation(0.022f))
.texOffs(44,208).addBox(-6,-6,-2,12,12,4, new CubeDeformation(0.023f))
.texOffs(44,224).addBox(-7,-1,-5,14,2,10, new CubeDeformation(0.024f))
.texOffs(44,236).addBox(-7,-3,-4,14,6,8, new CubeDeformation(0.025f))
.texOffs(92,0).addBox(-7,-4,-3,14,8,6, new CubeDeformation(0.026000000000000002f))
.texOffs(92,14).addBox(-7,-5,-1,14,10,2, new CubeDeformation(0.027f))
.texOffs(92,26).addBox(-8,-1,-3,16,2,6, new CubeDeformation(0.028f))
.texOffs(92,34).addBox(-8,-2,-2,16,4,4, new CubeDeformation(0.029f))
.texOffs(92,42).addBox(-8,-3,-1,16,6,2, new CubeDeformation(0.03f))
        , PartPose.offset(0, 0, 0));

        return LayerDefinition.create(meshDefinition, 256, 256);
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
