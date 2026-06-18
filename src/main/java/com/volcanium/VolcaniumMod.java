package com.volcanium;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VolcaniumMod implements ClientModInitializer {
    public static final String MOD_ID = "volcanium";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Volcanium Engine: High-performance frustum culling active!");
    }
}

