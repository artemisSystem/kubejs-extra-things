package artemis.kubejs_extra_things.util;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxelMath {
	public static VoxelShape rotateX(VoxelShape shape) {
		// IntelliJ told me i had to wrap this in an object, lmk if you know a better way to do this
		var buffer = new Object() {
			VoxelShape returnShape = Shapes.empty();
		};
		shape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> buffer.returnShape =
						Shapes.or(buffer.returnShape, Shapes.create(x1, z1,1 - y2, x2, z2, 1 - y1)));
		return buffer.returnShape;
	}

	public static VoxelShape rotateZ(VoxelShape shape) {
		var buffer = new Object() {
			VoxelShape returnShape = Shapes.empty();
		};
		shape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> buffer.returnShape =
						Shapes.or(buffer.returnShape, Shapes.create(y1, 1 - x2, z1, y2, 1 - x1, z2)));
		return buffer.returnShape;
	}
}
