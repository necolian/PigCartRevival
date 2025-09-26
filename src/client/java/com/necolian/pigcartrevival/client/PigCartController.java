package com.necolian.pigcartrevival.client;

import com.necolian.pigcartrevival.network.MinecartForwardC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;

public class PigCartController {

    public static void onRenderTick(MinecraftClient client) {
        PlayerEntity player = client.player;
        if (player == null || !player.hasVehicle()) return;

        Entity vehicle = player.getVehicle();
        if (!(vehicle instanceof PigEntity pig) || !pig.hasVehicle()) return;

        Entity pigVehicle = pig.getVehicle();
        if (!(pigVehicle instanceof AbstractMinecartEntity mc)) return;

        int entityId = mc.getId();

        // 前進か後退かをチェック
        boolean forward = client.options.forwardKey.isPressed();
        boolean back    = client.options.backKey.isPressed();

        if (forward) {
            sendMessage(entityId, false, player); // false = 前進
        } else if (back) {
            sendMessage(entityId, true, player);  // true  = 後退
        }
    }

    private static void sendMessage(int entityId, boolean backward, PlayerEntity player) {
        float yaw = player.getYaw();
        double rad = Math.toRadians(yaw);

        // 前進方向のベクトル（単位ベクトル）
        double dx = Math.cos(rad);
        double dz = Math.sin(rad);

        // 後退なら逆方向
        if (backward) {
            dx = -dx;
            dz = -dz;
        }

        // MinecartForwardC2SPayload を dx/dz を使うように拡張する
        ClientPlayNetworking.send(new MinecartForwardC2SPayload(entityId, dx, dz));
    }

}
