package com.stalemated.simplyswordsbstweaks;

public class BattlesStandardCombatContext {
    public static final ThreadLocal<Boolean> IS_BANNER_DAMAGE = ThreadLocal.withInitial(() -> false);
}
