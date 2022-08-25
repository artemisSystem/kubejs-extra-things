package artemis.kubejs_extra_things.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

import javax.annotation.Nonnull;

public class CustomCraftingMenu extends CraftingMenu {

	public CustomCraftingMenu(int id, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
		super(id, inventory, containerLevelAccess);
	}

	@Override
	public boolean stillValid(@Nonnull Player player) {
		return true;
	}
}
