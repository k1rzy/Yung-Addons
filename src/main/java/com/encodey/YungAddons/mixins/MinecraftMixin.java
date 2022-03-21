package com.encodey.YungAddons.mixins;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.events.LeftClickEvent;
import com.encodey.YungAddons.events.RightClickEvent;
import gg.essential.universal.UChat;
import net.minecraft.block.Block;
import net.minecraft.client.*;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.settings.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.lwjgl.input.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

@Mixin({ Minecraft.class })
public class MinecraftMixin {
    @Shadow
    private int leftClickCounter;
    @Shadow
    public MovingObjectPosition objectMouseOver;
    @Shadow
    public WorldClient theWorld;
    @Shadow
    public PlayerControllerMP playerController;
    @Shadow
    private Entity renderViewEntity;
    @Shadow
    public EntityPlayerSP thePlayer;
    @Shadow
    public GameSettings gameSettings;
    @Shadow
    public boolean inGameHasFocus;
    @Shadow
    private Timer timer;
    @Shadow public GuiScreen currentScreen;

    private static Block currentBlock;
    private float currentDamage;
    private byte blockHitDelay;
    private BlockPos pos;
    private static BlockPos blockpos;

    public float mineSpeed;
    private NetHandlerPlayClient netClientHandler;

    @Inject(method = { "getRenderViewEntity" }, at = { @At("HEAD") })
    public void getRenderViewEntity(final CallbackInfoReturnable<Entity> cir) {

    }

    @Inject(method = { "runTick" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V") })
    public void keyPresses(final CallbackInfo ci) {
        final int k = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + '\u0100') : Keyboard.getEventKey();
        if (Minecraft.getMinecraft().currentScreen == null && Keyboard.getEventKeyState()) {

        }
    }

    @Inject(method = { "rightClickMouse" }, at = { @At("HEAD") }, cancellable = true)
    public void onRightClick(final CallbackInfo callbackInfo) {
        if (MinecraftForge.EVENT_BUS.post((Event)new RightClickEvent())) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = { "clickMouse" }, at = { @At("HEAD") }, cancellable = true)
    public void onClick(final CallbackInfo callbackInfo) {
        if (MinecraftForge.EVENT_BUS.post((Event)new LeftClickEvent())) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = { "sendClickBlockToController" }, at = { @At("RETURN") })
    public void sendClickBlock(final CallbackInfo callbackInfo) {

    }
}
