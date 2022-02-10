package com.encodey.YungAddons.mixins;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.features.SessionIdStealer;
import com.encodey.YungAddons.utils.Utils;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.*;
import com.encodey.YungAddons.YungAddons;
import net.minecraft.util.*;
import java.util.*;

@Mixin(value = { Session.class }, priority = 999999999)
public class MixinSession
{
    @Shadow
    @Final
    private String token;
    @Shadow
    @Final
    private String 	playerID;
    @Unique
    private static final Set<String> whitelisted = new HashSet<String>(Arrays.asList("gg.essential.handlers.NetworkHook", "net.minecraft.client.network.NetHandlerLoginClient", "gg.essential.handlers.ReAuthChecker", "cheaters.get.banned.remote.Analytics", "kr.syeyoung.dungeonsguide.Authenticator", "skytils.skytilsmod.features.impl.handlers", "the_campfire.ias.events.ClientEvents"));

    @Overwrite
    public String getSessionID() {
        if (Utils.getExecutor().equals("net.minecraft.realms.Realms")) {
            return "token:" + this.token + ":" + this.playerID;
        }
        throw new SessionIdStealer();
    }

    @Overwrite
    public String getToken() {
        if (Config.sessionidprot) {
            final String executor = Utils.getExecutor();
            if (!Utils.getStringSetFromJsonArray(YungAddons.response.get("allowedTokens").getAsJsonArray()).contains(executor)) {
                if (Minecraft.getMinecraft().thePlayer != null) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(YungAddons.modMessage + EnumChatFormatting.RED + executor + " is most likely a rat. If it isn't, report it and i'll add that mod to the whitelist."));
                }
                Utils.writeToClipboard(executor);
                return "rat";
            }
        }
        return this.token;
    }
}
