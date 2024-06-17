package com.finderfeed.quick_draw.misc;

import com.finderfeed.quick_draw.QuickDraw;
import com.finderfeed.quick_draw.QuickDrawConfig;
import com.finderfeed.quick_draw.init.QDClientInit;
import com.finderfeed.quick_draw.net.packets.ExchangeTwoSlotsPacket;
import com.finderfeed.quick_draw.renderables.QuickDrawOverlay;
import com.finderfeed.quick_draw.renderables.RadialMenu;
import com.finderfeed.quick_draw.renderables.RenderHelper;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = QuickDraw.MODID,bus = EventBusSubscriber.Bus.GAME,value = Dist.CLIENT)
public class QDClientEvents {


    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Post event){
        if (QuickDrawOverlay.menu != null){
            if (Minecraft.getInstance().mouseHandler.isMouseGrabbed()){
                QuickDrawOverlay.menu = null;
            }
        }
    }

    @SubscribeEvent
    public static void keyPressed(InputEvent.Key event){
        if (event.getKey() == QDClientInit.OPEN_QUICK_DRAW_MENU_KEY.getKey().getValue() && Minecraft.getInstance().screen == null){
            List<Integer> slots = QuickDrawConfig.SLOTS.get();
            if (!slots.isEmpty()) {
                if (event.getAction() == GLFW.GLFW_PRESS) {
                    Minecraft.getInstance().mouseHandler.releaseMouse();
                    RadialMenu menu = generateMenu(slots);
                    QuickDrawOverlay.menu = menu;

                } else if (event.getAction() == GLFW.GLFW_RELEASE) {
                    RadialMenu menu = QuickDrawOverlay.menu;
                    if (menu != null){
                        var mouse = ClientHelpers.getMousePosition();
                        var section = menu.getSection(menu.getSectionUnderMouse(mouse.getFirst(),mouse.getSecond()));
                        if (section != null){
                            section.onPress.run();
                        }
                    }
                    Minecraft.getInstance().mouseHandler.grabMouse();
                }
            }
        }
    }

    private static float[] argbToFloatArray(List<Integer> color){
        return new float[]{
                color.get(1) / 255f,
                color.get(2) / 255f,
                color.get(3) / 255f,
                color.get(0) / 255f
        };
    }

    private static RadialMenu generateMenu(List<Integer> slots){
        List<Integer> ints = new ArrayList<>(slots);
        ints.sort(Comparator.comparingInt(i->i));
        float[] unselected = argbToFloatArray(QuickDrawConfig.QUICK_DRAW_MENU_UNSELECTED.get());
        float[] selected = argbToFloatArray(QuickDrawConfig.QUICK_DRAW_MENU_SELECTED.get());

        RadialMenu.RadialMenuShaderSettings settings =
                new RadialMenu.RadialMenuShaderSettings(0,0.5f,0.1f,
                        unselected,
                        selected,
                        0.05f
                );
        List<RadialMenu.RadialMenuSection> sections = new ArrayList<>();
        Player player = Minecraft.getInstance().player;
        for (int slot : ints){
            if (slot < Constants.MIN_SLOT || slot > Constants.MAX_SLOT) continue;

            ItemStack itemInSlot = player.getInventory().getItem(slot);
            if (itemInSlot.isEmpty()){
                itemInSlot = Items.STRUCTURE_VOID.getDefaultInstance();
            }

            ItemStack finalItemInSlot = itemInSlot;
            RadialMenu.RadialMenuSection section = new RadialMenu.RadialMenuSection(
                    ()->{
                        PacketDistributor.sendToServer(new ExchangeTwoSlotsPacket(player.getInventory().selected,slot));
                    },
                    (graphics, x, y) -> {
                        RenderHelper.renderScaledGuiItemCentered(graphics, finalItemInSlot, x, y, 1f, 100);

                    },
                    (graphics, x, y) -> {
                        if (!finalItemInSlot.is(Items.STRUCTURE_VOID)) {
                            graphics.renderTooltip(Minecraft.getInstance().font, finalItemInSlot, (int) x, (int) y);
                        }else{
                            graphics.renderTooltip(Minecraft.getInstance().font, Component.translatable("quick_draw.no_item_found"), (int) x, (int) y);
                        }
                    }
            );
            section.setPressable(false);
            sections.add(section);
        }
        if (sections.isEmpty()) return null;
        Window window = Minecraft.getInstance().getWindow();
        int scaledWidth = window.getGuiScaledWidth();
        int scaledHeight = window.getGuiScaledHeight();
        int relX = scaledWidth / 2;
        int relY = scaledHeight / 2;
        RadialMenu menu = new RadialMenu(settings,relX,relY,100,
                sections
        );
        return menu;
    }

}
