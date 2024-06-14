package com.finderfeed.quick_draw;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(QuickDraw.MODID)
public class QuickDraw {

    public static final String MODID = "quick_draw";

    public static final Logger LOGGER = LogManager.getLogger("Quick Draw");

    public QuickDraw(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT,QuickDrawConfig.SPEC);
    }


}
