package com.stalemated.simplyswordsbstweaks.mixin;

import net.sweenus.simplyswords.entity.BattleStandardDarkEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import com.stalemated.simplyswordsbstweaks.SimplySwordsBattleStandardConfig;

@Mixin(BattleStandardDarkEntity.class)
public class BattleStandardDarkEntityMixin {
	@Shadow public String standardType;

	// Modifies the haste amplifier for both Abyssal Standard and Galeforce
	@ModifyArg(
			method = "baseTick",
			at = @At(
					value = "INVOKE",
					target = "Lnet/sweenus/simplyswords/util/HelperMethods;incrementStatusEffect(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/effect/StatusEffect;III)V"
			),
			index = 4
	)
	private int modifyHasteConstant(int originalValue) {
		if (!standardType.equals("enigma")) {
			return SimplySwordsBattleStandardConfig.abyssalStandardHasteAmplifier;
		}
		return SimplySwordsBattleStandardConfig.galeforceHasteAmplifier;
	}

	// Modifies the haste radius for both Abyssal Standard and Galeforce
	@ModifyConstant(
			method = "baseTick",
			constant = @Constant(floatValue = 3, ordinal = 0)
	)
	private float modifyHasteRadius(float originalValue) {
		if (!standardType.equals("enigma")) {
			return SimplySwordsBattleStandardConfig.abyssalStandardHasteRadius;
		}
		return SimplySwordsBattleStandardConfig.galeforceHasteRadius;
	}

	// Modifies Galeforce's magic damage value, keeps Abyssal Standard's value unchanged
	// (that one's already editable in the Simply Swords config)
	@ModifyConstant(
			method = "baseTick",
			constant = @Constant(floatValue = 1.0f),
			slice = @Slice(
					from = @At(value = "CONSTANT", args = "stringValue=enigmaChaseRadius")
			)
	)
	private float modifyGaleforceDamage(float originalValue) {
		if (!standardType.equals("enigma")) {
			return originalValue;
		}
		return SimplySwordsBattleStandardConfig.galeforceDamage;
	}

	// Modifies the pain amplifier for Galeforce (Abyssal Standard doesn't apply pain)
	@ModifyArg(
			method = "baseTick",
			at = @At(
					value = "INVOKE",
					target = "Lnet/sweenus/simplyswords/util/HelperMethods;incrementSimplySwordsStatusEffect(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/effect/StatusEffect;III)Lnet/sweenus/simplyswords/effect/instance/SimplySwordsStatusEffectInstance;"
			),
			index = 4
	)
	private int modifyPainAmplifier(int originalValue) {
		return SimplySwordsBattleStandardConfig.galeforcePainAmplifier;
	}

	// The 2 next methods modify the AoE radius for both Abyssal Standard and Galeforce
	// This affects damage, harmful effects as well as team haste for Abyssal Standard
	@ModifyConstant(
			method = "baseTick",
			constant = @Constant(intValue = 6, ordinal = 1)
	)
	private int modifyAbyssalStandardAoeRadius(int constant) {
		return SimplySwordsBattleStandardConfig.abyssalStandardAOERadius;
	}
	@ModifyConstant(
			method = "baseTick",
			constant = @Constant(intValue = 2, ordinal = 0)
	)
	private int modifyGaleforceAoeRadius(int constant) {
		return SimplySwordsBattleStandardConfig.galeforceAOERadius;
	}

	// Modifies the AoE haste amplifier for Abyssal Standard
	@ModifyConstant(
			method = "baseTick",
			constant = @Constant(intValue = 2, ordinal = 0),
			slice = @Slice(
					from = @At(value = "CONSTANT", args = "intValue=80")
			)
	)
	private int modifyAbyssalStandardAoeHasteAmplifier(int originalValue) {
		return SimplySwordsBattleStandardConfig.abyssalStandardAOEHasteAmplifier;
	}

	// Modifies the AoE slowness amplifier for Abyssal Standard
	@ModifyConstant(
			method = "baseTick",
			constant = @Constant(intValue = 0, ordinal = 0),
			slice = @Slice(
					from = @At(value = "CONSTANT", args = "intValue=120")
			)
	)
	private int modifyAbyssalStandardAoeSlownessAmplifier(int originalValue) {
		return SimplySwordsBattleStandardConfig.abyssalStandardAOEHasteAmplifier;
	}

}