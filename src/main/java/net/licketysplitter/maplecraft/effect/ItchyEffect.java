package net.licketysplitter.maplecraft.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;


public class ItchyEffect extends MobEffect {
    protected ItchyEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }


    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if(entity.isSleeping() ){
            entity.stopSleeping();
            entity.playSound(SoundEvents.CHORUS_FLOWER_DEATH, 2.0f, 0.5f);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        int i = 125 >> pAmplifier;
        return i > 0 ? pDuration % i == 0 : true;
    }
}
