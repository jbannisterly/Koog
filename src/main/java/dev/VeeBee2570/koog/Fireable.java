package dev.VeeBee2570.koog;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface Fireable {
    public ResourceLocation getBulletIcon();
    public int getBulletCount(Player player);
    public void Fire(Level level, Player player);
}
