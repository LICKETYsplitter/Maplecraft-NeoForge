package net.licketysplitter.maplecraft.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class ModChestBoatEntity extends ChestBoat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModChestBoatEntity.class, EntityDataSerializers.INT);

    public ModChestBoatEntity(EntityType<? extends ChestBoat> entityType, Level level, Supplier<Item> chestBoatItemSupplier) {
        super(entityType, level, chestBoatItemSupplier);
    }

    public ModChestBoatEntity(EntityType<? extends ChestBoat> entityType, Level level, Supplier<Item> chestBoatItemSupplier, double pX, double pY, double pZ){
        this(entityType, level, chestBoatItemSupplier);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    public void setVariant(ModBoatEntity.Type variant){
        this.entityData.set(DATA_ID_TYPE, variant.ordinal());
    }

    public ModBoatEntity.Type getModVariant(){
        return ModBoatEntity.Type.byId(this.entityData.get(DATA_ID_TYPE));
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
            this.setVariant(ModBoatEntity.Type.byName(String.valueOf(compoundTag.getString("Type"))));
        }
    }
}
