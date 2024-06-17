package com.finderfeed.quick_draw.misc;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class ClientHelpers {



    public static float[] hexRGBToRGBA(int hex,float alpha){
        float r = (hex & 0xff0000) >> 16;
        float g = (hex & 0x00ff00) >> 8;
        float b = (hex & 0x0000ff);
        return new float[]{
                r/255f,g/255f,b/255f,alpha
        };
    }

    public static int argbToHex(List<Integer> argb){
        int col = 0;
        col += argb.get(3);
        col += argb.get(2) << 8;
        col += argb.get(1) << 16;
        col += argb.get(0) << 24;
        return col;
    }

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
