package com.stalemated.simplyswordsbstweaks.mixin;

import net.minecraft.entity.LivingEntity;
import net.sweenus.simplyswords.effect.ElementalVortexEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElementalVortexEffect.class)
public class ElementalVortexEffectMixin {
    @Shadow public LivingEntity sourceEntity;
    @Shadow public int additionalData;

    // Resets entity data on call to fix bugs such as Enigma banner hitting the player after using Tempest's ability
    @Inject(
            method = "applyUpdateEffect",
            at = @At("HEAD")
    )
    private void resetSourceEntityData(LivingEntity livingEntity, int amplifier, CallbackInfo ci) {
        this.sourceEntity = null;
        this.additionalData = 0;
    }
}
