package com.finderfeed.quick_draw.init;

import com.finderfeed.quick_draw.QuickDraw;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

@EventBusSubscriber(modid = QuickDraw.MODID,bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class QDCoreShaders {


    public static ShaderInstance RADIAL_MENU = null;
    public static RenderStateShard.ShaderStateShard RADIAL_MENU_SHADER = new RenderStateShard.ShaderStateShard(()-> RADIAL_MENU);


    @SubscribeEvent
    public static void register(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(),ResourceLocation.tryBuild(QuickDraw.MODID,"radial_menu"),
                        DefaultVertexFormat.POSITION_TEX),
                (instance)->{
                    QDCoreShaders.RADIAL_MENU = instance;
                });
    }
}
