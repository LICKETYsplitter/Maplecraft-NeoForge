package net.licketysplitter.maplecraft.datagen.loot;

import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.block.custom.CattailBlock;
import net.licketysplitter.maplecraft.block.custom.FloweringAppleLeavesBlock;
import net.licketysplitter.maplecraft.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Set;

public class ModBlockLootTablesProvider extends BlockLootSubProvider {
    public ModBlockLootTablesProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.MAPLE_SYRUP_BLOCK.get());

        //add(ModBlocks.SUGAR_GLASS.get(), block -> createSugarGlassDrop(ModBlocks.SUGAR_GLASS.get(), ModItems.SUGAR_GLASS_SHARD.get()));

        dropSelf(ModBlocks.MAPLE_LOG.get());
        dropSelf(ModBlocks.MAPLE_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_MAPLE_LOG.get());
        dropSelf(ModBlocks.STRIPPED_MAPLE_WOOD.get());
        dropSelf(ModBlocks.MAPLE_PLANKS.get());

        add(ModBlocks.SUGAR_MAPLE_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.SUGAR_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        add(ModBlocks.RED_MAPLE_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.RED_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        dropSelf(ModBlocks.MAPLE_STAIRS.get());
        dropSelf(ModBlocks.MAPLE_BUTTON.get());
        dropSelf(ModBlocks.MAPLE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.MAPLE_TRAPDOOR.get());
        dropSelf(ModBlocks.MAPLE_FENCE.get());
        dropSelf(ModBlocks.MAPLE_FENCE_GATE.get());

        add(ModBlocks.MAPLE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.MAPLE_SLAB.get()));
        add(ModBlocks.MAPLE_DOOR.get(),
                block -> createDoorTable(ModBlocks.MAPLE_DOOR.get()));

        dropSelf(ModBlocks.RED_MAPLE_SAPLING.get());
        dropSelf(ModBlocks.SUGAR_MAPLE_SAPLING.get());

        dropSelf(ModBlocks.PILE_OF_LEAVES.get());
        dropSelf(ModBlocks.EVAPORATOR.get());

        add(ModBlocks.ASTER.get(), block -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        add(ModBlocks.CATTAIL.get(), createCattailsDrop());

        dropSelf(ModBlocks.APPLE_LOG.get());
        dropSelf(ModBlocks.APPLE_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_APPLE_LOG.get());
        dropSelf(ModBlocks.STRIPPED_APPLE_WOOD.get());
        dropSelf(ModBlocks.APPLE_PLANKS.get());
        dropSelf(ModBlocks.APPLE_STAIRS.get());
        dropSelf(ModBlocks.APPLE_BUTTON.get());
        dropSelf(ModBlocks.APPLE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.APPLE_TRAPDOOR.get());
        dropSelf(ModBlocks.APPLE_FENCE.get());
        dropSelf(ModBlocks.APPLE_FENCE_GATE.get());
        add(ModBlocks.APPLE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.APPLE_SLAB.get()));
        add(ModBlocks.APPLE_DOOR.get(),
                block -> createDoorTable(ModBlocks.APPLE_DOOR.get()));
        add(ModBlocks.APPLE_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.APPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        add(ModBlocks.FLOWERING_APPLE_LEAVES.get(), createFloweringAppleLeavesDrops());
        dropSelf(ModBlocks.APPLE_SAPLING.get());

        dropSelf(ModBlocks.SINKING_MUD.get());


        add(ModBlocks.POISON_IVY.get(), createPoisonIvyDrop());
        add(ModBlocks.POISON_IVY_PLANT.get(), createPoisonIvyDrop());

        dropSelf(ModBlocks.SAWMILL.get());
    }

    protected LootTable.Builder createSugarGlassDrop(Block pBlock, Item item) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                                .apply(LimitCount.limitCount(IntRange.range(3, 4)))));
    }

    protected LootTable.Builder createPoisonIvyDrop(){
            return LootTable.lootTable().withPool(
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(ModBlocks.POISON_IVY.get()).when(this.hasShears())));
    }

    protected LootTable.Builder createCattailsDrop(){
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        (LootPoolEntryContainer.Builder<?>)this.applyExplosionDecay(
                                                ModBlocks.CATTAIL.get(),
                                                LootItem.lootTableItem(ModBlocks.CATTAIL.get())
                                                        .apply(
                                                                List.of(2, 3, 4),
                                                                blockState -> SetItemCountFunction.setCount(ConstantValue.exactly((float)blockState.intValue()))
                                                                        .when(
                                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.CATTAIL.get())
                                                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CattailBlock.CATTAILS, blockState))
                                                                        )
                                                        )
                                                        .when(
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.CATTAIL.get())
                                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))
                                                        )
                                        )
                                )
                );
    }

    protected LootTable.Builder createFloweringAppleLeavesDrops() {
        return super.createLeavesDrops(ModBlocks.FLOWERING_APPLE_LEAVES.get(), ModBlocks.APPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(this.hasShears().or(super.hasSilkTouch().invert()))
                                .add(
                                        ((LootPoolSingletonContainer.Builder<?>)this.applyExplosionCondition(ModBlocks.FLOWERING_APPLE_LEAVES.get(), LootItem.lootTableItem(Items.APPLE)))
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.FLOWERING_APPLE_LEAVES.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FloweringAppleLeavesBlock.AGE, 3))
                                                )
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.FLOWERING_APPLE_LEAVES.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LeavesBlock.PERSISTENT, false)))
                                )
                                .add(
                                        ((LootPoolSingletonContainer.Builder<?>)this.applyExplosionCondition(ModBlocks.FLOWERING_APPLE_LEAVES.get(), LootItem.lootTableItem(ModItems.GREEN_APPLE.get())))
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.FLOWERING_APPLE_LEAVES.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FloweringAppleLeavesBlock.AGE, 2))
                                                )
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.FLOWERING_APPLE_LEAVES.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LeavesBlock.PERSISTENT, false)))
                                )
                );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
