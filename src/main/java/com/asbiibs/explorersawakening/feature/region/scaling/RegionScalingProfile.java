package com.asbiibs.explorersawakening.feature.region.scaling;

public record RegionScalingProfile(
        double healthBonus,
        double damageBonus,
        double speedBonus,
        double armorBonus
) {

    public static RegionScalingProfile forLevel(int level) {
        return switch (level) {
            case 1 -> new RegionScalingProfile(
                    0.00,
                    0.00,
                    0.00,
                    0.0
            );

            case 2 -> new RegionScalingProfile(
                    0.10,
                    0.08,
                    0.02,
                    0.0
            );

            case 3 -> new RegionScalingProfile(
                    0.25,
                    0.18,
                    0.04,
                    1.0
            );

            case 4 -> new RegionScalingProfile(
                    0.45,
                    0.30,
                    0.06,
                    2.0
            );

            case 5 -> new RegionScalingProfile(
                    0.70,
                    0.45,
                    0.08,
                    4.0
            );

            default -> throw new IllegalArgumentException(
                    "Invalid region level: " + level
            );
        };
    }
}