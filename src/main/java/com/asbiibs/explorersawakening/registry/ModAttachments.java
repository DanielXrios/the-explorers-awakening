package com.asbiibs.explorersawakening.registry;

import com.asbiibs.explorersawakening.TheExplorersAwakening;
import com.mojang.serialization.Codec;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(
                    NeoForgeRegistries.ATTACHMENT_TYPES,
                    TheExplorersAwakening.MODID
            );

    public static final Supplier<AttachmentType<Integer>> SPAWN_REGION_LEVEL =
            ATTACHMENT_TYPES.register(
                    "spawn_region_level",
                    () -> AttachmentType.builder(() -> 1)
                            .serialize(Codec.INT.fieldOf("level"))
                            .build()
            );

    private ModAttachments() {
    }
}