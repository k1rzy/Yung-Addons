package com.encodey.YungAddons.commands;

import com.encodey.YungAddons.Config;
//import com.encodey.YungAddons.Core.gui.GuiStart;
import com.encodey.YungAddons.YungAddons;
import gg.essential.api.EssentialAPI;
import gg.essential.universal.UChat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class addonsCommand implements ICommand {
    public String getCommandName() {
        return "yungaddons";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    public List<String> getCommandAliases() {
        return new ArrayList<>(Arrays.asList("yungaddon", "yung"));
    }

    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            EssentialAPI.getGuiUtil().openScreen(YungAddons.configFile.gui());
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return new ArrayList<>();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(@NotNull ICommand o) {
        return 0;
    }
}

