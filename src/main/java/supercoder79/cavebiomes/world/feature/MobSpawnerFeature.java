package supercoder79.cavebiomes.world.feature;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class MobSpawnerFeature extends Feature<DefaultFeatureConfig> {
	private final EntityType<?>[] types;
	private final int maxY;
	private final int consumption;

	public MobSpawnerFeature(int consumption, int maxY, EntityType<?>... types) {
		super(DefaultFeatureConfig.CODEC);

		this.types = types;
		this.maxY = maxY;
		this.consumption = consumption;
	}

	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		StructureWorldAccess world = context.getWorld();
		BlockPos pos = context.getOrigin();
		Random random = context.getRandom();

		((ChunkRandom) random).skip(this.consumption);
		int y = random.nextInt(this.maxY - 5) + 5;
		BlockPos.Mutable mutable = pos.mutableCopy();
		mutable.setY(y);

		int attempts = 25;
		while (mutable.getY() > 3 && attempts > 0) {

			if (world.getBlockState(mutable).isAir() && world.getBlockState(mutable.down()).getBlock().equals(Blocks.STONE)) {
				this.setBlockState(world, mutable, Blocks.SPAWNER.getDefaultState());

				BlockEntity entity = world.getBlockEntity(mutable);
				if (entity instanceof MobSpawnerBlockEntity) {
					((MobSpawnerBlockEntity)entity).getLogic().setEntityId(this.chooseEntity(random));
				}

				return true;
			}

			mutable.move(Direction.DOWN);
			attempts--;
		}

		return false;
	}

	private EntityType<?> chooseEntity(Random rand) {
		return this.types[rand.nextInt(this.types.length)];
	}
}
