package net.licketysplitter.maplecraft.particle;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MaplecraftMod.MOD_ID);

    public static final Supplier<SimpleParticleType> RED_MAPLE_PARTICLES =
            PARTICLE_TYPES.register("red_maple_particles", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> SUGAR_MAPLE_PARTICLES =
            PARTICLE_TYPES.register("sugar_maple_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
