package com.stalemated.simplyswordsbstweaks.mixin.compat;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.stalemated.simplyswordsbstweaks.compat.RebalanceCompatHelper;
import com.stalemated.simplyswordsbstweaks.BattlesStandardCombatContext;

@Mixin(value = LivingEntity.class, priority = 2000)
public class RebalanceCompatMixin {

    @Inject(method = "modifyAppliedDamage", at = @At("RETURN"), cancellable = true)
    private void applyRebalanceToBattleStandardDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof PlayerEntity)) return;

        if (BattlesStandardCombatContext.IS_BANNER_DAMAGE.get()) {

            float damage = cir.getReturnValue();

            RebalanceCompatHelper.init();
            float newDamage = RebalanceCompatHelper.calculateNewDamage(amount, damage);

            cir.setReturnValue(newDamage);
        }
    }
}