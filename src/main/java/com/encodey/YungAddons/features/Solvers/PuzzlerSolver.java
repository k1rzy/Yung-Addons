package com.encodey.YungAddons.features.Solvers;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.utils.RenderUtils;
import com.encodey.YungAddons.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class PuzzlerSolver {
    public static BlockPos rightblock;

    public static boolean getRightedBlockBool;
    public static boolean alreadyCompleted;

    BlockPos pos = new BlockPos(181, 195, 135);

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        rightblock = null;
        getRightedBlockBool = false;
    }

    @SubscribeEvent
    public void startblock(ClientChatReceivedEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if(event.message.getFormattedText().startsWith("\u00A7e[NPC] \u00A7dPuzzler") &&
                event.message.getUnformattedText().contains(":")) {
            String clean = Utils.cleanColour(event.message.getUnformattedText());
            clean = clean.split(":")[1].trim();

            for (int i = 0; i < clean.length(); i++) {
                char c = clean.charAt(i);

                if (c == '\u25C0') { //Left
                    pos = pos.add(1, 0, 0);
                } else if (c == '\u25B2') { //Up
                    pos = pos.add(0, 0, 1);
                } else if (c == '\u25BC') { //Down
                    pos = pos.add(0, 0, -1);
                } else if (c == '\u25B6') { //Right
                    pos = pos.add(-1, 0, 0);
                } else {
                    return;
                }
            }
            if(event.message.getUnformattedText().startsWith("Puzzler gaver you")) {
                pos = new BlockPos(181, 195, 135);
            }
            getRightedBlockBool = true;
        }
    }
    @SubscribeEvent
    public void onRenderBlock(RenderWorldLastEvent event) {
        if(getRightedBlockBool && !alreadyCompleted) {
            RenderUtils.drawBlockBox(pos, Color.BLUE, true, event.partialTicks);
        }
    }
}
