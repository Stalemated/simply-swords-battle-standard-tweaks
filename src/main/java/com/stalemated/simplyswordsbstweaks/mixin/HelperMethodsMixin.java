package com.stalemated.simplyswordsbstweaks.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.sweenus.simplyswords.effect.instance.SimplySwordsStatusEffectInstance;
import net.sweenus.simplyswords.util.HelperMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(HelperMethods.class)
public class HelperMethodsMixin {
    // Swaps currentAmplifier for amplifierMax to fix the bug on buffs
    @Inject(
            method = "incrementStatusEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z",
                    ordinal = 0
            ),
            cancellable = true
    )
    private static void fixHigherEffects(LivingEntity livingEntity, StatusEffect statusEffect, int duration, int amplifier, int amplifierMax, CallbackInfo ci) {

        int currentDuration = Objects.requireNonNull(livingEntity.getStatusEffect(statusEffect)).getDuration();

        livingEntity.addStatusEffect(new StatusEffectInstance(
                statusEffect, Math.max(currentDuration, duration), amplifierMax, false, false, true));

        ci.cancel();
    }

    // Swaps currentAmplifier for amplifierMax to fix the bug on SimplySwords Status Effects
    @Inject(
            method = "incrementSimplySwordsStatusEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z",
                    ordinal = 0
            ),
            cancellable = true
    )
    private static void fixHigherSimplySwordsEffects(LivingEntity livingEntity, StatusEffect statusEffect, int duration, int amplifier, int amplifierMax, CallbackInfoReturnable<SimplySwordsStatusEffectInstance> cir) {
        int currentDuration = Objects.requireNonNull(livingEntity.getStatusEffect(statusEffect)).getDuration();

        SimplySwordsStatusEffectInstance statusReturn = new SimplySwordsStatusEffectInstance(
                statusEffect, Math.max(currentDuration, duration), amplifierMax, false, false, true);

        livingEntity.addStatusEffect(statusReturn);

        cir.setReturnValue(statusReturn);
    }
}