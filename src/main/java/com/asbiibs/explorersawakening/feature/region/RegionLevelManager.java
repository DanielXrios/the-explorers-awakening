package com.asbiibs.explorersawakening.feature.region;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public final class RegionLevelManager {

    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 5;

    private static final double SAFE_RADIUS = 1024.0;

    private static final double LEVEL_2_DISTANCE = 3000.0;
    private static final double LEVEL_3_DISTANCE = 6000.0;
    private static final double LEVEL_4_DISTANCE = 10000.0;

    private RegionLevelManager() {
    }

    public static int getLevel(ServerLevel level, BlockPos pos) {
        RegionPos region = RegionPos.from(pos);

        BlockPos origin = getProgressionOrigin(level);

        double dx = region.centerX() - origin.getX();
        double dz = region.centerZ() - origin.getZ();

        double distance = Math.hypot(dx, dz);

        // Região inicial garantidamente segura.
        if (distance <= SAFE_RADIUS) {
            return 1;
        }

        int baseLevel = getBaseLevel(distance);

        long hash = getRegionHash(level, region);

        // 20% de chance de -1 nível
        // 60% de ficar no nível base
        // 20% de chance de +1 nível
        int roll = (int) Math.floorMod(hash, 100L);

        int variation;

        if (roll < 20) {
            variation = -1;
        } else if (roll < 80) {
            variation = 0;
        } else {
            variation = 1;
        }

        return clamp(baseLevel + variation);
    }

    private static int getBaseLevel(double distance) {
        if (distance < LEVEL_2_DISTANCE) {
            return 2;
        }

        if (distance < LEVEL_3_DISTANCE) {
            return 3;
        }

        if (distance < LEVEL_4_DISTANCE) {
            return 4;
        }

        return 5;
    }

    private static BlockPos getProgressionOrigin(ServerLevel level) {
        var respawnData = level.getServer().getRespawnData();

        if (respawnData.dimension().equals(level.dimension())) {
            return respawnData.pos();
        }

        // Por enquanto Nether/End usam 0,0 como origem.
        // Depois faremos regras específicas por dimensão.
        return BlockPos.ZERO;
    }

    private static long getRegionHash(ServerLevel level, RegionPos region) {
        long value = level.getSeed();

        value ^= (long) region.x() * 341873128712L;
        value ^= (long) region.z() * 132897987541L;
        value ^= (long) level.dimension().hashCode() * 42317861L;

        return mix64(value);
    }

    private static long mix64(long value) {
        value ^= value >>> 33;
        value *= 0xff51afd7ed558ccdL;
        value ^= value >>> 33;
        value *= 0xc4ceb9fe1a85ec53L;
        value ^= value >>> 33;

        return value;
    }

    private static int clamp(int value) {
        return Math.max(MIN_LEVEL, Math.min(MAX_LEVEL, value));
    }
}