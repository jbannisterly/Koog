package dev.VeeBee2570.koog;

import org.joml.Math;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Gun extends Item{

    public Gun(Properties p_41383_) {
        super(p_41383_);
    }

    public void Fire(Level level, Player player) {
        Grenade grenade = new Grenade(level, player, player.getLookAngle(), new Vec3(player.getX(), player.getY(0.5), player.getZ()));
        level.addFreshEntity(grenade);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        this.Fire(level, player);
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
}
