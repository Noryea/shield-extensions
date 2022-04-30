package cn.noryea.shield_indicator;

import cn.noryea.shield_indicator.config.Config;
import net.fabricmc.api.ModInitializer;

public class ShieldIndicatorMod implements ModInitializer {

    @Override
    public void onInitialize() {
        Config.init("shield_indicator", Config.class);
    }

}
