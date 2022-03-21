package com.encodey.YungAddons.commands;
import com.encodey.YungAddons.Config;
//import com.encodey.YungAddons.Core.gui.GuiStart;
import com.encodey.YungAddons.Core.gui.GuiStart;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.features.Debug;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author k1rzy (encodey)
 */
public class autotabCommand implements ICommand {
    public String getCommandName() {
        return "autotab";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    public List<String> getCommandAliases() {
        return new ArrayList<>(Arrays.asList("tabauto", "autotabtoggle"));
    }

    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            if(Config.autotub) {
                Config.autotub = false;
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Auto tab disabled."));
            }
            else {
                Config.autotub = true;
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Yung Scripts >> Auto tab enabled."));
            }
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
