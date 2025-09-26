package com.necolian.pigcartrevival;

import com.necolian.pigcartrevival.network.MinecartForwardC2SPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.Context;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.DefaultMinecartController;

public class PigCartManager {

    public static void setMineCart(MinecartForwardC2SPayload payload, Context context) {
        double x = payload.x();
        double z = payload.z();
        Entity e = context.player().getWorld().getEntityById(payload.entityId());
        if (e instanceof AbstractMinecartEntity mc) {
            DefaultMinecartController controller = (DefaultMinecartController) mc.getController();
            ((IMixinDefaultMinecartController) controller).pigCartRevival$setPigCartDirection(x, z);
        }
    }

}
