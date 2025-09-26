package com.necolian.pigcartrevival.network;

import com.necolian.pigcartrevival.PigCartRevival;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record MinecartForwardC2SPayload(int entityId, double x, double z) implements CustomPayload {
    public static final Identifier ID = Identifier.of(PigCartRevival.MODID, "forward");
    public static final CustomPayload.Id<MinecartForwardC2SPayload> PAYLOAD_ID = new CustomPayload.Id<>(ID);
    public static final PacketCodec<RegistryByteBuf, MinecartForwardC2SPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.VAR_INT,
                    MinecartForwardC2SPayload::entityId,
                    PacketCodecs.DOUBLE,
                    MinecartForwardC2SPayload::x,
                    PacketCodecs.DOUBLE,
                    MinecartForwardC2SPayload::z,
                    MinecartForwardC2SPayload::new
            );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}