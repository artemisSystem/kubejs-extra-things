package artemis.kubejs_extra_things.builder;

import artemis.kubejs_extra_things.custom.CustomCraftingTableBlock;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class CraftingTableBlockBuilder extends BlockBuilder {
	public CraftingTableBlockBuilder(ResourceLocation id) {
		super(id);
	}

	@Override
	public Block createObject() {
		return new CustomCraftingTableBlock(createProperties(), new TranslatableComponent("container.crafting." + id.toString()));
	}
}
