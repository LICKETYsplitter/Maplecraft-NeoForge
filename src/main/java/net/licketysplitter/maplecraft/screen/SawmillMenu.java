package net.licketysplitter.maplecraft.screen;

import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.recipe.custom.SawmillRecipe;
import net.licketysplitter.maplecraft.recipe.custom.SawmillRecipeInput;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SawmillMenu extends AbstractContainerMenu {
    public static final int INPUT_SLOT = 0;
    public static final int RESULT_SLOT = 1;
    private static final int INV_SLOT_START = 2;
    private static final int INV_SLOT_END = 29;
    private static final int USE_ROW_SLOT_START = 29;
    private static final int USE_ROW_SLOT_END = 38;
    private final ContainerLevelAccess access;

    final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private final Level level;
    private SelectableRecipe.SingleInputSet<SawmillRecipe> recipesForInput = SelectableRecipe.SingleInputSet.empty();
    private final SelectableRecipe.SingleInputSet<SawmillRecipe> sawMillRecipes;

    private ItemStack input = ItemStack.EMPTY;

    long lastSoundTime;
    final Slot inputSlot;

    final Slot resultSlot;
    Runnable slotUpdateListener = () -> {};
    public final Container container = new SimpleContainer(1) {
        @Override
        public void setChanged() {

            super.setChanged();
            SawmillMenu.this.slotsChanged(this);
            SawmillMenu.this.slotUpdateListener.run();
        }
    };
    final ResultContainer resultContainer = new ResultContainer();

    public SawmillMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public SawmillMenu(int containerId, Inventory playerInventory, final ContainerLevelAccess access) {
        super(ModMenuTypes.SAWMILL_MENU.get(), containerId);
        this.access = access;
        this.level = playerInventory.player.level();
        this.inputSlot = this.addSlot(new Slot(this.container, 0, 20, 33));
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 1, 143, 33) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                stack.onCraftedBy(player, stack.getCount());
                SawmillMenu.this.resultContainer.awardUsedRecipes(player, this.getRelevantItems());
                ItemStack itemstack = SawmillMenu.this.inputSlot.remove(1);
                if (!itemstack.isEmpty()) {
                    SawmillMenu.this.setupResultSlot(SawmillMenu.this.selectedRecipeIndex.get());
                }

                access.execute((p_393254_, p_393255_) -> {
                    long i = p_393254_.getGameTime();
                    if (SawmillMenu.this.lastSoundTime != i) {
                        p_393254_.playSound(null, p_393255_, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        SawmillMenu.this.lastSoundTime = i;
                    }
                });
                super.onTake(player, stack);
            }

            private List<ItemStack> getRelevantItems() {
                return List.of(SawmillMenu.this.inputSlot.getItem());
            }
        });
        this.addStandardInventorySlots(playerInventory, 8, 84);
        this.addDataSlot(this.selectedRecipeIndex);
        this.sawMillRecipes = getSawMillRecipes();
    }

    public SawmillMenu(int pContainerID, Inventory inv, RegistryFriendlyByteBuf extraData) {
        this(pContainerID, inv);
    }

    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }

    public SelectableRecipe.SingleInputSet<SawmillRecipe> getVisibleRecipes() {
        return this.recipesForInput;
    }

    public int getNumberOfVisibleRecipes() {
        return this.recipesForInput.size();
    }

    public boolean hasInputItem() {
        return this.inputSlot.hasItem() && !this.recipesForInput.isEmpty();
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlocks.SAWMILL.get());
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (this.selectedRecipeIndex.get() == id) {
            return false;
        } else {
            if (this.isValidRecipeIndex(id)) {
                this.selectedRecipeIndex.set(id);
                this.setupResultSlot(id);
            }

            return true;
        }
    }

    private boolean isValidRecipeIndex(int recipeIndex) {
        return recipeIndex >= 0 && recipeIndex < this.recipesForInput.size();
    }

    @Override
    public void slotsChanged(Container inventory) {
        ItemStack itemstack = this.inputSlot.getItem();
        if (!itemstack.is(this.input.getItem())) {
            this.input = itemstack.copy();
            this.setupRecipeList(itemstack);
        }
    }

    private SelectableRecipe.SingleInputSet<SawmillRecipe> getSawMillRecipes(){
        RecipeManager recipes;
        if(this.level instanceof ServerLevel serverLevel)
            recipes = serverLevel.recipeAccess();
        else{
            recipes = ServerLifecycleHooks.getCurrentServer().getRecipeManager();
        }
        List<SelectableRecipe.SingleInputEntry<SawmillRecipe>> list = new ArrayList<>();

        recipes.recipeMap().values().forEach(recipeHolder ->{
                Recipe<?> recipe = recipeHolder.value();
                if (!(!recipe.isSpecial() && recipe.placementInfo().isImpossibleToPlace())) {
                    if (recipe instanceof SawmillRecipe sawmillRecipe) {
                        list.add(
                            new SelectableRecipe.SingleInputEntry<>(
                                    sawmillRecipe.input(),
                                    new SelectableRecipe<>(sawmillRecipe.resultDisplay(), Optional.of((RecipeHolder<SawmillRecipe>)recipeHolder))
                            )
                    );
                }
            }
        });
        return new SelectableRecipe.SingleInputSet<>(list);
    }

    private void setupRecipeList(ItemStack stack) {
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            this.recipesForInput = sawMillRecipes.selectByInput(stack);
        } else {
            this.recipesForInput = SelectableRecipe.SingleInputSet.empty();
        }
    }

    void setupResultSlot(int id) {
        Optional<RecipeHolder<SawmillRecipe>> optional;
        if (!this.recipesForInput.isEmpty() && this.isValidRecipeIndex(id)) {
            SelectableRecipe.SingleInputEntry<SawmillRecipe> singleinputentry = this.recipesForInput.entries().get(id);
            optional = singleinputentry.recipe().recipe();
        } else {
            optional = Optional.empty();
        }

        optional.ifPresentOrElse(p_379191_ -> {
            this.resultContainer.setRecipeUsed((RecipeHolder<?>)p_379191_);
            this.resultSlot.set(p_379191_.value().assemble(new SawmillRecipeInput(this.container.getItem(0)), this.level.registryAccess()));
        }, () -> {
            this.resultSlot.set(ItemStack.EMPTY);
            this.resultContainer.setRecipeUsed(null);
        });
        this.broadcastChanges();
    }

    @Override
    public MenuType<?> getType() {
        return ModMenuTypes.SAWMILL_MENU.get();
    }

    public void registerUpdateListener(Runnable listener) {
        this.slotUpdateListener = listener;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultContainer && super.canTakeItemForPickAll(stack, slot);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index == 1) {
                item.onCraftedBy(itemstack1, player);
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.sawMillRecipes.acceptsInput(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 2 && index < 29) {
                if (!this.moveItemStackTo(itemstack1, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemstack1, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
            if (index == 1) {
                player.drop(itemstack1, false);
            }

            this.broadcastChanges();
        }

        return itemstack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.resultContainer.removeItemNoUpdate(1);
        this.access.execute((p_40313_, p_40314_) -> this.clearContainer(player, this.container));
    }
}
