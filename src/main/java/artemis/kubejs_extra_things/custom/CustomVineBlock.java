package artemis.kubejs_extra_things.custom;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CustomVineBlock extends VineBlock implements MaybeWaterloggedBlock {

	public final BlockBuilder blockBuilder;

	public CustomVineBlock(BlockBuilder builder) {
		super(builder.createProperties());
		this.blockBuilder = builder;
		if (this.blockBuilder.waterlogged) {
			// grab the registered state from VineBlock and add waterlogging
			// registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
	}
}
