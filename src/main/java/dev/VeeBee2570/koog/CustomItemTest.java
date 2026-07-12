package dev.VeeBee2570.koog;

import org.joml.Math;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CustomItemTest extends Item{

    public CustomItemTest(Properties p_41383_) {
        super(p_41383_);
    }

    BowItem bow;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ExampleMod.LOGGER.info("Item used");

        Grenade grenade = new Grenade(level, player, player.getLookAngle(), new Vec3(player.getX(), player.getY(), player.getZ()));

        ExampleMod.LOGGER.info("grenade object created");

        PrimedTnt tnt = new PrimedTnt(level, player.getX(), player.getY(), player.getZ(), player);
        tnt.setDeltaMovement(player.getLookAngle().multiply(3, 3, 3));

        // level.addFreshEntity(tnt);
        level.addFreshEntity(grenade);

        ExampleMod.LOGGER.info("entity added");

        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
}
