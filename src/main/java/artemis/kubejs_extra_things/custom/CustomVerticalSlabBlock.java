package artemis.kubejs_extra_things.custom;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.block.custom.BasicBlockJS;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CustomVerticalSlabBlock extends BasicBlockJS implements SimpleWaterloggedBlock {
	public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
	protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_AABB = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	public CustomVerticalSlabBlock(BlockBuilder builder) {
		super(builder);
		this.registerDefaultState(this.defaultBlockState()
			.setValue(TYPE, SlabType.BOTTOM)
			.setValue(AXIS, Direction.Axis.X)
			.setValue(WATERLOGGED, false)
		);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(TYPE, AXIS, WATERLOGGED);
	}

	public static BlockState setStateToDirection(Direction dir, BlockState state) {
		boolean isDouble = state.getValue(TYPE) == SlabType.DOUBLE;
		return switch (dir) {
			case NORTH -> state.setValue(AXIS, Direction.Axis.Z).setValue(TYPE, isDouble ? SlabType.DOUBLE : SlabType.BOTTOM);
			case SOUTH -> state.setValue(AXIS, Direction.Axis.Z).setValue(TYPE, isDouble ? SlabType.DOUBLE : SlabType.TOP);
			case WEST -> state.setValue(AXIS, Direction.Axis.X).setValue(TYPE, isDouble ? SlabType.DOUBLE : SlabType.BOTTOM);
			case EAST -> state.setValue(AXIS, Direction.Axis.X).setValue(TYPE, isDouble ? SlabType.DOUBLE : SlabType.TOP);
			default -> state;
		};
	}

	public static Direction getDirectionFromState(BlockState state) {
		Direction axisDirection = switch (state.getValue(AXIS)) {
			case X -> Direction.WEST;
			case Z -> Direction.NORTH;
			// this can't happen but java doesn't know that :(
			case Y -> Direction.DOWN;
		};
		return state.getValue(TYPE) == SlabType.TOP ? axisDirection.getOpposite() : axisDirection;
	}


	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return setStateToDirection(rot.rotate(getDirectionFromState(state)), state);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return setStateToDirection(mirror.mirror(getDirectionFromState(state)), state);
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return state.getValue(TYPE) != SlabType.DOUBLE;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		if (state.getValue(TYPE) == SlabType.DOUBLE) {
			return Shapes.block();
		}
		return switch (getDirectionFromState(state)) {
			case NORTH -> NORTH_AABB;
			case SOUTH -> SOUTH_AABB;
			case WEST -> WEST_AABB;
			case EAST -> EAST_AABB;
			// impossible
			case DOWN, UP -> Shapes.block();
		};
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		BlockPos clickedPos = blockPlaceContext.getClickedPos();
		BlockState clickedPosState = blockPlaceContext.getLevel().getBlockState(clickedPos);
		if (clickedPosState.is(this)) {
			return clickedPosState.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, false);
		} else {
			FluidState fluidState = blockPlaceContext.getLevel().getFluidState(clickedPos);
			BlockState state = this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
			Direction direction = blockPlaceContext.getHorizontalDirection();
			return setStateToDirection(direction, state);
		}
	}

	@Override
	public boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
		ItemStack itemStack = blockPlaceContext.getItemInHand();
		SlabType slabType = blockState.getValue(TYPE);
		if (slabType != SlabType.DOUBLE && itemStack.is(this.asItem())) {
			if (blockPlaceContext.replacingClickedOnBlock()) {
				//return false;
				boolean bl = blockPlaceContext.getClickLocation().y - (double)blockPlaceContext.getClickedPos().getY() > 0.5D;
				Direction direction = blockPlaceContext.getClickedFace();
				if (slabType == SlabType.BOTTOM) {
					return direction == Direction.UP || bl && direction.getAxis().isHorizontal();
				} else {
					return direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal();
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
		return blockState.getValue(TYPE) != SlabType.DOUBLE && SimpleWaterloggedBlock.super.placeLiquid(levelAccessor, blockPos, blockState, fluidState);
	}

	public boolean canPlaceLiquid(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
		return blockState.getValue(TYPE) != SlabType.DOUBLE && SimpleWaterloggedBlock.super.canPlaceLiquid(blockGetter, blockPos, blockState, fluid);
	}

	@Override
	public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
		return pathComputationType == PathComputationType.WATER && blockGetter.getFluidState(blockPos).is(FluidTags.WATER);
	}
}
