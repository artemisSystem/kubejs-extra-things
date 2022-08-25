package artemis.kubejs_extra_things.block;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;

public class BeeHiveBlockBuilder extends BlockBuilder {

	public BeeHiveBlockBuilder(ResourceLocation id) {
		super(id);
		texture("top", id.getNamespace() + ":block/" + id.getPath() + "_top");
		texture("bottom", id.getNamespace() + ":block/" + id.getPath() + "_bottom");
		texture("side", id.getNamespace() + ":block/" + id.getPath() + "_side");
		texture("front", id.getNamespace() + ":block/" + id.getPath() + "_front");
		texture("front_honey", id.getNamespace() + ":block/" + id.getPath() + "_front_honey");
	}

	@Override
	public Block createObject() {
		return new BeehiveBlock(createProperties());
	}
}
