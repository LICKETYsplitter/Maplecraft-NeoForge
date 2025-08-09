package net.licketysplitter.maplecraft.block.custom;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.effect.ModEffects;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PoisonIvyBlock extends GrowingPlantHeadBlock implements IShearable {
    public static final MapCodec<PoisonIvyBlock> CODEC = simpleCodec(PoisonIvyBlock::new);
    private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 3.0, 1.0, 14.0, 13.0);
    private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 3.0, 16.0, 14.0, 13.0);
    private static final VoxelShape NORTH_AABB = Block.box(3.0, 0.0, 0.0, 13.0, 14.0, 1.0);
    private static final VoxelShape SOUTH_AABB = Block.box(3.0, 0.0, 15.0, 13.0, 14.0, 16.0);
    private final Map<BlockState, VoxelShape> shapesCache;

    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION
            .entrySet()
            .stream()
            .filter(p_57886_ ->  p_57886_.getKey() != Direction.DOWN && p_57886_.getKey() != Direction.UP)
            .collect(Util.toMap());

    @Override
    public MapCodec<PoisonIvyBlock> codec() {
        return CODEC;
    }

    public PoisonIvyBlock(Properties p_154864_) {
        super(p_154864_, Direction.UP, NORTH_AABB, false, 0.1);

        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(NORTH, false)
                        .setValue(EAST, false)
                        .setValue(SOUTH, false)
                        .setValue(WEST, false));
        this.shapesCache = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), PoisonIvyBlock::calculateShape)));

    }

    private static VoxelShape calculateShape(BlockState p_57906_) {
        VoxelShape voxelshape = Shapes.empty();

        if (p_57906_.getValue(NORTH)) {
            voxelshape = Shapes.or(voxelshape, NORTH_AABB);
        }
        else if (p_57906_.getValue(SOUTH)) {
            voxelshape = Shapes.or(voxelshape, SOUTH_AABB);
        }
        else if (p_57906_.getValue(EAST)) {
            voxelshape = Shapes.or(voxelshape, EAST_AABB);
        }
        else if (p_57906_.getValue(WEST)) {
            voxelshape = Shapes.or(voxelshape, WEST_AABB);
        }

        return voxelshape.isEmpty() ? Shapes.block() : voxelshape;
    }

    private BlockState getUpdatedState(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.above();

        BlockState blockstate = null;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BooleanProperty booleanproperty = getPropertyForFace(direction);
            if (pState.getValue(booleanproperty)) {
                boolean flag = this.canSupportAtFace(pLevel, pPos, direction);
                if (!flag) {
                    if (blockstate == null) {
                        blockstate = pLevel.getBlockState(blockpos);
                    }

                    flag = blockstate.is(this) && blockstate.getValue(booleanproperty);
                }

                pState = pState.setValue(booleanproperty, Boolean.valueOf(flag));
            }
        }

        return pState;
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockState = this.getUpdatedState(pState, pLevel, pPos);
        BlockPos belowPos = pPos.below();
        BlockState belowState = pLevel.getBlockState(belowPos);
        return canSurviveOn(pLevel, belowPos, belowState ) && this.hasFaces(blockState);
    }

    private boolean canSurviveOn(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return pState.isFaceSturdy(pLevel, pPos, Direction.UP) ||
                pState.is(ModBlocks.POISON_IVY.get()) || pState.is(ModBlocks.POISON_IVY_PLANT.get());
    }

    private boolean hasFaces(BlockState pState) {
        return this.countFaces(pState) > 0;
    }

    private int countFaces(BlockState pState) {
        int i = 0;

        for (BooleanProperty booleanproperty : PROPERTY_BY_DIRECTION.values()) {
            if (pState.getValue(booleanproperty)) {
                i++;
            }
        }

        return i;
    }

    public static BooleanProperty getPropertyForFace(Direction pFace) {
        return PROPERTY_BY_DIRECTION.get(pFace);
    }

    public static boolean isAcceptableNeighbour(BlockGetter pBlockReader, BlockPos pNeighborPos, Direction pAttachedFace) {
        return MultifaceBlock.canAttachTo(pBlockReader, pAttachedFace, pNeighborPos, pBlockReader.getBlockState(pNeighborPos));
    }

    private boolean canSupportAtFace(BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        if (pDirection == Direction.DOWN || pDirection == Direction.UP) {
            return false;
        } else {
            BlockPos blockpos = pPos.relative(pDirection);
            if (isAcceptableNeighbour(pLevel, blockpos, pDirection)) {
                return true;
            } else if (pDirection.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BooleanProperty booleanproperty = PROPERTY_BY_DIRECTION.get(pDirection);
                BlockState blockstate = pLevel.getBlockState(pPos.above());
                return blockstate.is(this) && blockstate.getValue(booleanproperty);
            }
        }
    }

    @Override
    protected boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        BlockState blockstate = pUseContext.getLevel().getBlockState(pUseContext.getClickedPos());
        return blockstate.is(this) ? this.countFaces(blockstate) < PROPERTY_BY_DIRECTION.size() : super.canBeReplaced(pState, pUseContext);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        boolean flag = blockstate.is(this);
        BlockState blockstate1 = flag ? blockstate : this.defaultBlockState();
        boolean flag2 =  true;
        if(blockstate.is(ModBlocks.POISON_IVY.get()) || blockstate.is(ModBlocks.POISON_IVY_PLANT.get())) {
            flag2 = !(blockstate.getValue(NORTH).booleanValue() || blockstate.getValue(SOUTH).booleanValue() ||
                    blockstate.getValue(EAST).booleanValue() || blockstate.getValue(WEST).booleanValue());
        }

        for (Direction direction : pContext.getNearestLookingDirections()) {
            if (direction != Direction.DOWN && direction != Direction.UP ) {
                BooleanProperty booleanproperty = getPropertyForFace(direction);
                boolean flag1 = flag && blockstate.getValue(booleanproperty);
                if (!flag1 && this.canSupportAtFace(pContext.getLevel(), pContext.getClickedPos(), direction) && flag2) {
                    return blockstate1.setValue(booleanproperty, Boolean.valueOf(true));
                }
            }
        }

        return flag ? blockstate1 : null;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.shapesCache.get(pState);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource pRandom) {
        double d0 = 1.0;

        int i;
        for (i = 0; pRandom.nextDouble() < d0; i++) {
            d0 *= 0.826;
        }

        return i;
    }

    @Override
    protected Block getBodyBlock() {
        return ModBlocks.POISON_IVY_PLANT.get();
    }

    @Override
    protected boolean canGrowInto(BlockState blockState) {
        return blockState.is(Blocks.AIR);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, EAST, SOUTH, WEST);
        pBuilder.add(AGE);
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
        if (direction == Direction.UP || direction == Direction.DOWN) {
            return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
        } else {
            BlockState blockstate = this.getUpdatedState(state, level, pos);
            return !this.hasFaces(blockstate) ? Blocks.AIR.defaultBlockState() : blockstate;
        }
    }

    @Override
    protected BlockState updateBodyAfterConvertedFromHead(BlockState pHead, BlockState pBody) {
        return pBody.setValue(NORTH, pHead.getValue(NORTH))
                .setValue(SOUTH, pHead.getValue(SOUTH))
                .setValue(EAST, pHead.getValue(EAST))
                .setValue(WEST, pHead.getValue(WEST));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(!player.getMainHandItem().is(Tags.Items.TOOLS_SHEAR)) {
            if(player.hasEffect(ModEffects.ITCHY_EFFECT)){
                player.addEffect(new MobEffectInstance(ModEffects.ITCHY_EFFECT,
                        Math.min((player.getEffect(ModEffects.ITCHY_EFFECT).getDuration() + 6000), 36000), 0));
            }
            else{
                player.addEffect(new MobEffectInstance(ModEffects.ITCHY_EFFECT, 6000, 0));
            }
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        if(entity instanceof Player){
            Player player = (Player) entity;
            player.addEffect(new MobEffectInstance(ModEffects.ITCHY_EFFECT, 36000, 0));
        }
    }
}