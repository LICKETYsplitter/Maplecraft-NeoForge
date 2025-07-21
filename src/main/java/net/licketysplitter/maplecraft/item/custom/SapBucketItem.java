package net.licketysplitter.maplecraft.item.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class SapBucketItem extends Item {
    private static final int DRINK_DURATION = 32;

    public SapBucketItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        if (pEntityLiving instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, pStack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (pEntityLiving instanceof Player player) {
            return ItemUtils.createFilledResult(pStack, player, new ItemStack(Items.BUCKET), false);
        } else {
            pStack.consume(1, pEntityLiving);
            return pStack;
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
        return 32;
    }
}
