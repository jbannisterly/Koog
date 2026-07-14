package dev.VeeBee2570.koog;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.client.Minecraft;
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
import net.minecraft.server.packs.resources.Resource;;

public class CharacterScreen extends Screen{

    public CharacterScreen(Component component) {
        super(component);


    }

    @Override
    public void init() {

        final int textureImageWidth = 16;
        final int gridImageCount = 17;
        final int texSize = 16;
        final int gridImageSize = 16;
        
        ResourceLocation charactersLoc = new ResourceLocation("minecraft", "character_list.json");
        Resource characterRes = Minecraft.getInstance().getResourceManager().getResource(charactersLoc).orElseThrow();
        String[] characters = {"default"};
        try {
            characters = new Gson().fromJson(characterRes.openAsReader(), String[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResourceLocation flagAtlas = new ResourceLocation("minecraft", "textures/entity/ball/flag_atlas.png");

        for (int i = 0; i < 243; i++) {
            CharacterButton button = new CharacterButton(
                ((Integer)i).toString(),
                i % gridImageCount * gridImageSize, i / gridImageCount * gridImageSize, 
                texSize, texSize, 
                (i % textureImageWidth) *  texSize, (i / textureImageWidth) * texSize, 
                (int)(texSize * 0.25), flagAtlas
                , 512, 512, 
            pressedButton -> {((CharacterButton)pressedButton).SelectCharacter();});
            this.addRenderableWidget(button);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);

        super.render(graphics, mouseX, mouseY, partialTicks);    
    }


}
