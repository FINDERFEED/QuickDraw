package com.finderfeed.quick_draw.misc;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class ClientHelpers {




    public static boolean shiftAndControlPressed(){
        long window = Minecraft.getInstance().getWindow().getWindow();
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT) && InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_CONTROL);
    }


    public static boolean isKeyPressed(int key){
        long window = Minecraft.getInstance().getWindow().getWindow();
        return InputConstants.isKeyDown(window,key);
    }

    public static Pair<Integer,Integer> getMousePosition(){
        int i = (int)(
                Minecraft.getInstance().mouseHandler.xpos()
                        * (double)Minecraft.getInstance().getWindow().getGuiScaledWidth()
                        / (double)Minecraft.getInstance().getWindow().getScreenWidth()
        );
        int j = (int)(
                Minecraft.getInstance().mouseHandler.ypos()
                        * (double)Minecraft.getInstance().getWindow().getGuiScaledHeight()
                        / (double)Minecraft.getInstance().getWindow().getScreenHeight()
        );
        return new Pair<>(i,j);
    }
}
