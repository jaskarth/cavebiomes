package supercoder79.cavebiomes.carver;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

public class PerlerpCarver extends BaseCarver {
    private OctavePerlinNoiseSampler caveNoise = new OctavePerlinNoiseSampler(new ChunkRandom(System.currentTimeMillis()), IntStream.rangeClosed(-5, 0));
    private OctavePerlinNoiseSampler offsetNoise = new OctavePerlinNoiseSampler(new ChunkRandom(System.currentTimeMillis() - 576), IntStream.rangeClosed(-2, 0));

    public PerlerpCarver(Codec<ProbabilityConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean carve(Chunk chunk, Function<BlockPos, Biome> posToBiome, Random random, int seaLevel, int chunkX, int chunkZ, int mainChunkX, int mainChunkZ, BitSet carvingMask, ProbabilityConfig carverConfig) {
        if (!(mainChunkX == chunkX && mainChunkZ == chunkZ)) {
            return false;
        }

        int chunkStartX = chunkX << 4;
        int chunkStartZ = chunkZ << 4;

        double[][][] noiseData = new double[2][5][9];

        for(int noiseZ = 0; noiseZ < 5; ++noiseZ) {
            noiseData[0][noiseZ] = new double[9];
            sampleNoiseColumn(noiseData[0][noiseZ], chunkX * 4, chunkZ * 4 + noiseZ);
            noiseData[1][noiseZ] = new double[9];
        }

        // [0, 4] -> x noise chunks
        for(int noiseX = 0; noiseX < 4; ++noiseX) {
            // Initialize noise data on the x1 column
            int noiseZ;
            for (noiseZ = 0; noiseZ < 5; ++noiseZ) {
                sampleNoiseColumn(noiseData[1][noiseZ], chunkX * 4 + noiseX + 1, chunkZ * 4 + noiseZ);
            }

            // [0, 4] -> z noise chunks
            for (noiseZ = 0; noiseZ < 4; ++noiseZ) {
                ChunkSection section = ((ProtoChunk)chunk).getSection(15);
//                section.lock();

                // [0, 32] -> y noise chunks
                for (int noiseY = 7; noiseY >= 0; --noiseY) {
                    // Lower samples
                    double x0z0y0 = noiseData[0][noiseZ][noiseY];
                    double x0z1y0 = noiseData[0][noiseZ + 1][noiseY];
                    double x1z0y0 = noiseData[1][noiseZ][noiseY];
                    double x1z1y0 = noiseData[1][noiseZ + 1][noiseY];
                    // Upper samples
                    double x0z0y1 = noiseData[0][noiseZ][noiseY + 1];
                    double x0z1y1 = noiseData[0][noiseZ + 1][noiseY + 1];
                    double x1z0y1 = noiseData[1][noiseZ][noiseY + 1];
                    double x1z1y1 = noiseData[1][noiseZ + 1][noiseY + 1];

                    // [0, 8] -> y noise pieces
                    for (int pieceY = 7; pieceY >= 0; --pieceY) {
                        int realY = noiseY * 8 + pieceY;
                        int localY = realY & 15;
                        int sectionY = realY >> 4;
                        if (section.getYOffset() >> 4 != sectionY) {
//                            section.unlock();
                            section = ((ProtoChunk)chunk).getSection(sectionY);
//                            section.lock();
                        }

                        // progress within loop
                        double yLerp = (double) pieceY / 8.0;

                        // Interpolate noise data based on y progress
                        double x0z0 = MathHelper.lerp(yLerp, x0z0y0, x0z0y1);
                        double x1z0 = MathHelper.lerp(yLerp, x1z0y0, x1z0y1);
                        double x0z1 = MathHelper.lerp(yLerp, x0z1y0, x0z1y1);
                        double x1z1 = MathHelper.lerp(yLerp, x1z1y0, x1z1y1);

                        // [0, 4] -> x noise pieces
                        for (int pieceX = 0; pieceX < 4; ++pieceX) {
                            int realX = chunkStartX + noiseX * 4 + pieceX;
                            int localX = realX & 15;
                            double xLerp = (double) pieceX / 4.0;
                            // Interpolate noise based on x progress
                            double z0 = MathHelper.lerp(xLerp, x0z0, x1z0);
                            double z1 = MathHelper.lerp(xLerp, x0z1, x1z1);

                            // [0, 4) -> z noise pieces
                            for (int pieceZ = 0; pieceZ < 4; ++pieceZ) {
                                int realZ = chunkStartZ + noiseZ * 4 + pieceZ;
                                int localZ = realZ & 15;
                                double zLerp = (double) pieceZ / 4.0;
                                // Get the real noise here by interpolating the last 2 noises together
                                double density = MathHelper.lerp(zLerp, z0, z1);

                                if (density < 0.0) {
                                    // TODO no new
                                    chunk.setBlockState(new BlockPos(localX, realY, localZ), Blocks.AIR.getDefaultState(), false);

                                    // TODO: wat
                                    int i = localX | localZ << 4 | realY << 8;
                                    carvingMask.set(i);

                                    ((ProtoChunk) chunk).setCarvingMask(GenerationStep.Carver.AIR, carvingMask);
                                }
                            }
                        }
                    }
                }
            }

            // Reuse noise data from the previous column for speed
            double[][] xColumn = noiseData[0];
            noiseData[0] = noiseData[1];
            noiseData[1] = xColumn;
        }

        return true;
    }

    protected void sampleNoiseColumn(double[] buffer, int x, int z) {
        double offset = offsetNoise.sample(x / 128.0, 5423.434, z / 128.0) * 5.45;

        for (int y = 0; y < buffer.length; y++) {
            buffer[y] = sampleNoise(x, y, z) + getFalloff(offset, y);
        }
    }

    private double sampleNoise(int x, int y, int z) {
        double noise = 0;
        double amplitude = 1;
        for (int i = 0; i < 6; i++) {
            noise += caveNoise.sample(x * 18.42 * amplitude, y * 84.18 * amplitude, z * 18.42 * amplitude) / amplitude;

            amplitude /= 2.0;
        }

        noise /= 1.25;

        return noise;
    }

    private double getFalloff(double offset, int y) {
        double falloffScale = 23.0 + offset;

        double falloff = Math.max((falloffScale / y), 0); // lower bound
        falloff += Math.max((falloffScale / ((8) - y)), 0); // upper bound

        double scaledY = y + 9.3;

        falloff = (1.5 * falloff) - (0.0525 * scaledY * scaledY) - (-0.2 * y);

        return falloff;
    }

    @Override
    protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return false;
    }
}
