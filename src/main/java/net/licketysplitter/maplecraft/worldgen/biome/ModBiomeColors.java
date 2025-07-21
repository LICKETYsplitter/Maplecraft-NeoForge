package net.licketysplitter.maplecraft.worldgen.biome;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.FoliageColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ModBiomeColors {

    public static final ColorResolver BIRCH_COLOR_RESOLVER = (pBiome, pX, pZ) ->
            pBiome.getFoliageColor() == ModBiomes.FALL_FOREST_FOLIAGE_COLOR ? 0xee8b20: FoliageColor.FOLIAGE_BIRCH;
    public static final ColorResolver EVERGREEN_COLOR_RESOLVER = (pBiome, pX, pZ) ->
            pBiome.getFoliageColor() == ModBiomes.FALL_FOREST_FOLIAGE_COLOR ? 0x5e6345: FoliageColor.FOLIAGE_EVERGREEN;

    private static int getAverageColor(BlockAndTintGetter pLevel, BlockPos pBlockPos, ColorResolver pColorResolver) {
        return pLevel.getBlockTint(pBlockPos, pColorResolver);
    }

    public static int getBirchColor(BlockAndTintGetter pLevel, BlockPos pBlockPos){
        return getAverageColor(pLevel, pBlockPos, BIRCH_COLOR_RESOLVER);
    }

    public static int getEvergreenColor(BlockAndTintGetter pLevel, BlockPos pBlockPos){
        return getAverageColor(pLevel, pBlockPos, EVERGREEN_COLOR_RESOLVER);
    }
}
