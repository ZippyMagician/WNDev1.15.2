package com.matez.wildnature.world.gen.structures.nature.woods.shrubs;

import com.matez.wildnature.world.gen.structures.nature.SchemFeature;
import net.minecraft.util.math.BlockPos;

import java.util.Set;


public class scrubland_shrub3 extends SchemFeature {
    public scrubland_shrub3() {
        super();

    }

    @Override
    public Set<BlockPos> setBlocks() {
        Block(0, 1, -3, "minecraft:oak_leaves[distance=3,persistent=true]");
        Block(0, 1, -2, "minecraft:oak_leaves[distance=2,persistent=true]");
        Block(1, 1, -2, "minecraft:oak_leaves[distance=3,persistent=true]");
        Block(-2, 1, -1, "minecraft:oak_leaves[distance=3,persistent=true]");
        Block(-1, 1, -1, "minecraft:oak_leaves[distance=2,persistent=true]");
        Block(0, 1, -1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(1, 1, -1, "minecraft:oak_leaves[distance=2,persistent=true]");
        Block(-1, 2, -1, "minecraft:oak_leaves[distance=2,persistent=true]");
        Block(0, 2, -1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(-2, 1, 0, "minecraft:oak_leaves[distance=2,persistent=true]");
        Block(-1, 1, 0, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 1, 0, "minecraft:oak_log[axis=y]");
        Block(1, 1, 0, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(-1, 2, 0, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 2, 0, "minecraft:oak_log[axis=y]");
        Block(1, 2, 0, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 3, 0, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(-1, 1, 1, "minecraft:oak_leaves[distance=2,persistent=true]");
        Block(0, 1, 1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(1, 1, 1, "minecraft:oak_leaves[distance=2,persistent=true]");
        Block(2, 1, 1, "minecraft:oak_leaves[distance=3,persistent=true]");
        Block(-1, 2, 1, "minecraft:oak_leaves[distance=2,persistent=true]");
        Block(-1, 1, 2, "minecraft:oak_leaves[distance=3,persistent=true]");
        Block(2, 1, 2, "minecraft:oak_leaves[distance=4,persistent=true]");

//   wildnature mod
//           created by matez 
//         all rights reserved   
//     http://bit.ly/wildnature-mod
        return super.setBlocks();
    }
}