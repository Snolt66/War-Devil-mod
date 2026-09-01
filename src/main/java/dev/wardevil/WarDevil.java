package dev.wardevil;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(WarDevil.MOD_ID)
public final class WarDevil {
    public static final String MOD_ID = "wardevil";
    public static final Logger LOGGER = LogUtils.getLogger();

    public WarDevil(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("War Devil loading.");
    }
}
