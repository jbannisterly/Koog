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
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.server.packs.resources.Resource;;

public class CharacterScreen extends GuiScreen{

    private List<List<ImageButton>> screens = new ArrayList<List<ImageButton>>();
    private int currentPage = 0;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private final int imageWidth = 300;
    private final int imageHeight = 220;
    private int offsetX;
    private int offsetY;


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

        this.offsetX = (this.width - this.imageWidth) / 2;
        this.offsetY = (this.height - this.imageHeight) / 2;

        ExampleMod.LOGGER.info(String.valueOf(offsetX));
        
        ResourceLocation charactersLoc = ResourceLocation.fromNamespaceAndPath("koog", "character_list.json");
        Resource characterRes = Minecraft.getInstance().getResourceManager().getResource(charactersLoc).orElseThrow();
        String[] characters = {"default"};
        try {
            characters = new Gson().fromJson(characterRes.openAsReader(), String[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResourceLocation flagAtlas = ResourceLocation.fromNamespaceAndPath("koog", "textures/entity/ball/flag_atlas.png");
        ResourceLocation next = ResourceLocation.fromNamespaceAndPath("koog", "textures/gui/arrow_r.png");
        ResourceLocation prev = ResourceLocation.fromNamespaceAndPath("koog", "textures/gui/arrow_l.png");

        List<ImageButton> screen = new ArrayList<ImageButton>();
        this.screens = new ArrayList<>();

        for (int i = 0; i < flagCount; i++) {
            screen.add(CreateButton(i % gridImageCountX, (i / gridImageCountX) % gridImageCountY, gridImageSize, i, textureImageWidth, texSize, flagAtlas));
            if ((i + 1) % (gridImageCountX * gridImageCountY) == 0 || i + 1 == flagCount) {
                screens.add(new ArrayList<>(screen));
                screen = new ArrayList<ImageButton>();
            }
        }

        prevButton = new ImageButton(
            8 + offsetX, 200 + offsetY,
            16, 16,
            0, 0,
            0, prev,
            16, 16,
            pressedButton -> 
            {
                currentPage--;
                ChangeScreen(currentPage);
            }
        );

        nextButton = new ImageButton(
            278 + offsetX, 200 + offsetY,
            16, 16,
            0, 0,
            0, next,
            16, 16,
            pressedButton -> 
            {
                currentPage++;
                ChangeScreen(currentPage);
            }
        );


        ChangeScreen(0);
    }

    public void ChangeScreen(int screenIndex) {
        List<ImageButton> screen = screens.get(screenIndex);
        clearWidgets();
        screen.forEach(button -> {
            addRenderableWidget(button);
        });
        if (screenIndex > 0) {
            addRenderableWidget(prevButton);
        }
        if (screenIndex < screens.size() - 1) {
            addRenderableWidget(nextButton);
        }
    }

    private CharacterButton CreateButton(int xPos, int yPos, int gridImageSize, int textureID, int textureImageWidth, int texSize, ResourceLocation flagAtlas) {
        final int xOffset = 8;
        final int yOffset = 128;
        final int border = 2;

        ExampleMod.LOGGER.info("Character button offset " + this.offsetX);
        
        CharacterButton button = new CharacterButton(
            ((Integer)textureID).toString(),
            xPos * (gridImageSize + border) + xOffset + this.offsetX, yPos * (gridImageSize + border) + yOffset + this.offsetY, 
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
        
        RenderBack(graphics, offsetX, offsetY, imageWidth, imageHeight);

        super.render(graphics, mouseX, mouseY, partialTicks);    

        final int xCharacterPos = 150;
        final int yCharacterPos = 96;
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, xCharacterPos + offsetX, yCharacterPos + offsetY, 32, xCharacterPos + this.offsetX - mouseX, yCharacterPos + this.offsetY - mouseY, currentPlayer);
    }


}
