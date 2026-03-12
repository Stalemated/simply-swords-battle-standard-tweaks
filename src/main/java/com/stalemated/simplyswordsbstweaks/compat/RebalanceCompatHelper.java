package com.stalemated.simplyswordsbstweaks.compat;

import net.fabricmc.loader.api.FabricLoader;
import static com.stalemated.simplyswordsbstweaks.SimplySwordsBattleStandardTweaks.LOGGER;
import java.lang.reflect.Field;

public class RebalanceCompatHelper {

    private static boolean initialized = false;
    private static boolean hasRebalance = false;
    private static Object configInstance = null;

    private static Field enablePvpField;
    private static Field pvpMultiplierField;
    private static Field enableGlobalField;
    private static Field globalMultiplierField;
    private static Field globalStartField;

    public static void init() {
        if (initialized) return;
        initialized = true;

        if (FabricLoader.getInstance().isModLoaded("rebalance")) {
            try {
                Class<?> rebalanceClass = Class.forName("elocindev.rebalance.ReBalance");
                Field configField = rebalanceClass.getField("CONFIG");
                configInstance = configField.get(null);
                Class<?> configClass = configInstance.getClass();

                enablePvpField = configClass.getField("enable_pvp_rebalance");
                pvpMultiplierField = configClass.getField("pvp_damage_multiplier");
                enableGlobalField = configClass.getField("enable_global_reduction");
                globalMultiplierField = configClass.getField("global_reduction_multiplier");
                globalStartField = configClass.getField("global_reduction_start");

                hasRebalance = true;
                LOGGER.info("Successfully hooked into ReBalance config for Battle Standards.");
            } catch (Exception e) {
                hasRebalance = false;
            }
        }
    }

    public static float calculateNewDamage(float originalAmount, float damage) {
        if (!hasRebalance || configInstance == null) return damage;

        try {
            float newAmount = damage;
            LOGGER.info("Initial damage: {}", damage);
            boolean enableGlobal = enableGlobalField.getBoolean(configInstance);
            boolean enablePvp = enablePvpField.getBoolean(configInstance);
            float globalStart = globalStartField.getFloat(configInstance);

            if (enableGlobal && originalAmount > globalStart) {
                float globalMultiplier = globalMultiplierField.getFloat(configInstance);
                newAmount *= globalMultiplier;
                newAmount += globalStart;
                LOGGER.info("New global damage: {}", newAmount);
            }

            if (enablePvp) {
                float pvpMultiplier = pvpMultiplierField.getFloat(configInstance);
                newAmount *= pvpMultiplier;
                LOGGER.info("New pvp damage: {}", newAmount);
            }

            return newAmount;
        } catch (Exception e) {
            return damage;
        }
    }
}