package com.asbiibs.explorersawakening.feature.region.scaling;

import com.asbiibs.explorersawakening.TheExplorersAwakening;
import com.asbiibs.explorersawakening.feature.region.RegionLevelManager;
import com.asbiibs.explorersawakening.registry.ModAttachments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Monster;

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
        if (!(event.getEntity() instanceof Monster mob)) {
            return;
        }

        if (!(mob.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        int regionLevel = RegionLevelManager.getLevel(
                serverLevel,
                mob.blockPosition()
        );

        mob.setData(
                ModAttachments.SPAWN_REGION_LEVEL.get(),
                regionLevel
        );

        MobScaling.apply(mob, regionLevel);
    }
}