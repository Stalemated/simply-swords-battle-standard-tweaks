package com.stalemated.simplyswordsbstweaks.mixin;

import net.sweenus.simplyswords.entity.BattleStandardEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static com.stalemated.simplyswordsbstweaks.BattlesStandardCombatContext.IS_BANNER_DAMAGE;

@Mixin(BattleStandardEntity.class)
public class BattleStandardEntityMixin {

    @Inject(method = "baseTick", at = @At("HEAD"))
    private void startBattleStandardContext(CallbackInfo ci) {
        IS_BANNER_DAMAGE.set(true);
    }

    @Inject(method = "baseTick", at = @At("RETURN"))
    private void endBattleStandardContext(CallbackInfo ci) {
        IS_BANNER_DAMAGE.set(false);
    }
}