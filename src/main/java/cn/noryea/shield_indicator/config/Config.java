package cn.noryea.shield_indicator.config;

public class Config extends TinyConfig{



    public enum CoolingDownMode { NEVER, OFFHAND, ALWAYS }

    @Entry public static boolean enabled = true;
    @Entry public static boolean showDelay = true;
    @Entry public static CoolingDownMode coolingDownMode = CoolingDownMode.OFFHAND;



}
