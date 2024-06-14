package com.finderfeed.quick_draw;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class QuickDrawConfig {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<List<Integer>> SLOTS;
    public static final ModConfigSpec.ConfigValue<String> QUICK_DRAW_SLOT_COLOR;
    public static final ModConfigSpec.ConfigValue<String> QUICK_DRAW_MENU_UNSELECTED;
    public static final ModConfigSpec.ConfigValue<String> QUICK_DRAW_MENU_SELECTED;
    public static final ModConfigSpec.ConfigValue<Boolean> RENDER_QUICK_DRAW_SLOT;

    static {
        BUILDER.push("Quick draw config");


        SLOTS = BUILDER.comment("Fast access slots").define("slots", new ArrayList<>());

        QUICK_DRAW_SLOT_COLOR = BUILDER.comment("Color in hex format that indicates which slot is a fast access slot")
                .define("fast_access_slot_color","0x00ff00");

        QUICK_DRAW_MENU_SELECTED = BUILDER.comment("Color in hex format (ARGB) when hovered over a section in quick draw menu")
                .define("selected_section_color","0xaaaaaa");

        QUICK_DRAW_MENU_UNSELECTED = BUILDER.comment("Color in hex format (ARGB) when of an unselected section in quick draw menu")
                .define("unselected_section_color","0xeeeeee");

        RENDER_QUICK_DRAW_SLOT = BUILDER.comment("If true - always renders which slot is a fast access one, if false - renders only when ctrl+shift is pressed")
                        .define("render_slot",false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
