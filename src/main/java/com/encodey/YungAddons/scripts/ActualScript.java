package com.encodey.YungAddons.scripts;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.ScriptAliases;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.events.Overlay;
import com.encodey.YungAddons.features.Farming.ProfitCalc;
import com.encodey.YungAddons.features.Scripts.ScriptExt;
import com.encodey.YungAddons.handler.ScoreboardHandler;
import com.encodey.YungAddons.handler.TimerHandler;
import com.encodey.YungAddons.utils.*;
import com.encodey.YungAddons.utils.MathUtils.MathUtils;
import com.encodey.YungAddons.utils.MathUtils.TimeHelper;
import gg.essential.api.EssentialAPI;
import gg.essential.universal.UChat;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.util.*;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import sun.font.Script;

import java.security.Key;
import java.util.*;

/**
 * @author k1rzy (encodey)
 */

public class ActualScript extends ScriptUtils {

    private boolean reconnectCdDueResync;
    public static boolean reconnectCooldown;
    // dont bully me for those booleans im too lazy for switches and enums, etc.
    protected boolean clicked;

    protected boolean successResync;
    protected boolean forward;

    private boolean left;

    private boolean right;

    private boolean noneLoc;

    private boolean switchingLayersLadder;

    private boolean oneTimeCheckForward;

    private boolean oneLobby;

    private boolean resyncMessage;

    private boolean oneLimbo;

    public boolean inHub;

    private boolean sold;

    private boolean oneOpen;

    public boolean started;

    public static boolean isOnIsland;

    private boolean movingLeft;

    private boolean movingRight;

    private boolean continueInvFull;

    private boolean movingForward;

    private boolean isOnLimbo;

    private boolean isOnLobby;

    private boolean fakeInvFull;

    private boolean stuckStatel;


    public static boolean dontCountSessions;

    private boolean extraHubWarp;

    private boolean layerswitchSafe;

    private boolean extraDisable;


    private boolean alreadyChangedYawLadder;

    private boolean alreadyNorth;
    private boolean alreadyWest;

    private final Set<Block> blocks;

    private boolean wasOnLadder;
    public static int workedSessions;

    private double savex = -999;
    private double savez = -999;

    private Thread thread2;

    private int countDesync;
    public static boolean wasOnLadderForRe;
    public static boolean switchedLayersLadder;
    public static boolean cdPad;
    public static boolean activeContest;

    public static FacingRow facingRow;

    private boolean oneReconnect;
    private boolean oneUnPress;

    private boolean swingItemFocus;

    private boolean invFull;

    private boolean actualDesync;

    private List<ItemStack> oldInv;

    private int oldInvCount;

    private SameInvState sameInvState;

    private boolean itWasLocked;
    private boolean getResync;
    private TimeHelper sameInvTimer;

    private static boolean oneCommand;

    private static boolean onEnable;

    private LadderState ladderState;

    private static boolean onBedrockCage;

    private boolean dontSpamLobby;
    private boolean dontSpamLimbo;
    private boolean dontSpamHub;

    private boolean oneSuccess;

    private float lastYaw = -999;
    private CharSequence[] cropHarvested = { "455,", "456", "457", "459,", "460" };

    private boolean banned;

    static int windowId;
    private boolean successSetSpawn;
    private boolean successLobby;

    private boolean dontSpamMoveMessages;
    private boolean countingResync;
    private boolean currentScreenUpdate;
    public static Minecraft mc = Minecraft.getMinecraft();
    private int countBedrock;
    private net.minecraft.network.play.client.C07PacketPlayerDigging C07PacketPlayerDigging = new C07PacketPlayerDigging();
    private boolean setspawnLadder;
    private boolean lobbyladder;

    private boolean wasLockedYaw;

