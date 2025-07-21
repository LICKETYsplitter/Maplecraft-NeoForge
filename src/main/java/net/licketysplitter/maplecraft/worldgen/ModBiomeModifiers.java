package net.licketysplitter.maplecraft.worldgen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_DEER = registerKey("add_deer");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);
    }

    private static ResourceKey<BiomeModifier> registerKey(String name){
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, name));
    }
}
