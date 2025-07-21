package net.licketysplitter.maplecraft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MapleSyrupBlock extends HalfTransparentBlock {

    public MapleSyrupBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other)
    {
        return !(other.getBlock() == Blocks.SLIME_BLOCK || other.getBlock() == Blocks.HONEY_BLOCK);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        entity.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
        entity.causeFallDamage(fallDistance,0.0f, level.damageSources().fall());
    }
}
