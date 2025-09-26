package com.necolian.pigcartrevival;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import com.necolian.pigcartrevival.network.MinecartForwardC2SPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PigCartRevival implements ModInitializer {

    public static final String MODID = "pigcartrevival";
    public static final Logger Logger = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        Logger.info("PigCartRevival initialized successful!");
        PayloadTypeRegistry.playC2S().register(MinecartForwardC2SPayload.PAYLOAD_ID, MinecartForwardC2SPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MinecartForwardC2SPayload.PAYLOAD_ID, PigCartManager::setMineCart);
    }
}
