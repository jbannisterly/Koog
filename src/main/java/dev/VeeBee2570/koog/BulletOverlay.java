package dev.VeeBee2570.koog;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class BulletOverlay {
    public static final IGuiOverlay overlay = 
    (ForgeGui gui, GuiGraphics graphics, float partialTick, int width, int height) -> {
        graphics.blit(ResourceLocation.fromNamespaceAndPath("minecraft", "texture/block/dirt.png"), 0, 0, 0, 0, 32, 16, 16, 16);
    };
}
