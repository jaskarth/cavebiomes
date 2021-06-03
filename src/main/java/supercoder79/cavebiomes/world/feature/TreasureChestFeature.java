package supercoder79.cavebiomes.world.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import supercoder79.cavebiomes.util.TreasureLootTables;

import java.util.Random;
import java.util.function.Predicate;

public class TreasureChestFeature extends Feature<DefaultFeatureConfig> {
	private final Type type;
	private final int consumption;

	public TreasureChestFeature(int consumption, TreasureChestFeature.Type type) {
		super(DefaultFeatureConfig.CODEC);

		this.type = type;
		this.consumption = consumption;
	}

	//TODO: use block tags instead of block checks
	public enum Type {
		// overworld
		CAVERN(63, bs -> bs.getMaterial() == Material.STONE || bs.getBlock() == Blocks.DIRT, TreasureLootTables.CAVERN_CHEST),
		SPELUNKERS(38, bs -> bs.getMaterial() == Material.STONE || bs.getBlock() == Blocks.DIRT, TreasureLootTables.SPELUNKERS_CHEST),
		// nether
		NETHER(100, bs -> {
			Block b = bs.getBlock();
			return b == Blocks.NETHERRACK || b == Blocks.SOUL_SOIL || b == Blocks.SOUL_SAND || b == Blocks.GRAVEL || b == Blocks.WARPED_NYLIUM || b == Blocks.CRIMSON_NYLIUM;
		}, TreasureLootTables.NETHER_CHEST);

		public final int maxY;
		public final Predicate<BlockState> canGenerateOn;
		public final Identifier loot;

		Type(int maxY, Predicate<BlockState> canGenerateOn, Identifier lootTable) {
			this.maxY = maxY;
			this.canGenerateOn = canGenerateOn;
			this.loot = lootTable;
		}
	}

	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		StructureWorldAccess world = context.getWorld();
		BlockPos pos = context.getOrigin();
		ChunkGenerator chunkGenerator = context.getGenerator();
		Random random = context.getRandom();

		((ChunkRandom) random).skip(this.consumption);
		int y = random.nextInt(this.type.maxY - 5) + 5;
		BlockPos.Mutable mutable = pos.mutableCopy();
		mutable.setY(y);

		int attempts = 25;
		while (mutable.getY() > 3 && attempts > 0) {
			if (world.getBlockState(mutable).isAir() && type.canGenerateOn.test(world.getBlockState(mutable.down()))) {
				this.setBlockState(world, mutable, Blocks.CHEST.getDefaultState());

				BlockEntity entity = world.getBlockEntity(mutable);
				if (entity instanceof ChestBlockEntity) {
					((ChestBlockEntity)entity).setLootTable(type.loot, random.nextLong());
				}
				return true;
			}

			mutable.move(Direction.DOWN);
			attempts--;
		}

		return false;
	}
}
