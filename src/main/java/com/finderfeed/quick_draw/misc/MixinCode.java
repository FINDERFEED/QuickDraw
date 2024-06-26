package com.finderfeed.quick_draw.misc;

import com.finderfeed.quick_draw.QuickDraw;
import com.finderfeed.quick_draw.QuickDrawConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.apache.logging.log4j.Level;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class MixinCode {

    public static boolean slotClicked(Slot slot, int shit,int idk1, ClickType type,boolean isCreativeInventory){
        if (slot != null){
            int slotid = slot.getSlotIndex();
            if (ClientHelpers.shiftAndControlPressed()){
                if (slotid >= Constants.MIN_SLOT && slotid <= Constants.MAX_SLOT){
                    List<Integer> slots = QuickDrawConfig.SLOTS.get();
                    if (!ClientHelpers.isKeyPressed(GLFW.GLFW_KEY_LEFT_ALT)){
                        if (!slots.contains(slotid)) {
                            slots.add(slotid);
                            QuickDrawConfig.SLOTS.save();
                            QuickDraw.LOGGER.log(Level.INFO,"Slot with id " + slotid + " was added.");
                        }
                    }else{
                        if (slots.remove((Integer) slotid)){
                            QuickDrawConfig.SLOTS.save();
                            QuickDraw.LOGGER.log(Level.INFO,"Slot with id " + slotid + " was removed.");
                        }
                    }

                }
                return true;
            }

        }

        return false;
    }


    public static boolean renderFastAccessSlots(GuiGraphics graphics, Slot slot){
        if (!(slot.container instanceof Inventory)) return false;
        if (!QuickDrawConfig.RENDER_QUICK_DRAW_SLOT.get() && !ClientHelpers.shiftAndControlPressed()) return false;
        int id = slot.getSlotIndex();
        if (QuickDrawConfig.SLOTS.get().contains(id)){
            List<Integer> color = QuickDrawConfig.QUICK_DRAW_SLOT_COLOR.get();
            graphics.fill(
                    slot.x,slot.y,slot.x+16,slot.y+16,ClientHelpers.argbToHex(color)
            );
        }
        return false;
    }



}
