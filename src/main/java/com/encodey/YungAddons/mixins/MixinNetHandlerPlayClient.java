package com.encodey.YungAddons.mixins;

//import com.encodey.YungAddons.features.Mining.Warnings.Titanium;
import com.encodey.YungAddons.utils.UtilsFull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.*;
import net.minecraft.util.EnumParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    private static final String TARGET = "Lnet/minecraft/entity/player/EntityPlayer;" +
            "setPositionAndRotation(DDDFF)V";
    @Redirect(method="handlePlayerPosLook", at=@At(value="INVOKE", target=TARGET))
    public void handlePlayerPosLook_setPositionAndRotation(EntityPlayer player, double x, double y, double z, float yaw, float pitch) {
        player.setPositionAndRotation(x, y, z, yaw, pitch);
    }

    @Redirect(method="handleParticles", at=@At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/multiplayer/WorldClient;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;ZDDDDDD[I)V"
    ))
    public void handleParticles(WorldClient world, EnumParticleTypes particleTypes, boolean isLongDistance,
                                double xCoord, double yCoord, double zCoord,
                                double xOffset, double yOffset, double zOffset, int[] params) {
    }

    @Inject(method="handleSpawnMob", at=@At("RETURN"))
    public void handleSpawnMob(S0FPacketSpawnMob packetIn, CallbackInfo ci) {
        //CollectionLogManager.getInstance().onEntityMetadataUpdated(packetIn.getEntityID());
    }

    @Inject(method="handleSetSlot", at=@At("RETURN"))
    public void handleSetSlot(S2FPacketSetSlot packetIn, CallbackInfo ci) {

    }

    @Inject(method="handleOpenWindow", at=@At("RETURN"))
    public void handleOpenWindow(S2DPacketOpenWindow packetIn, CallbackInfo ci) {

    }

    @Inject(method="handleCloseWindow", at=@At("RETURN"))
    public void handleCloseWindow(S2EPacketCloseWindow packetIn, CallbackInfo ci) {

    }

    @Inject(method="handleWindowItems", at=@At("RETURN"))
    public void handleOpenWindow(S30PacketWindowItems packetIn, CallbackInfo ci) {

    }

    @Inject(method="handleRespawn", at=@At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
            shift = At.Shift.AFTER))
    public void handleOpenWindow(S07PacketRespawn packetIn, CallbackInfo ci) {

    }

    @Inject(method="handleBlockChange", at=@At("HEAD"))
    public void handleBlockChange(S23PacketBlockChange packetIn, CallbackInfo ci) {

    }

    @Inject(method="addToSendQueue", at=@At("HEAD"))
    public void addToSendQueue(Packet packet, CallbackInfo ci) {
        if(packet instanceof C0EPacketClickWindow) {

        }
    }

    @Inject(method="handlePlayerListHeaderFooter", at=@At("HEAD"))
    public void handlePlayerListHeaderFooter(S47PacketPlayerListHeaderFooter packetIn, CallbackInfo ci) {
        UtilsFull.getInstance().header = packetIn.getHeader().getFormattedText().length() == 0 ? null : packetIn.getHeader();
        UtilsFull.getInstance().footer = packetIn.getFooter().getFormattedText().length() == 0 ? null : packetIn.getFooter();
    }
}
