package net.licketysplitter.maplecraft.block.custom;

import net.licketysplitter.maplecraft.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.IShearable;

import javax.annotation.Nullable;

public class PileOfLeavesBlock extends Block implements IShearable {

    private static final int MAX_HEIGHT = 8;
    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
    protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{
            Shapes.empty(),
            Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    };

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return Shapes.empty();
    }

    public PileOfLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, Integer.valueOf(1)));
    }

    protected void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!pLevel.isClientSide) {
            if ((pEntity instanceof Player) && pEntity.mayInteract((ServerLevel) pLevel, pPos)) {
                pLevel.destroyBlock(pPos, false);
            }
        }
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_LAYER[pState.getValue(LAYERS)];
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return SHAPE_BY_LAYER[pState.getValue(LAYERS)];
    }

    @Override
    protected VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_LAYER[pState.getValue(LAYERS)];
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    protected float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.getValue(LAYERS) == 8 ? 0.2F : 1.0F;
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        return blockstate.is(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON) || (Block.isFaceFull(blockstate.getCollisionShape(pLevel, pPos.below()), Direction.UP)
                || blockstate.is(this) && blockstate.getValue(LAYERS) == 8);
    }

    @Override
    protected BlockState updateShape(
            BlockState state,
            LevelReader level,
            ScheduledTickAccess scheduledTickAccess,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            RandomSource random) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() :
                super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        int i = pState.getValue(LAYERS);
        if (!pUseContext.getItemInHand().is(this.asItem()) || i >= 8) {
            return i == 1;
        } else {
            return pUseContext.replacingClickedOnBlock() ? pUseContext.getClickedFace() == Direction.UP : true;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (blockstate.is(this)) {
            int i = blockstate.getValue(LAYERS);
            return blockstate.setValue(LAYERS, Integer.valueOf(Math.min(8, i + 1)));
        } else {
            return super.getStateForPlacement(pContext);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LAYERS);
    }
}
