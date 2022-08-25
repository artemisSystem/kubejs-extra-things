package artemis.kubejs_extra_things.custom;

import artemis.kubejs_extra_things.menu.CustomCraftingMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CustomCraftingTableBlock extends CraftingTableBlock {
	private final Component guiTitle;

	public CustomCraftingTableBlock(Properties properties, TranslatableComponent guiTitle) {
		super(properties);
		this.guiTitle = guiTitle;
	}

	@Override
	public MenuProvider getMenuProvider(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos) {
		return new SimpleMenuProvider((id, inventory, entity) -> new CustomCraftingMenu(id, inventory, ContainerLevelAccess.create(level, blockPos)), guiTitle);
	}
}