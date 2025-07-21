package net.licketysplitter.maplecraft.effect;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MaplecraftMod.MOD_ID);

    public static final Holder<MobEffect> ITCHY_EFFECT = MOB_EFFECTS.register("itchy",
            () -> new ItchyEffect(MobEffectCategory.HARMFUL, 0x703704)
                    .addAttributeModifier(Attributes.FALL_DAMAGE_MULTIPLIER, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "itchy"),
                            0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "itchy"),
                            -0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ARMOR_TOUGHNESS, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "itchy"),
                            -0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.OXYGEN_BONUS, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "itchy"),
                            -0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
