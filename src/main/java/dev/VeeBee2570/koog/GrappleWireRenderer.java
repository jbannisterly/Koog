package dev.VeeBee2570.koog;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class GrappleWireRenderer extends EntityRenderer<GrappleWire> {

    protected GrappleWireRenderer(Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(GrappleWire grappleWire) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/dirt.png");
    }
    
}
