package com.finderfeed.quick_draw.mixins;

import com.finderfeed.quick_draw.misc.MixinCode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerMenuMixin {

    @Inject(method = "renderSlot", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V",shift = At.Shift.AFTER))
    public void renderSlot(GuiGraphics graphics, Slot slot, CallbackInfo ci){
        AbstractContainerScreen screen = (AbstractContainerScreen) (Object) this;
        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
            if (MixinCode.renderFastAccessSlots(graphics, slot)) {
                ci.cancel();
            }
        }
    }

}
