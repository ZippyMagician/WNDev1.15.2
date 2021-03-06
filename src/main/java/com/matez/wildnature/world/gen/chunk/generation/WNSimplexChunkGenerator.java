package com.matez.wildnature.world.gen.chunk.generation;

import com.matez.wildnature.Main;
import com.matez.wildnature.other.Utilities;
import com.matez.wildnature.world.gen.biomes.layer.ColumnBiomeContainer;
import com.matez.wildnature.world.gen.biomes.layer.SmoothColumnBiomeMagnifier;
import com.matez.wildnature.world.gen.biomes.setup.WNGenSettings;
import com.matez.wildnature.world.gen.chunk.generation.landscape.ChunkLandscape;
import com.matez.wildnature.world.gen.chunk.primers.FastChunkPrimer;
import com.matez.wildnature.world.gen.generators.carves.PathGenerator;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.ColumnFuzzedBiomeMagnifier;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraftforge.common.BiomeDictionary;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class WNSimplexChunkGenerator extends ChunkGenerator<WNGenSettings> {
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    protected IChunk chunk = null;


    private WNGenSettings settings;
    private final int verticalNoiseResolution;
    private final int horizontalNoiseResolution;
    private final int noiseSizeX;
    private final int noiseSizeY;
    private final int noiseSizeZ;

    private final OctavesNoiseGenerator surfaceDepthNoise;


    protected HashMap<Long, int[]> noiseCache = new HashMap<>();

    private SharedSeedRandom randomSeed;

    private PathGenerator pathGenerator;

    public WNSimplexChunkGenerator(IWorld worldIn, BiomeProvider biomeProviderIn, WNGenSettings generationSettingsIn) {
        super(worldIn, biomeProviderIn, generationSettingsIn);
        this.settings = generationSettingsIn;
        this.randomSeed = new SharedSeedRandom(this.seed);

        this.verticalNoiseResolution = 8;
        this.horizontalNoiseResolution = 4;
        this.noiseSizeX = 16 / this.horizontalNoiseResolution;
        this.noiseSizeY = 256 / this.verticalNoiseResolution;
        this.noiseSizeZ = 16 / this.horizontalNoiseResolution;

        this.surfaceDepthNoise = new OctavesNoiseGenerator(this.randomSeed, 4, 0);

        this.pathGenerator = new PathGenerator(worldIn);
    }

    @Override
    public void generateBiomes(IChunk chunkIn) {
        // Saves 98% of vanilla biome generation calls
        ((ChunkPrimer) chunkIn).func_225548_a_(new ColumnBiomeContainer(chunkIn.getPos(), biomeProvider));
    }

    public void generateSurface(WorldGenRegion worldGenRegion, IChunk chunkIn) {
        ChunkPos chunkpos = chunkIn.getPos();
        int i = chunkpos.x;
        int j = chunkpos.z;
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
        sharedseedrandom.setBaseChunkSeed(i, j);
        ChunkPos chunkpos1 = chunkIn.getPos();
        int xChunkPos = chunkpos1.getXStart();
        int zChunkPos = chunkpos1.getZStart();
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();


        for (int relativeX = 0; relativeX < 16; ++relativeX) {
            for (int relativeZ = 0; relativeZ < 16; ++relativeZ) {
                int x = xChunkPos + relativeX;
                int z = zChunkPos + relativeZ;
                int startHeight = chunkIn.getTopBlockY(Heightmap.Type.WORLD_SURFACE_WG, relativeX, relativeZ) + 1;
                double noise = surfaceDepthNoise.noiseAt((double)x * 0.0625, (double)z * 0.0625, 0.0625, (double)startHeight * 0.0625) * 15;

                Biome biome = worldGenRegion.getBiome(blockpos$mutable.setPos(xChunkPos + relativeX, startHeight, zChunkPos + relativeZ));
                biome.buildSurface(sharedseedrandom, chunkIn, x, z, startHeight, noise, this.getSettings().getDefaultBlock(), this.getSettings().getDefaultFluid(), this.getSeaLevel(), this.world.getSeed());

                pathGenerator.generate(x,startHeight,z,biome,chunkIn);
            }
        }

        this.makeBedrock(chunkIn, sharedseedrandom);
    }

    protected void makeBedrock(IChunk chunkIn, Random rand) {
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
        int i = chunkIn.getPos().getXStart();
        int j = chunkIn.getPos().getZStart();
        WNGenSettings t = this.getSettings();
        int k = t.getBedrockFloorHeight();
        int l = t.getBedrockRoofHeight();

        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(i, 0, j, i + 15, 0, j + 15)) {
            if (l > 0) {
                for (int i1 = l; i1 >= l - 4; --i1) {
                    if (i1 >= l - rand.nextInt(5)) {
                        chunkIn.setBlockState(blockpos$mutableblockpos.setPos(blockpos.getX(), i1, blockpos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                    }
                }
            }

            if (k < 256) {
                for (int j1 = k + 4; j1 >= k; --j1) {
                    if (j1 <= k + rand.nextInt(5)) {
                        chunkIn.setBlockState(blockpos$mutableblockpos.setPos(blockpos.getX(), j1, blockpos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                    }
                }
            }
        }

    }

    @Override
    public int getGroundHeight() {
        return this.getSeaLevel() + 1;
    }

    @Override
    public void makeBase(IWorld worldIn, IChunk chunkIn) {
        this.chunk = chunkIn;
        ObjectList<AbstractVillagePiece> structurePieces = new ObjectArrayList<>(10);
        ObjectList<JigsawJunction> jigsaws = new ObjectArrayList<>(32);
        ChunkPos chunkpos = chunkIn.getPos();
        int chunkX = chunkpos.x;
        int chunkZ = chunkpos.z;
        int chunkStartX = chunkX << 4;
        int chunkStartZ = chunkZ << 4;

        for (Structure<?> structure : Feature.ILLAGER_STRUCTURES) {
            String s = structure.getStructureName();
            LongIterator longiterator = chunkIn.getStructureReferences(s).iterator();

            while (longiterator.hasNext()) {
                long j1 = longiterator.nextLong();
                ChunkPos chunkpos1 = new ChunkPos(j1);
                IChunk ichunk = worldIn.getChunk(chunkpos1.x, chunkpos1.z);
                StructureStart structurestart = ichunk.getStructureStart(s);
                if (structurestart != null && structurestart.isValid()) {
                    for (StructurePiece structurepiece : structurestart.getComponents()) {
                        if (structurepiece.func_214810_a(chunkpos, 12) && structurepiece instanceof AbstractVillagePiece) {
                            AbstractVillagePiece abstractvillagepiece = (AbstractVillagePiece) structurepiece;
                            JigsawPattern.PlacementBehaviour jigsawpattern$placementbehaviour = abstractvillagepiece.getJigsawPiece().getPlacementBehaviour();
                            if (jigsawpattern$placementbehaviour == JigsawPattern.PlacementBehaviour.RIGID) {
                                structurePieces.add(abstractvillagepiece);
                            }

                            for (JigsawJunction jigsawjunction : abstractvillagepiece.getJunctions()) {
                                int k1 = jigsawjunction.getSourceX();
                                int l1 = jigsawjunction.getSourceZ();
                                if (k1 > chunkStartX - 12 && l1 > chunkStartZ - 12 && k1 < chunkStartX + 15 + 12 && l1 < chunkStartZ + 15 + 12) {
                                    jigsaws.add(jigsawjunction);
                                }
                            }
                        }
                    }
                }
            }
        }

        this.generateTerrain(chunkIn, this.getHeightsInChunk(chunkpos,worldIn));
    }

    public void generateTerrain(IChunk chunk, int[] noise) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int height = (int) noise[(x * 16) + z];

                for (int y = 0; y < 256; y++) {
                    BlockPos pos = new BlockPos(x, y, z);

                    //if (y > (height * 0.75) + (height * 0.3 * scale) + (depth * 15))
                    if (y > height) {
                        if (y < this.getSeaLevel()) {
                            chunk.setBlockState(pos, this.settings.getDefaultFluid(), false);
                        }
                    } else {
                        chunk.setBlockState(pos, this.settings.getDefaultBlock(), false);
                    }
                }
            }
        }
    }

    protected int[] getHeightsInChunk(ChunkPos pos, IWorld worldIn) {
        int[] res = noiseCache.get(pos.asLong());
        if (res != null) return res;

        int[] vals = new int[256];

        // useNoise(vals, pos, 0, 16);
        int threads = 4;

        CompletableFuture<?>[] futures = new CompletableFuture[threads];
        for (int i = 0; i < threads; i++) {
            int position = i;
            futures[i] = CompletableFuture.runAsync(() -> useNoise(vals, pos, position * 16 / threads, 16 / threads,worldIn));
        }

        for (int i = 0; i < futures.length; i++) {
            futures[i].join();
        }

        noiseCache.put(pos.asLong(), vals);

        return vals;
    }

    public void useNoise(int[] noise, ChunkPos pos, int start, int size, IWorld worldIn) {
        final Object2DoubleMap<Biome> weightMap16 = new Object2DoubleOpenHashMap<>(4), weightMap4 = new Object2DoubleOpenHashMap<>(4), weightMap1 = new Object2DoubleOpenHashMap<>();

        final ChunkArraySampler.CoordinateAccessor<Biome> biomeAccessor = (x, z) -> (Biome) SmoothColumnBiomeMagnifier.SMOOTH.getBiome(worldIn.getSeed(), pos.getXStart() + x, 0, pos.getZStart() + z, worldIn);

        final Biome[] sampledBiomes16 = ChunkArraySampler.fillSampledArray(new Biome[10 * 10], biomeAccessor, 4);
        final Biome[] sampledBiomes4 = ChunkArraySampler.fillSampledArray(new Biome[13 * 13], biomeAccessor, 2);
        final Biome[] sampledBiomes1 = ChunkArraySampler.fillSampledArray(new Biome[24 * 24], biomeAccessor);

        for (int x = start; x < start + size; x++) {
            for (int z = 0; z < 16; z++) {
                // Sample biome weights at different distances
                ChunkArraySampler.fillSampledWeightMap(sampledBiomes16, weightMap16, 4, x, z);
                ChunkArraySampler.fillSampledWeightMap(sampledBiomes4, weightMap4, 2, x, z);
                ChunkArraySampler.fillSampledWeightMap(sampledBiomes1, weightMap1, x, z);

                noise[(x * 16) + z] = getTerrainHeight((pos.x * 16) + x, (pos.z * 16) + z,weightMap16,weightMap4,weightMap1);
            }
        }
    }

    public int getTerrainHeight(int x, int z, Object2DoubleMap<Biome> weightMap16, Object2DoubleMap<Biome> weightMap4, Object2DoubleMap<Biome> weightMap1) {
        Biome biome = this.biomeProvider.getNoiseBiome(x / 4, 1, z / 4);
        ChunkLandscape landscape = ChunkLandscape.getOrCreate(x, z, this.seed, this.getSeaLevel(), biome, this.chunk);

        return (int) landscape.generateHeightmap(biomeProvider,weightMap16,weightMap4,weightMap1);
    }


    @Override
    public int func_222529_a(int x, int z, Type heightmap) {
        return getSeaLevel();
    }
}
