package artemis.kubejs_extra_things.block;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;

public class RotatedPillarBlockBuilder extends BlockBuilder {
	public RotatedPillarBlockBuilder(ResourceLocation id) {
		super(id);
		texture("side", id.getNamespace() + ":block/" + id.getPath());
		texture("top", id.getNamespace() + ":block/" + id.getPath() + "_top");
	}

	@Override
	public Block createObject() {
		return new RotatedPillarBlock(createProperties());
	}

	@Override
	public void generateAssetJsons(AssetJsonGenerator generator) {
		generator.blockState(id, stateGen -> {
			String verticalModel = newID("block/", "").toString();
			String horizontalModel = newID("block/", "_horizontal").toString();

			stateGen.variant("axis=x", v -> v.model(horizontalModel).x(90).y(90));
			stateGen.variant("axis=y", v -> v.model(verticalModel));
			stateGen.variant("axis=z", v -> v.model(horizontalModel).x(90));
		});

		final var sideTexture = textures.get("side").getAsString();
		final var topTexture = textures.get("top").getAsString();

		generator.blockModel(id, modelGen -> {
			modelGen.parent("minecraft:block/cube_column");
			modelGen.texture("side", sideTexture);
			modelGen.texture("end", topTexture);
		});

		generator.blockModel(newID("", "_horizontal"), modelGen -> {
			modelGen.parent("minecraft:block/cube_column_horizontal");
			modelGen.texture("side", sideTexture);
			modelGen.texture("end", topTexture);
		});

		generator.itemModel(itemBuilder.id, modelGen -> modelGen.parent(newID("block/", "").toString()));
	}
}
