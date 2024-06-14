package com.finderfeed.quick_draw.net;

import com.finderfeed.quick_draw.QuickDraw;
import com.finderfeed.quick_draw.net.packets.ExchangeTwoSlotsPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = QuickDraw.MODID,bus = EventBusSubscriber.Bus.MOD)
public class QDPacketRegistry {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar(QuickDraw.MODID)
                .versioned("1")
                .optional();
        registrar.commonToServer(ExchangeTwoSlotsPacket.TYPE, ExchangeTwoSlotsPacket.CODEC,ExchangeTwoSlotsPacket::handle);
    }

}
