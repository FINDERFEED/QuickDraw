package com.finderfeed.quick_draw.renderables;

import com.finderfeed.quick_draw.misc.ClientHelpers;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public class QuickDrawOverlay implements LayeredDraw.Layer {


    public static RadialMenu menu;

    @Override
    public void render(GuiGraphics graphics, DeltaTracker tracker) {
        var mouse = ClientHelpers.getMousePosition();
        if (menu != null){
            menu.render(graphics,mouse.getFirst(),mouse.getSecond(), tracker.getGameTimeDeltaTicks(),0);
        }
    }
}
