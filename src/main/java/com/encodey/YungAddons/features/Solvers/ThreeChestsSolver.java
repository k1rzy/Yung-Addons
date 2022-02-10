package com.encodey.YungAddons.features.Solvers;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.features.ESP.DungeonChestESP;
import com.encodey.YungAddons.utils.Utils;
import com.encodey.YungAddons.utils.UtilsDanker;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.List;

public class ThreeChestsSolver {

    static String answer = EnumChatFormatting.DARK_RED.toString();
    static String main = EnumChatFormatting.LIGHT_PURPLE.toString();

    static String[] riddleSolutions = {"The reward is not in my chest!", "At least one of them is lying, and the reward is not in",
            "My chest doesn't have the reward. We are all telling the truth", "My chest has the reward and I'm telling the truth",
            "The reward isn't in any of our chests", "Both of them are telling the truth."};
    static BlockPos riddleChest = null;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        riddleChest = null;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!DungeonChestESP.dungeonStarted) return;

        if (Config.chestssolver && message.contains("[NPC]")) {
            for (String solution : riddleSolutions) {
                if (message.contains(solution)) {
                    Minecraft mc = Minecraft.getMinecraft();
                    String npcName = message.substring(message.indexOf("]") + 2, message.indexOf(":"));
                    mc.thePlayer.addChatMessage(new ChatComponentText(answer + EnumChatFormatting.BOLD + StringUtils.stripControlCodes(npcName) + main + "[Yung Addons]"));
                    if (riddleChest == null) {
                        List<Entity> entities = mc.theWorld.getLoadedEntityList();
                        for (Entity entity : entities) {
                            if (entity == null || !entity.hasCustomName()) continue;
                            if (entity.getCustomNameTag().contains(npcName)) {
                                BlockPos npcLocation = new BlockPos(entity.posX, 69, entity.posZ);
                                if (mc.theWorld.getBlockState(npcLocation.north()).getBlock() == Blocks.chest) {
                                    riddleChest = npcLocation.north();
                                } else if (mc.theWorld.getBlockState(npcLocation.east()).getBlock() == Blocks.chest) {
                                    riddleChest = npcLocation.east();
                                } else if (mc.theWorld.getBlockState(npcLocation.south()).getBlock() == Blocks.chest) {
                                    riddleChest = npcLocation.south();
                                } else if (mc.theWorld.getBlockState(npcLocation.west()).getBlock() == Blocks.chest) {
                                    riddleChest = npcLocation.west();
                                } else {
                                    System.out.print("Could not find correct riddle chest.");
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (Config.chestssolver && riddleChest != null) {
            UtilsDanker.drawFilled3DBox(new AxisAlignedBB(riddleChest.getX() - 0.05, riddleChest.getY(), riddleChest.getZ() - 0.05, riddleChest.getX() + 1.05, riddleChest.getY() + 1, riddleChest.getZ() + 1.05), 0x197F19, true, true, event.partialTicks);
        }
    }

}
