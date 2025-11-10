package net.licketysplitter.maplecraft.entity.custom;

import net.licketysplitter.maplecraft.block.ModBlocks;
import net.licketysplitter.maplecraft.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.function.IntFunction;
import java.util.function.Supplier;

public class ModBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModBoatEntity.class, EntityDataSerializers.INT);

    public ModBoatEntity(EntityType<? extends Boat> entityType, Level level, Supplier<Item> boatItemSupplier) {
        super(entityType, level, boatItemSupplier);
    }

    public ModBoatEntity(EntityType<? extends Boat> entityType, Level level, Supplier<Item> boatItemSupplier, double pX, double pY, double pZ){
        this(entityType, level, boatItemSupplier);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    public void setVariant(Type variant){
        this.entityData.set(DATA_ID_TYPE, variant.ordinal());
    }

    public Type getModVariant(){
        return Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, ModBoatEntity.Type.byId(this.entityData.get(DATA_ID_TYPE)).ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag compoundTag){
        compoundTag.putString("Type", this.getModVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(CompoundTag compoundTag){
        if(compoundTag.contains("Type")){
            this.setVariant(Type.byName(String.valueOf(compoundTag.getString("Type"))));
        }
    }

    public static enum Type implements StringRepresentable
    {
        MAPLE(ModBlocks.MAPLE_PLANKS.get(), "maple"),
        APPLE(ModBlocks.APPLE_PLANKS.get(), "apple");

        private final String name;
        private final Block planks;
        public static final StringRepresentable.EnumCodec<ModBoatEntity.Type> CODEC = StringRepresentable.fromEnum(ModBoatEntity.Type::values);
        private static final IntFunction<ModBoatEntity.Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        private Type(Block planks, String name){
            this.name = name;
            this.planks = planks;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public String getName(){
            return this.name;
        }

        public Block getPlanks(){
            return this.planks;
        }

        public String toString(){
            return this.name;
        }

        public static ModBoatEntity.Type byId(int id){
            return BY_ID.apply(id);
        }

        public static ModBoatEntity.Type byName(String name){
            return CODEC.byName(name);
        }
    }
}
