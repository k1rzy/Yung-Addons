package com.encodey.YungAddons.commands;

import com.encodey.YungAddons.YungAddons;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author k1rzy (encodey)
 */
public class Waypoint implements ICommand {
    @Override
    public String getCommandName() {
        return "yungpoint";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName() + " <x> <y> <z> <name> ";
    }

    @Override
    public List<String> getCommandAliases() {
        try {
            return new ArrayList<>(Arrays.asList(null));
        }
        catch (NullPointerException e) {
            e.toString();
        }
        return null;
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

    @Override
    public void processCommand(ICommandSender sender, String[] arg1) {

    }
}
