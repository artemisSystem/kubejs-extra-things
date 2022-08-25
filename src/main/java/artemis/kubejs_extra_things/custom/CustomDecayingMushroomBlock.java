package artemis.kubejs_extra_things.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Map;
import java.util.Objects;

public class CustomDecayingMushroomBlock extends LeavesBlock {
	private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION;

	public CustomDecayingMushroomBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState()
			.setValue(DISTANCE, 7)
			.setValue(PERSISTENT, false)
			.setValue(HugeMushroomBlock.NORTH, true)
			.setValue(HugeMushroomBlock.EAST, true)
			.setValue(HugeMushroomBlock.SOUTH, true)
			.setValue(HugeMushroomBlock.WEST, true)
			.setValue(HugeMushroomBlock.UP, true)
			.setValue(HugeMushroomBlock.DOWN, true)
		);
	}

	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		BlockGetter blockGetter = blockPlaceContext.getLevel();
		BlockPos blockPos = blockPlaceContext.getClickedPos();

		// i don't think this will ever be null?? i hope?
		return Objects.requireNonNull(super.getStateForPlacement(blockPlaceContext))
			.setValue(HugeMushroomBlock.DOWN, !blockGetter.getBlockState(blockPos.below()).is(this))
			.setValue(HugeMushroomBlock.UP, !blockGetter.getBlockState(blockPos.above()).is(this))
			.setValue(HugeMushroomBlock.NORTH, !blockGetter.getBlockState(blockPos.north()).is(this))
			.setValue(HugeMushroomBlock.EAST, !blockGetter.getBlockState(blockPos.east()).is(this))
			.setValue(HugeMushroomBlock.SOUTH, !blockGetter.getBlockState(blockPos.south()).is(this))
			.setValue(HugeMushroomBlock.WEST, !blockGetter.getBlockState(blockPos.west()).is(this));
	}

	public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		BlockState newBlockState = super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
		return blockState2.is(this) ? newBlockState.setValue(PROPERTY_BY_DIRECTION.get(direction), false) : newBlockState;
	}

	public BlockState rotate(BlockState blockState, Rotation rotation) {
		return blockState
			.setValue(PROPERTY_BY_DIRECTION.get(rotation.rotate(Direction.NORTH)), blockState.getValue(HugeMushroomBlock.NORTH))
			.setValue(PROPERTY_BY_DIRECTION.get(rotation.rotate(Direction.SOUTH)), blockState.getValue(HugeMushroomBlock.SOUTH))
			.setValue(PROPERTY_BY_DIRECTION.get(rotation.rotate(Direction.EAST)), blockState.getValue(HugeMushroomBlock.EAST))
			.setValue(PROPERTY_BY_DIRECTION.get(rotation.rotate(Direction.WEST)), blockState.getValue(HugeMushroomBlock.WEST))
			.setValue(PROPERTY_BY_DIRECTION.get(rotation.rotate(Direction.UP)), blockState.getValue(HugeMushroomBlock.UP))
			.setValue(PROPERTY_BY_DIRECTION.get(rotation.rotate(Direction.DOWN)), blockState.getValue(HugeMushroomBlock.DOWN));
	}

	public BlockState mirror(BlockState blockState, Mirror mirror) {
		return blockState
			.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.NORTH)), blockState.getValue(HugeMushroomBlock.NORTH))
			.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.SOUTH)), blockState.getValue(HugeMushroomBlock.SOUTH))
			.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.EAST)), blockState.getValue(HugeMushroomBlock.EAST))
			.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.WEST)), blockState.getValue(HugeMushroomBlock.WEST))
			.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.UP)), blockState.getValue(HugeMushroomBlock.UP))
			.setValue(PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.DOWN)), blockState.getValue(HugeMushroomBlock.DOWN));
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HugeMushroomBlock.UP, HugeMushroomBlock.DOWN, HugeMushroomBlock.NORTH, HugeMushroomBlock.EAST, HugeMushroomBlock.SOUTH, HugeMushroomBlock.WEST);
	}

	static { PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION; }
}
