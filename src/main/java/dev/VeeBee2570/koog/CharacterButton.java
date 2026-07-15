package dev.VeeBee2570.koog;

import org.stringtemplate.v4.compiler.STParser.compoundElement_return;

import net.minecraft.client.ComponentCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;

public class CharacterButton extends ImageButton {
    private String identifier;

    public CharacterButton(String identifier, int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation textureLocation, int textureWidth, int textureHeight, OnPress onPress) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, textureLocation, textureWidth, textureHeight, onPress);
        this.identifier = identifier;
    }

    public void SelectCharacter() {
        NetworkMessages.channel.sendToServer(new PacketChangeCharacterServer(Integer.parseInt(identifier)));
    }
    
}
