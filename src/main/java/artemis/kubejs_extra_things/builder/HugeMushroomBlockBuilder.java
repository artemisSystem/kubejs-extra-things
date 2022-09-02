package artemis.kubejs_extra_things.builder;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HugeMushroomBlock;

public class HugeMushroomBlockBuilder extends BlockBuilder {

	public HugeMushroomBlockBuilder(ResourceLocation id) {
		super(id);
		texture("outside", id.getNamespace() + ":block/" + id.getPath());
		texture("inside", id.getNamespace() + ":block/" + id.getPath() + "_inside");
	}

	@Override
	public Block createObject() {
		return new HugeMushroomBlock(createProperties());
	}

	@Override
	public void generateAssetJsons(AssetJsonGenerator generator) {
		generator.multipartState(id, stateGen -> {
			var outsideModel = newID("block/", "").toString();
			var insideModel = newID("block/", "_inside").toString();

			stateGen.part("north=true", v -> v.model(outsideModel));
			stateGen.part("north=false", v -> v.model(insideModel));
			stateGen.part("east=true", v -> v.model(outsideModel).y(90).uvlock());
			stateGen.part("east=false", v -> v.model(insideModel).y(90).uvlock());
			stateGen.part("south=true", v -> v.model(outsideModel).y(180).uvlock());
			stateGen.part("south=false", v -> v.model(insideModel).y(180).uvlock());
			stateGen.part("west=true", v -> v.model(outsideModel).y(270).uvlock());
			stateGen.part("west=false", v -> v.model(insideModel).y(270).uvlock());
			stateGen.part("up=true", v -> v.model(outsideModel).x(270).uvlock());
			stateGen.part("up=false", v -> v.model(insideModel).x(270).uvlock());
			stateGen.part("down=true", v -> v.model(outsideModel).x(90).uvlock());
			stateGen.part("down=false", v -> v.model(insideModel).x(90).uvlock());
		});

		final var outsideTexture = textures.get("outside").getAsString();
		final var insideTexture = textures.get("inside").getAsString();

		generator.blockModel(id, modelGen -> {
			modelGen.parent("minecraft:block/template_single_face");
			modelGen.texture("texture", outsideTexture);
		});

		generator.blockModel(newID("", "_inside"), modelGen -> {
			modelGen.parent("minecraft:block/template_single_face");
			modelGen.texture("texture", insideTexture);
		});

		generator.blockModel(newID("", "_inventory"), modelGen -> {
			modelGen.parent("minecraft:block/cube_all");
			modelGen.texture("all", outsideTexture);
		});

		generator.itemModel(itemBuilder.id, modelGen -> modelGen.parent(newID("block/", "_inventory").toString()));
	}
}
