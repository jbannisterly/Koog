package dev.VeeBee2570.koog;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GuiScreen extends Screen{
    public static final ResourceLocation BACK_00 = new ResourceLocation("koog","textures/gui/back_00.png");
    public static final ResourceLocation BACK_01 = new ResourceLocation("koog","textures/gui/back_01.png");
    public static final ResourceLocation BACK_02 = new ResourceLocation("koog","textures/gui/back_02.png");
    public static final ResourceLocation BACK_10 = new ResourceLocation("koog","textures/gui/back_10.png");
    public static final ResourceLocation BACK_11 = new ResourceLocation("koog","textures/gui/back_11.png");
    public static final ResourceLocation BACK_12 = new ResourceLocation("koog","textures/gui/back_12.png");
    public static final ResourceLocation BACK_20 = new ResourceLocation("koog","textures/gui/back_20.png");
    public static final ResourceLocation BACK_21 = new ResourceLocation("koog","textures/gui/back_21.png");
    public static final ResourceLocation BACK_22 = new ResourceLocation("koog","textures/gui/back_22.png");
    public static final ResourceLocation TEST = new ResourceLocation("minecraft", "textures/block/dirt.png");


    protected GuiScreen(Component component) {
        super(component);
    }

    public void RenderBack(GuiGraphics graphics, int x, int y, int width, int height) {
        graphics.blit(BACK_00, x,           y,          0, 0, 4, 4, 4, 4);
        graphics.blit(BACK_10, x+4,         y,          0, 0, width-8,      4, 4, 4);
        graphics.blit(BACK_20, x+width-4,   y,          0, 0, 4, 4, 4, 4);
        graphics.blit(BACK_01, x,           y+4,        0, 0, 4,    height-8, 4, 4);
        graphics.blit(BACK_11, x+4,         y+4,        0, 0, width-8, height-8, 4, 4);
        graphics.blit(BACK_21, x+width-4,   y+4,        0, 0, 4, height-8, 4, 4);
        graphics.blit(BACK_02, x,           y+height-4, 0, 0, 4, 4, 4, 4);
        graphics.blit(BACK_12, x+4,         y+height-4, 0, 0, width-8, 4, 4, 4);
        graphics.blit(BACK_22, x+width-4,   y+height-4, 0, 0, 4, 4, 4, 4);
    }

}
