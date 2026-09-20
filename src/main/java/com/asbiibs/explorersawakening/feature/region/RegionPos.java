package com.asbiibs.explorersawakening.feature.region;

import net.minecraft.core.BlockPos;

public record RegionPos(int x, int z) {

    public static final int SIZE_BLOCKS = 512;

    public static RegionPos from(BlockPos pos) {
        return new RegionPos(
                Math.floorDiv(pos.getX(), SIZE_BLOCKS),
                Math.floorDiv(pos.getZ(), SIZE_BLOCKS)
        );
    }

    public int centerX() {
        return x * SIZE_BLOCKS + SIZE_BLOCKS / 2;
    }

    public int centerZ() {
        return z * SIZE_BLOCKS + SIZE_BLOCKS / 2;
    }
}