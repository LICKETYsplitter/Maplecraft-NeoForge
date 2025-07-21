package net.licketysplitter.maplecraft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MudBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SinkingMud extends MudBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
    public SinkingMud(Properties p_221545_) {
        super(p_221545_);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        if (!(entity instanceof LivingEntity) || entity.getInBlockState().is(this)) {
            entity.makeStuckInBlock(state, new Vec3(0.9F, 1.5, 0.9F));
            if (level.isClientSide) {
                RandomSource randomsource = level.getRandom();
                boolean flag = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (flag && randomsource.nextBoolean()) {
                    level.addParticle(
                            ParticleTypes.MYCELIUM,
                            entity.getX(),
                            (double)(pos.getY() + 1),
                            entity.getZ(),
                            (double)(Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F),
                            0.05F,
                            (double)(Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F)
                    );
                }
            }
        }
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState p_221561_, BlockGetter p_221562_, BlockPos p_221563_, CollisionContext p_221564_) {
        return SHAPE;
    }
}
