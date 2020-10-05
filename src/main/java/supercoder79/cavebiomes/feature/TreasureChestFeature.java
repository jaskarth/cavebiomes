package supercoder79.cavebiomes.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.cavebiomes.loot.TreasureLootTables;

public class TreasureChestFeature extends Feature<DefaultFeatureConfig> {
	private final Type type;

	public TreasureChestFeature(TreasureChestFeature.Type type) {
		super(DefaultFeatureConfig.CODEC);

		this.type = type;
	}

	public static enum Type {
		// overworld
		CAVERN(63, Blocks.STONE, TreasureLootTables.CAVERN_CHEST),
		SPELUNKERS(38, Blocks.STONE, TreasureLootTables.SPELUNKERS_CHEST),
		// nether
		NETHER(100, Blocks.NETHERRACK, TreasureLootTables.NETHER_CHEST);

		public final int maxY;
		public final Block on;
		public final Identifier loot;

		private Type(int my, Block b, Identifier lootTable) {
			maxY = my;
			on = b;
			loot = lootTable;
		}
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random rand, BlockPos origin, DefaultFeatureConfig config) {
		int y = rand.nextInt(type.maxY - 5) + 5;
		BlockPos.Mutable mutPos = new BlockPos.Mutable(origin.getX(), y, origin.getZ());

		int attempts = 25;
		while (mutPos.getY() > 3 && attempts > 0) {

			if (world.getBlockState(mutPos).isAir() && world.getBlockState(mutPos.down()).getBlock().equals(type.on)) {
				this.setBlockState(world, mutPos, Blocks.CHEST.getDefaultState());

				BlockEntity entity = world.getBlockEntity(mutPos);
				if (entity instanceof ChestBlockEntity) {
					((ChestBlockEntity)entity).setLootTable(type.loot, rand.nextLong());
				}
				return true;
			}

			mutPos.setY(--y);
			attempts--;
		}

		return false;
	}
}
