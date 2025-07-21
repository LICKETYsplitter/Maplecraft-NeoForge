package net.licketysplitter.maplecraft.datagen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.item.ModItems;
import net.licketysplitter.maplecraft.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {

    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, completableFuture, MaplecraftMod.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.MAPLE_LOG.get().asItem())
                .add(ModBlocks.MAPLE_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_MAPLE_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_MAPLE_WOOD.get().asItem())
                .add(ModBlocks.APPLE_LOG.get().asItem())
                .add(ModBlocks.APPLE_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_APPLE_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_APPLE_WOOD.get().asItem());

        tag(ItemTags.PLANKS)
                .add(ModBlocks.MAPLE_PLANKS.get().asItem())
                .add(ModBlocks.APPLE_PLANKS.get().asItem());

        tag(ModTags.Items.MAPLE_LOGS)
                .add(ModBlocks.MAPLE_LOG.get().asItem())
                .add(ModBlocks.MAPLE_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_MAPLE_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_MAPLE_WOOD.get().asItem());

        tag(ModTags.Items.APPLE_LOGS)
                .add(ModBlocks.APPLE_LOG.get().asItem())
                .add(ModBlocks.APPLE_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_APPLE_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_APPLE_WOOD.get().asItem());

        tag(ModTags.Items.ANTLERS)
                .add(ModItems.TWO_POINT_ANTLER.get())
                .add(ModItems.FOUR_POINT_ANTLER.get())
                .add(ModItems.SIX_POINT_ANTLER.get())
                .add(ModItems.EIGHT_POINT_ANTLER.get());
    }
}
