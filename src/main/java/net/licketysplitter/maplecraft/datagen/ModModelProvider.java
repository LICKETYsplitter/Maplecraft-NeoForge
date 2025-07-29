package net.licketysplitter.maplecraft.datagen;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.item.ModItems;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, MaplecraftMod.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        //render types
        TexturedModel.Provider translucentCube = TexturedModel.CUBE.updateTemplate(template ->
                        template.extend().renderType("minecraft:translucent").build());
        TexturedModel.Provider cutoutCube = TexturedModel.CUBE.updateTemplate(template ->
                template.extend().renderType("minecraft:cutout").build());

        //BLOCKS


        blockModels.createTrivialBlock(ModBlocks.MAPLE_SYRUP_BLOCK.get(), translucentCube);
        //blockModels.createTrivialCube(ModBlocks.SUGAR_GLASS.get());

        blockModels.createTrivialBlock(ModBlocks.SUGAR_GLASS.get(), translucentCube);

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

        blockModels.createTrivialBlock(ModBlocks.SUGAR_MAPLE_LEAVES.get(), cutoutCube);
        blockModels.createTrivialBlock(ModBlocks.RED_MAPLE_LEAVES.get(), cutoutCube);


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

        itemModels.itemModelOutput.accept(ModBlocks.APPLE_LEAVES.get().asItem(),
                new BlockModelWrapper.Unbaked(
                        ModelLocationUtils.getModelLocation(ModBlocks.APPLE_LEAVES.get().asItem()),
                        List.of(new Constant(-12012264))
                ));
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
