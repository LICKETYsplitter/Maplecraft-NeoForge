package net.licketysplitter.maplecraft.block.entity;

import net.licketysplitter.maplecraft.block.custom.EvaporatorBlock;
import net.licketysplitter.maplecraft.block.entity.custom.EvaporatorPan;
import net.licketysplitter.maplecraft.item.ModItems;
import net.licketysplitter.maplecraft.screen.EvaporatorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class EvaporatorBlockEntity extends BaseContainerBlockEntity implements MenuProvider {
    protected static final int SLOT_BUCKET_INPUT = 0;
    protected static final int SLOT_BUCKET_OUTPUT = 1;
    protected static final int SLOT_BOTTLE_INPUT = 2;
    protected static final int SLOT_BOTTLE_OUTPUT = 3;
    protected static final int SLOT_FUEL = 4;
    //private NonNullList<ItemStack> inventory = NonNullList.withSize(5, ItemStack.EMPTY);
    public final ItemStackHandler itemStackHandler = new ItemStackHandler(5);

    public static final int DATA_LIT_TIME = 0;
    public static final int DATA_LIT_DURATION = 1;
    public static final int DATA_EMPTY_PROGRESS = 2;
    public static final int DATA_FILL_PROGRESS = 3;
    public static final int DATA_MAX_PROGRESS = 4;
    public static final int NUM_DATA_VALUES = 5;


    public static final int DATA_PAN0_FULLNESS = 0;
    public static final int DATA_PAN1_FULLNESS = 1;
    public static final int DATA_PAN2_FULLNESS = 2;
    public static final int DATA_PAN3_FULLNESS = 3;
    public static final int DATA_PAN0_PROGRESS = 4;
    public static final int DATA_PAN1_PROGRESS = 5;
    public static final int DATA_PAN2_PROGRESS = 6;
    public static final int DATA_PAN3_PROGRESS = 7;
    public static final int NUM_PAN_DATA_VALUES = 8;

    private NonNullList<EvaporatorPan> pans = NonNullList.withSize(4, new EvaporatorPan());

    private final int MAX_PROGRESS = 20;
    private int litTime;
    private int litDuration;
    private int emptyProgress = MAX_PROGRESS;
    private int fillProgress = MAX_PROGRESS;


    protected final ContainerData dataAccess;
    protected final ContainerData panData;

    public EvaporatorBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.EVAPORATOR_BE.get(), pPos, pBlockState);
        this.dataAccess = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch(pIndex){
                    case 0:
                        return EvaporatorBlockEntity.this.litTime;
                    case 1:
                        return EvaporatorBlockEntity.this.litDuration;
                    case 2:
                        return EvaporatorBlockEntity.this.emptyProgress;
                    case 3:
                        return EvaporatorBlockEntity.this.fillProgress;
                    case 4:
                        return EvaporatorBlockEntity.this.MAX_PROGRESS;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0:
                        EvaporatorBlockEntity.this.litTime = pValue;
                        break;
                    case 1:
                        EvaporatorBlockEntity.this.litDuration = pValue;
                        break;
                    case 2:
                        EvaporatorBlockEntity.this.emptyProgress = pValue;
                        break;
                    case 3:
                        EvaporatorBlockEntity.this.fillProgress = pValue;
                        break;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };

        for(int i = 0; i < 4; i++)
            pans.set(i, new EvaporatorPan());

        this.panData = new ContainerData() {
            @Override
            public int get(int pIndex) {
                if(pIndex >= 0 && pIndex < 4)
                    return EvaporatorBlockEntity.this.pans.get(pIndex).fullness;
                else if (pIndex < 8)
                    return (int)(EvaporatorBlockEntity.this.pans.get(pIndex - 4).progress * 1000);
                else
                    return 0;
            }

            @Override
            public void set(int pIndex, int pValue) {
                if(pIndex >= 0 && pIndex < 4)
                    EvaporatorBlockEntity.this.pans.get(pIndex).fullness = pValue;
                else if (pIndex < 8)
                    EvaporatorBlockEntity.this.pans.get(pIndex - 4).progress = (float) pValue / 1000;
            }

            @Override
            public int getCount() {
                return 8;
            }
        };
    }

    @Override
    public int getContainerSize() {
        return itemStackHandler.getSlots();
    }

    @Override
    public void clearContent() {
        super.clearContent();
        pans.clear();
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return  new EvaporatorMenu(pContainerId, pInventory, this, this.dataAccess, this.panData);
    }

    @Override
    protected Component getDefaultName() {return Component.literal("Evaporator");}

    @Override
    protected NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> list = NonNullList.withSize(5, ItemStack.EMPTY);
        for(int i = 0; i < 5; i++){
            list.set(i, this.itemStackHandler.getStackInSlot(i));
        }
        return list;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItems) {
        for(int i = 0; i < 5; i++){
            this.itemStackHandler.setStackInSlot(i, pItems.get(i));
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        itemStackHandler.serialize(output);
        output.putInt("BurnTime", this.litTime);
        output.putInt("BurnDuration", this.litDuration);
        output.putInt("EmptyProgress", this.emptyProgress);
        output.putInt("FillProgress", this.fillProgress);

        for(int i = 0; i < 4; i++){
            output.putInt("FullnessPan" + i, this.pans.get(i).fullness);
            output.putFloat("ProgressPan" + i, this.pans.get(i).progress);
        }
        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        itemStackHandler.deserialize(input);
        this.litTime = input.getIntOr("BurnTime", 0);
        this.litDuration = input.getIntOr("BurnDuration",0);
        this.emptyProgress = input.getIntOr("EmptyProgress", 0);
        this.fillProgress = input.getIntOr("FillProgress", 0);

        for(int i = 0; i < 4; i++){
            this.pans.get(i).fullness = input.getIntOr("FullnessPan" + i, 0);
            this.pans.get(i).progress = input.getFloatOr("ProgressPan" + i, 0.0f);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, EvaporatorBlockEntity blockEntity){
        boolean flag1 = false;
        boolean lit = blockEntity.isLit();;
        if (blockEntity.isLit()) {
            blockEntity.litTime--;
            state = state.setValue(EvaporatorBlock.UPDATER, !state.getValue(EvaporatorBlock.UPDATER));
            level.setBlock(pos, state, 3);
        } else{
            flag1 = true;
            blockEntity.litDuration = 0;
        }

        int emptyPan = blockEntity.firstEmptyPan();
        if(emptyPan != -1 &&
                blockEntity.getItem(SLOT_BUCKET_INPUT).is(ModItems.SAP_BUCKET.get()) &&
                blockEntity.getItem(SLOT_BUCKET_OUTPUT).getCount() < blockEntity.getItem(SLOT_BUCKET_OUTPUT).getMaxStackSize()){
            if(blockEntity.emptyProgress <= 0) {
                blockEntity.removeItem(SLOT_BUCKET_INPUT, 1);
                ItemStack outputItemStack = blockEntity.getItem(1);
                if(blockEntity.getItem(SLOT_BUCKET_OUTPUT).getCount() > 0){
                    blockEntity.getItem(SLOT_BUCKET_OUTPUT).grow(1);
                } else {
                    blockEntity.setItem(SLOT_BUCKET_OUTPUT, Items.BUCKET.getDefaultInstance());
                }
                blockEntity.setItem(1, outputItemStack);
                blockEntity.pans.get(emptyPan).addFluid(1000);
                updateFillLevel(level, pos, state, blockEntity);
                blockEntity.emptyProgress = blockEntity.MAX_PROGRESS;
            }else {
                blockEntity.emptyProgress--;
            }
            flag1 = true;
        } else {
            blockEntity.emptyProgress = blockEntity.MAX_PROGRESS;;
        }

        int donePan = blockEntity.firstDonePan();
        if(donePan != -1 &&
                blockEntity.getItem(2).is(Items.GLASS_BOTTLE) &&
                blockEntity.getItem(3).getCount() < blockEntity.getItem(3).getMaxStackSize()){
            if(blockEntity.fillProgress <= 0) {
                blockEntity.removeItem(2, 1);
                ItemStack outputItemStack = blockEntity.getItem(3);
                int outputItemCount = outputItemStack.getCount();
                if(outputItemCount > 0){
                    outputItemStack.grow(1);
                } else {
                    outputItemStack = ModItems.MAPLE_SYRUP_BOTTLE.get().getDefaultInstance();
                }
                blockEntity.setItem(3, outputItemStack);
                blockEntity.pans.get(donePan).empty();
                updateFillLevel(level, pos, state, blockEntity);
                blockEntity.fillProgress = blockEntity.MAX_PROGRESS;
            } else {
                blockEntity.fillProgress--;
            }
            flag1 = true;
        } else {
            blockEntity.fillProgress = blockEntity.MAX_PROGRESS;
        }

        if(blockEntity.isLit()){
            flag1 = true;
            blockEntity.evaporateAll();
        } else if(blockEntity.canRun() && (blockEntity.getItem(4).getBurnTime(RecipeType.SMELTING, level.fuelValues())) > 0) {
            flag1 = true;
            blockEntity.evaporateAll();
            blockEntity.litDuration = blockEntity.getItem(4).getBurnTime(RecipeType.SMELTING, level.fuelValues());
            blockEntity.litTime = blockEntity.litDuration;
            blockEntity.removeItem(4,1);
        }

        if (lit != blockEntity.isLit()) {
            flag1 = true;
            state = state.setValue(EvaporatorBlock.LIT, Boolean.valueOf(blockEntity.isLit()));
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }
        //blockEntity.printPanReadout();
    }

    private void printPanReadout(){
        for(int i = 0; i <pans.size(); i++){
            System.out.println("Pan" + i + ": Fullness: " + pans.get(i).fullness + " Progress: " + pans.get(i).progress);
        }
    }

    private int firstDonePan(){
        for(int i = 0; i <pans.size(); i++)
            if (pans.get(i).isReady())
                return i;
        return -1;
    }
    private int firstEmptyPan(){
        for(int i = 0; i < pans.size(); i++)
            if(pans.get(i).isEmpty())
                return i;
        return -1;
    }
    private void evaporateAll(){
        for (EvaporatorPan pan : pans) pan.evaporate();
    }
    private boolean canRun(){
        if(this.getAccumulatedFullness() > 0){
            return this.getAccumulatedProgress() < 1.0f;
        }
        return false;
    }

    private boolean isLit() { return this.litTime > 0; }
    private boolean isEmptying() { return this.emptyProgress < this.MAX_PROGRESS; }
    private boolean isFilling() { return this.fillProgress < this.MAX_PROGRESS; }

    protected int getBurnDuration(ItemStack pFuel) {
        if (pFuel.isEmpty()) {
            return 0;
        } else {
            Item item = pFuel.getItem();
            return item.getBurnTime(pFuel, RecipeType.SMELTING, this.level.fuelValues());
        }
    }

    public int getPanFullness( int panID){
        return pans.get(panID).fullness;
    }

    public float getPanProgress(int panID){
        return pans.get(panID).progress;
    }

    public int getAccumulatedFullness(){
        return pans.get(0).fullness + pans.get(1).fullness + pans.get(2).fullness + pans.get(3).fullness;
    }
    public float getAccumulatedProgress(){
        int total = 0;
        int possible = 0;
        for(int i = 0; i < 4; i++){
            total += (int)(pans.get(i).fullness * pans.get(i).progress);
            possible += pans.get(i).fullness;
        }
        return (float) total /possible;
    }

    private static void updateFillLevel(Level level, BlockPos pos, BlockState state, EvaporatorBlockEntity entity){
        int fillLevel = entity.getAccumulatedFullness();
        System.out.println(fillLevel);
        state = state.setValue(EvaporatorBlock.FIll_LEVEL, fillLevel/1000);
        level.setBlock(pos, state, 3);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}
