package com.finderfeed.quick_draw.init;


import com.finderfeed.quick_draw.QuickDraw;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = QuickDraw.MODID,bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class QDClientInit {

    public static final String KEY_CATEGORY = "quick_draw.keys.category";

    public static final KeyMapping OPEN_QUICK_DRAW_MENU_KEY = new KeyMapping("quick_draw.keys.open_menu",InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_TAB,KEY_CATEGORY);

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event){
        event.register(OPEN_QUICK_DRAW_MENU_KEY);
    }

}
