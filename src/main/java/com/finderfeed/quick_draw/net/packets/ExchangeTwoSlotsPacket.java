package com.finderfeed.quick_draw.net.packets;

import com.finderfeed.quick_draw.Helpers;
import com.finderfeed.quick_draw.QuickDraw;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ExchangeTwoSlotsPacket(int slot1,int slot2) implements CustomPacketPayload {

    public static final StreamCodec<FriendlyByteBuf,ExchangeTwoSlotsPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,x->x.slot1,
            ByteBufCodecs.INT,x->x.slot2,
            ExchangeTwoSlotsPacket::new
    );


    public static final Type<ExchangeTwoSlotsPacket> TYPE = new Type<>(ResourceLocation.tryBuild(QuickDraw.MODID,"exchange_two_slots"));

    public static void handle(ExchangeTwoSlotsPacket payload, IPayloadContext context){
        context.enqueueWork(()->{
            Player player = context.player();
            int slot1 = payload.slot1;
            int slot2 = payload.slot2;
            if (slot1 >= Helpers.MIN_SLOT && slot1 <= Helpers.MAX_SLOT && slot2 >= Helpers.MIN_SLOT && slot2 <= Helpers.MAX_SLOT && slot2 != slot1){
                Inventory inventory = player.getInventory();
                ItemStack item1 = inventory.getItem(slot1);
                ItemStack item2 = inventory.getItem(slot2);
                inventory.setItem(slot1,item2);
                inventory.setItem(slot2,item1);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
