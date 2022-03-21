package com.encodey.YungAddons.features.Scripts;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.events.SkyblockJoin;
import com.encodey.YungAddons.utils.MathUtils.Rotate;
import com.encodey.YungAddons.utils.MathUtils.Rotater;
import com.encodey.YungAddons.utils.PlayerUtil;
import com.encodey.YungAddons.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.client.entity.*;
import java.awt.*;
import net.minecraft.item.*;
import java.util.*;
import java.util.List;

public class ScriptExt
{
    public static final List<String> messages = new ArrayList<String>(Arrays.asList("hello?", "wtf", "what", "?", "dud", "uhm what?"));;

    public static void playerInRange() {
            for (final Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                if (entity instanceof EntityOtherPlayerMP) {
                    final String name = entity.getDisplayName().getUnformattedText();
                    if (StringUtils.stripControlCodes(name).equals(name)) {
                        continue;
                    }
                    if (!name.startsWith("§")) {
                        continue;
                    }
                    if (entity.isInvisible() && !hasSorrow((EntityOtherPlayerMP)entity)) {
                        continue;
                    }
                    if (PlayerUtil.getPositionEyes().squareDistanceTo(new Vec3(entity.posX, entity.posY + 1.6200000047683716, entity.posZ)) < 40) {
                        YungAddons.mc.thePlayer.addChatMessage(new ChatComponentText("Yung Script >> Found player nearby, warping out. Player name: " + name));
                        unpressMovement();
                        YungAddons.rotater = null;
                        return;
                    }
                    continue;
                }
            }
    }

    public static void unpressMovement() {
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), false);
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), false);
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), false);
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), false);
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), false);
    }
    public static void unpressMovementStuck() {
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), false);
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), false);
    }
    public static void unpressMovementLayer() {
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), false);
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), false);
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), false);
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), false);
    }

    private static boolean hasSorrow(final EntityOtherPlayerMP entity) {
        for (final ItemStack item : entity.inventory.armorInventory) {
            if (item != null) {
                final Item item2 = item.getItem();
                if (item2 == Items.chainmail_boots || item2 == Items.chainmail_leggings || item2 == Items.chainmail_chestplate || item2 == Items.chainmail_helmet) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void sleepAndStop(final int rotationAmt) {
        if (YungAddons.rotater == null) {
            Utils.sleep(500 + Utils.random.nextInt(300));
        }
        else {
            Utils.sleep(300 + Utils.random.nextInt(150));
            YungAddons.rotater = null;
            Utils.sleep(50 + Utils.random.nextInt(25));
        }
        unpressMovement();
        Utils.sleep(300 + Utils.random.nextInt(150));
        KeyBinding.setKeyBindState(YungAddons.mc.gameSettings.keyBindSneak.getKeyCode(), true);
        Utils.sleep(400 + Utils.random.nextInt(100));
        new FailsafeRotater(rotationAmt).rotate();
        while (Rotater.rotating) {
            Utils.sleep(1);
        }
        Utils.sleep(1500 + Utils.random.nextInt(500));
    }

    private static void sneak() {
        Utils.sleep(1000 + Utils.random.nextInt(400));
        KeyBinding.setKeyBindState(YungAddons.mc.gameSettings.keyBindSneak.getKeyCode(), true);
        Utils.sleep(100 + Utils.random.nextInt(50));
        KeyBinding.setKeyBindState(YungAddons.mc.gameSettings.keyBindSneak.getKeyCode(), false);
        Utils.sleep(110 + Utils.random.nextInt(60));
        KeyBinding.setKeyBindState(YungAddons.mc.gameSettings.keyBindSneak.getKeyCode(), true);
        Utils.sleep(100 + Utils.random.nextInt(50));
        KeyBinding.setKeyBindState(YungAddons.mc.gameSettings.keyBindSneak.getKeyCode(), false);
        Utils.sleep(400 + Utils.random.nextInt(100));
    }

    public static void sendRandomMessage() {
        Utils.sleep(2400 + Utils.random.nextInt(500));
        final String msg = "/ac " + messages.get(Utils.random.nextInt(messages.size()));
        YungAddons.mc.thePlayer.sendChatMessage(msg);
        Utils.sleep(300 + Utils.random.nextInt(150));
    }

    private static void disable() {

    }
}
