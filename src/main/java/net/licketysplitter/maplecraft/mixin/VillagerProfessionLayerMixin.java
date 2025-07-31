package net.licketysplitter.maplecraft.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.licketysplitter.maplecraft.MaplecraftMod;
import net.licketysplitter.maplecraft.villager.ModVillagers;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(VillagerProfessionLayer.class)
public class VillagerProfessionLayerMixin {

    @Shadow @Final private String path;

    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 1)
    private ResourceLocation modifyResourceLocation(ResourceLocation resourceLocation, @Local(name = "holder") Holder<VillagerType> holder, @Local(name = "holder1") Holder<VillagerProfession> holder1){
        if (holder1.is(ModVillagers.LUMBERJACK)){
            String path1 = resourceLocation.getPath();
            path1 = path1.substring(0,path1.length()-4);
            String folder = holder.getRegisteredName().substring(10);
            if(folder.equals("desert") || folder.equals("jungle") || folder.equals("plains") || folder.equals("savanna")
                    || folder.equals("snow") || folder.equals("swamp") || folder.equals("taiga")) {
                path1 = path1 + "/" + folder + "/lumberjack.png";
                return ResourceLocation.fromNamespaceAndPath(MaplecraftMod.MOD_ID, path1);
            }
        }
        return resourceLocation;
    }
}
