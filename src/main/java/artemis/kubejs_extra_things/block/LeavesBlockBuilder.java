package artemis.kubejs_extra_things.block;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;

public class LeavesBlockBuilder extends BlockBuilder {
	public LeavesBlockBuilder(ResourceLocation id) {
		super(id);
		notSolid();
		viewBlocking(false);
		suffocating(false);
		redstoneConductor(false);
		opaque(false);
		transparent(true);
	}

	@Override
	public Block createObject() {
		return new LeavesBlock(createProperties().randomTicks());
	}
}
