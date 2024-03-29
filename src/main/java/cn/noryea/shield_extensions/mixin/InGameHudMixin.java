package cn.noryea.shield_extensions.mixin;

import cn.noryea.shield_extensions.config.Config;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.noryea.shield_extensions.ShieldExtensionsMod.getBlockingProcess;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

    @Shadow
    MinecraftClient client;

    @Shadow private int scaledWidth;
    @Shadow private int scaledHeight;

    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    public void injectedRender(MatrixStack matrices, CallbackInfo ci) {
        GameOptions gameOptions = this.client.options;
        ClientPlayerEntity player = this.client.player;
        if (gameOptions.getPerspective().isFirstPerson() && !player.isSpectator() && !(gameOptions.debugEnabled && !gameOptions.hudHidden && !this.client.player.hasReducedDebugInfo() && !gameOptions.getReducedDebugInfo().getValue())) {
            renderInjected(matrices, player);
        }
    }

    private void renderInjected(MatrixStack matrices,ClientPlayerEntity player) {

        int i = this.scaledHeight / 2 - 7 + 23;
        int j = this.scaledWidth/ 2 - 8;

        RenderSystem.setShaderTexture(0, new Identifier("textures/gui/shield_indicator.png"));
        if (player.getItemCooldownManager().isCoolingDown(Items.SHIELD.asItem()) && shouldShowCoolingDown(player)) {
            this.drawTexture(matrices, j, i, 48, 0, 16, 14);
        } else if (player.isUsingItem() && !player.getActiveItem().isEmpty()) {
            ItemStack activeItem = player.getActiveItem();
            if (activeItem.getUseAction() == UseAction.BLOCK) {
                int a = (getBlockingProcess(player, !Config.showDelay)) * 2;
                if (a == 12) {
                    this.drawTexture(matrices, j, i, 32, 0, 16, 14);
                } else {
                    this.drawTexture(matrices, j, i, 0, 0, 16, 14);
                    this.drawTexture(matrices, j, i + 12 - a, 16, 12 - a, 16, a + 2);
                }
            }
        }
        RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
    }



    private boolean shouldShowCoolingDown(ClientPlayerEntity player) {
        if (Config.coolingDownMode == Config.CoolingDownMode.OFFHAND) {
            return (player.getOffHandStack().getItem() == Items.SHIELD);
        } else return (Config.coolingDownMode == Config.CoolingDownMode.ALWAYS);
    }

}
