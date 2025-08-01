package net.licketysplitter.maplecraft.screen;

import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.block.entity.EvaporatorBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class EvaporatorMenu extends AbstractContainerMenu {
    public final EvaporatorBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private final ContainerData panData;


    public EvaporatorMenu(int pContainerID, Inventory inv, FriendlyByteBuf extraData){
        this(pContainerID, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()),
                new SimpleContainerData(5), new SimpleContainerData(8));
    }

    public EvaporatorMenu(int pContainerID, Inventory inv, BlockEntity entity, ContainerData data, ContainerData panData){
        super(ModMenuTypes.EVAPORATOR_MENU.get(), pContainerID);
        checkContainerSize(inv, 5);
        blockEntity = ((EvaporatorBlockEntity) entity);
        this.level = inv.player.level();
        this.data = data;
        this.panData = panData;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new SlotItemHandler(blockEntity.itemStackHandler, 0, 17, 17));
        this.addSlot(new SlotItemHandler(blockEntity.itemStackHandler, 1, 17, 56));
        this.addSlot(new SlotItemHandler(blockEntity.itemStackHandler, 2, 143, 17));
        this.addSlot(new SlotItemHandler(blockEntity.itemStackHandler, 3, 143, 56));
        this.addSlot(new SlotItemHandler(blockEntity.itemStackHandler, 4, 80, 64));

        addDataSlots(data);
        addDataSlots(panData);
    }

    public boolean isBucketEmptying(){
        return data.get(2) < data.get(4);
    }
    public boolean isBottleFilling(){
        return data.get(3) < data.get(4);
    }
    public boolean isLit(){
        return data.get(0) > 0;
    }
    public boolean panHasContents(int panID){
        return panData.get(panID) > 0;
    }
    public int getPanMB(int panID){
        int fullness = panData.get(panID);
        int panSize = 31;

        return panSize * fullness / 1000;
    }
    public float getPanProgress(int panID){
        float panProgress = (float) panData.get(panID + 4) / 1000;
        return Math.max(0.0f, Math.min(1.0f, panProgress));
    }

    public int getBucketScaledProgress(){
        int progress = this.data.get(2);
        int maxProgress = this.data.get(4);
        int progressiveArrowSize = 15;

        return maxProgress != 0 && progress != maxProgress ? (maxProgress - progress) * progressiveArrowSize / maxProgress : 0;
    }

    public int getBottleScaledProgress(){
        int progress = this.data.get(3);
        int maxProgress = this.data.get(4);
        int progressiveArrowSize = 15;

        return maxProgress != 0 && progress != maxProgress ? (maxProgress - progress) * progressiveArrowSize / maxProgress : 0;
    }

    public int getLitScaledProgress(){
        int litTime = this.data.get(0);
        int litDuration = this.data.get(1);
        int flameSize = 14;

        return litDuration != 0 && litTime != litDuration? flameSize * (litDuration - litTime) / litDuration : 0;
    }


    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 5;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.EVAPORATOR.get());
    }

    private void addPlayerInventory(Inventory playerInventory){
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j){
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory){
        for(int i = 0; i < 9; ++i){
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
