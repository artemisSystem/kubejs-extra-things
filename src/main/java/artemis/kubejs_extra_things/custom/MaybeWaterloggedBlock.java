package artemis.kubejs_extra_things.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.Optional;

public interface MaybeWaterloggedBlock extends SimpleWaterloggedBlock {

	default boolean canPlaceLiquid(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
		return blockState.hasProperty(BlockStateProperties.WATERLOGGED) && !blockState.getValue(BlockStateProperties.WATERLOGGED) && fluid == Fluids.WATER;
	}

	default boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
		if (blockState.hasProperty(BlockStateProperties.WATERLOGGED) && !blockState.getValue(BlockStateProperties.WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
			if (!levelAccessor.isClientSide()) {
				levelAccessor.setBlock(blockPos, blockState.setValue(BlockStateProperties.WATERLOGGED, true), 3);
				levelAccessor.scheduleTick(blockPos, fluidState.getType(), fluidState.getType().getTickDelay(levelAccessor));
			}

			return true;
		} else {
			return false;
		}
	}

	default ItemStack pickupBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
		if (blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED)) {
			levelAccessor.setBlock(blockPos, blockState.setValue(BlockStateProperties.WATERLOGGED, false), 3);
			if (!blockState.canSurvive(levelAccessor, blockPos)) {
				levelAccessor.destroyBlock(blockPos, true);
			}

			return new ItemStack(Items.WATER_BUCKET);
		} else {
			return ItemStack.EMPTY;
		}
	}

	default Optional<SoundEvent> getPickupSound() {
		return Fluids.WATER.getPickupSound();
	}
}
