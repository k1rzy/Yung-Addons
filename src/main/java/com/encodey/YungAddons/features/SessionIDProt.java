package com.encodey.YungAddons.features;

import java.io.*;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.ConfigFile;
import com.encodey.YungAddons.utils.RandomString;
import com.encodey.YungAddons.utils.Utils;
import com.google.gson.*;
import net.minecraft.client.Minecraft;

import java.util.*;

public class SessionIDProt
{
    public static String changed;
    public static boolean changedToken;

    public SessionIDProt(final SessionIDProt a) {
        throw new SessionIdStealer();
    }

    public static void changeLauncherAccounts() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir, "launcher_accounts.json");
        if (!file.exists()) {
            return;
        }
        // removed
    }
}
