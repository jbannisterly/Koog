package dev.VeeBee2570.koog;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class ProjectileFactory {
    public abstract Projectile get(Level level, Player player, Vec3 fireAngle, Vec3 startPosition);
}
