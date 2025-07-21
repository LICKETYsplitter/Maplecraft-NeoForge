package net.licketysplitter.maplecraft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SawmillBlock extends Block {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final VoxelShape BASE = Block.box(0,0,0, 16, 5, 16);
    public static final VoxelShape BLADE_NORTH_SOUTH = Block.box(1, 5, 6, 15, 15, 10 );
    public static final VoxelShape BLADE_EAST_WEST = Block.box(6, 5, 1, 10, 15, 15 );
    public static final VoxelShape MILL_NORTH_SOUTH = Shapes.or(BASE, BLADE_NORTH_SOUTH);
    public static final VoxelShape MILL_EAST_WEST = Shapes.or(BASE, BLADE_EAST_WEST);


    public SawmillBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH));
    }

    private VoxelShape getVoxelShape(BlockState blockState){
        return blockState.getValue(FACING) == Direction.NORTH || blockState.getValue(FACING) == Direction.SOUTH ?
                MILL_NORTH_SOUTH : MILL_EAST_WEST;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getVoxelShape(pState);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getVoxelShape(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }
}
