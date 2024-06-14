package com.finderfeed.quick_draw.mixins;


import com.finderfeed.quick_draw.misc.MixinCode;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {

    @Inject(method = "slotClicked",at=@At("HEAD"), cancellable = true)
    private void slotClicked(Slot slot, int slotId, int idk1, ClickType clickType, CallbackInfo ci){
        if (slot != null && slot.container instanceof Inventory) {
            boolean shouldCancel = MixinCode.slotClicked(slot, slotId, idk1, clickType, false);
            if (shouldCancel) {
                ci.cancel();
            }
        }
    }


}