    private int done;
    public ActualScript() {
        this.blocks = new HashSet<>(Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.trapdoor, Blocks.iron_trapdoor, Blocks.oak_door, Blocks.iron_door));
    }

    public static boolean inLimbo() {
        if(mc.objectMouseOver == null || mc.objectMouseOver.getBlockPos() == null) return false;
        ItemStack heldItem = mc.thePlayer.getHeldItem();
        return (mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() == Blocks.wall_sign && heldItem == null);
    }
    public static boolean standingOnPad() {
        BlockPos pos = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY - 1, Minecraft.getMinecraft().thePlayer.posZ);
        return mc.theWorld.getBlockState(pos).getBlock() == Blocks.end_portal_frame;
    }
    public boolean setspawnRight;

    public boolean setspawnLeft;

    public boolean isMovingRight;

    public boolean isMovingLeft;

    public boolean isMovingForward;
    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        if(reconnectCooldown || reconnectCdDueResync) return;
        isOnIsland = SkyblockUtils.isInIsland();
        World world = mc.theWorld;
        if(this.right && world != null && (SkyblockUtils.isInIsland() || isOnIsland)) {
            this.right = true;
            this.left = false;
            this.forward = false;
            this.isMovingForward = false;
            this.isMovingLeft = false;
            this.isMovingRight = true;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), false);
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
        }
        if(this.left && world != null && (SkyblockUtils.isInIsland() || isOnIsland)) {
            this.right = false;
            this.left = true;
            this.forward = false;
            this.isMovingForward = false;
            this.isMovingLeft = true;
            this.isMovingRight = false;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), false);
        }
        if(oneLimbo) oneLimbo = false;
        if(oneLobby) oneLobby = false;
    }
    boolean shit;
    private void VerticalFailSafe() {
        if(reconnectCooldown || reconnectCdDueResync) return;
        isOnIsland = SkyblockUtils.isInIsland();
        if(Config.typeoffarm == 0) return;
        if(!setspawnLeft && this.left) {
            setspawnRight = false;
            setspawnLeft = true;
            mc.thePlayer.sendChatMessage("/setspawn");
        }
        if(!setspawnRight && this.right) {
            setspawnLeft = false;
            setspawnRight = true;
        }
    }

    public static String testBooleans;
    private boolean stillMovingOnLadder;
    private boolean oneGold;
    private boolean oneJacob;
    private boolean onetimeStuck;
    private boolean onetimeStuckMsg;
    private boolean oneLobbyPlayer;
    private boolean oneLimboPlayer;
    private boolean oneHubPlayer;
    private boolean oneMoveDebugRight;
    private boolean oneMoveDebugLeft;
    private boolean oneMoveDebugForward;
    private boolean oneLadderCheck;
    private boolean onePressBackLadder;
    @SubscribeEvent
    public void onTick(final Overlay event) {
        if(reconnectCooldown) return;
        //mc.thePlayer.addChatMessage(new ChatComponentText("Slots: " + mc.thePlayer.inventory.getFirstEmptyStack()));
        if(shit) {
            shit = false;
            Config.nwscript = true;
            onTick();
        }
        if(this.reconnectCdDueResync) {
            this.reconnectCdDueResync = false;
            new Thread(() -> {
                try {
                    if(Config.resyncdelay <= 0) {
                        reconnectCdDueResync = false;
                        ScriptAliases.addMessage(EnumChatFormatting.BLUE + "Yung Scripts >> Resync delay is zero.");
                        return;
                    }
                    Thread.sleep((Config.resyncdelay * 1000) + Utils.random.nextInt(400));
                    EssentialAPI.getNotifications().push("Yung Scripts", "Script resumed");
                    Config.nwscript = true;
                    done = 1;
                    shit = true;
                    this.successResync = true;
                    this.reconnectCdDueResync = false;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            if(this.successResync && this.reconnectCdDueResync) {
                this.reconnectCdDueResync = false;
/*                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Resyncer disabled. Warping to the island..."));
                mc.thePlayer.sendChatMessage("/play sb");*/
                this.successResync = false;
            }
            return;
        }
        if(!Config.nwscript) return;
        if(PlayerUtil.isInLimbo() || SkyblockUtils.isInLimbo || isOnLimbo || inLimbo()) {
            this.right = false;
            this.left = false;
            this.oneHubPlayer = false;
            this.oneLobbyPlayer = false;
            if(this.dontSpamLimbo) {
                new Thread(() -> {
                    try {
                        Thread.sleep(20000 + Utils.random.nextInt(1200));
                        this.dontSpamLimbo = false;
                        mc.thePlayer.sendChatMessage("/lobby");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                return;
            }
            if(!oneLimboPlayer) {
                if(this.dontSpamLimbo) {
                    return;
                }
                oneLimboPlayer = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(3000 + Utils.random.nextInt(420));
                        if(SkyblockUtils.isInIsland() || isOnIsland || SkyblockUtils.isInHub() || bedrockdisconnect) return;
                        HookExecute.send("Yung Script Log", "Warped into the Limbo", "Warping back...", false);
                        if(Config.stylemessages == 0) {
                            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Got warped into the Limbo. Warping back..."));
                        } else {
                            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Got warped into the Limbo. Warping back..."));
                        }
                        mc.thePlayer.sendChatMessage("/lobby");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
        isOnIsland = SkyblockUtils.isInIsland();
        switch (SkyblockUtils.getLocation()) {
            case NONE: {
                if (isOnIsland || SkyblockUtils.isInIsland()) {
                    noneLoc = false;
                } else {
                    noneLoc = true;
                }
                break;
            }
            case HUB: {
                this.extraHubWarp = false;
                this.oneLimboPlayer = false;
                this.oneLobbyPlayer = false;
                if(this.dontSpamHub) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(20000 + Utils.random.nextInt(1200));
                            this.dontSpamHub = false;
                            mc.thePlayer.sendChatMessage("/is");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                    return;
                }
                if(!oneHubPlayer) {
                    if(this.dontSpamHub) {
                        return;
                    }
                        oneHubPlayer = true;
                    new Thread(() -> {
                        try {
                            Thread.sleep(3000 + Utils.random.nextInt(500));
                            HookExecute.send("Yung Script Log", "Warped into the Hub", "Warping back...", false);
                            if(Config.stylemessages == 0) {
                                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Got warped into the Hub. Warping back..."));
                            } else {
                                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Got warped into the Hub. Warping back..."));
                            }
                            mc.thePlayer.sendChatMessage("/is");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
                break;
            }
            case LOBBY: {
                if(this.dontSpamHub || warp) return;
                this.extraHubWarp = false;
                this.oneHubPlayer = false;
                this.oneLimboPlayer = false;
                if(this.dontSpamLobby) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(20000 + Utils.random.nextInt(1200));
                            this.dontSpamLobby = false;
                            if(SkyblockUtils.isInIsland() || isOnIsland || SkyblockUtils.isInHub() || bedrockdisconnect || warp) return;
                            mc.thePlayer.sendChatMessage("/play sb");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                    return;
                }
                if(!oneLobbyPlayer) {
                    if(this.dontSpamHub || warp) {
                        return;
                    }
                        oneLobbyPlayer = true;
                    new Thread(() -> {
                        try {
                            Thread.sleep(3000 + Utils.random.nextInt(550));
                            if(SkyblockUtils.isInIsland() || isOnIsland || SkyblockUtils.isInHub() || bedrockdisconnect || warp) return;
                            HookExecute.send("Yung Script Log", "Warped into the Lobby", "Warping back...", false);
                            if(Config.stylemessages == 0) {
                                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Got warped into the Lobby. Warping back..."));
                            } else {
                                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Got warped into the Lobby. Warping back..."));
                            }
                            mc.thePlayer.sendChatMessage("/play sb");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
                break;
            }
            case ISLAND: {
                this.oneLobbyPlayer = false;
                this.oneLimboPlayer = false;
                this.oneHubPlayer = false;
                break;
            }
        }
        if (SkyblockUtils.isInIsland() || isOnIsland) {
            noneLoc = false;
        }
    }
    private int rotatingSouth, rotatingNorth, rotatingWest, rotatingEast;

    private boolean warp;
    // i hate doing threads
    private static boolean cagepr;
    private static boolean cagepr3;
    public static boolean gotBlockOnLadder() {
        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        EnumFacing rowblockstop = mc.thePlayer.getHorizontalFacing();
        switch (rowblockstop) {
            case WEST: {
                pos = new BlockPos(mc.thePlayer.posX + 1, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
                return mc.theWorld.getBlockState(pos).getBlock() != Blocks.air && mc.theWorld.getBlockState(pos).getBlock() != Blocks.nether_wart;
            }
            case EAST: {
                pos = new BlockPos(mc.thePlayer.posX - 1, mc.thePlayer.posY-1, mc.thePlayer.posZ);
                return mc.theWorld.getBlockState(pos).getBlock() != Blocks.air && mc.theWorld.getBlockState(pos).getBlock() != Blocks.nether_wart;
            }
            case NORTH: {
                pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY-1, mc.thePlayer.posZ + 1);
                return mc.theWorld.getBlockState(pos).getBlock() != Blocks.air && mc.theWorld.getBlockState(pos).getBlock() != Blocks.nether_wart;
            }
            case SOUTH: {
                pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY-1, mc.thePlayer.posZ - 1);
                return mc.theWorld.getBlockState(pos).getBlock() != Blocks.air && mc.theWorld.getBlockState(pos).getBlock() != Blocks.nether_wart;
            }
        }
        return false;
    }
    public static boolean onLadder() {
        World world = mc.theWorld;
        if(world != null) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            return mc.theWorld.getBlockState(pos).getBlock() == Blocks.ladder;
        }
        return false;
    }
    public static boolean lookingAtHorizontalWart() {
        BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        EnumFacing facing = mc.thePlayer.getHorizontalFacing();
        return Minecraft.getMinecraft().theWorld.getBlockState(pos.up().offset(facing)).getBlock() == Blocks.nether_wart;
    }
    boolean forwardBedrock;
    boolean rightBedrock;
    boolean leftBedrock;
    public static boolean lookingAtWart() {
        if(mc.objectMouseOver == null || mc.objectMouseOver.getBlockPos() == null) return false;
        return mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() == Blocks.nether_wart || mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() == Blocks.potatoes || mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() == Blocks.carrots;
    }
    private boolean nonEmptyIsland;

    private boolean getLadders;
    private boolean bedrockdisconnect;
    private boolean restartAfterStuck;
    private boolean rowExecute;
    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        World world = mc.theWorld;
        if(nonEmptyIsland) {
            KeyBinding.unPressAllKeys();
            new Thread(() -> {
                try {
                    Config.nwscript = false;
                    Thread.sleep(30000);
                    nonEmptyIsland = false;
                    Config.nwscript = true;
                    countDesync = 100;
                }
                catch (InterruptedException e) {
                    YungAddons.logger.debug(e.getMessage());
                }
            }).start();
        }
        if(world != null) {
            oneReconnect = false;
        }
        EnumFacing row = mc.thePlayer.getHorizontalFacing();
        switch (row) {
            case EAST: {
                facingRow = FacingRow.EAST;
                break;
            }
            case WEST: {
                facingRow = FacingRow.WEST;
                break;
            }
            case NORTH: {
                facingRow = FacingRow.NORTH;
                break;
            }
            case SOUTH: {
                facingRow = FacingRow.SOUTH;
                break;
            }
        }
        if(!Config.autotub) {
            ChadUtils.regrabMouse();
        }
        if(!Config.nwscript) {
            if(!clicked) {
                clicked = true;
                KeyBinding.unPressAllKeys();
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
            }
            this.getResync = true;
            onEnable = false;
            this.invFull = false;
            this.sameInvTimer = new TimeHelper();
            this.oldInvCount = 0;
            this.rotatingSouth = 0;
            this.rotatingEast = 0;
            this.rotatingWest = 0;
            this.rotatingNorth = 0;
            shit = false;
            this.forward = false;
            this.left = false;
            this.right = false;
            this.getLadders = false;
            this.oneLadderCheck = false;
            cdPad = false;
            this.layerswitchSafe = false;
            this.stuckStatel = false;
            this.extraHubWarp = false;
            this.oneMoveDebugForward = false;
            this.oneMoveDebugRight = false;
            this.oneMoveDebugLeft = false;
            this.oneJacob = false;
            this.oneUnPress = false;
            this.onetimeStuckMsg = false;
            this.onetimeStuck = false;
            this.movingForward = false;
            this.movingLeft = false;
            this.movingRight = false;
            this.oneTimeCheckForward = false;
            this.alreadyNorth = false;
            this.alreadyWest = false;
            this.inHub = false;
            this.started = false;
            isOnIsland = false;
            this.isOnLimbo = false;
            this.noneLoc = false;
            this.oneLimbo = false;
            this.oneLobby = false;
            onBedrockCage = false;
            return;
        }
        if(!bedrockdisconnect) {
            forwardBedrock = false;
            rightBedrock = false;
            leftBedrock = false;
        }
        if(cagepr) {
            cagepr = false;
            EssentialAPI.getNotifications().push("Yung Scripts", "Script resumed");
            mc.thePlayer.sendChatMessage("/play sb");
            Config.nwscript = false;
            Config.nwscript = true;
            bedrockdisconnect = false;
            warp = false;
        }
        if(bedrockdisconnect && (SkyblockUtils.isInLobby() || isOnLobby || SkyblockUtils.isInLimbo || isOnLimbo)) {
            this.forward = false;
            this.left = false;
            this.right = false;
            warp = true;
            bedrockdisconnect = false;
            new Thread(() -> {
                try {
                    Thread.sleep(140000);
                    cagepr = true;
                    if(wasLockedYaw) {
                        Config.lockyaw = true;
                        wasLockedYaw = false;
                    }
                }

                catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        if(mc.currentScreen == null && lookingAtWart()) {
            onBedrockCage = false;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
        }
        else if(mc.currentScreen != null || this.invFull) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
        }
        if(reconnectCooldown || reconnectCdDueResync) return;
        if(!onEnable) {
            onEnable = true;
            clicked = false;
            this.banned = false;
            this.oldInv = null;
            this.sameInvState = SameInvState.CHILLING;
        }
        if(Config.autotub) {
            ChadUtils.ungrabMouse();
        }
        if(mc.currentScreen != null && !this.invFull) {
            KeyBinding.unPressAllKeys();
        }
        if(mc.currentScreen == null) {
            this.onTick();
        }
        EnumFacing playerYaw = mc.thePlayer.getHorizontalFacing();
        /*switch (playerYaw) {
            case SOUTH: {
                this.alreadyWest = false;
                this.alreadyNorth = false;
                break;
            }
            case NORTH: {
                this.alreadyNorth = true;
                this.alreadyWest = false;
                break;
            }
            case WEST: {
                this.alreadyWest = true;
                this.alreadyNorth = false;
                break;
            }
            case EAST: {
                this.alreadyWest = false;
                this.alreadyNorth = false;
                break;
            }
        }*/
        if(Config.lockyaw) {
            switch (playerYaw) {
                case SOUTH: {
                    mc.thePlayer.rotationPitch = 3f;
                    mc.thePlayer.rotationYaw = 0;
                    //ScriptAliases.rotateSouth();
                    break;
                }
                case NORTH: {
                    mc.thePlayer.rotationPitch = 3f;
                    mc.thePlayer.rotationYaw = 180f;
                    //ScriptAliases.rotateNorth();
                    break;
                }
                case WEST: {
                    mc.thePlayer.rotationPitch = 3f;
                    mc.thePlayer.rotationYaw = 90f;
                    //ScriptAliases.rotateWest();
                    break;
                }
                case EAST: {
                    mc.thePlayer.rotationPitch = 3f;
                    mc.thePlayer.rotationYaw = -90f;
                    //ScriptAliases.rotateEast();
                    break;
                }
            }
        }
        else {
            switch (playerYaw) {
                case SOUTH: {
                    if(rotatingSouth == 0) {
                        ScriptAliases.rotateCustomYaw(0, 3f, 1f, 0.3f);
                        if(mc.thePlayer.rotationYaw >= -1 && mc.thePlayer.rotationYaw <= 1) {
                            rotatingSouth = -1;
                        }
                    }
                    break;
                }
                case NORTH: {
                    if(rotatingNorth == 0) {
                        ScriptAliases.rotateCustomYaw(180f, 3f, 1f, 0.3f);
                        if(mc.thePlayer.rotationYaw >= 179 && mc.thePlayer.rotationYaw <= 181) {
                            rotatingNorth = -1;
                        }
                    }
                    break;
                }
                case WEST: {
                    if(rotatingWest == 0) {
                        ScriptAliases.rotateCustomYaw(90f, 3f, 1f, 0.3f);
                        if(mc.thePlayer.rotationYaw >= 89 && mc.thePlayer.rotationYaw <= 91) {
                            rotatingWest = -1;
                        }
                    }
                    break;
                }
                case EAST: {
                    if(rotatingEast == 0) {
                        ScriptAliases.rotateCustomYaw(-90f, 3f, 1f, 0.3f);
                        if(mc.thePlayer.rotationYaw >= -91f && mc.thePlayer.rotationYaw <= -89f) {
                            rotatingEast = -1;
                        }
                    }
                    break;
                }
            }
        }
        if(Config.autosell) {
            if(invFull) testBooleans = "true";
            else testBooleans = "false";
            switch (this.sameInvState) {
                case CHILLING: {
                    final List<ItemStack> inv = InventoryUtils.getInventoryStacks();
                    if (!inv.equals(this.oldInv)) {
                        this.oldInv = InventoryUtils.getInventoryStacks();
                        this.oldInvCount = 0;
                        break;
                    }
                    if (++this.oldInvCount >= 240) {
                        this.sameInvState = SameInvState.UNPRESS;
                        this.oldInvCount = 0;
                        break;
                    }
                    break;
                }
                case UNPRESS: {
                    this.sameInvState = SameInvState.PRESS;
                    break;
                }
                case PRESS: {
                    if (!this.invFull) {
                        break;
                    }
                    break;
                }
            }
            if (mc.thePlayer.inventory.getFirstEmptyStack() == -1 && !this.invFull) {
                if(onLadder() || standingOnPad()) return;
                new Thread(() -> {
                    try {
                        Thread.sleep(10000 + Utils.random.nextInt(600));
                        if(mc.thePlayer.inventory.getFirstEmptyStack() != -1) {
                            this.continueInvFull = false;
                            this.fakeInvFull = true;
                            return;
                        }
                        else {
                            this.continueInvFull = true;
                            this.fakeInvFull = false;
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                if(fakeInvFull || !this.continueInvFull) return;
                int fullInvCheck = Math.abs(mc.thePlayer.inventory.getFirstEmptyStack());
                if(!SkyblockUtils.isInIsland() && !isOnIsland && world == null) return;
                this.swingItemFocus = false;
                this.oneSuccess = false;
                this.currentScreenUpdate = false;
                this.invFull = true;
                if(Config.stylemessages == 0) {
                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Inventory Full. Selling wart..."));
                } else {
                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Inventory Full. Selling wart..."));
                }
                extraDisable = true;
                if(!oneCommand) {
                    oneCommand = true;
                    mc.thePlayer.sendChatMessage("/sbmenu");
                }
                ScriptExt.unpressMovement();
                if(!oneOpen) {
                    oneOpen = true;
                    //KeyBinding.setKeyBindState(mc.gameSettings.keyBindInventory.getKeyCode(), true);
                }
            }
            if (mc.thePlayer.inventory.getFirstEmptyStack() > 2 && this.invFull && sold) {
                if(!SkyblockUtils.isInIsland() && !isOnIsland) return;
                if(onLadder() || standingOnPad()) return;
                extraDisable = false;
                mc.inGameHasFocus = true;
                mc.setIngameFocus();
                if(!swingItemFocus) {
                    if (Minecraft.getMinecraft().objectMouseOver == null || Minecraft.getMinecraft().objectMouseOver.getBlockPos() == null) {
                        return;
                    }
                    if(Minecraft.getMinecraft().theWorld.getBlockState(Minecraft.getMinecraft().objectMouseOver.getBlockPos()).getBlock() == Blocks.nether_wart) {
                        BlockPos pos = mc.objectMouseOver.getBlockPos();

                        swingItemFocus = true;
                    }
                }
                mc.mouseHelper.grabMouseCursor();
                if (!currentScreenUpdate) {
                    mc.currentScreen = null;
                    currentScreenUpdate = true;
                    new Thread(() -> {
                        HookExecute.send("Yung Script Log", "Full Inventory Failsafe", "Restarting and adding new session", false);
                        Utils.sleep(500);
                        ChadUtils.ungrabMouse();
                        Config.nwscript = false;
                        Utils.sleep(500);
                        ChadUtils.regrabMouse();
                        mc.thePlayer.sendChatMessage("/lobby");
                        Config.nwscript = true;
                    }).start();
                }
                if(!oneSuccess) {
                    this.oneSuccess = true;
                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.YELLOW + "Script resumed."));
                }
                oneCommand = false;
                oneOpen = false;
                this.onTick();
                this.sameInvState = SameInvState.CHILLING;
                this.invFull = false;
                this.sold = false;
            }
        }
        if(!oneTimeCheckForward) {
            oneTimeCheckForward = true;
            CheckDir();
        }
        if(!this.invFull) {
            onTick();
        }
    }
    /* south: 0 yaw
     * north: 180 yaw
     * west: 90 yaw
     * east: -90 yaw
     */
    private boolean antistaff;
    public void antistaffTest() {
        float nowYaw = mc.thePlayer.rotationYaw;
        float yawdiff = Math.abs(lastYaw - nowYaw);
        if(yawdiff > 1.5f || yawdiff < -1.5f) {
            yawdiff = 0;
            if(Config.stylemessages == 0) {
                ScriptAliases.addMessage(EnumChatFormatting.BLUE + "Yung Scripts >> Fake rotation Detected. Probably Staff checking.");
            }
            else ScriptAliases.addMessage(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Fake rotation Detected. Probably Staff checking.");
            antistaff = true;
        }
        lastYaw = mc.thePlayer.rotationYaw;
    }
    @SubscribeEvent
    public void guiDraw(GuiScreenEvent.BackgroundDrawnEvent event) {
        if(reconnectCooldown || reconnectCdDueResync) return;
        if(!extraDisable) return;
        if(!Config.nwscript) return;
        if(onLadder() || standingOnPad()) return;
        if(!SkyblockUtils.isInIsland() && !isOnIsland) return;
        if(!this.invFull) return;
        if(event.gui instanceof GuiChest && (SkyblockUtils.isInIsland() || isOnIsland)) {
            Container container = ((GuiChest) event.gui).inventorySlots;
            if (container instanceof ContainerChest) {
                String chestName = ((ContainerChest) container).getLowerChestInventory().getDisplayName().getUnformattedText();
                List<Slot> invSlots = container.inventorySlots;
                if (chestName.contains("SkyBlock Menu")) {
                    int i;
                    for (i = 0; i < invSlots.size(); i++) {
                        if (!invSlots.get(i).getHasStack()) continue;
                        String slotName = StringUtils.stripControlCodes(invSlots.get(i).getStack().getDisplayName());
                        if (slotName.contains("Booster Cookie")) {
                            Utils.sleep(500);
                            clickSlot(invSlots.get(i));
                        }
                    }
                }
                if (chestName.contains("Booster Cookie")) {
                    int i;
                    for (i = 0; i < invSlots.size(); i++) {
                        if (!invSlots.get(i).getHasStack()) continue;
                        String slotName = StringUtils.stripControlCodes(invSlots.get(i).getStack().getDisplayName());
                        if (slotName.contains("Bazaar")) {
                            clickSlot(invSlots.get(i));
                            Utils.sleep(500);
                        }
                    }
                }
                if (chestName.contains("Bazaar")) {
                    int i;
                    for (i = 0; i < invSlots.size(); i++) {
                        if (!invSlots.get(i).getHasStack()) continue;
                        String slotName = StringUtils.stripControlCodes(invSlots.get(i).getStack().getDisplayName());
                        if (slotName.contains("Sell Inventory Now")) {
                            clickSlot(invSlots.get(i));
                            Utils.sleep(500);
                        }
                    }
                }
                if (chestName.contains("Are you sure?")) {
                    int i;
                    for (i = 0; i < invSlots.size(); i++) {
                        if (!invSlots.get(i).getHasStack()) continue;
                        String slotName = StringUtils.stripControlCodes(invSlots.get(i).getStack().getDisplayName());
                        if (slotName.contains("Selling whole inventory")) {
                            clickSlot(invSlots.get(i));
                        }
                        if (mc.thePlayer.inventory.getFirstEmptyStack() != -1 && this.invFull) {
                            this.currentScreenUpdate = false;
                            this.swingItemFocus = false;
                            this.sold = true;
                            oneSuccess = false;
                        }
                    }
                }
            }
        }
    }
    public static boolean triggeredCageDebug;
    private void clickSlot(Slot slot) {
        if(reconnectCooldown || reconnectCdDueResync) return;
        windowId = Minecraft.getMinecraft().thePlayer.openContainer.windowId;
        Minecraft.getMinecraft().playerController.windowClick(windowId, slot.slotNumber, 1, 0, Minecraft.getMinecraft().thePlayer);
    }
    private void CheckDir() {
        if(reconnectCooldown || reconnectCdDueResync) return;
        new Thread(() -> {
            try {
                Thread.sleep(10000 + Utils.random.nextInt(400));
                getResync = false;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        if(!dontCountSessions) {
            workedSessions++;
        }
        TimerHandler.writeIntConfig("script", "sessions", workedSessions);
        int totalSessions = TimerHandler.getInt("script", "sessions");
        if(!Config.hideusernamehook) {
            if(dontCountSessions) return;
            HookExecute.send("Yung Script Log", "Script started!", "Script started! Farming for ```" + mc.getSession().getUsername() + "```" + " Worked sessions: " + totalSessions, false);
        }
        if(Config.hideusernamehook) {
            if(dontCountSessions) return;
            HookExecute.send("Yung Script Log", "Script started!", "Script started! Farming for ```" + "Username hided" + "```" + " Worked sessions: " + totalSessions, false);
        }
        if(Config.hideusernamesession) {
            if(dontCountSessions) return;
            if(Config.stylemessages == 0) {
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Current session: " + EnumChatFormatting.GREEN + "HIDED"));
            } else {
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Current session: " + EnumChatFormatting.GREEN + "HIDED"));
            }
        }
        else {
            if(dontCountSessions) return;
            if(Config.stylemessages == 0) {
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Current session: " + EnumChatFormatting.GREEN + mc.getSession().getUsername()));
            } else {
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Current session: " + EnumChatFormatting.GREEN + mc.getSession().getUsername()));
            }
        }
        if(!dontCountSessions) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.YELLOW + "If script doesn't work, start at the end/start"));
        }
    }
    @SubscribeEvent
    public void antiStuck(TickEvent.ClientTickEvent event) {
        if(reconnectCooldown || countingResync || wasOnLadder || reconnectCdDueResync) return;
        if (!Config.nwscript || Minecraft.getMinecraft().currentScreen != null || this.invFull || (!SkyblockUtils.isInIsland() || !isOnIsland) || onLadder()) return;
        if (event.phase.toString().equals("START")) {
            if (thread2 == null || !thread2.isAlive()) {
                thread2 = new Thread(() -> {
                    try {
                        Thread.sleep(5000 + Utils.random.nextInt(895));
                        if(reconnectCooldown || countingResync || wasOnLadder || reconnectCdDueResync) return;
                        double xdiff = Math.abs(savex - Minecraft.getMinecraft().thePlayer.posX);
                        double zdiff = Math.abs(savez - Minecraft.getMinecraft().thePlayer.posZ);
                        if(lookingAtHorizontalWart() && !this.left && !this.right && !this.forward) {
                            if(stuckStatel || standingOnPad() || layerswitchSafe || !Config.nwscript) return;
                            if(!rowExecute && Config.nwscript) {
                                rowExecute = true;
                                if(Config.stylemessages == 0) {
                                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Max row - 5 warts. Script probably got stopped."));
                                } else {
                                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Max row - 5 warts. " + EnumChatFormatting.LIGHT_PURPLE + "Script probably got stopped."));
                                }
                                HookExecute.send("Yung Script Log", "Limit Execute", "Max row - 5 warts. Script probably got stopped.", false);
                            }
                        }
                        else {
                            rowExecute = false;
                        }
                        //no fast break
                        if(xdiff < 1.5f && zdiff < 1.5f && !Config.fastbreak) {
                            if(reconnectCooldown || countingResync || wasOnLadder || reconnectCdDueResync) return;
                            stuckStatel = true;
                            if(!oneUnPress && Config.nwscript) {
                                oneUnPress = true;
                                ScriptExt.unpressMovementStuck();
                            }
                            if(!onetimeStuckMsg && Config.nwscript) {
                                onetimeStuckMsg = true;
                                HookExecute.send("Yung Script Log", "You got stuck in block", "Trying to prevent that...", false);
                                if(Config.stylemessages == 0) {
                                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Block stuck detected. Stopping thread for ~5 seconds." + EnumChatFormatting.YELLOW + " (Restarting script after)"));
                                } else {
                                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Block stuck detected. Stopping thread for ~5 seconds." + EnumChatFormatting.YELLOW + " (Restarting script after)"));
                                }
                            }
                            if(!onetimeStuck && Config.nwscript) {
                                if(!Config.nwscript) return;
                                dontCountSessions = true;
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), true);
                                Thread.sleep(650 + Utils.random.nextInt(420));
                                if(!Config.nwscript) return;
                                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), false);
                                Thread.sleep(1050 + Utils.random.nextInt(440));
                                if(!Config.nwscript) return;
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), true);
                                Thread.sleep(650 + Utils.random.nextInt(460));
                                if(!Config.nwscript) return;
                                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), false);
                                Thread.sleep(1050 + Utils.random.nextInt(480));
                                if(!Config.nwscript) return;
                                if(!this.forward) {
                                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode(), true);
                                }
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), true);
                                Thread.sleep(450 + Utils.random.nextInt(120));
                                if(!Config.nwscript) return;
                                if(!this.forward) {
                                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode(), false);
                                }
                                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), false);
                                Thread.sleep(1000 + Utils.random.nextInt(125));
                                if(!Config.nwscript) return;
                                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), true);
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), true);
                                Thread.sleep(750 + Utils.random.nextInt(300));
                                if(!Config.nwscript) return;
                                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), false);
                                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), false);
                                if(!this.restartAfterStuck) {
                                    restartAfterStuck = true;
                                    new Thread(() -> {
                                        Utils.sleep(500);
                                        Config.nwscript = false;
                                        Utils.sleep(500);
                                        Config.nwscript = true;
                                    }).start();
                                }
                                this.onTick();
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), this.right);
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), this.left);
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), this.forward);
                                onetimeStuck = true;
                            }
                        }
                        else if(!Config.fastbreak && xdiff > 1.5f && zdiff > 1.5f){
                            this.restartAfterStuck = false;
                            stuckStatel = false;
                            oneUnPress = false;
                            onetimeStuckMsg = false;
                            onetimeStuck = false;
                        }
                        if(xdiff < 2f && zdiff < 2f && Config.fastbreak) {
                            if(reconnectCooldown || countingResync || wasOnLadder || reconnectCdDueResync) return;
                            stuckStatel = true;
                            if(!oneUnPress && Config.nwscript) {
                                oneUnPress = true;
                                ScriptExt.unpressMovementStuck();
                            }
                            if(!onetimeStuckMsg && Config.nwscript) {
                                onetimeStuckMsg = true;
                                if(!Config.nwscript || layerswitchSafe) return;
                                HookExecute.send("Yung Script Log", "You got stuck in block", "Trying to prevent that...", false);
                                if(Config.stylemessages == 0) {
                                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Block stuck detected. Stopping thread for ~5 seconds." + EnumChatFormatting.YELLOW + " (Restarting script after)"));
                                } else {
                                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Block stuck detected. Stopping thread for ~5 seconds." + EnumChatFormatting.YELLOW + " (Restarting script after)"));
                                }
                            }
                            if(!onetimeStuck && Config.nwscript) {
                                if(!Config.nwscript || layerswitchSafe) return;
                                dontCountSessions = true;
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), true);
                                Thread.sleep(650 + Utils.random.nextInt(421));
                                if(!Config.nwscript || layerswitchSafe) return;
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), false);
                                Thread.sleep(1000 + Utils.random.nextInt(436));
                                if(!Config.nwscript || layerswitchSafe) return;
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), true);
                                Thread.sleep(650 + Utils.random.nextInt(743));
                                if(!Config.nwscript || layerswitchSafe) return;
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), false);
                                Thread.sleep(1000 + Utils.random.nextInt(275));
                                if(!Config.nwscript || layerswitchSafe) return;
                                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode(), true);
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), true);
                                Thread.sleep(450 + Utils.random.nextInt(120));
                                if(!Config.nwscript || layerswitchSafe) return;
                                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode(), false);
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), false);
                                Thread.sleep(1000 + Utils.random.nextInt(125));
                                if(!Config.nwscript) return;
                                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), true);
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), true);
                                Thread.sleep(750 + Utils.random.nextInt(420));
                                if(!Config.nwscript) return;
                                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), false);
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), false);
                                if(!this.restartAfterStuck) {
                                    restartAfterStuck = true;
                                    new Thread(() -> {
                                        Utils.sleep(500);
                                        Config.nwscript = false;
                                        Utils.sleep(500);
                                        Config.nwscript = true;
                                    }).start();
                                }
                                this.onTick();
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), this.right);
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), this.left);
                                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), this.forward);
                                onetimeStuck = true;
                            }
                        }
                        else {
                            this.restartAfterStuck = false;
                            stuckStatel = false;
                            oneUnPress = false;
                            onetimeStuckMsg = false;
                            onetimeStuck = false;
                        }
                        if(standingOnPad() && onetimeStuck) {
                            new Thread(() -> {
                                Utils.sleep(3000);
                                onetimeStuck = false;
                                stuckStatel = false;
                                oneUnPress = false;
                            }).start();
                        }
                        savex = Minecraft.getMinecraft().thePlayer.posX;
                        savez = Minecraft.getMinecraft().thePlayer.posZ;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, "Anti Stuck");
                thread2.start();
            }
        }
    }
    private boolean isntNull;

    private boolean canSetSpawn;
    private boolean rotateSouth;
    private boolean rotateWest;
    private boolean rotateEast;
    private boolean rotateNorth;

    @Override
    public void onTick() {
        World world = mc.theWorld;
        if(world != null) {
            isntNull = true;
        }
        else isntNull = false;
        if(reconnectCooldown) return;
        if(!Config.nwscript) return;
        if(isntNull) {
            //ScriptAliases.rotateCustomYaw((float) Math.atan(Math.cos(mc.thePlayer.rotationYaw)*2/7), (float) Math.atan(Math.cos(mc.thePlayer.rotationPitch)*2/7), 1f, 0.1f);
        }
        if(onBedrockCage && closestBedrock() == null && !bedrockdisconnect && (SkyblockUtils.isInIsland() || isOnIsland)) onBedrockCage = false;
        if(countDesync >= Config.resyncerAuto && !this.reconnectCdDueResync) {
            if(closestLadder() != null || closestBedrock() != null || !Config.resyncer || Config.resyncerAuto == 0 || wasOnLadder) return;

            this.reconnectCdDueResync = true;
            Config.nwscript = false;
            KeyBinding.unPressAllKeys();
            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Resync..."));
            EssentialAPI.getNotifications().push("Yung Scripts", "Resyncer activated!");
            //mc.thePlayer.sendChatMessage("/lobby");
            countDesync = 0;
            return;

        }
        if(reconnectCdDueResync) {
            return;
        }
        if(dontSpamMoveMessages) {
            new Thread(() -> {
                try {
                    Thread.sleep(10000);
                    dontSpamMoveMessages = false;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        if(closestBedrock() != null) {
            bedrockdisconnect = true;
            if(Config.lockyaw && !wasLockedYaw) {
                Config.lockyaw = false;
                wasLockedYaw = true;
            }
            if(!onBedrockCage) {
                onBedrockCage = true;

                EssentialAPI.getNotifications().push("Yung Scripts", "You were spawned in bedrock cage. Wait ~5 minutes.");
                HookExecute.send("Yung Script Log", "Anti-bedrock Cage", "You were spawned in bedrock cage. Applying failsafes...", false);
                new Thread(() -> {
                    try {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
                        Thread.sleep(600 + Utils.random.nextInt(125));
                        this.forward = false;
                        this.right = false;
                        this.left = false;
                        KeyBinding.unPressAllKeys();
                        Thread.sleep(300);
                        //Failsafes.sendRandomMessage();
                        Thread.sleep(300 + Utils.random.nextInt(120));
                        ScriptAliases.rotateCustomYaw(-42.5f, 3f, 1f, 0.2f);
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
                        Thread.sleep(300);
                        ScriptAliases.rotateCustomYaw(-42.5f, 3f, 1f, 0.2f);
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
                        Thread.sleep(500);
                        mc.thePlayer.sendChatMessage("/lobby");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            return;
            //throw new BedrockCageExcep();
        }
        if(standingOnPad() && isOnIsland && !layerswitchSafe) {
            if(cdPad) return;
            new Thread(() -> {
                if(cdPad) return;
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
                Utils.sleep(350);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
            }).start();
        }
        if (!this.banned && this.mc.currentScreen instanceof GuiDisconnected) {
            final GuiDisconnected gd = (GuiDisconnected)this.mc.currentScreen;

            final IChatComponent message = (IChatComponent) ObfuscationReflectionHelper.getPrivateValue((Class)GuiDisconnected.class, (Object)gd, new String[] { "message", "message" });
            final StringBuilder reason = new StringBuilder();
            for (final IChatComponent cc : message.getSiblings()) {
                reason.append(cc.getUnformattedText());
            }
            String re = reason.toString();

            re = re.replace("\r", "\\r").replace("\n", "\\n");
            if (re.contains("banned")) {
                this.banned = true;
                HookExecute.send("Yung Script Log", "You got banned! " + re, "Account banned: " + mc.getSession().getUsername(), false);
            }
        }
        if(layerswitchSafe && mc.thePlayer.onGround) {
            new Thread(() -> {
                try {
                    Thread.sleep(2000 + Utils.random.nextInt(400));
                    layerswitchSafe = false;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        if(cdPad && mc.thePlayer.onGround) {
            new Thread(() -> {
                try {
                    Thread.sleep(39000 + Utils.random.nextInt(400));
                    cdPad = false;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        isOnIsland = SkyblockUtils.isInIsland();
        if (SkyblockUtils.isInIsland() || isOnIsland) {
            noneLoc = false;
        }
        switch (SkyblockUtils.getLocation()) {
            case NONE: {
                if (isOnIsland || SkyblockUtils.isInIsland()) {
                    noneLoc = false;
                } else {
                    noneLoc = true;
                }
            }
            case HUB: {
                isOnIsland = false;
                noneLoc = true;
            }

        }
        if (!Config.nwscript || YungAddons.displayScreen != null || Minecraft.getMinecraft().currentScreen != null || !isOnIsland || !SkyblockUtils.isInIsland() || activeContest || oneUnPress) {
            return;
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if (cleanedLine.contains("Village")) {
                return;
            }
        }
        BlockPos pos = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
        VerticalFailSafe();
        if(this.actualDesync) {
            //KeyBinding.unPressAllKeys();
        }
        if(ProfitCalc.cropMin > 0 && !reconnectCooldown && world != null) {
            this.actualDesync = false;
            this.resyncMessage = false;
        }
        if(!mc.thePlayer.onGround && !onLadder()) {
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), true);
        }
        else KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
        if(isOnIsland) {
            this.dontSpamLobby = false;
            this.dontSpamHub = false;
            this.dontSpamLimbo = false;
            // old anti-stuck
/*            if ((PlayerUtil.isStandingStillStuck() && !mc.thePlayer.onGround) && !layerswitchSafe) {
                onetimeStuck = true;
                if(!onetimeStuckMsg) {
                    onetimeStuckMsg = true;
                    HookExecute.send("Yung Script Log", "You got stuck in block", "Trying to prevent that. Maybe you are not on ground?", false);
                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Block stuck detected. Stopping thread for 10 seconds." + EnumChatFormatting.YELLOW + " (Can be just fly movement...)"));
                }
                Failsafes.unpressMovementStuck();
                if(this.right) KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), true);
                if(this.left) KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), true);
                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode(), true);
            } else {
                new Thread (() -> {
                    try {
                        if(onetimeStuck) {
                            Thread.sleep(10000);
                            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), false);
                            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), false);
                            onetimeStuck = false;
                            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode(), false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }*/
            if(alreadyChangedYawLadder && closestLadder() == null) {
                new Thread(() -> {
                    try {
                        Thread.sleep(30000);
                        this.successLobby = false;
                        this.successSetSpawn = false;
                        alreadyChangedYawLadder = false;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            if(closestLadder() == null) {
                oneLadderCheck = false;
                onePressBackLadder = false;
                //wasOnLadder = false;
                getLadders = false;
            }
            if(closestLadder() != null) {
                this.getLadders = true;
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
                if(!onePressBackLadder) {
                    onePressBackLadder = true;
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
                }
            }
            if(this.getLadders) {
                new Thread(() -> {
                    try {
                        if(this.right && !this.stillMovingOnLadder) {
                            this.right = true;
                            KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), this.right);
                            this.left = false;
                        }
                        if(this.left && !this.stillMovingOnLadder) {
                            this.left = true;
                            KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), this.left);
                            this.right = false;
                        }
                        Thread.sleep(500 + Utils.random.nextInt(100));
                        this.stillMovingOnLadder = true;
                        this.right = false;
                        this.left = false;
                        ScriptExt.unpressMovementLayer();
                        Thread.sleep(300 + Utils.random.nextInt(50));
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
                        if (!oneLadderCheck) {
                            BlockPos oldPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.3f, mc.thePlayer.posZ);
                            BlockPos playerpos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ);
                            EnumFacing row = mc.thePlayer.getHorizontalFacing();
                            oneLadderCheck = true;
                            //mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Ladder Detected. Changing yaw..."));
                            this.wasOnLadder = true;
                            //wasOnLadderForRe = true;
                            switch (row) {
                                case SOUTH: {
                                    ScriptExt.unpressMovementLayer();
                                    Thread.sleep(7000 + Utils.random.nextInt(432));
                                    if (gotBlockOnLadder()) {
                                        if(alreadyChangedYawLadder) return;
                                        if(Config.lockyaw) {
                                            itWasLocked = true;
                                            Config.lockyaw = false;
                                        }
                                        else itWasLocked = false;
                                        this.forward = true;
                                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), this.forward);
                                        rotateNorth = true;
                                        canSetSpawn = true;
                                        alreadyChangedYawLadder = true;
                                    }
                                    break;
                                }
                                case NORTH: {
                                    ScriptExt.unpressMovementLayer();
                                    Thread.sleep(7000 + Utils.random.nextInt(412));
                                    if (gotBlockOnLadder()) {
                                        if(alreadyChangedYawLadder) return;
                                        if(Config.lockyaw) {
                                            itWasLocked = true;
                                            Config.lockyaw = false;
                                        }
                                        else itWasLocked = false;
                                        this.forward = true;
                                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), this.forward);
                                        rotateSouth = true;
                                        canSetSpawn = true;
                                        alreadyChangedYawLadder = true;
                                    }
                                    break;
                                }
                                case EAST: {
                                    ScriptExt.unpressMovementLayer();
                                    Thread.sleep(7000 + Utils.random.nextInt(450));
                                    if (gotBlockOnLadder()) {
                                        if(alreadyChangedYawLadder) return;
                                        if(Config.lockyaw) {
                                            itWasLocked = true;
                                            Config.lockyaw = false;
                                        }
                                        else itWasLocked = false;
                                        this.forward = true;
                                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), this.forward);
                                        rotateWest = true;
                                        canSetSpawn = true;
                                        alreadyChangedYawLadder = true;
                                    }
                                    break;
                                }
                                case WEST: {
                                    ScriptExt.unpressMovementLayer();
                                    Thread.sleep(7000 + Utils.random.nextInt(425));
                                    if (gotBlockOnLadder()) {
                                        if(alreadyChangedYawLadder) return;
                                        if(Config.lockyaw) {
                                            itWasLocked = true;
                                            Config.lockyaw = false;
                                        }
                                        else itWasLocked = false;
                                        this.forward = true;
                                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), this.forward);
                                        rotateEast = true;
                                        canSetSpawn = true;
                                        alreadyChangedYawLadder = true;
                                    }
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            if(rotateSouth) {
                ScriptAliases.rotateCustomYaw(0f, 3f, 1f, 0.2f);
            } else if(rotateNorth) {
                ScriptAliases.rotateCustomYaw(180f, 3f, 1f, 0.2f);
            } else if(rotateWest) {
                ScriptAliases.rotateCustomYaw(90f, 3f, 1f, 0.2f);
            } else if(rotateEast) {
                ScriptAliases.rotateCustomYaw(-90f, 3f, 1f, 0.2f);
            }
            if(closestLadder() == null && this.wasOnLadder) {
                new Thread(() -> {
                    try {
                        Thread.sleep(1250 + Utils.random.nextInt(200));
                        dontSpamMoveMessages = true;
                        Config.nwscript = false;
                        Thread.sleep(500 + Utils.random.nextInt(200));
                        dontSpamMoveMessages = true;
                        dontCountSessions = true;
                        this.switchingLayersLadder = true;
                        this.getLadders = false;
                        this.wasOnLadder = false;
                        rotateSouth = false;
                        rotateEast = false;
                        rotateWest = false;
                        rotateNorth = false;
                        if(itWasLocked) {
                            Config.lockyaw = true;
                            itWasLocked = false;
                        }
                        Config.nwscript = true;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            if(this.switchingLayersLadder) {
                if(closestLadder() != null) return;
                wasOnLadderForRe = false;
                this.stillMovingOnLadder = false;
                this.switchingLayersLadder = false;
                new Thread(() -> {
                    try {
                        if(this.switchingLayersLadder) return;
                        if(!this.successSetSpawn) this.setspawnLadder = true;
                        Thread.sleep(2300 + Utils.random.nextInt(200));
                        if(!this.successLobby) this.lobbyladder = true;
                        Thread.sleep(3300);
                        canSetSpawn = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            if(this.setspawnLadder) {
                this.successSetSpawn = true;
                this.setspawnLadder = false;
                if(closestLadder() != null) return;
                if(Config.stylemessages == 0) {
                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Layer switched. Warping to the lobby..."));
                } else {
                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Layer switched. " + EnumChatFormatting.LIGHT_PURPLE + "Warping to the lobby..."));
                }
                if(closestBedrock() != null) return;
                mc.thePlayer.sendChatMessage("/setspawn");
            }
            if(this.lobbyladder) {
                this.successLobby = true;
                this.lobbyladder = false;
                this.forward = false;
                mc.thePlayer.sendChatMessage("/lobby");
            }
            if(switchedLayersLadder && !this.switchingLayersLadder) {
                new Thread(() -> {
                    try {
                        Thread.sleep(20000);
                        switchedLayersLadder = false;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            final EnumFacing player = mc.thePlayer.getHorizontalFacing();
            final BlockPos pos1 = new BlockPos(mc.thePlayer.posX, (double) MathUtils.round(mc.thePlayer.posY), mc.thePlayer.posZ);
            if (PlayerUtil.isStandingStill()) {
                if (!this.layerswitchSafe) {
                    final EnumFacing facing = player;
                    /*if(alreadyNorth || alreadyWest) {
                        // forward north - blocks: x: y: z: -1
                        // forward west - blocks: x: -1 y: z:
                        switch (facingRow) {
                            case NORTH: {
                                BlockPos playerNorthForward = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ - 1);
                                if(mc.theWorld.getBlockState(playerNorthForward).getBlock() == Blocks.air) {
                                    this.forward = true;
                                    this.right = false;
                                    this.left = false;
                                }
                                else this.forward = false;
                                break;
                            }
                            case WEST: {
                                BlockPos playerWestForward = new BlockPos(mc.thePlayer.posX - 1, mc.thePlayer.posY, mc.thePlayer.posZ);
                                if(mc.theWorld.getBlockState(playerWestForward).getBlock() == Blocks.air) {
                                    this.forward = true;
                                    this.right = false;
                                    this.left = false;
                                }
                                else this.forward = false;
                                break;
                            }
                        }
                        return;
                    }*/
                    this.forward = isValid(facing, pos1);
                }
            }
            if (PlayerUtil.isStandingStill()) {
                if (!this.right || !this.movingRight) {
                    if (closestLadder() != null) {
                        return;
                    }
                    final EnumFacing facing = Utils.getLeftEnumfacing(player);
                    /*if(alreadyNorth || alreadyWest) {
                        // left north - blocks: x: -1 y: z:
                        // left west - blocks: x: y: z: +1
                        switch (facingRow) {
                            case NORTH: {
                                BlockPos playerNorthLeft = new BlockPos(mc.thePlayer.posX - 1, mc.thePlayer.posY, mc.thePlayer.posZ);
                                if(mc.theWorld.getBlockState(playerNorthLeft).getBlock() == Blocks.air) {
                                    if(this.right || this.forward) return;
                                    this.left = true;
                                    this.right = false;
                                    this.forward = false;
                                }
                                else this.left = false;
                                break;
                            }
                            case WEST: {
                                BlockPos playerWestLeft = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ + 1);
                                if(mc.theWorld.getBlockState(playerWestLeft).getBlock() == Blocks.air) {
                                    if(this.right || this.forward) return;
                                    this.left = true;
                                    this.right = false;
                                    this.forward = false;
                                }
                                else this.left = false;
                                break;
                            }
                        }
                        return;
                    }*/
                    this.left = isValid(facing, pos1);
                }
                if (this.left || this.movingLeft) {
                    if (closestLadder() != null) {
                        return;
                    }
                    this.right = false;
                    this.movingRight = false;
                    this.left = true;
                }
                if (!this.left || !this.movingLeft) {
                    if (closestLadder() != null) {
                        return;
                    }
                    final EnumFacing facing = Utils.getRightEnumfacing(player);
                    /*if(alreadyNorth || alreadyWest) {
                        // right north - blocks: x: +1 y: z:
                        // right west - blocks: x: y: z: -1
                        switch (facingRow) {
                            case NORTH: {
                                BlockPos playerNorthRight = new BlockPos(mc.thePlayer.posX + 1, mc.thePlayer.posY, mc.thePlayer.posZ);
                                if(mc.theWorld.getBlockState(playerNorthRight).getBlock() == Blocks.air) {
                                    if(this.left || this.forward) return;
                                    this.right = true;
                                    this.left = false;
                                    this.forward = false;
                                }
                                else this.right = false;
                                break;
                            }
                            case WEST: {
                                BlockPos playerWestRight = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ - 1);
                                if(mc.theWorld.getBlockState(playerWestRight).getBlock() == Blocks.air) {
                                    if(this.left || this.forward) return;
                                    this.right = true;
                                    this.left = false;
                                    this.forward = false;
                                }
                                else this.right = false;
                                break;
                            }
                        }
                        return;
                    }*/
                    this.right = isValid(facing, pos1);
                }
                if (this.right || this.movingRight) {
                    if (closestLadder() != null) {
                        return;
                    }
                    this.left = false;
                    this.movingLeft = false;
                    this.right = true;
                }
            }
            if (!this.oneMoveDebugLeft && this.left) {
                this.oneMoveDebugRight = false;
                this.oneMoveDebugForward = false;
                this.oneMoveDebugLeft = true;
                if (closestLadder() != null) {
                    return;
                }
                if(dontSpamMoveMessages) return;
                new Thread(() -> {
                    try {
                        Thread.sleep(380);
                        if(Config.stylemessages == 0) {
                            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Detected a new row. Moving Left in " + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA + "X: " + (int)ActualScript.mc.thePlayer.posX + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA + " Z: " + (int)ActualScript.mc.thePlayer.posZ));
                        } else {
                            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Detected a new row. Moving Left in " + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA + "X: " + (int)ActualScript.mc.thePlayer.posX + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA + " Z: " + (int)ActualScript.mc.thePlayer.posZ));
                        }
                    }
                    catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    return;
                }).start();
            }
            if (!this.oneMoveDebugRight && this.right) {
                this.oneMoveDebugLeft = false;
                this.oneMoveDebugForward = false;
                this.oneMoveDebugRight = true;
                if(dontSpamMoveMessages) return;
                if (closestLadder() != null) {
                    return;
                }
                new Thread(() -> {
                    try {
                        Thread.sleep(380);
                        if(Config.stylemessages == 0) {
                            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Detected a new row. Moving Right in " + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA + "X: " + (int)ActualScript.mc.thePlayer.posX + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA + " Z: " + (int)ActualScript.mc.thePlayer.posZ));
                        } else {
                            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Detected a new row. Moving Right in " + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA + "X: " + (int)ActualScript.mc.thePlayer.posX + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA + " Z: " + (int)ActualScript.mc.thePlayer.posZ));
                        }
                    }
                    catch (Exception e4) {
                        e4.printStackTrace();
                    }
                    return;
                }).start();
            }
            if (!this.oneMoveDebugForward && this.forward) {
                this.oneMoveDebugForward = true;
                if(dontSpamMoveMessages) return;
                if (closestLadder() != null) {
                    return;
                }
                if(Config.stylemessages == 0) {
                    ActualScript.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Changing row."));
                    ActualScript.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Moving Forward in " + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA + "X: " + (int)mc.thePlayer.posX + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA + " Z: " + (int)ActualScript.mc.thePlayer.posZ));
                } else {
                    ActualScript.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Changing row."));
                    ActualScript.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Moving Forward in " + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA + "X: " + (int)mc.thePlayer.posX + EnumChatFormatting.BOLD + EnumChatFormatting.AQUA + " Z: " + (int)ActualScript.mc.thePlayer.posZ));
                }
            }
            if (!this.layerswitchSafe) {
                if(closestLadder() != null || Config.typeoffarm == 1 || this.getLadders) return;
                KeyBinding.setKeyBindState(ActualScript.mc.gameSettings.keyBindForward.getKeyCode(), this.forward);
            }
            if (this.forward && !this.setspawnLeft && !this.layerswitchSafe && !standingOnPad()) {
                if (closestLadder() != null) {
                    return;
                }
                this.setspawnLeft = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(150 + Utils.random.nextInt(94));
                        if(Config.fastbreak && Config.resyncer) {
                            countDesync++;
                        }
                        if(!canSetSpawn) {
                            ActualScript.mc.thePlayer.sendChatMessage("/setspawn");
                        }
                    }
                    catch (Exception e5) {
                        e5.printStackTrace();
                    }
                    return;
                }).start();
            }
            if (!this.forward && this.setspawnLeft) {
                this.setspawnLeft = false;
            }
            if (!this.forward) {
                if (closestLadder() != null) {
                    return;
                }
                KeyBinding.setKeyBindState(ActualScript.mc.gameSettings.keyBindLeft.getKeyCode(), this.left);
                KeyBinding.setKeyBindState(ActualScript.mc.gameSettings.keyBindRight.getKeyCode(), this.right);
            }
            else if (this.forward && !standingOnPad()) {
                KeyBinding.setKeyBindState(ActualScript.mc.gameSettings.keyBindLeft.getKeyCode(), false);
                KeyBinding.setKeyBindState(ActualScript.mc.gameSettings.keyBindRight.getKeyCode(), false);
            }
        }
    }

    @Override
    public void onToggle(boolean p0) {

    }

    @Override
    public boolean applyPositionFailsafe() {
        return true;
    }

    @Override
    public boolean applyBedrockFailsafe() {
        return true;
    }
    private BlockPos closestLadderFor() {
        float r = 0.5f;
        BlockPos playerPos = Minecraft.getMinecraft().thePlayer.getPosition();
        playerPos.add(0, 1, 0);
        Vec3 playerVec = Minecraft.getMinecraft().thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, r, r);
        Vec3i vec3i2 = new Vec3i(r, r, r);
        ArrayList<Vec3> ladder = new ArrayList<Vec3>();
        if (playerPos != null) {
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i2))) {
                IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
                //Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(blockState.getBlock().toString()));
                if (blockState.getBlock() == Blocks.ladder) {
                    ladder.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
                }
            }
        }
        double smallest = 9999;
        Vec3 closest = null;
        for (Vec3 stone : ladder) {
            double dist = stone.distanceTo(playerVec);
            if (dist < smallest) {
                smallest = dist;
                closest = stone;
            }
        }
        if (closest != null && smallest < 15) {
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        }
        return null;
    }
    public boolean isValid(final EnumFacing f, final BlockPos pos) {
        final BlockPos offset = pos.offset(f);
        return blocks.contains(mc.theWorld.getBlockState(offset).getBlock()) && blocks.contains(mc.theWorld.getBlockState(offset.up()).getBlock()) && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, (double)(MathUtils.roundUp(mc.thePlayer.posY) - 1), mc.thePlayer.posZ)).getBlock() != Blocks.air;
    }
    private BlockPos closestLadder() {
        int r = 1;
        BlockPos playerPos = Minecraft.getMinecraft().thePlayer.getPosition();
        playerPos.add(0, 1, 0);
        Vec3 playerVec = Minecraft.getMinecraft().thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, r, r);
        Vec3i vec3i2 = new Vec3i(r, r, r);
        ArrayList<Vec3> ladder = new ArrayList<Vec3>();
        if (playerPos != null) {
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i2))) {
                IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
                //Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(blockState.getBlock().toString()));
                if (blockState.getBlock() == Blocks.ladder) {
                    ladder.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
                }
            }
        }
        double smallest = 9999;
        Vec3 closest = null;
        for (Vec3 stone : ladder) {
            double dist = stone.distanceTo(playerVec);
            if (dist < smallest) {
                smallest = dist;
                closest = stone;
            }
        }
        if (closest != null && smallest < 15) {
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        }
        return null;
    }
    private BlockPos closestBedrock() {
        int r = 8;
        BlockPos playerPos = Minecraft.getMinecraft().thePlayer.getPosition();
        playerPos.add(0, 1, 0);
        Vec3 playerVec = Minecraft.getMinecraft().thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, r, r);
        Vec3i vec3i2 = new Vec3i(r, r, r);
        ArrayList<Vec3> bedr = new ArrayList<Vec3>();
        if (playerPos != null) {
            for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i2))) {
                IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
                //Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(blockState.getBlock().toString()));
                if (blockState.getBlock() == Blocks.bedrock) {
                    bedr.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
                }
            }
        }
        double smallest = 9999;
        Vec3 closest = null;
        for (Vec3 stone : bedr) {
            double dist = stone.distanceTo(playerVec);
            if (dist < smallest) {
                smallest = dist;
                closest = stone;
            }
        }
        if (closest != null && smallest < 15) {
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        }
        return null;
    }
    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
        if(reconnectCooldown || reconnectCdDueResync) return;
        String message = net.minecraft.util.StringUtils.stripControlCodes(event.message.getUnformattedText());

        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if(cleanedLine.contains("SILVER ") || cleanedLine.contains("BRONZE ") || cleanedLine.contains("Collected ")) {
                oneGold = false;
                if(activeContest && !this.invFull) {
                    Config.nwscript = true;
                    onTick();
                    if(this.forward) KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), this.forward);
                    if(this.right) KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), this.right);
                    if(this.left) KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), this.left);
                    activeContest = false;
                }
                if(!Config.eventsender) return;
                if(!oneJacob) {
                    oneJacob = true;
                    //HookExecute.send("Yung Script Log", "Jacob's Contest", "Contest started.", false);
                }
            }
        }
        if(message.contains(" Farming Contest is over!") && activeContest) {
            Config.nwscript = true;
            HookExecute.send("Yung Script Log", "Jacob's Contest End", "Contest ended.", false);
            oneGold = false;
            oneJacob = false;
            activeContest = false;
        }
        if(!Config.nwscript) return;
        if(message.contains("You were kicked while joining ")) {
            mc.thePlayer.sendChatMessage("/lobby");
        }
        if(message.contains("You need the Cookie ")) {
            Config.nwscript = false;
            KeyBinding.unPressAllKeys();
            if(Config.stylemessages == 0) {

            } else {

            }
            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> No cookie buff, disabling script. Anti-sell failure."));
        }
        if(message.contains("Something went wrong trying to send ") || message.contains("don't spam") || message.contains("A disconnect occurred ") || message.contains("An exception occurred ") || message.contains("Couldn't warp ") || message.contains("You are sending commands ") || message.contains("Cannot join ") || message.contains("There was a problem ") || message.contains("You cannot join ") || message.contains("You were kicked while ")) {
            if(Config.stylemessages == 0) {
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Got error while sending to server. Stopping thread for 20 seconds"));
            } else {
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Got error while sending to server. Stopping thread for 20 seconds"));
            }
            new Thread(() -> {
                try {
                    switch (SkyblockUtils.getLocation()) {
                        case ISLAND: {
                            if(extraHubWarp) {
                                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Failed to extra warp"));
                            }
                        }
                        case LOBBY: {
                            this.dontSpamLobby = true;
                            this.dontSpamHub = false;
                            this.dontSpamLimbo = false;
                            break;
                        }
                        case HUB: {
                            this.dontSpamHub = true;
                            this.dontSpamLimbo = false;
                            this.dontSpamLobby = false;
                            break;
                        }
                        case LIMBO: {
                            this.dontSpamLimbo = true;
                            this.dontSpamHub = false;
                            this.dontSpamLobby = false;
                            break;
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        for (String line : ScoreboardHandler.getSidebarLines()) {
            String cleanedLine = ScoreboardHandler.cleanSB(line);
            if(cleanedLine.contains("Jacob's Contest") && cleanedLine.contains("GOLD ") && (cleanedLine.contains("431,") || cleanedLine.contains("432,") || cleanedLine.contains("433,") || cleanedLine.contains("434,") || cleanedLine.contains("435,"))) {
                if(!Config.preventjacob) return;
                activeContest = true;
                if(!oneGold) {
                    oneGold = true;
                    if(Config.stylemessages == 0) {
                        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> GOLD Medal Achieved. Script paused."));
                    } else {
                        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GOLD + "GOLD" + EnumChatFormatting.GRAY + " Medal Achieved. Script paused."));
                    }
                    HookExecute.send("Yung Script Log", "GOLD Achieved.", "Script Stopped.", false);
                }
                if(activeContest && !this.invFull) {
                    Config.nwscript = false;
                    KeyBinding.unPressAllKeys();
                }
            }
        }
        if(message.contains(" Farming Contest is over!")) {
            HookExecute.send("Yung Script Log", "Jacob's Contest End", "Contest ended.", false);
            oneGold = false;
            oneJacob = false;
            activeContest = false;
        }
        if(message.contains("You were ") && message.contains(" in Limbo.")) {
            isOnLimbo = true;
            isOnIsland = false;
            inHub = false;
            isOnLobby = false;
            SkyblockUtils.isInLimbo = true;
        }
        if(message.contains("/limbo ")) {
            isOnLobby = false;
            inHub = false;
            isOnIsland = false;
            isOnLimbo = true;
            SkyblockUtils.isInLimbo = true;
        }
        if(message.contains("Warped from the ")) {
            if(cdPad) return;
            BlockPos pos = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
            EnumFacing player;
            EnumFacing facing = player = mc.thePlayer.getHorizontalFacing();
            switch (Config.dirfarm) {
                case 0: {
                    this.left = true;
                    this.right = false;
                    break;
                }
                case 1: {
                    this.left = false;
                    this.right = true;
                    break;
                }
            }
            layerswitchSafe = true;
            if(Config.stylemessages == 0) {
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Switching layers..."));
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Layer switched."));
            } else {
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Switching layers..."));
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Layer switched."));
            }
            cdPad = true;
        }
        final String msg = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (msg.matches("\\[SkyBlock] .*? is visiting Your Island!.*")) {
            HookExecute.send("Yung Script Log", "Someone visited you!", "Applying failsafes. (dumbass just close ur island)", false);
            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Someone annoying you! applying failsafes..."));
            Utils.sleep(2400 + Utils.random.nextInt(500));
            final String msgac = "/ac " + ScriptExt.messages.get(Utils.random.nextInt(ScriptExt.messages.size()));
            mc.thePlayer.sendChatMessage(msgac);
            Utils.sleep(300 + Utils.random.nextInt(150));
            nonEmptyIsland = true;
            if (msg.startsWith("From")) {
            }
/*            else if (msg.matches("\\[SkyBlock] .*? is visiting Your Island!.*")) {
                final Pattern pat = Pattern.compile("\\[SkyBlock] (.*?) is visiting Your Island!.*");
                final Matcher mat = pat.matcher(msg);
                if (mat.matches()) {
                }
            }
            else if (msg.contains("has invited you to join their party!")) {
                final String[] split = msg.split("\n");
                if (split.length == 4) {
                    final String mm = split[1];
                    final Pattern pat2 = Pattern.compile("(.*?) has invited you to join their party!.*");
                    final Matcher mat2 = pat2.matcher(mm);
                    if (mat2.matches()) {

                    }
                }
            }*/
        }
        if (msg.startsWith("[Important] This server will restart soon:")) {
            HookExecute.send("Yung Script Log", "Server rebooting!", "Warping out", false);
            if(Config.stylemessages == 0) {
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Server rebooting. Warping to lobby in 10 Seconds."));
            } else {
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> " + EnumChatFormatting.GRAY + "Server rebooting. " + EnumChatFormatting.LIGHT_PURPLE + "Warping to lobby in 10 Seconds."));
            }
            new Thread(() -> {
                try {
                    Thread.sleep(10000 + Utils.random.nextInt(400));
                    if(!extraHubWarp) {
                        extraHubWarp = true;
                        mc.thePlayer.sendChatMessage("/lobby");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    @SubscribeEvent(receiveCanceled = true)
    public void onOpenGui(final GuiOpenEvent event) {
        if (event.gui instanceof GuiDisconnected && Config.nwscript) {
            reconnect();
            event.setCanceled(true);
        }
    }
    private void reconnect() {
        FMLClientHandler.instance().connectToServer((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()), new ServerData("Hypixel", "mc.hypixel.net", false));
    }
    enum SameInvState
    {
        CHILLING,
        UNPRESS,
        PRESS
    }
    enum FacingRow {
        SOUTH,
        EAST,
        WEST,
        NORTH
    }
    enum LadderState {
        WALKING_IN,
        REVERTING_YAW,
        STOPPED
    }
}
