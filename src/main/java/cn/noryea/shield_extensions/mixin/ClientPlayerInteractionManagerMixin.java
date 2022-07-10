package cn.noryea.shield_extensions.mixin;

import cn.noryea.shield_extensions.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.noryea.shield_extensions.ShieldExtensionsMod.willBlockedByShield;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

    @Inject(method = "attackEntity", at = @At("TAIL"))
    public void playSound(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (player.getMainHandStack().getItem() instanceof AxeItem) {
            if (Config.disablingSound && target instanceof PlayerEntity && willBlockedByShield((LivingEntity) target, player.getPos())) {
                player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 1.8F, 0.66F);
            }
        }
    }

}
