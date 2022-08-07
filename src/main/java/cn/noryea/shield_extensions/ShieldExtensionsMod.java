package cn.noryea.shield_extensions;

import cn.noryea.shield_extensions.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShieldExtensionsMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Config.init("shield_extensions", Config.class);
    }

    public static boolean willBlockedByShield(@NotNull LivingEntity entity, @Nullable Vec3d pos) {
        if (entity.isBlocking()) {
            if (pos != null) {
                Vec3d vec3d = entity.getRotationVec(1.0F);
                Vec3d vec3d2 = pos.relativize(entity.getPos()).normalize();
                vec3d2 = new Vec3d(vec3d2.x, 0.0, vec3d2.z);
                return vec3d2.dotProduct(vec3d) < 0.0;
            }
        }
        return false;
    }

    public static int getBlockingProcess(PlayerEntity player, Boolean ignored) {
        if (player.isUsingItem() && !player.getActiveItem().isEmpty()) {
            Item activeItem = player.getActiveItem().getItem();
            if (ignored) {
                return 6;
            } else if (activeItem.getUseAction(player.getActiveItem()) != UseAction.BLOCK) {
                return 0;
            } else {
                return Math.min(activeItem.getMaxUseTime(player.getActiveItem()) - player.getItemUseTimeLeft(), 6);
            }
        }
        return 0;
    }
}
