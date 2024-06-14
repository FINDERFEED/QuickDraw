package com.finderfeed.quick_draw;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(modid = QuickDraw.MODID,bus = EventBusSubscriber.Bus.GAME,value = Dist.CLIENT)
public class QDClientEvents {

    @SubscribeEvent
    public static void keyPressed(InputEvent.Key event){

    }

}
