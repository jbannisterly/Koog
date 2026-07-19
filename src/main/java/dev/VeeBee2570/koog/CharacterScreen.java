package dev.VeeBee2570.koog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.server.packs.resources.Resource;;

public class CharacterScreen extends GuiScreen{

    private List<List<CharacterButton>> screens = new ArrayList<List<CharacterButton>>();

    public CharacterScreen(Component component) {
        super(component);
    }

    @Override
    public void init() {

        final int textureImageWidth = 17;
        final int gridImageSize = 16;
        final int texSize = 16;
        final int gridImageCountX = 16;
        final int gridImageCountY = 4;
        final int flagCount = 258;
        
        ResourceLocation charactersLoc = new ResourceLocation("minecraft", "character_list.json");
        Resource characterRes = Minecraft.getInstance().getResourceManager().getResource(charactersLoc).orElseThrow();
        String[] characters = {"default"};
        try {
            characters = new Gson().fromJson(characterRes.openAsReader(), String[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResourceLocation flagAtlas = new ResourceLocation("minecraft", "textures/entity/ball/flag_atlas.png");

        List<CharacterButton> screen = new ArrayList<CharacterButton>();
        for (int i = 0; i < flagCount; i++) {
            screen.add(CreateButton(i % gridImageCountX, (i / gridImageCountX) % gridImageCountY, gridImageSize, i, textureImageWidth, texSize, flagAtlas));
            if ((i + 1) % (gridImageCountX * gridImageCountY) == 0 || i + 1 == flagCount) {
                screens.add(new ArrayList<>(screen));
                screen = new ArrayList<CharacterButton>();
            }
        }
        ChangeScreen(1);
    }

    public void ChangeScreen(int screenIndex) {
        List<CharacterButton> screen = screens.get(screenIndex);
        clearWidgets();
        screen.forEach(button -> {
            addRenderableWidget(button);
        });
    }

    private CharacterButton CreateButton(int xPos, int yPos, int gridImageSize, int textureID, int textureImageWidth, int texSize, ResourceLocation flagAtlas) {
        final int xOffset = 16;
        final int yOffset = 128;
        final int border = 2;
        
        CharacterButton button = new CharacterButton(
            ((Integer)textureID).toString(),
            xPos * (gridImageSize + border) + xOffset, yPos * (gridImageSize + border) + yOffset, 
            texSize, texSize, 
            (textureID % textureImageWidth) *  texSize, (textureID / textureImageWidth) * texSize, 
            (int)(texSize * 0.25), flagAtlas
            , 512, 512, 
            pressedButton -> {((CharacterButton)pressedButton).SelectCharacter();});
        return button;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        LocalPlayer currentPlayer = Minecraft.getInstance().player;

        this.renderBackground(graphics);
        
        RenderBack(graphics, 8, 8, 288, 200);

        super.render(graphics, mouseX, mouseY, partialTicks);    

        final int xCharacterPos = 128;
        final int yCharacterPos = 96;
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, xCharacterPos, yCharacterPos, 32, xCharacterPos - mouseX, yCharacterPos - mouseY, currentPlayer);
    }


}
