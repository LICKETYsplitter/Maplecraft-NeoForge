package net.licketysplitter.maplecraft.datagen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, MaplecraftMod.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        //BLOCKS

        blockModels.createTrivialCube(ModBlocks.MAPLE_SYRUP_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.SUGAR_GLASS.get());

        blockModels.woodProvider(ModBlocks.MAPLE_LOG.get()).logWithHorizontal(ModBlocks.MAPLE_LOG.get())
                .wood(ModBlocks.MAPLE_WOOD.get());
        blockModels.woodProvider(ModBlocks.STRIPPED_MAPLE_LOG.get()).logWithHorizontal(ModBlocks.STRIPPED_MAPLE_LOG.get())
                .wood(ModBlocks.STRIPPED_MAPLE_WOOD.get());

        blockModels.family(ModBlocks.MAPLE_PLANKS.get())
                .fence(ModBlocks.MAPLE_FENCE.get())
                .fenceGate(ModBlocks.MAPLE_FENCE_GATE.get())
                .stairs(ModBlocks.MAPLE_STAIRS.get())
                .slab(ModBlocks.MAPLE_SLAB.get())
                .button(ModBlocks.MAPLE_BUTTON.get())
                .pressurePlate(ModBlocks.MAPLE_PRESSURE_PLATE.get())
                .door(ModBlocks.MAPLE_DOOR.get())
                .trapdoor(ModBlocks.MAPLE_TRAPDOOR.get());

        blockModels.woodProvider(ModBlocks.APPLE_LOG.get()).logWithHorizontal(ModBlocks.APPLE_LOG.get())
                .wood(ModBlocks.APPLE_WOOD.get());
        blockModels.woodProvider(ModBlocks.STRIPPED_APPLE_LOG.get()).logWithHorizontal(ModBlocks.STRIPPED_APPLE_LOG.get())
                .wood(ModBlocks.STRIPPED_APPLE_WOOD.get());

        blockModels.family(ModBlocks.APPLE_PLANKS.get())
                .fence(ModBlocks.APPLE_FENCE.get())
                .fenceGate(ModBlocks.APPLE_FENCE_GATE.get())
                .stairs(ModBlocks.APPLE_STAIRS.get())
                .slab(ModBlocks.APPLE_SLAB.get())
                .button(ModBlocks.APPLE_BUTTON.get())
                .pressurePlate(ModBlocks.APPLE_PRESSURE_PLATE.get())
                .door(ModBlocks.APPLE_DOOR.get())
                .trapdoor(ModBlocks.APPLE_TRAPDOOR.get());

        blockModels.createTrivialCube(ModBlocks.SUGAR_MAPLE_LEAVES.get());
        blockModels.createTrivialCube(ModBlocks.RED_MAPLE_LEAVES.get());

        blockModels.createCrossBlock(ModBlocks.SUGAR_MAPLE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createCrossBlock(ModBlocks.RED_MAPLE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createCrossBlock(ModBlocks.APPLE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        blockModels.createTrivialCube(ModBlocks.SINKING_MUD.get());

        //ITEMS
        itemModels.generateFlatItem(ModItems.MAPLE_SYRUP_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SUGAR_GLASS_SHARD.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.VENISON.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COOKED_VENISON.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.SAP_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GREEN_APPLE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.ANTLER.get(), ModelTemplates.FLAT_ITEM);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().filter(x ->
                !x.is(ModBlocks.APPLE_LEAVES) &&
                !x.is(ModBlocks.ASTER) &&
                !x.is(ModBlocks.CATTAIL) &&
                !x.is(ModBlocks.EVAPORATOR) &&
                !x.is(ModBlocks.FLOWERING_APPLE_LEAVES) &&
                !x.is(ModBlocks.PILE_OF_LEAVES) &&
                !x.is(ModBlocks.POISON_IVY) &&
                !x.is(ModBlocks.POISON_IVY_PLANT) &&
                !x.is(ModBlocks.SAWMILL));
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream().filter(x ->
                x.get() != ModBlocks.ASTER.asItem() &&
                x.get() != ModBlocks.CATTAIL.asItem() &&
                x.get() != ModBlocks.FLOWERING_APPLE_LEAVES.asItem() &&
                x.get() != ModBlocks.PILE_OF_LEAVES.asItem());
    }
}
