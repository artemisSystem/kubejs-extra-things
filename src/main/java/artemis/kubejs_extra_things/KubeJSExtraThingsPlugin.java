package artemis.kubejs_extra_things;

import artemis.kubejs_extra_things.builder.*;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.RegistryObjectBuilderTypes;

public class KubeJSExtraThingsPlugin extends KubeJSPlugin {
	@Override
	public void init() {
		RegistryObjectBuilderTypes.BLOCK.addType("crafting_table", CraftingTableBlockBuilder.class, CraftingTableBlockBuilder::new);
		RegistryObjectBuilderTypes.BLOCK.addType("rotated_pillar", RotatedPillarBlockBuilder.class, RotatedPillarBlockBuilder::new);
		RegistryObjectBuilderTypes.BLOCK.addType("leaves", LeavesBlockBuilder.class, LeavesBlockBuilder::new);
		RegistryObjectBuilderTypes.BLOCK.addType("mushroom_block", HugeMushroomBlockBuilder.class, HugeMushroomBlockBuilder::new);
		RegistryObjectBuilderTypes.BLOCK.addType("decaying_mushroom_block", DecayingMushroomBlockBuilder.class, DecayingMushroomBlockBuilder::new);
		RegistryObjectBuilderTypes.BLOCK.addType("beehive", BeeHiveBlockBuilder.class, BeeHiveBlockBuilder::new);
		RegistryObjectBuilderTypes.BLOCK.addType("vertical_slab", VerticalSlabBlockBuilder.class, VerticalSlabBlockBuilder::new);
		RegistryObjectBuilderTypes.BLOCK.addType("vine", VineBlockBuilder.class, VineBlockBuilder::new);
	}
}