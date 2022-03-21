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

public class ScaleCommmand extends CommandBase {
    public static double cakeTimerScale;
    public static double passTimerScale;
    public static double waterAnswerScale;
    public static double farmingTextScale;

    @Override
    public String getCommandName() {
        return "scaliy";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " <caketimer/wateranswer/miningpasstimer/farmingtext> <size (0.1 - 1)>";
    }

    public static String usage(ICommandSender arg0) {
        return new ScaleCommmand().getCommandUsage(arg0);
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
        final EntityPlayer player = (EntityPlayer) arg0;

        if (arg1.length < 2) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: " + getCommandUsage(arg0)));
            return;
        }

        double scaleAmount = Math.floor(Double.parseDouble(arg1[1]) * 10.0) / 10.0;
        if (scaleAmount < 0.1 || scaleAmount > 1) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Scale multipler can only be between 0.1x and 1x."));
            return;
        }
        switch (arg1[0].toLowerCase()) {
            case "caketimer":
                
                TimerHandler.writeDoubleConfig("scales", "cakeTimerScale", cakeTimerScale);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Cake timer has been scaled to " + EnumChatFormatting.RED + cakeTimerScale + "x"));
                break;
            case "wateranswer":
                waterAnswerScale = scaleAmount;
                TimerHandler.writeDoubleConfig("scales", "waterAnswerScale", waterAnswerScale);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Water solver answer has been scaled to " + EnumChatFormatting.RED + waterAnswerScale + "x"));
                break;
            case "miningpasstimer":
                passTimerScale = scaleAmount;
                TimerHandler.writeDoubleConfig("scales", "passTimerScale", passTimerScale);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Hollows pass timer has been scaled to " + EnumChatFormatting.RED + passTimerScale + "x"));
                break;
            case "farmingtext":
                farmingTextScale = scaleAmount;
                TimerHandler.writeDoubleConfig("scales", "farmingTextScale", farmingTextScale);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Farming overlay has been scaled to " + EnumChatFormatting.RED + farmingTextScale + "x"));
                break;
        }
    }
}
