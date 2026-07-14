package dev.VeeBee2570.koog;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class CharacterScreen extends Screen{

    public CharacterScreen(Component component) {
        super(component);


    }

    @Override
    public void init() {
        CharacterButton button = new CharacterButton(0, 0, 32, 32, 0, 0, 16, 
            new ResourceLocation("minecraft", "textures/entity/example_atlas.png"), 256, 256, 
        pressedButton -> {ExampleMod.LOGGER.info("Pressed button");});
        this.addRenderableWidget(button);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);

        super.render(graphics, mouseX, mouseY, partialTicks);    
    }


}
