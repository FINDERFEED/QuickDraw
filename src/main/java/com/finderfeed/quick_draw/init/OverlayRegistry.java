package com.finderfeed.quick_draw.init;


import com.finderfeed.quick_draw.QuickDraw;
import com.finderfeed.quick_draw.renderables.QuickDrawOverlay;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = QuickDraw.MODID,bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class OverlayRegistry {

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event){
        event.registerAboveAll(ResourceLocation.tryBuild(QuickDraw.MODID,"quick_draw_overlay"),new QuickDrawOverlay());
    }

}
