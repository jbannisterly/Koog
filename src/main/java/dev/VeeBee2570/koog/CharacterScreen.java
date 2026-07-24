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
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.server.packs.resources.Resource;;

public class CharacterScreen extends GuiScreen{

    private List<List<ImageButton>> screens = new ArrayList<List<ImageButton>>();
    private int currentPage = 0;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int globalOffsetX;
    private int globalOffsetY;
    private final int gridOffsetX = 8;
    private final int gridOffsetY = 128;
    private final int border = 4;
    private final int textureImageWidth = 17;
    private final int gridImageSize = 16;
    private final int texSize = 16;
    private final int gridImageCountX = 16;
    private final int gridImageCountY = 4;
    private final int flagCount = 258;

    private final int imageWidth = 2 * gridOffsetX + (border + gridImageSize) * gridImageCountX - border;
    private final int imageHeight = gridOffsetY + (border + gridImageSize) * gridImageCountY + 32;

    private final ResourceLocation selectedLocation = ResourceLocation.fromNamespaceAndPath("koog", "textures/gui/selected.png");
    private final ResourceLocation selectingLocation = ResourceLocation.fromNamespaceAndPath("koog", "textures/gui/selecting.png");

    private int screenIndex = 0;

    public CharacterScreen(Component component) {
        super(component);
    }

    @Override
    public void init() {


        this.globalOffsetX = (this.width - this.imageWidth) / 2;
        this.globalOffsetY = (this.height - this.imageHeight) / 2;

        ExampleMod.LOGGER.info(String.valueOf(globalOffsetX));
        
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
            gridOffsetX + globalOffsetX, gridImageCountY * (border + gridImageSize) + globalOffsetY + gridOffsetY + 4,
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
            imageWidth + globalOffsetX - 16 - gridOffsetX, gridImageCountY * (border + gridImageSize) + globalOffsetY + gridOffsetY + 4,
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

        this.screenIndex = screenIndex;
    }

    private CharacterButton CreateButton(int xPos, int yPos, int gridImageSize, int textureID, int textureImageWidth, int texSize, ResourceLocation flagAtlas) {

        ExampleMod.LOGGER.info("Character button offset " + this.globalOffsetX);
        
        CharacterButton button = new CharacterButton(
            ((Integer)textureID).toString(),
            xPos * (gridImageSize + border) + gridOffsetX + this.globalOffsetX, yPos * (gridImageSize + border) + gridOffsetY + this.globalOffsetY, 
            texSize, texSize, 
            (textureID % textureImageWidth) *  texSize, (textureID / textureImageWidth) * texSize, 
            0, flagAtlas
            , 512, 512, 
            pressedButton -> {((CharacterButton)pressedButton).SelectCharacter();});
        return button;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        LocalPlayer currentPlayer = Minecraft.getInstance().player;

        this.renderBackground(graphics);
        
        RenderBack(graphics, globalOffsetX, globalOffsetY, imageWidth, imageHeight);

        super.render(graphics, mouseX, mouseY, partialTicks);    

        RenderSelection(graphics, mouseX, mouseY, partialTicks, screens.get(screenIndex).size(), gridImageCountX * gridImageCountY * screenIndex);

        final int xCharacterPos = 150;
        final int yCharacterPos = 96;
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, xCharacterPos + globalOffsetX, yCharacterPos + globalOffsetY, 32, xCharacterPos + this.globalOffsetX - mouseX, yCharacterPos + this.globalOffsetY - mouseY, currentPlayer);
    }

    private void RenderSelection(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, int characterCount, int characterOffset) {
        int selectingIndexX = (mouseX - globalOffsetX - gridOffsetX + border) / (gridImageSize + border);
        int selectingIndexY = (mouseY - globalOffsetY - gridOffsetY + border) / (gridImageSize + border);
        RenderSelect(graphics, selectingLocation, selectingIndexX, selectingIndexY, characterCount);

        int currentIndex = GetCurrentCharacter();
        int indexOffset = currentIndex - characterOffset;
        int selectedIndexX = indexOffset % gridImageCountX;
        int selectedIndexY = indexOffset / gridImageCountX;
        RenderSelect(graphics, selectedLocation, selectedIndexX, selectedIndexY, characterCount);

    }

    private void RenderSelect(GuiGraphics graphics, ResourceLocation location, int selectIndexX, int selectIndexY, int characterCount) {
        int selectingPixelsX = selectIndexX * (gridImageSize + border) + globalOffsetX + gridOffsetX - border;
        int selectingPixelsY = selectIndexY * (gridImageSize + border) + globalOffsetY + gridOffsetY - border;
        int selectingIndex = selectIndexX + selectIndexY * gridImageCountX;
        
        if (selectIndexX >= 0 && selectIndexX < gridImageCountX && selectIndexY >= 0 && selectIndexY < gridImageCountY && selectingIndex < characterCount) {
            graphics.blit(location, selectingPixelsX, selectingPixelsY, 0, 0, 24, 24, 24, 24);
        }
    }

    private int GetCurrentCharacter() {
        Player player = (Player)Minecraft.getInstance().getCameraEntity();
        CompoundTag data = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        return Integer.valueOf(data.getString("koog:skin"));
    }


}
