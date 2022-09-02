package artemis.kubejs_extra_things.custom;

import dev.latvian.mods.kubejs.BuilderBase;
import dev.latvian.mods.kubejs.RegistryObjectBuilderTypes;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.block.custom.BasicBlockJS;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomRotatedPillarBlock extends BasicBlockJS implements MaybeWaterloggedBlock {
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public CustomRotatedPillarBlock(BlockBuilder builder) {
		super(builder);
		BlockState defaultState = this.defaultBlockState().setValue(AXIS, Direction.Axis.Y);
		if (this.blockBuilder.waterlogged) {
			defaultState.setValue(BlockStateProperties.WATERLOGGED, false);
		}
		this.registerDefaultState(defaultState);
	}

	public BlockState rotate(BlockState blockState, Rotation rotation) {
		return rotatePillar(blockState, rotation);
	}

	public static BlockState rotatePillar(BlockState blockState, Rotation rotation) {
		return switch (rotation) {
			case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (blockState.getValue(AXIS)) {
				case X -> blockState.setValue(AXIS, Direction.Axis.Z);
				case Z -> blockState.setValue(AXIS, Direction.Axis.X);
				default -> blockState;
			};
			default -> blockState;
		};
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
		BuilderBase<? extends Block> builderBase = RegistryObjectBuilderTypes.BLOCK.getCurrent();
		if (builderBase instanceof BlockBuilder current) {
			if (current.waterlogged) {
				builder.add(WATERLOGGED);
			}
		}
	}

	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		BlockState basicState = super.getStateForPlacement(blockPlaceContext);
		return basicState.setValue(AXIS, blockPlaceContext.getClickedFace().getAxis());
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		VoxelShape baseShape = super.getShape(blockState, blockGetter, blockPos, collisionContext);
		return baseShape;
	}
}
