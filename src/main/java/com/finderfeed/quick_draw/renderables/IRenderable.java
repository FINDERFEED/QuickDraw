package com.finderfeed.quick_draw.renderables;

import net.minecraft.client.gui.GuiGraphics;

@FunctionalInterface
public interface IRenderable {

    void render(GuiGraphics graphics, float x, float y);

}
