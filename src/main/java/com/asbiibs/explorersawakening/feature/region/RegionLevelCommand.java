package com.asbiibs.explorersawakening.feature.region;

import com.asbiibs.explorersawakening.TheExplorersAwakening;

import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = TheExplorersAwakening.MODID)
public final class RegionLevelCommand {

    private RegionLevelCommand() {
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {

        event.getDispatcher().register(
                Commands.literal("explorersawakening")
                        .then(
                                Commands.literal("region")
                                        .executes(context -> {

                                            var source = context.getSource();

                                            ServerLevel level = source.getLevel();
                                            Vec3 position = source.getPosition();

                                            BlockPos pos = BlockPos.containing(
                                                    position.x,
                                                    position.y,
                                                    position.z
                                            );

                                            RegionPos region = RegionPos.from(pos);

                                            int regionLevel =
                                                    RegionLevelManager.getLevel(level, pos);

                                            String message =
                                                    "Region (" +
                                                            region.x() + ", " +
                                                            region.z() + ") | Level " +
                                                            regionLevel +
                                                            " | Position: " +
                                                            pos.getX() + ", " +
                                                            pos.getY() + ", " +
                                                            pos.getZ();

                                            source.sendSuccess(
                                                    () -> Component.literal(message),
                                                    false
                                            );

                                            return 1;
                                        })
                        )
        );
    }
}