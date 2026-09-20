package com.asbiibs.explorersawakening.feature.region.scaling;

import com.asbiibs.explorersawakening.TheExplorersAwakening;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class MobScaling {

    private static final Identifier HEALTH_MODIFIER =
            Identifier.fromNamespaceAndPath(
                    TheExplorersAwakening.MODID,
                    "region_health"
            );

    private static final Identifier DAMAGE_MODIFIER =
            Identifier.fromNamespaceAndPath(
                    TheExplorersAwakening.MODID,
                    "region_damage"
            );

    private static final Identifier SPEED_MODIFIER =
            Identifier.fromNamespaceAndPath(
                    TheExplorersAwakening.MODID,
                    "region_speed"
            );

    private static final Identifier ARMOR_MODIFIER =
            Identifier.fromNamespaceAndPath(
                    TheExplorersAwakening.MODID,
                    "region_armor"
            );

    private MobScaling() {
    }

    public static void apply(Mob mob, int regionLevel) {
        RegionScalingProfile profile =
                RegionScalingProfile.forLevel(regionLevel);

        applyModifier(
                mob,
                Attributes.MAX_HEALTH,
                HEALTH_MODIFIER,
                profile.healthBonus(),
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        );

        applyModifier(
                mob,
                Attributes.ATTACK_DAMAGE,
                DAMAGE_MODIFIER,
                profile.damageBonus(),
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        );

        applyModifier(
                mob,
                Attributes.MOVEMENT_SPEED,
                SPEED_MODIFIER,
                profile.speedBonus(),
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        );

        applyModifier(
                mob,
                Attributes.ARMOR,
                ARMOR_MODIFIER,
                profile.armorBonus(),
                AttributeModifier.Operation.ADD_VALUE
        );

        mob.setHealth(mob.getMaxHealth());
    }

    private static void applyModifier(
            Mob mob,
            Holder<Attribute> attribute,
            Identifier id,
            double amount,
            AttributeModifier.Operation operation
    ) {
        AttributeInstance instance = mob.getAttribute(attribute);

        if (instance == null) {
            return;
        }

        if (amount == 0) {
            instance.removeModifier(id);
            return;
        }

        instance.addOrReplacePermanentModifier(
                new AttributeModifier(
                        id,
                        amount,
                        operation
                )
        );
    }
}