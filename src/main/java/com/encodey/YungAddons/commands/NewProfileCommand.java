package com.encodey.YungAddons.commands;

import com.encodey.YungAddons.ScriptAliases;
import com.encodey.YungAddons.inventory.items.ItemList;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author k1rzy (encodey)
 */
public class NewProfileCommand implements ICommand {

    public static boolean ReplacingMenu;

    public String getCommandName() {
        return "newprofile";
    }
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    public List<String> getCommandAliases() {
        return new ArrayList<>(Arrays.asList("startskyblock", "startprofile"));
    }

    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            ReplacingMenu = true;
            ScriptAliases.mc.thePlayer.replaceItemInInventory(9, new ItemStack(ItemList.skyblock_menu));
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
