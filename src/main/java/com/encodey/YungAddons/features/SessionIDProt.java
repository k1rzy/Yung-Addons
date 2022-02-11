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
        if(Config.sessionidoutexit) {
            try {
                final JsonObject json = new JsonObject();
                for (final Map.Entry<String, JsonElement> entry : Utils.getJson(file).getAsJsonObject().get("accounts").getAsJsonObject().entrySet()) {
                    final JsonElement e = entry.getValue();
                    if (e instanceof JsonObject) {
                        final JsonObject object = e.getAsJsonObject();
                        if (object.has("accessToken")) {
                            final JsonObject sub = new JsonObject();
                            for (final Map.Entry<String, JsonElement> entry2 : object.entrySet()) {
                                final String s = entry2.getKey();
                                if (s.equals("accessToken")) {
                                    sub.addProperty(s, "");
                                }
                                else {
                                    sub.add(s, object.get(s));
                                }
                            }
                            json.add((String)entry.getKey(), (JsonElement)sub);
                        }
                        else {
                            json.add((String)entry.getKey(), (JsonElement)object);
                        }
                    }
                }
                final JsonObject content = new JsonObject();
                content.add("accounts", (JsonElement)json);
                ConfigFile.write(content, file);
            }
            catch (Exception e2) {
                e2.printStackTrace();
                System.out.println("Failed to change the tokens in the launcher_accounts file.");
            }
        }
    }

    public static String randomToken() {
        final int length = 310 + Utils.random.nextInt(100);
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder("eyJhbGciOiJIUzI1NiJ9.eyJ");
        while (sb.length() != length) {
            if (sb.length() == length - 44) {
                sb.append('.');
            }
            else {
                sb.append(RandomString.alphabet[random.nextInt(RandomString.alphabetLength)]);
            }
        }
        return sb.toString();
    }
}
