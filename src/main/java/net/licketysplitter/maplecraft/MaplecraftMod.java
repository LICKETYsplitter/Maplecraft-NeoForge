package net.licketysplitter.maplecraft;

import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.block.entity.ModBlockEntities;
import net.licketysplitter.maplecraft.effect.ModEffects;
import net.licketysplitter.maplecraft.item.ModItems;
import net.licketysplitter.maplecraft.particle.ModParticles;
import net.licketysplitter.maplecraft.recipe.ModRecipes;
import net.licketysplitter.maplecraft.screen.EvaporatorScreen;
import net.licketysplitter.maplecraft.screen.ModMenuTypes;
import net.licketysplitter.maplecraft.screen.SawmillMenu;
import net.licketysplitter.maplecraft.screen.SawmillScreen;
import net.licketysplitter.maplecraft.util.ModCreativeModeTabs;
import net.licketysplitter.maplecraft.villager.ModVillagers;
import net.licketysplitter.maplecraft.worldgen.biome.ModBiomeColors;
import net.licketysplitter.maplecraft.worldgen.biome.ModFeature;
import net.licketysplitter.maplecraft.worldgen.biome.ModTerrablender;
import net.licketysplitter.maplecraft.worldgen.tree.ModTrunkPlacerTypes;
import net.licketysplitter.maplecraft.block.entity.EvaporatorBlockEntity;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterNamedRenderTypesEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.awt.*;

@Mod(MaplecraftMod.MOD_ID)
public class MaplecraftMod {
    public static final String MOD_ID = "maplecraft";
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public MaplecraftMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModBlocks.register(modEventBus);
        ModParticles.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModTerrablender.registerBiomes();
        ModFeature.register(modEventBus);
        ModTrunkPlacerTypes.register(modEventBus);
        ModEffects.register(modEventBus);
        ModRecipes.register(modEventBus);


        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        //modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }



    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAPLE_SYRUP_BLOCK.get(), ChunkSectionLayer.TRANSLUCENT);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SUGAR_GLASS.get(), ChunkSectionLayer.TRANSLUCENT);

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SUGAR_MAPLE_SAPLING.get(), ChunkSectionLayer.CUTOUT);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.RED_MAPLE_SAPLING.get(), ChunkSectionLayer.CUTOUT);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.APPLE_SAPLING.get(), ChunkSectionLayer.CUTOUT);

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAPLE_DOOR.get(), ChunkSectionLayer.TRANSLUCENT);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAPLE_TRAPDOOR.get(), ChunkSectionLayer.TRANSLUCENT);
        }

        @SubscribeEvent
        public static void registerColorResolver(RegisterColorHandlersEvent.ColorResolvers event){
            event.register(ModBiomeColors.BIRCH_COLOR_RESOLVER);
            event.register(ModBiomeColors.EVERGREEN_COLOR_RESOLVER);
        }

        @SubscribeEvent
        public static void registerColoredBlocks(RegisterColorHandlersEvent.Block event){
            event.register(new BlockColor() {
                @Override
                public int getColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex) {
                    if (pLevel != null && pPos != null) {
                        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

                        if (blockEntity instanceof EvaporatorBlockEntity) {
                            int white = 255;
                            int red = 113;
                            int green = 57;
                            int blue = 9;
                            float progress = ((EvaporatorBlockEntity) blockEntity).getAccumulatedProgress();

                            red = white - (int)((white - red) * progress);
                            green = white - (int)((white - green) * progress);
                            blue = white - (int)((white - blue) * progress);

                            Color returnColor = new Color(red, green, blue);
                            return returnColor.hashCode();
                        }
                    }
                    return 0xFFFFFF;
                }
            }, ModBlocks.EVAPORATOR.get());

            /*
            event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null &&
                    pPos != null ? BiomeColors.getAverageFoliageColor(pLevel,pPos) : FoliageColor.getDefaultColor(), ModBlocks.PILE_OF_LEAVES.get());

             */
            event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null &&
                    pPos != null ? ModBiomeColors.getBirchColor(pLevel, pPos) : FoliageColor.FOLIAGE_BIRCH, Blocks.BIRCH_LEAVES);

            event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null &&
                    pPos != null ? ModBiomeColors.getEvergreenColor(pLevel, pPos) : FoliageColor.FOLIAGE_EVERGREEN, Blocks.SPRUCE_LEAVES);

            event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null &&
                    pPos != null ? BiomeColors.getAverageFoliageColor(pLevel,pPos) : FoliageColor.FOLIAGE_DEFAULT, ModBlocks.APPLE_LEAVES.get());
            event.register((pState, pLevel, pPos, pTintIndex) -> pLevel != null &&
                    pPos != null ? BiomeColors.getAverageFoliageColor(pLevel,pPos) : FoliageColor.FOLIAGE_DEFAULT, ModBlocks.FLOWERING_APPLE_LEAVES.get());

            event.register((blockState, blockAndTintGetter, blockPos, pInt) -> 0xb08623, ModBlocks.SUGAR_MAPLE_SAPLING.get());
            event.register((blockState, blockAndTintGetter, blockPos, pInt) -> 0x8b2a2a, ModBlocks.RED_MAPLE_LEAVES.get());
        }

        /*
        @SubscribeEvent
        public static void registerColoredItems(RegisterColorHandlersEvent.ItemTintSources event){
            event.register(ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "apple_leaves"), ItemTintColors.MAP_CODEC);
            event.register(ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "flowering_apple_leaves"), ItemTintColors.MAP_CODEC);
        }

         */

        @SubscribeEvent
        public static void registerNamedRenderTypes(RegisterNamedRenderTypesEvent event){
            event.register(ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, "evaporator"),
                    ChunkSectionLayer.CUTOUT, RenderType.entityTranslucent(ResourceLocation.withDefaultNamespace("textures/blocks/water_still.png")));
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event){
            event.register(ModMenuTypes.EVAPORATOR_MENU.get(), EvaporatorScreen::new);
            event.register(ModMenuTypes.SAWMILL_MENU.get(), SawmillScreen::new);
        }
    }
}
