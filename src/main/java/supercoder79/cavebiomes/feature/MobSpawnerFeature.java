package supercoder79.cavebiomes.feature;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class MobSpawnerFeature extends Feature<DefaultFeatureConfig> {
	private final EntityType<?>[] types;
	private final int maxY;

	public MobSpawnerFeature(int maxY, EntityType<?>... types) {
		super(DefaultFeatureConfig.CODEC);

		this.types = types;
		this.maxY = maxY;
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random rand, BlockPos origin, DefaultFeatureConfig config) {
		BlockPos.Mutable mutPos = new BlockPos.Mutable(origin.getX(), rand.nextInt(maxY - 5) + 5, origin.getZ());

		int attempts = 25;
		while (mutPos.getY() > 3 && attempts > 0) {

			if (world.getBlockState(mutPos).isAir() && world.getBlockState(mutPos.down()).getBlock().equals(Blocks.STONE)) {
				this.setBlockState(world, mutPos, Blocks.SPAWNER.getDefaultState());

				BlockEntity entity = world.getBlockEntity(mutPos);
				if (entity instanceof MobSpawnerBlockEntity) {
					((MobSpawnerBlockEntity)entity).getLogic().setEntityId(this.chooseEntity(rand));
				}
				return true;
			}

			mutPos.setY(mutPos.getY() - 1);
			attempts--;
		}

		return false;
	}

	private EntityType<?> chooseEntity(Random rand) {
		return this.types[rand.nextInt(this.types.length)];
	}
}
