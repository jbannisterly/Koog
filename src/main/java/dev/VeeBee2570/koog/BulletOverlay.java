package dev.VeeBee2570.koog;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class BulletOverlay {
    public static final IGuiOverlay overlay = 
    (ForgeGui gui, GuiGraphics graphics, float partialTick, int width, int height) -> {
        Entity currentSpectating = gui.getMinecraft().getCameraEntity();
        
        if (currentSpectating instanceof Player) {
            Player currentPlayer = (Player)currentSpectating;
            Fireable currentFireable = null;

            Entity currentVehicle = currentPlayer.getVehicle();
            Item currentWeapon = currentPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem();

            if (currentVehicle instanceof Fireable) {
                currentFireable = (Fireable) currentVehicle;
            } else if (currentWeapon instanceof Fireable) {
                currentFireable = (Fireable) currentWeapon;
            }

            if (currentFireable != null){
                ResourceLocation icon = currentFireable.getBulletIcon();
                int bulletCount = currentFireable.getBulletCount(currentPlayer);
                int offsetX = width / 2 - 91;
                int offsetY = height - gui.leftHeight;
                
                for (int i = 0; i < bulletCount; i++) {
                    graphics.blit(icon, offsetX + i * 8, offsetY, 0, 0, 9, 9, 9, 9);
                }
            }

        }

    };
}
