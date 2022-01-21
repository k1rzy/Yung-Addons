package com.encodey.YungAddons.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class addonsCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "yung";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName();
    }

    public static String usage(ICommandSender arg0) {
        return new addonsCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    public addonsCommand() {

    }
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            //EssentialAPI.getGuiUtil().openScreen(YungAddons.config.gui());
            return;
        }
    }
}
