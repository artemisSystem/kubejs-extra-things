package artemis.kubejs_extra_things.builder;

import artemis.kubejs_extra_things.custom.CustomVineBlock;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class VineBlockBuilder extends BlockBuilder {
	public VineBlockBuilder(ResourceLocation id) {
		super(id);
	}

	@Override
	public Block createObject() {
		return new CustomVineBlock(this);
	}
}
