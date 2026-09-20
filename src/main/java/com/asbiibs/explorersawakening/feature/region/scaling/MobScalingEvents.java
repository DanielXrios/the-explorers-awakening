package com.asbiibs.explorersawakening.feature.region.scaling;

import com.asbiibs.explorersawakening.TheExplorersAwakening;
import com.asbiibs.explorersawakening.feature.region.RegionLevelManager;
import com.asbiibs.explorersawakening.registry.ModAttachments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.zombie.Zombie;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

@EventBusSubscriber(modid = TheExplorersAwakening.MODID)
public final class MobScalingEvents {

    private MobScalingEvents() {
    }

    @SubscribeEvent
    public static void onFinalizeSpawn(FinalizeSpawnEvent event) {

        // Por enquanto estamos testando somente zombies.
        if (!(event.getEntity() instanceof Zombie zombie)) {
            return;
        }

        if (!(zombie.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        int regionLevel = RegionLevelManager.getLevel(
                serverLevel,
                zombie.blockPosition()
        );

        zombie.setData(
                ModAttachments.SPAWN_REGION_LEVEL.get(),
                regionLevel
        );

        MobScaling.apply(zombie, regionLevel);
    }
}