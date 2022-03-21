package com.encodey.YungAddons.utils;
import net.minecraft.client.*;
import net.minecraft.util.*;
import org.lwjgl.input.*;

public class ChadUtils
{
    private static Minecraft mc = Minecraft.getMinecraft();
    public static boolean isUngrabbed;
    private static MouseHelper oldMouseHelper;
    private static boolean doesGameWantUngrab = true;
    private static int oldRenderDist = 0;
    private static int oldFpsCap = 0;
    private static boolean improving;

    public static void ungrabMouse() {
        if (!ChadUtils.mc.inGameHasFocus || ChadUtils.isUngrabbed) {
            return;
        }
        if (ChadUtils.oldMouseHelper == null) {
            ChadUtils.oldMouseHelper = ChadUtils.mc.mouseHelper;
        }
        ChadUtils.mc.gameSettings.pauseOnLostFocus = false;
        ChadUtils.doesGameWantUngrab = !Mouse.isGrabbed();
        ChadUtils.oldMouseHelper.ungrabMouseCursor();
        ChadUtils.mc.inGameHasFocus = true;
        ChadUtils.mc.mouseHelper = new MouseHelper() {
            public void mouseXYChange() {
            }

            public void grabMouseCursor() {
                ChadUtils.doesGameWantUngrab = false;
            }

            public void ungrabMouseCursor() {
                ChadUtils.doesGameWantUngrab = true;
            }
        };
        ChadUtils.isUngrabbed = true;
    }

    public static void regrabMouse() {
        if (!ChadUtils.isUngrabbed) {
            return;
        }
        ChadUtils.mc.mouseHelper = ChadUtils.oldMouseHelper;
        if (!ChadUtils.doesGameWantUngrab) {
            ChadUtils.mc.mouseHelper.grabMouseCursor();
        }
        ChadUtils.oldMouseHelper = null;
        ChadUtils.isUngrabbed = false;
    }
    public static void regrabMouseRet() {
        ChadUtils.mc.mouseHelper = ChadUtils.oldMouseHelper;
        if (!ChadUtils.doesGameWantUngrab) {
            ChadUtils.mc.mouseHelper.grabMouseCursor();
        }
        ChadUtils.oldMouseHelper = null;
        ChadUtils.isUngrabbed = false;
    }

    public static void improveCpuUsage() {
        if (!improving) {
            oldRenderDist = mc.gameSettings.renderDistanceChunks;
            oldFpsCap = mc.gameSettings.limitFramerate;
            mc.gameSettings.renderDistanceChunks = 2;
            mc.gameSettings.limitFramerate = 30;
            improving = true;
        }
    }

    public static void revertCpuUsage() {
        ChadUtils.mc.gameSettings.renderDistanceChunks = ChadUtils.oldRenderDist;
        ChadUtils.mc.gameSettings.limitFramerate = ChadUtils.oldFpsCap;
        ChadUtils.improving = false;
    }
}

