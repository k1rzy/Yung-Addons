package com.encodey.YungAddons.commands;

import net.minecraft.command.ICommandSender;

public interface commands {
    String getCommandName();

    boolean isHidden();

    String getCommandUsage();

    String getCommandDescription();

    boolean processCommand(ICommandSender sender, String[] args);
}
