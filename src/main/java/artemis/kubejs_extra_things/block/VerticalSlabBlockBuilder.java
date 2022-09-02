package artemis.kubejs_extra_things.block;

import artemis.kubejs_extra_things.custom.CustomVerticalSlabBlock;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class VerticalSlabBlockBuilder extends BlockBuilder {
	public VerticalSlabBlockBuilder(ResourceLocation id) {
		super(id);
		this.tagBoth(new ResourceLocation("c:vertical_slabs"));
	}

	@Override
	public Block createObject() {
		return new CustomVerticalSlabBlock(this);
	}

	@Override
	public void generateAssetJsons(AssetJsonGenerator generator) {
		generator.blockState(id, stateGen -> {
				String slabModel = newID("block/", "").toString();
				String fullModel = newID("block/", "_double").toString();

				stateGen.variant("type=double,axis=x", v -> v.model(fullModel));
				stateGen.variant("type=double,axis=z", v -> v.model(fullModel));
				stateGen.variant("type=top,axis=z", v -> v.model(slabModel));
				stateGen.variant("type=bottom,axis=x", v -> v.model(slabModel).y(90).uvlock());
				stateGen.variant("type=bottom,axis=z", v -> v.model(slabModel).y(180).uvlock());
				stateGen.variant("type=top,axis=x", v -> v.model(slabModel).y(270).uvlock());
		});

		final String texture = textures.get("texture").getAsString();

		generator.blockModel(id, modelGen -> {
			modelGen.parent("kubejs_extra_things:block/vertical_slab");
			modelGen.texture("side", texture);
			modelGen.texture("end", texture);
		});

		generator.blockModel(newID("", "_double"), modelGen -> {
			modelGen.parent("minecraft:block/cube_all");
			modelGen.texture("all", texture);
		});

		generator.itemModel(itemBuilder.id, modelGen -> modelGen.parent(newID("block/", "").toString()));
	}
}
