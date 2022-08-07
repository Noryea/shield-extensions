package cn.noryea.shield_extensions.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class Config extends MidnightConfig {

    @Entry public static boolean disablingSound = true;

    @Entry public static boolean blockedSound = true;

    //@Entry public static boolean tryBlockingAnimation = true;

    @Entry public static boolean showDelay = false;

    public enum CoolingDownMode { NEVER, OFFHAND, ALWAYS }
    @Entry public static CoolingDownMode coolingDownMode = CoolingDownMode.NEVER;

    public enum EnemyRaisingDelay { NEVER, fourT, fiveT }

    @Entry public static EnemyRaisingDelay enemyRaisingDelay = EnemyRaisingDelay.NEVER;

}
