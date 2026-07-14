package dev.VeeBee2570.koog;

import org.stringtemplate.v4.compiler.STParser.compoundElement_return;

import net.minecraft.client.ComponentCollector;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;

public class CharacterButton extends ImageButton {


    public CharacterButton(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation textureLocation, int textureWidth, int textureHeight, OnPress onPress) {
            super(x, y, width, height, xTexStart, yTexStart, yDiffTex, textureLocation, textureWidth, textureHeight, onPress);
    }
    
}
