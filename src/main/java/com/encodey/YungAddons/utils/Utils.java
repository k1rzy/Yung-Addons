package com.encodey.YungAddons.utils;

import com.google.common.collect.Iterables;
import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.util.*;
import net.minecraftforge.common.config.Configuration;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public class Utils {

    public static Configuration config;
    private final static String file = "config/yungaddons/colorconfig.cfg";

    public static final Random random;
    public static boolean bypassVolume;

    static Minecraft mc = Minecraft.getMinecraft();

    public static final JsonParser jsonParser = new JsonParser();

    public static String MAIN_COLOUR = initString("colors", "main", EnumChatFormatting.GREEN.toString());
    public static String ERROR_COLOUR = initString("colors", "error", EnumChatFormatting.RED.toString());
    public static String DELIMITER_COLOUR = initString("colors", "delimiter", EnumChatFormatting.AQUA.toString() + EnumChatFormatting.STRIKETHROUGH.toString());

    private final String troll = "\u28c0\u28e0\u28e4\u28e4\u28e4\u28e4\u28a4\u28e4\u28c4\u28c0\u28c0\u28c0\u28c0\u2840\u2840\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2809\u2839\u28fe\u28ff\u28db\u28ff\u28ff\u28de\u28ff\u28db\u28fa\u28fb\u28be\u28fe\u28ff\u28ff\u28ff\u28f6\u28f6\u28f6\u28c4\u2840\u2804\u2804\u2804\u2804\u2804\u2820\u28ff\u28f7\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ef\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28c6\u2804\u2804\u2804\u2804\u2818\u281b\u281b\u281b\u281b\u280b\u283f\u28f7\u28ff\u28ff\u287f\u28ff\u28bf\u281f\u281f\u281f\u283b\u283b\u28ff\u28ff\u28ff\u28ff\u2840\u2804\u2804\u2880\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u289b\u28ff\u28c1\u2804\u2804\u2812\u2802\u2804\u2804\u28c0\u28f0\u28ff\u28ff\u28ff\u28ff\u2840\u2804\u2809\u281b\u283a\u28b6\u28f7\u2876\u2803\u2804\u2804\u2828\u28ff\u28ff\u2847\u2804\u287a\u28fe\u28fe\u28fe\u28ff\u28ff\u28ff\u28ff\u28fd\u28ff\u28ff\u2804\u2804\u2804\u2804\u2804\u281b\u2801\u2804\u2804\u2804\u2880\u28ff\u28ff\u28e7\u2840\u2804\u2839\u28ff\u28ff\u28ff\u28ff\u28ff\u287f\u28ff\u28fb\u28ff\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2809\u281b\u281f\u2807\u2880\u28b0\u28ff\u28ff\u28ff\u28cf\u2809\u28bf\u28fd\u28bf\u284f\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2820\u2824\u28e4\u28f4\u28fe\u28ff\u28ff\u28fe\u28ff\u28ff\u28e6\u2804\u28b9\u287f\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2812\u28f3\u28f6\u28e4\u28e4\u28c4\u28c0\u28c0\u2848\u28c0\u2881\u2881\u2881\u28c8\u28c4\u2890\u2803\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u28f0\u28ff\u28db\u28fb\u287f\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u286f\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u28ec\u28fd\u28ff\u28fb\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u2801\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2898\u28ff\u28ff\u28fb\u28db\u28ff\u287f\u28df\u28fb\u28ff\u28ff\u28ff\u28ff\u285f\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u281b\u289b\u28bf\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28f7\u287f\u2801\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2809\u2809\u2809\u2809\u2808\u2804\u2804\u2804\u2804\u2804\u2804";
    public static List<Packet<?>> pausedPackets;
    public static boolean stopPackets;
    private static float yaw;
    private static float pitch;
    public static Vec3 rotationVec;
    public static boolean leftClick;
    public static int rightClick;
    public static BlockPos block;

    public static String getString(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, "").getString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return "";
    }

    public static boolean hasKey(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (!config.hasCategory(category)) return false;
            return config.getCategory(category).containsKey(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
        return false;
    }

    public static String cleanColour(String in) {
        return in.replaceAll("(?i)\\u00A7.", "");
    }

    public static void writeStringConfig(String category, String key, String value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            String set = config.get(category, key, value).getString();
            config.getCategory(category).get(key).set(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static String initString(String category, String key, String defaultValue) {
        if (!hasKey(category, key)) {
            writeStringConfig(category, key, defaultValue);
            return defaultValue;
        } else {
            return getString(category, key);
        }
    }

    public static String getColouredBoolean(final boolean bool) {
        return bool ? (EnumChatFormatting.GREEN + "On") : (EnumChatFormatting.RED + "Off");
    }

    public static void sleep(final int sleeptime) {
        try {
            Thread.sleep(sleeptime);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void writeToClipboard(final String text, final String successMessage) {
        try {
            final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            final StringSelection output = new StringSelection(text);
            clipboard.setContents(output, output);
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(successMessage));
        }
        catch (IllegalStateException exception) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Clipboard not available!"));
            exception.printStackTrace();
        }
    }

    public static void writeToClipboard(final String text) {
        try {
            final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            final StringSelection output = new StringSelection(text);
            clipboard.setContents(output, output);
        }
        catch (Exception e) {
            System.out.println("[ERROR] Failed to copy to clipboard.");
            e.printStackTrace();
        }
    }

    public static int betterRandom(int randomness) {
        int randomNumb = 0;
        while (randomness > 0) {
            randomNumb += (int)(Utils.random.nextGaussian() * randomness);
            --randomness;
        }
        return randomNumb;
    }

    public static <T> T firstOrNull(final Iterable<T> iterable) {
        return (T) Iterables.getFirst((Iterable)iterable, (Object)null);
    }

    public static String readURL(final URL url) throws IOException {
        final StringBuilder sb = new StringBuilder();
        try (final InputStream in = url.openStream()) {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    public static String readURL(final String url) {
        try {
            final StringBuilder sb = new StringBuilder();
            final URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            try (final InputStream in = connection.getInputStream()) {
                final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append(System.lineSeparator());
                }
            }
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void openUrl(final String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static String readFile(final String file) throws IOException {
        final StringBuilder sb = new StringBuilder();
        final BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString();
    }
    public static String readFile(final File file) {
        try {
            final StringBuilder sb = new StringBuilder();
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            return sb.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String[] getStringArrayFromJsonArray(final JsonArray jsonArray) {
        final int arraySize = jsonArray.size();
        final String[] stringArray = new String[arraySize];
        for (int i = 0; i < arraySize; ++i) {
            stringArray[i] = jsonArray.get(i).getAsString();
        }
        return stringArray;
    }

    public static Set<String> getStringSetFromJsonArray(final JsonArray jsonArray) {
        final Set<String> strings = new HashSet<String>();
        for (int i = 0; i < jsonArray.size(); ++i) {
            strings.add(jsonArray.get(i).getAsString());
        }
        return strings;
    }

    public static boolean isJar() {
        try {
            Minecraft.getMinecraft().getClass().getDeclaredMethod("func_147121_ag", (Class<?>[])new Class[0]);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static JsonElement getJson(final String jsonUrl) {
        return new JsonParser().parse((Reader)getInputStream(jsonUrl));
    }

    public static JsonElement getJson(final File file) {
        return new JsonParser().parse(readFile(file));
    }

    public static JsonElement getJson(final URL url) {
        try {
            final URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            return Utils.jsonParser.parse((Reader)new InputStreamReader(conn.getInputStream()));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InputStreamReader getInputStream(final String url) {
        try {
            final URLConnection conn = new URL(url).openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            return new InputStreamReader(conn.getInputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatValue(final long amount) {
        if (amount >= 1000000000L) {
            return formatValue(amount, 1000000000L, 'b');
        }
        if (amount >= 1000000L) {
            return formatValue(amount, 1000000L, 'm');
        }
        if (amount >= 1000L) {
            return formatValue(amount, 1000L, 'k');
        }
        return NumberFormat.getInstance().format(amount);
    }

    private static String formatValue(final long amount, final long div, final char suffix) {
        return new DecimalFormat(".##").format(amount / (double)div) + suffix;
    }

    public static boolean isGoldenGoblin(final EntityOtherPlayerMP entityIn) {
        for (final ItemStack item : entityIn.inventory.armorInventory) {
            if (item == null) {
                return false;
            }
            final Item armor = item.getItem();
            if (!armor.equals(Items.golden_helmet) && !armor.equals(Items.golden_chestplate) && !armor.equals(Items.golden_leggings) && !armor.equals(Items.golden_boots)) {
                return false;
            }
        }
        return true;
    }

    public static double getXandZDistance(final double x1, final double x2, final double z1, final double z2) {
        final double diffX = x1 - x2;
        final double diffZ = z1 - z2;
        return Math.sqrt(diffX * diffX + diffZ * diffZ);
    }

    public static double getXandZDistanceSquared(final double x1, final double x2, final double z1, final double z2) {
        final double diffX = x1 - x2;
        final double diffZ = z1 - z2;
        return diffX * diffX + diffZ * diffZ;
    }

    public static String getExecutor() {
        try {
            return new Exception().getStackTrace()[2].getClassName();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStackTrace() {
        try {
            final StringBuilder sb = new StringBuilder();
            for (final StackTraceElement s : Thread.currentThread().getStackTrace()) {
                sb.append(s.toString());
                sb.append("\n");
            }
            return sb.toString();
        }
        catch (Exception ex) {
            return null;
        }
    }

    public static EnumFacing getRightEnumfacing(final EnumFacing facing) {
        switch (facing) {
            case NORTH: {
                return EnumFacing.EAST;
            }
            case EAST: {
                return EnumFacing.SOUTH;
            }
            case SOUTH: {
                return EnumFacing.WEST;
            }
            case WEST: {
                return EnumFacing.NORTH;
            }
            default: {
                return EnumFacing.NORTH;
            }
        }
    }

    public static EnumFacing getLeftEnumfacing(final EnumFacing facing) {
        switch (facing) {
            case NORTH: {
                return EnumFacing.WEST;
            }
            case EAST: {
                return EnumFacing.NORTH;
            }
            case SOUTH: {
                return EnumFacing.EAST;
            }
            case WEST: {
                return EnumFacing.SOUTH;
            }
            default: {
                return EnumFacing.NORTH;
            }
        }
    }
    public static void sendPackets(final List<Packet<?>> packets) {
        final boolean bool = stopPackets;
        stopPackets = false;
        for (final Packet<?> packet : packets) {
            pausedPackets.remove(packet);
            mc.thePlayer.sendQueue.getNetworkManager().sendPacket((Packet)packet);
        }
        stopPackets = bool;
    }

    public static void continueAndSendPackets() {
        stopPackets = false;
        for (final Packet<?> packet : pausedPackets) {
            mc.thePlayer.sendQueue.getNetworkManager().sendPacket((Packet)packet);
        }
        pausedPackets.clear();
    }

    public static void onPlayerMovePost() {
        if (rotationVec != null && mc.currentScreen == null) {
            mc.thePlayer.rotationPitch = pitch;
            mc.thePlayer.rotationYaw = yaw;
            if (leftClick) {
                KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
                leftClick = false;
            }
            else if (rightClick == 0) {
                rotationVec = null;
            }
        }
    }

    public static String cleanColourNotModifiers(String in) {
        return in.replaceAll("(?i)\\u00A7[0-9a-f]", "");
    }

    public static void renderShadowedString(String str, float x, float y, int maxLength) {
        int strLen = Minecraft.getMinecraft().fontRendererObj.getStringWidth(str);
        float factor;
        if(maxLength < 0) {
            factor = 1;
        } else {
            factor = maxLength/(float)strLen;
            factor = Math.min(1, factor);
        }

        for(int xOff=-2; xOff<=2; xOff++) {
            for(int yOff=-2; yOff<=2; yOff++) {
                if(Math.abs(xOff) != Math.abs(yOff)) {
                    Utils.drawStringCenteredScaledMaxWidth(Utils.cleanColourNotModifiers(str), Minecraft.getMinecraft().fontRendererObj,
                            x+xOff/2f*factor, y+4+yOff/2f*factor, false, maxLength,
                            new Color(0, 0, 0, 200/Math.max(Math.abs(xOff), Math.abs(yOff))).getRGB());
                }
            }
        }

        GlStateManager.color(1, 1, 1, 1);
        Utils.drawStringCenteredScaledMaxWidth(str, Minecraft.getMinecraft().fontRendererObj,
                x, y+4, false, maxLength, 4210752);
    }
    public static void renderAlignedString(String first, String second, float x, float y, int length) {
        FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
        if(fontRendererObj.getStringWidth(first + " " + second) >= length) {
            renderShadowedString(first + " " + second, x+length/2f, y, length);
        } else {
            for(int xOff=-2; xOff<=2; xOff++) {
                for(int yOff=-2; yOff<=2; yOff++) {
                    if(Math.abs(xOff) != Math.abs(yOff)) {
                        fontRendererObj.drawString(Utils.cleanColourNotModifiers(first),
                                x+xOff/2f, y+yOff/2f,
                                new Color(0, 0, 0, 200/Math.max(Math.abs(xOff), Math.abs(yOff))).getRGB(), false);
                    }
                }
            }

            int secondLen = fontRendererObj.getStringWidth(second);
            GlStateManager.color(1, 1, 1, 1);
            fontRendererObj.drawString(first, x, y, 4210752, false);
            for(int xOff=-2; xOff<=2; xOff++) {
                for(int yOff=-2; yOff<=2; yOff++) {
                    if(Math.abs(xOff) != Math.abs(yOff)) {
                        fontRendererObj.drawString(Utils.cleanColourNotModifiers(second),
                                x+length-secondLen+xOff/2f, y+yOff/2f,
                                new Color(0, 0, 0, 200/Math.max(Math.abs(xOff), Math.abs(yOff))).getRGB(), false);
                    }
                }
            }

            GlStateManager.color(1, 1, 1, 1);
            fontRendererObj.drawString(second, x+length-secondLen, y, 4210752, false);
        }
    }
    public static BufferedImage readBufferedImage(final ResourceLocation location) throws IOException {
        return TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream());
    }
    public static void drawStringScaledMaxWidth(String str, FontRenderer fr, float x, float y, boolean shadow, int len, int colour) {
        int strLen = fr.getStringWidth(str);
        float factor = len/(float)strLen;
        factor = Math.min(1, factor);

        drawStringScaled(str, fr, x, y, shadow, colour, factor);
    }

    public static void drawStringCentered(String str, FontRenderer fr, float x, float y, boolean shadow, int colour) {
        int strLen = fr.getStringWidth(str);

        float x2 = x - strLen/2f;
        float y2 = y - fr.FONT_HEIGHT/2f;

        GL11.glTranslatef(x2, y2, 0);
        fr.drawString(str, 0, 0, colour, shadow);
        GL11.glTranslatef(-x2, -y2, 0);
    }

    public static void drawStringScaled(String str, FontRenderer fr, float x, float y, boolean shadow, int colour, float factor) {
        GlStateManager.scale(factor, factor, 1);
        fr.drawString(str, x/factor, y/factor, colour, shadow);
        GlStateManager.scale(1/factor, 1/factor, 1);
    }

    public static void drawStringCenteredScaledMaxWidth(String str, FontRenderer fr, float x, float y, boolean shadow, int len, int colour) {
        int strLen = fr.getStringWidth(str);
        float factor = len/(float)strLen;
        factor = Math.min(1, factor);
        int newLen = Math.min(strLen, len);

        float fontHeight = 8*factor;

        drawStringScaled(str, fr, x-newLen/2, y-fontHeight/2, shadow, colour, factor);
    }

    static {
        pausedPackets = new ArrayList<Packet<?>>();
    }

    static {
        random = new Random();
    }
}
