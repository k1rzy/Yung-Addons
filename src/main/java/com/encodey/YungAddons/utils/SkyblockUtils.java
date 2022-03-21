package com.encodey.YungAddons.utils;

import com.encodey.YungAddons.events.PacketSentEvent;
import com.encodey.YungAddons.scripts.ActualScript;
import com.encodey.YungAddons.scripts.netherwart;
import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import java.util.regex.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.scoreboard.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;
/**
 * @author k1rzy (encodey)
 */
public class SkyblockUtils
{
    private static Minecraft mc = Minecraft.getMinecraft();
    private static ArrayList<Block> interactables = new ArrayList<Block>(Arrays.asList(Blocks.acacia_door, Blocks.anvil, (Block)Blocks.beacon, Blocks.bed, Blocks.birch_door, Blocks.brewing_stand, Blocks.command_block, Blocks.crafting_table, (Block)Blocks.chest, Blocks.dark_oak_door, (Block)Blocks.daylight_detector, (Block)Blocks.daylight_detector_inverted, Blocks.dispenser, Blocks.dropper, Blocks.enchanting_table, Blocks.ender_chest, Blocks.furnace, (Block)Blocks.hopper, Blocks.jungle_door, Blocks.lever, Blocks.noteblock, (Block)Blocks.powered_comparator, (Block)Blocks.unpowered_comparator, (Block)Blocks.powered_repeater, (Block)Blocks.unpowered_repeater, Blocks.standing_sign, Blocks.wall_sign, Blocks.trapdoor, Blocks.trapped_chest, Blocks.wooden_button, Blocks.stone_button, Blocks.oak_door, (Block)Blocks.skull));
    public static int lastReportedSlot;
    public static boolean isInLimbo;

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(isInIsland()) {
            ActualScript.isOnIsland = false;
        }
        getLocation();
    }

    @SubscribeEvent
    public void onPacketSent(final PacketSentEvent event) {
        if (event.packet instanceof C09PacketHeldItemChange) {
            SkyblockUtils.lastReportedSlot = ((C09PacketHeldItemChange)event.packet).getSlotId();
        }
    }

    public static Location getLocation() {
        if (ActualScript.isOnIsland) {
            isInLimbo = false;
            return Location.ISLAND;
        }
        if (isInIsland()) {
            isInLimbo = false;
            return Location.ISLAND;
        }
        if (isInHub()) {
            isInLimbo = false;
            return Location.HUB;
        }
        if (isAtLift()) {
            isInLimbo = false;
            return Location.LIFT;
        }
        if (isInSkyblock()) {
            isInLimbo = false;
            return Location.SKYBLOCK;
        }
        if (isInLobby()) {
            isInLimbo = false;
            return Location.LOBBY;
        }
        final IBlockState ibs = SkyblockUtils.mc.theWorld.getBlockState(SkyblockUtils.mc.thePlayer.getPosition().down());
        if (ibs != null && ibs.getBlock() == Blocks.planks) {
            return Location.LIMBO;
        }
        return Location.NONE;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if(message.contains(" spawned in Limbo") && message.contains("/limbo ")) {
            isInLimbo = true;
        }
    }
    public static boolean isInIsland() {
        return hasLine("Your Island");
    }

    public static boolean isInHub() {
        return hasLine("Village") && !hasLine("Dwarven");
    }

    public static boolean isAtLift() {
        return hasLine("The Lift");
    }

    public static boolean isInDungeon() {
        return hasLine("Dungeon Cleared:") || hasLine("Start");
    }

    public static boolean isInFloor(final String floor) {
        return hasLine("The Catacombs (" + floor + ")");
    }

    public static boolean isInSkyblock() {
        return hasLine("SKYBLOCK");
    }

    public static boolean isInLobby() {
        return hasLine("HYPIXEL") || hasLine("PROTOTYPE");
    }

    public static boolean hasLine(final String sbString) {
        if (SkyblockUtils.mc != null && SkyblockUtils.mc.thePlayer != null) {
            final ScoreObjective sbo = SkyblockUtils.mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (sbo != null) {
                final List<String> scoreboard = getSidebarLines();
                scoreboard.add(StringUtils.stripControlCodes(sbo.getDisplayName()));
                for (final String s : scoreboard) {
                    final String validated = stripString(s);
                    if (validated.contains(sbString)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String stripString(final String s) {
        final char[] nonValidatedString = StringUtils.stripControlCodes(s).toCharArray();
        final StringBuilder validated = new StringBuilder();
        for (final char a : nonValidatedString) {
            if (a < '\u007f' && a > '\u0014') {
                validated.append(a);
            }
        }
        return validated.toString();
    }

    private static List<String> getSidebarLines() {
        final List<String> lines = new ArrayList<String>();
        final Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
        if (scoreboard == null) {
            return lines;
        }
        final ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
        if (objective == null) {
            return lines;
        }
        Collection<Score> scores = (Collection<Score>)scoreboard.getSortedScores(objective);
        final List<Score> list = new ArrayList<Score>();
        for (final Score s : scores) {
            if (s != null && s.getPlayerName() != null && !s.getPlayerName().startsWith("#")) {
                list.add(s);
            }
        }
        if (list.size() > 15) {
            scores = (Collection<Score>)Lists.newArrayList(Iterables.skip((Iterable)list, scores.size() - 15));
        }
        else {
            scores = list;
        }
        for (final Score score : scores) {
            final ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
            lines.add(ScorePlayerTeam.formatPlayerName((Team)team, score.getPlayerName()));
        }
        return lines;
    }

    public static void silentUse(final int mainSlot, final int useSlot) {
        final int oldSlot = SkyblockUtils.mc.thePlayer.inventory.currentItem;
        if (useSlot > 0 && useSlot <= 9) {
            SkyblockUtils.mc.thePlayer.inventory.currentItem = useSlot - 1;
            SkyblockUtils.mc.playerController.sendUseItem((EntityPlayer)SkyblockUtils.mc.thePlayer, (World)SkyblockUtils.mc.theWorld, SkyblockUtils.mc.thePlayer.getHeldItem());
        }
        if (mainSlot > 0 && mainSlot <= 9) {
            SkyblockUtils.mc.thePlayer.inventory.currentItem = mainSlot - 1;
        }
        else if (mainSlot == 0) {
            SkyblockUtils.mc.thePlayer.inventory.currentItem = oldSlot;
        }
    }

    public enum Location
    {
        ISLAND,
        HUB,
        LIFT,
        SKYBLOCK,
        LOBBY,
        LIMBO,
        NONE;
    }
}
