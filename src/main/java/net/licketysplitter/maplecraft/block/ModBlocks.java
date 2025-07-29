package net.licketysplitter.maplecraft.block;

import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.block.custom.*;
import net.licketysplitter.maplecraft.item.ModItems;
import net.licketysplitter.maplecraft.particle.ModParticles;
import net.licketysplitter.maplecraft.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Function;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MaplecraftMod.MOD_ID);

    public static final DeferredBlock<Block> SUGAR_GLASS = registerBlock("sugar_glass",
            (properties) -> new Block(properties
                    .instrument(NoteBlockInstrument.HAT).noLootTable()
                    .strength(0.3F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath( MaplecraftMod.MOD_ID, "sugar_glass")))));


    public static final DeferredBlock<Block> MAPLE_SYRUP_BLOCK = registerBlock("maple_syrup_block",
            (properties) -> new MapleSyrupBlock(properties
                    .mapColor(MapColor.COLOR_ORANGE)
                    .speedFactor(0.4F)
                    .jumpFactor(0.5F)
                    .noOcclusion()
                    .sound(SoundType.HONEY_BLOCK)
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath( MaplecraftMod.MOD_ID, "maple_syrup_block")))));

    public static final DeferredBlock<Block> MAPLE_LOG = registerBlock("maple_log",
            (properties) -> new ModFlammableRotatedPillarBlock(logProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "maple_log")))));
    public static final DeferredBlock<Block> MAPLE_WOOD = registerBlock("maple_wood",
            (properties) -> new ModFlammableRotatedPillarBlock(logProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "maple_wood")))));
    public static final DeferredBlock<Block> STRIPPED_MAPLE_LOG = registerBlock("stripped_maple_log",
            (properties) -> new ModFlammableRotatedPillarBlock(logProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "stripped_maple_log")))));
    public static final DeferredBlock<Block> STRIPPED_MAPLE_WOOD = registerBlock("stripped_maple_wood",
            (properties) -> new ModFlammableRotatedPillarBlock(logProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID,"stripped_maple_wood")))));
    public static final DeferredBlock<Block> MAPLE_PLANKS = registerBlock("maple_planks",
            (properties) -> new Block(plankProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "maple_planks")))){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }});

    public static final DeferredBlock<Block> SUGAR_MAPLE_LEAVES = registerBlock("sugar_maple_leaves",
            (properties) -> new UntintedParticleLeavesBlock(0.01f,
                    ModParticles.SUGAR_MAPLE_PARTICLES.get(), leavesProperty(MapColor.COLOR_YELLOW)
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "sugar_maple_leaves")))){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            }
    );
    public static final DeferredBlock<Block> RED_MAPLE_LEAVES = registerBlock("red_maple_leaves",
            (properties) -> new UntintedParticleLeavesBlock(0.01f,
                    ModParticles.RED_MAPLE_PARTICLES.get(), leavesProperty(MapColor.COLOR_RED)
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "red_maple_leaves")))){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });


    public static final DeferredBlock<Block> RED_MAPLE_SAPLING = registerBlock("red_maple_sapling",
            (properties) -> new SaplingBlock(ModTreeGrowers.RED_MAPLE, saplingProperty()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "red_maple_sapling")))));
    public static final DeferredBlock<Block> SUGAR_MAPLE_SAPLING = registerBlock("sugar_maple_sapling",
            (properties) -> new SaplingBlock(ModTreeGrowers.SUGAR_MAPLE, saplingProperty()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "sugar_maple_sapling")))));

    public static final DeferredBlock<Block> MAPLE_STAIRS = registerBlock("maple_stairs",
            (properties) -> new StairBlock(ModBlocks.MAPLE_PLANKS.get().defaultBlockState(),
                    plankProperties()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "maple_stairs")))){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }});
    public static final DeferredBlock<Block> MAPLE_SLAB = registerBlock("maple_slab",
            (properties) -> new SlabBlock(plankProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "maple_slab")))) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }});

    public static final DeferredBlock<Block> MAPLE_BUTTON = registerBlock("maple_button",
            (properties) -> new ButtonBlock(BlockSetType.OAK, 30, buttonProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "maple_button")))));
    public static final DeferredBlock<Block> MAPLE_PRESSURE_PLATE = registerBlock("maple_pressure_plate",
            (properties) -> new PressurePlateBlock(BlockSetType.OAK, pressurePlateProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "maple_pressure_plate")))));

    public static final DeferredBlock<Block> MAPLE_FENCE = registerBlock("maple_fence",
            (properties) -> new FenceBlock(plankProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "maple_fence")))){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }});
    public static final DeferredBlock<Block> MAPLE_FENCE_GATE = registerBlock("maple_fence_gate",
            (properties) -> new FenceGateBlock(Optional.of(WoodType.OAK), plankProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "maple_fence_gate"))),
                    Optional.of(SoundEvents.FENCE_GATE_OPEN), Optional.of(SoundEvents.FENCE_GATE_CLOSE)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }});

    public static final DeferredBlock<Block> MAPLE_DOOR = registerBlock("maple_door",
            (properties) -> new DoorBlock(BlockSetType.OAK, plankProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "maple_door")))
                    .noOcclusion()));
    public static final DeferredBlock<Block> MAPLE_TRAPDOOR = registerBlock("maple_trapdoor",
            (properties) -> new TrapDoorBlock(BlockSetType.OAK, plankProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "maple_trapdoor")))
                    .noOcclusion()));

    public static final DeferredBlock<Block> APPLE_LOG = registerBlock("apple_log",
            (properties) -> new ModFlammableRotatedPillarBlock(logProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_log")))));
    public static final DeferredBlock<Block> APPLE_WOOD = registerBlock("apple_wood",
            (properties) -> new ModFlammableRotatedPillarBlock(logProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_wood")))));
    public static final DeferredBlock<Block> STRIPPED_APPLE_LOG = registerBlock("stripped_apple_log",
            (properties) -> new ModFlammableRotatedPillarBlock(logProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "stripped_apple_log")))));
    public static final DeferredBlock<Block> STRIPPED_APPLE_WOOD = registerBlock("stripped_apple_wood",
            (properties) -> new ModFlammableRotatedPillarBlock(logProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "stripped_apple_wood")))));
    public static final DeferredBlock<Block> APPLE_PLANKS = registerBlock("apple_planks",
            (properties) -> new Block(plankProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_planks")))){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }});

    public static final DeferredBlock<Block> APPLE_STAIRS = registerBlock("apple_stairs",
            (properties) -> new StairBlock(ModBlocks.APPLE_PLANKS.get().defaultBlockState(),
                    plankProperties()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_stairs")))) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }});
    public static final DeferredBlock<Block> APPLE_SLAB = registerBlock("apple_slab",
            (properties) -> new SlabBlock(plankProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_slab")))) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }});

    public static final DeferredBlock<Block> APPLE_BUTTON = registerBlock("apple_button",
            (properties) -> new ButtonBlock(BlockSetType.OAK, 15, buttonProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_button")))));
    public static final DeferredBlock<Block> APPLE_PRESSURE_PLATE = registerBlock("apple_pressure_plate",
            (properties) -> new PressurePlateBlock(BlockSetType.OAK, pressurePlateProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_pressure_plate")))));

    public static final DeferredBlock<Block> APPLE_FENCE = registerBlock("apple_fence",
            (properties) -> new FenceBlock(plankProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_fence")))){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }});
    public static final DeferredBlock<Block> APPLE_FENCE_GATE = registerBlock("apple_fence_gate",
            (properties) -> new FenceGateBlock(Optional.of(WoodType.OAK), plankProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_fence_gate"))), Optional.of(SoundEvents.FENCE_GATE_OPEN), Optional.of(SoundEvents.FENCE_GATE_CLOSE)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }});

    public static final DeferredBlock<Block> APPLE_DOOR = registerBlock("apple_door",
            (properties) -> new DoorBlock(BlockSetType.OAK, plankProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_door")))
                    .noOcclusion()));
    public static final DeferredBlock<Block> APPLE_TRAPDOOR = registerBlock("apple_trapdoor",
            (properties) -> new TrapDoorBlock(BlockSetType.OAK, plankProperties()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_trapdoor")))
                    .noOcclusion()));

    public static final DeferredBlock<Block> APPLE_LEAVES = registerBlock("apple_leaves",
            (properties) -> new TintedParticleLeavesBlock(0.01f, leavesProperty(MapColor.PLANT)
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_leaves")))){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });

    public static final DeferredBlock<Block> FLOWERING_APPLE_LEAVES = registerBlock("flowering_apple_leaves",
            (properties) -> new FloweringAppleLeavesBlock(leavesProperty(MapColor.PLANT)
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "flowering_apple_leaves")))) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }});
    public static final DeferredBlock<Block> APPLE_SAPLING = registerBlock("apple_sapling",
            (properties) -> new SaplingBlock(ModTreeGrowers.APPLE, saplingProperty()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_sapling")))));


    public static final DeferredBlock<Block> PILE_OF_LEAVES = registerBlock("pile_of_leaves",
            (properties) -> new PileOfLeavesBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .sound(SoundType.PINK_PETALS)
                    .pushReaction(PushReaction.DESTROY)
                    .strength(0.1F)
                    .requiresCorrectToolForDrops()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "pile_of_leaves")))));

    public static final DeferredBlock<Block> POISON_IVY = registerBlock("poison_ivy",
            (properties) -> new PoisonIvyBlock(BlockBehaviour.Properties.of()
                    .replaceable()
                    .noOcclusion()
                    .strength(0.2F)
                    .sound(SoundType.GLOW_LICHEN)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "poison_ivy")))));

    public static final DeferredBlock<Block> POISON_IVY_PLANT = registerBlock("poison_ivy_plant",
            (properties) -> new PoisonIvyPlantBlock(BlockBehaviour.Properties.of()
                    .replaceable()
                    .noOcclusion()
                    .strength(0.2F)
                    .sound(SoundType.GLOW_LICHEN)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "poison_ivy_plant")))));

    public static final DeferredBlock<Block> EVAPORATOR = registerBlock("evaporator",
            (properties) -> new EvaporatorBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .lightLevel(state -> state.getValue(EvaporatorBlock.LIT) ? 13 : 0)
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "evaporator")))));

    public static final DeferredBlock<Block> ASTER = registerBlock("aster",
            (properties) -> new TallFlowerBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID,"aster")))));
    public static final DeferredBlock<Block> CATTAIL = registerBlock("cattail",
            (properties) -> new CattailBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "cattail")))));

    public static final DeferredBlock<Block> SINKING_MUD = registerBlock("sinking_mud",
            (properties) -> new SinkingMud(BlockBehaviour.Properties.of()
                    .strength(0.5F)
                    .sound(SoundType.GRAVEL)
                    .mapColor(MapColor.TERRACOTTA_CYAN)
                    .sound(SoundType.MUD)
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "sinking_mud")))));

    public static final DeferredBlock<Block> SAWMILL = registerBlock("sawmill",
            (properties) -> new SawmillBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(3.5F)
                    .noOcclusion()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "sawmill")))));



    private static BlockBehaviour.Properties logProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(p_152624_ -> p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD : MapColor.COLOR_BROWN)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties plankProperties(){
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F, 3.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties leavesProperty(MapColor mapColor){
        return BlockBehaviour.Properties.of()
                .mapColor(mapColor)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isValidSpawn(ModBlocks::never)
                .isViewBlocking(ModBlocks::never)
                .isSuffocating(ModBlocks::never)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
                .isRedstoneConductor(ModBlocks::never);
    }

    private static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {return false; }
    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return true;
    }
    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    private static BlockBehaviour.Properties saplingProperty(){
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties pressurePlateProperties(){
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .forceSolidOn()
                .instrument(NoteBlockInstrument.BASS)
                .noCollission()
                .strength(0.5F)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties buttonProperties() {
        return BlockBehaviour.Properties.of()
                .noCollission()
                .strength(0.5F)
                .pushReaction(PushReaction.DESTROY);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerItem(name, (properties) -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()
                .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath( MaplecraftMod.MOD_ID, name)))));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
