package com.necolian.pigcartrevival.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PigCartRevivalClient implements ClientModInitializer {

    public static final String MODID = "pigcartrevival";
    public static final Logger Logger = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitializeClient() {
        Logger.info("PigCartRevivalClient initialized successful!");

        ClientTickEvents.END_CLIENT_TICK.register(PigCartController::onRenderTick);
    }
}
