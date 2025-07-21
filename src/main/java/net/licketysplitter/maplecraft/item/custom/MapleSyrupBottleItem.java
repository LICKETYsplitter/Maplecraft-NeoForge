package net.licketysplitter.maplecraft.item.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public class MapleSyrupBottleItem extends Item {

    private static final int DRINK_DURATION = 40;

    public MapleSyrupBottleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        super.finishUsingItem(pStack, pLevel, pEntityLiving);
        if (pEntityLiving instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, pStack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!pLevel.isClientSide) {
            pEntityLiving.removeEffect(MobEffects.BAD_OMEN);
        }

        if (pStack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (pEntityLiving instanceof Player && !((Player) pEntityLiving).getAbilities().instabuild) {
                ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
                Player player = (Player) pEntityLiving;
                if (!player.getInventory().add(itemstack)) {
                    player.drop(itemstack, false);
                }
            }

            return pStack;
        }
    }
}
