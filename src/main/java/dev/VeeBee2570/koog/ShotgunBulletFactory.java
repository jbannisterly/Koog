package dev.VeeBee2570.koog;

import java.util.Random;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ShotgunBulletFactory extends ProjectileFactory {

    private static final double spread = 0.5;
    private static Random rand = new Random();

    @Override
    public Projectile get(Level level, Player player, Vec3 fireAngle, Vec3 startPosition) {
        Vec3 modifiedAngle = new Vec3(spread * (rand.nextDouble() - 0.5), spread * (rand.nextDouble() - 0.5), 0).add(fireAngle).normalize();
        return new Bullet(level, player, modifiedAngle, startPosition);
    }
    
}
