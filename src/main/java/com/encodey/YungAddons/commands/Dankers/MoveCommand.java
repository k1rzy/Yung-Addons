package com.encodey.YungAddons.commands.Dankers;

import com.encodey.YungAddons.handler.TimerHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class MoveCommand extends CommandBase {

    public static int[] coordsXY = {0, 0};
    public static int[] displayXY = {0, 0};
    public static int[] dungeonTimerXY = {0, 0};
    public static int[] skill50XY = {0, 0};
    public static int[] lividHpXY = {0, 0};
    public static int[] cakeTimerXY = {0, 0};
    public static int[] passTimerXY = {0, 0};
    public static int[] waterAnswerXY = {0, 0};
    public static int[] farmingTextXY = {0, 0};
    public static int[] golemTimerXY = {0 ,0};

    @Override
    public String getCommandName() {
        return "moviy";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " <caketimer/wateranswer/miningpasstimer/farmingtext/> <x> <y>";
    }

    public static String usage(ICommandSender arg0) {
        return new MoveCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "caketimer", "wateranswer", "miningpasstimer", "farmingtext");
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        final EntityPlayer player = (EntityPlayer)arg0;
        if (arg1.length < 2) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: " + getCommandUsage(arg0)));
            return;
        }

        switch (arg1[0].toLowerCase()) {
            case "caketimer":
                cakeTimerXY[0] = Integer.parseInt(arg1[1]);
                cakeTimerXY[1] = Integer.parseInt(arg1[2]);
                TimerHandler.writeIntConfig("locations", "cakeTimerX", cakeTimerXY[0]);
                TimerHandler.writeIntConfig("locations", "cakeTimerY", cakeTimerXY[1]);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Cake timer has been moved to " + EnumChatFormatting.RED + arg1[1] + ", " + arg1[2]));
                break;
            case "wateranswer":
                waterAnswerXY[0] = Integer.parseInt(arg1[1]);
                waterAnswerXY[1] = Integer.parseInt(arg1[2]);
                TimerHandler.writeIntConfig("locations", "waterAnswerX", waterAnswerXY[0]);
                TimerHandler.writeIntConfig("locations", "waterAnswerY", waterAnswerXY[1]);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Water solver answer has been moved to " + EnumChatFormatting.RED + arg1[1] + ", " + arg1[2]));
                break;
            case "miningpasstimer":
                passTimerXY[0] = Integer.parseInt(arg1[1]);
                passTimerXY[1] = Integer.parseInt(arg1[2]);
                TimerHandler.writeIntConfig("locations", "passTimerXY", passTimerXY[0]);
                TimerHandler.writeIntConfig("locations", "passTimerXY", passTimerXY[1]);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Hollows pass timer has been moved to " + EnumChatFormatting.RED + arg1[1] + ", " + arg1[2]));
                break;
            case "farmingtext":
                farmingTextXY[0] = Integer.parseInt(arg1[1]);
                farmingTextXY[1] = Integer.parseInt(arg1[2]);
                TimerHandler.writeIntConfig("locations", "farmingTextXY", farmingTextXY[0]);
                TimerHandler.writeIntConfig("locations", "farmingTextXY", farmingTextXY[1]);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Farming overlay has been moved to " + EnumChatFormatting.RED + arg1[1] + ", " + arg1[2]));
                break;
        }
    }

}