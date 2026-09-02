package dev.wardevil.network;

import java.util.UUID;

import dev.wardevil.WarDevil;
import dev.wardevil.state.WarDevilAction;
import dev.wardevil.state.WarDevilGrabAction;
import dev.wardevil.state.WarDevilLocomotion;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record S2CWarDevilStatePayload(
        UUID playerId,
        long sequence,
        boolean transformed,
        WarDevilLocomotion locomotion,
        WarDevilAction action,
        WarDevilGrabAction grabAction,
        int locomotionElapsedTicks,
        int actionElapsedTicks,
        int grabElapsedTicks) implements CustomPacketPayload {

    public static final Type<S2CWarDevilStatePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(WarDevil.MOD_ID, "war_devil_state"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CWarDevilStatePayload> STREAM_CODEC =
            StreamCodec.of((buf, payload) -> payload.write(buf), S2CWarDevilStatePayload::decode);

    private void write(RegistryFriendlyByteBuf buf) {
        buf.writeUUID(playerId);
        buf.writeVarLong(sequence);
        buf.writeBoolean(transformed);
        buf.writeVarInt(locomotion.ordinal());
        buf.writeVarInt(action.ordinal());
        buf.writeVarInt(grabAction.ordinal());
        buf.writeVarInt(locomotionElapsedTicks);
        buf.writeVarInt(actionElapsedTicks);
        buf.writeVarInt(grabElapsedTicks);
    }

    private static S2CWarDevilStatePayload decode(RegistryFriendlyByteBuf buf) {
        return new S2CWarDevilStatePayload(
                buf.readUUID(), buf.readVarLong(), buf.readBoolean(),
                WarDevilLocomotion.fromNetwork(buf.readVarInt()),
                WarDevilAction.fromNetwork(buf.readVarInt()),
                WarDevilGrabAction.fromNetwork(buf.readVarInt()),
                Math.max(0, buf.readVarInt()),
                Math.max(0, buf.readVarInt()),
                Math.max(0, buf.readVarInt()));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
