package com.finderfeed.quick_draw;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class Helpers {

    public static final int MIN_SLOT = 0;
    public static final int MAX_SLOT = 35;


    public static boolean shiftAndControlPressed(){
        long window = Minecraft.getInstance().getWindow().getWindow();
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT) && InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_CONTROL);
    }


    public static boolean isKeyPressed(int key){
        long window = Minecraft.getInstance().getWindow().getWindow();
        return InputConstants.isKeyDown(window,key);
    }
}
