package dev.VeeBee2570.koog;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Gun extends Item implements Fireable{

    private ProjectileFactory bulletFactory;
    private int bulletQuantity = 1;

    public Gun(Properties p_41383_) {
        super(p_41383_);
    }

    public void Fire(Level level, Player player) {
        ItemStack currentStack = player.getMainHandItem();
        int bulletCount = ReadBulletCount(currentStack); 

        if (bulletCount > 0) {
            for (int i = 0; i < bulletQuantity; i++) {
                Projectile bullet = bulletFactory.get(level, player, player.getLookAngle(), new Vec3(player.getX(), player.getY(0.5), player.getZ()));
                level.addFreshEntity(bullet);
            }
            bulletCount--;
        }

        SetBulletCount(currentStack, bulletCount);
    }

    public Gun SetBulletFactory(ProjectileFactory bulletFactory) {
        this.bulletFactory = bulletFactory;
        return this;
    }

    public Gun SetBulletQuantity(int quantity) {
        bulletQuantity = quantity;
        return this;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        this.Fire(level, player);
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @Override
    public ResourceLocation getBulletIcon() {
        return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/sprites/hud/food_full.png");
    }   

    @Override
    public int getBulletCount(Player player) {
        ItemStack currentStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        int bulletCount = ReadBulletCount(currentStack); 

        return bulletCount;
    }

    private int ReadBulletCount(ItemStack currentStack) {
        CompoundTag tag = currentStack.getOrCreateTag();
        if (!tag.contains("bulletCount")) {
            tag.putInt("bulletCount", 10);
        }

        return tag.getInt("bulletCount");
    }

    private void SetBulletCount(ItemStack currentStack, int value) {
        CompoundTag tag = currentStack.getOrCreateTag();

        tag.putInt("bulletCount", value);
    }
}

