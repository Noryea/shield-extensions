package cn.noryea.shield_extensions.mixin;

import cn.noryea.shield_extensions.ShieldExtensionsMod;
import cn.noryea.shield_extensions.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.noryea.shield_extensions.ShieldExtensionsMod.willBlockedByShield;

@Environment(EnvType.CLIENT)
@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin extends Entity {

    public ProjectileEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow @Nullable Entity owner;

    @Inject(method = "onEntityHit", at = @At("TAIL"))
    public void playSound(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity target = entityHitResult.getEntity();
        if (Config.arrowBlockedSound) {
            if (owner != null && target.isPlayer() && willBlockedByShield((LivingEntity) target, this.getPos())) {
                owner.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 1.0F);
            }
        }
    }

}
