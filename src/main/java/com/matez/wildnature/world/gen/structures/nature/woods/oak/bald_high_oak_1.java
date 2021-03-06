package com.matez.wildnature.world.gen.structures.nature.woods.oak;

import com.matez.wildnature.world.gen.structures.nature.SchemFeature;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class bald_high_oak_1 extends SchemFeature {
    public bald_high_oak_1() {
        super();

    }


    @Override
    public Set<BlockPos> setBlocks() {

        Block(-1, 13, -2, "minecraft:oak_log[axis=x]");
        Block(-1, 14, -2, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 11, -1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(2, 12, -1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(-1, 13, -1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 13, -1, "minecraft:oak_log[axis=z]");
        Block(2, 13, -1, "minecraft:oak_log[axis=z]");
        Block(-3, 14, -1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 14, -1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(2, 14, -1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(2, 15, -1, "minecraft:oak_leaves[distance=2,persistent=true]");
        Block(0, 1, 0, "minecraft:oak_log[axis=y]");
        Block(0, 2, 0, "minecraft:oak_log[axis=y]");
        Block(0, 3, 0, "minecraft:oak_log[axis=y]");
        Block(0, 4, 0, "minecraft:oak_log[axis=y]");
        Block(0, 5, 0, "minecraft:oak_log[axis=y]");
        Block(0, 6, 0, "minecraft:oak_log[axis=y]");
        Block(0, 7, 0, "minecraft:oak_log[axis=y]");
        Block(0, 8, 0, "minecraft:oak_log[axis=y]");
        Block(0, 9, 0, "minecraft:oak_log[axis=y]");
        Block(0, 10, 0, "minecraft:oak_log[axis=y]");
        Block(-1, 11, 0, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 11, 0, "minecraft:oak_log[axis=y]");
        Block(-2, 12, 0, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 12, 0, "minecraft:oak_log[axis=y]");
        Block(1, 12, 0, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(-2, 13, 0, "minecraft:oak_log[axis=x]");
        Block(-1, 13, 0, "minecraft:oak_log[axis=x]");
        Block(0, 13, 0, "minecraft:oak_log[axis=y]");
        Block(1, 13, 0, "minecraft:oak_log[axis=x]");
        Block(-3, 14, 0, "minecraft:oak_log[axis=x]");
        Block(-1, 14, 0, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 14, 0, "minecraft:oak_log[axis=y]");
        Block(-3, 15, 0, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 15, 0, "minecraft:oak_log[axis=y]");
        Block(1, 15, 0, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 16, 0, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(1, 12, 1, "minecraft:oak_leaves[distance=2,persistent=true]");
        Block(-2, 13, 1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 13, 1, "minecraft:oak_log[axis=z]");
        Block(1, 13, 1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 14, 1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 15, 1, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 12, 2, "minecraft:oak_leaves[distance=1,persistent=true]");
        Block(0, 13, 2, "minecraft:oak_log[axis=z]");
        Block(-1, 14, 2, "minecraft:oak_leaves[distance=2,persistent=true]");
        Block(0, 14, 2, "minecraft:oak_leaves[distance=1,persistent=true]");

//   wildnature mod
//           created by matez 
//         all rights reserved   
//     http://bit.ly/wildnature-mod
        return super.setBlocks();
    }
}