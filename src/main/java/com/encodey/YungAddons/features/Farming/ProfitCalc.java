package com.encodey.YungAddons.features.Farming;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.Core.gui.LocationEditGui;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.commands.Dankers.MoveCommand;
import com.encodey.YungAddons.commands.Dankers.ScaleCommmand;
import com.encodey.YungAddons.events.Overlay;
import com.encodey.YungAddons.handler.TextRenderer;
import com.encodey.YungAddons.utils.MathUtils.LerpUtils;
import com.encodey.YungAddons.utils.MathUtils.Position;
import com.encodey.YungAddons.utils.OverlayStyle;
import com.encodey.YungAddons.utils.TextOverlay;
import com.encodey.YungAddons.utils.Utils;
import com.encodey.YungAddons.utils.UtilsDanker;
import gg.essential.universal.UChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class ProfitCalc extends TextOverlay {
    private long lastUpdate;
    private int counterLast;
    private int counter;
    private float cropsPerSecondLast;
    private float cropsPerSecond;
    public static float minProfit;
    public static String cropFarming;
    private LinkedList<Integer> counterQueue;
    private float cpsInterp;
    private long lastUpdateSync;
    private int counterLastSync;
    private int counterSync;
    private float cropsPerSecondLastSync;
    private float cropsPerSecondSync;
    public static float minProfitSync;
    public static String cropFarmingSync;
    private LinkedList<Integer> counterQueueSync;
    private float cpsInterpSync;
    public static float cropMin;
    public static float cropMinSync;
    public static final ResourceLocation INVISIBLE_ICON = new ResourceLocation("yungaddons", "icons/invisicon.png");;
    Minecraft mc;
    public static String FARMING_OVERLAY_COLOR;
    public static String passText;
    private int delayNoCalc;
    private int delayupd;
    public static String crops;

    public ProfitCalc() {
        this.lastUpdate = -1L;
        this.counterLast = -1;
        this.counter = -1;
        this.cropsPerSecondLast = 0.0f;
        this.cropsPerSecond = 0.0f;
        this.counterQueue = new LinkedList<Integer>();
        this.lastUpdateSync = -1L;
        this.counterLastSync = -1;
        this.counterSync = -1;
        this.cropsPerSecondLastSync = 0.0f;
        this.cropsPerSecondSync = 0.0f;
        this.counterQueueSync = new LinkedList<Integer>();
        this.mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void onTick(final Overlay event) {
        updateSync();
        updateFrequentSync();
        if (!Config.profitfarming) {
            this.update();
            this.updateFrequent();
        }
        else {
            if(delayupd > 0) {
                --delayupd;
                return;
            }
            this.update();
            this.updateFrequent();
            delayupd = 100;

        }
    }

    public float interp(final float now, final float last) {
        float interp = now;
        if (last >= 0.0f && last != now) {
            float factor = (System.currentTimeMillis() - this.lastUpdate) / 1000.0f;
            factor = LerpUtils.clampZeroOne(factor);
            interp = last + (now - last) * factor;
        }
        return interp;
    }

    @Override
    public void update() {
        if (!Config.profitfarming) {
            this.counter = -1;
            return;
        }
        this.lastUpdate = System.currentTimeMillis();
        this.counterLast = this.counter;
        this.counter = -1;
        if (mc.thePlayer == null) {
            return;
        }
        // ty moulberry
        final ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        if (stack != null && stack.hasTagCompound()) {
            final NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes", 10)) {
                final NBTTagCompound ea = tag.getCompoundTag("ExtraAttributes");
                if (ea.hasKey("mined_crops", 99)) {
                    counter = ea.getInteger("mined_crops");
                    counterQueue.add(0, counter);
                }
                else if (ea.hasKey("farmed_cultivating", 99)) {
                    counter = ea.getInteger("farmed_cultivating");
                    counterQueue.add(0, counter);
                }
            }
        }
        while (this.counterQueue.size() >= 4) {
            this.counterQueue.removeLast();
        }
        if (this.counterQueue.isEmpty()) {
            this.cropsPerSecond = -1.0f;
            this.cropsPerSecondLast = 0.0f;
        }
        else {
            this.cropsPerSecondLast = this.cropsPerSecond;
            final int last = this.counterQueue.getLast();
            final int first = this.counterQueue.getFirst();
            this.cropsPerSecond = (first - last) / 3.0f;
        }
        if (this.counter != -1) {
            this.overlayStrings = new ArrayList<String>();
        }
        else {
            this.overlayStrings = null;
        }
    }

    public void updateSync() {
        this.lastUpdateSync = System.currentTimeMillis();
        this.counterLastSync = this.counterSync;
        this.counterSync = -1;
        if (mc.thePlayer == null) {
            return;
        }
        final ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        if (stack != null && stack.hasTagCompound()) {
            final NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes", 10)) {
                final NBTTagCompound ea = tag.getCompoundTag("ExtraAttributes");
                if (ea.hasKey("mined_crops", 99)) {
                    counterSync = ea.getInteger("mined_crops");
                    counterQueueSync.add(0, counter);
                }
                else if (ea.hasKey("farmed_cultivating", 99)) {
                    counterSync = ea.getInteger("farmed_cultivating");
                    counterQueueSync.add(0, counter);
                }
            }
        }
        while (this.counterQueueSync.size() >= 4) {
            this.counterQueueSync.removeLast();
        }
        if (this.counterQueueSync.isEmpty()) {
            this.cropsPerSecondSync = -1.0f;
            this.cropsPerSecondLastSync = 0.0f;
        }
        else {
            this.cropsPerSecondLastSync = this.cropsPerSecondSync;
            final int last = this.counterQueueSync.getLast();
            final int first = this.counterQueueSync.getFirst();
            this.cropsPerSecondSync = (first - last) / 3.0f;
        }
        if (this.counterSync != -1) {
            this.overlayStringsSync = new ArrayList<String>();
        }
        else {
            this.overlayStringsSync = null;
        }
    }

    @Override
    public void updateFrequent() {
        super.updateFrequent();
        if (this.counter < 0) {
            this.overlayStrings = null;
        }
        else {
            final HashMap<Integer, String> lineMap = new HashMap<Integer, String>();
            final NumberFormat format = NumberFormat.getIntegerInstance();
            if (this.counter >= 0) {
                final int n = (int)this.interp((float)this.counter, (float)this.counterLast);
            }
            if (this.counter >= 0) {
                if (this.cropsPerSecondLast == this.cropsPerSecond && this.cropsPerSecond <= 0.0f) {
                    this.cpsInterp = 0.0f;
                }
                else {
                    this.cpsInterp = this.interp(this.cropsPerSecond, this.cropsPerSecondLast);
                }
            }
            for (final int strIndex : YungAddons.locEdit.farmingTextOver) {
                if (lineMap.get(strIndex) != null) {
                    this.overlayStrings.add(lineMap.get(strIndex));
                    ProfitCalc.crops = "Cr: " + lineMap.get(strIndex);
                }
            }
            if (this.overlayStrings != null && this.overlayStrings.isEmpty()) {
                this.overlayStrings = null;
            }
        }
    }

    public void updateFrequentSync() {
        if (this.counterSync < 0) {
            this.overlayStringsSync = null;
        }
        else {
            final HashMap<Integer, String> lineMap = new HashMap<Integer, String>();
            final NumberFormat format = NumberFormat.getIntegerInstance();
            if (this.counterSync >= 0) {
                final int n = (int)this.interp((float)this.counterSync, (float)this.counterLastSync);
            }
            if (this.counterSync >= 0) {
                if (this.cropsPerSecondLastSync == this.cropsPerSecondSync && this.cropsPerSecondSync <= 0.0f) {
                    this.cpsInterpSync = 0.0f;
                }
                else {
                    this.cpsInterpSync = this.interp(this.cropsPerSecondSync, this.cropsPerSecondLastSync);
                }
            }
            for (final int strIndex : YungAddons.locEdit.farmingTextOver) {
                if (lineMap.get(strIndex) != null) {
                    this.overlayStringsSync.add(lineMap.get(strIndex));
                    ProfitCalc.crops = "Cr: " + lineMap.get(strIndex);
                }
            }
            if (this.overlayStringsSync != null && this.overlayStringsSync.isEmpty()) {
                this.overlayStringsSync = null;
            }
        }
    }
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if(!Config.profitfarming) {
            cropMin = cpsInterp * 60;
            cropMinSync = cpsInterpSync * 60;
            return;
        }
        if(Config.profitfarming) {
            if(event.type == RenderGameOverlayEvent.ElementType.ALL) {
                NumberFormat formatCalc = NumberFormat.getInstance();
                formatCalc.setGroupingUsed(true);
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                decimalFormat.setGroupingUsed(true);
                decimalFormat.setGroupingSize(3);
                HashMap<Integer, String> lineMap = new HashMap<>();
                cropMin = cpsInterp * 60;
                cropMinSync = cpsInterpSync * 60;
                float profittotal = (cropMin * 60)/2;
                Minecraft mc = Minecraft.getMinecraft();
                double scale = ScaleCommmand.farmingTextScale;
                int scaledWidth = scaledResolution.getScaledWidth();
                double scaleReset = Math.pow(scale, -1);
                GL11.glScaled(scale, scale, scale);

                mc.getTextureManager().bindTexture(INVISIBLE_ICON);
                Gui.drawModalRectWithCustomSizedTexture(MoveCommand.farmingTextXY[0], MoveCommand.farmingTextXY[1], 0, 0, 16, 16, 16, 16);

                int stringWidth = mc.fontRendererObj.getStringWidth(passText);

                if (stringWidth * scale > (scaledWidth * 0.9F)) {
                    scale = (scaledWidth * 0.9F) / (float) stringWidth;
                }
                for(int strIndex : YungAddons.locEdit.farmingTextOver) {
                    if(lineMap.get(strIndex) != null) {
                        overlayStrings.add(lineMap.get(strIndex));
                    }
                }
                if(Config.farmingtype == 0) {
                    cropFarming = FARMING_OVERLAY_COLOR + "Farming crop: " + FARMING_OVERLAY_COLOR + "Nether wart";
                    minProfit = cropMin * 2;
                    if(counter >= 0) {
                        if(passText == null) {
                            passText = FARMING_OVERLAY_COLOR + "NONE";
                        }
                        if (cropsPerSecond <= 0) {
                            passText = FARMING_OVERLAY_COLOR + "NONE";
                        } else {
                            passText = formatCalc.format(profittotal*2);
                        }
                    }
                    lineMap.put(1, "Crops/min:  "+
                            String.format("%.2f", cropMin));
                    lineMap.put(2, "Profit/h:  "+
                            String.format("%,.2f", profittotal*2));
                    new TextRenderer(mc, cropFarming + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(2) + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(1), MoveCommand.farmingTextXY[0] + 25, MoveCommand.farmingTextXY[1] + 8, scale);
                }
                if(Config.farmingtype == 1) {
                    cropFarming = FARMING_OVERLAY_COLOR + "Farming crop: " + FARMING_OVERLAY_COLOR + "Potato";
                    minProfit = cropMin * 1;
                    if(counter >= 0) {
                        if(passText == null) {
                            passText = FARMING_OVERLAY_COLOR + "NONE";
                        }
                        if (cropsPerSecond <= 0) {
                            passText = FARMING_OVERLAY_COLOR + "NONE";
                        } else {
                            passText = decimalFormat.format(profittotal*1);
                        }
                    }
                    lineMap.put(3, "Crops/min:  "+
                            String.format("%.2f", cropMin));
                    lineMap.put(4, "Profit/h:  "+
                            String.format("%,.2f", profittotal*1));
                    new TextRenderer(mc, cropFarming + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(4) + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(3), MoveCommand.farmingTextXY[0] + 25, MoveCommand.farmingTextXY[1] + 8, scale);
                }
                if(Config.farmingtype == 2) {
                    cropFarming = FARMING_OVERLAY_COLOR + "Farming crop: " + FARMING_OVERLAY_COLOR + "Carrot";
                    minProfit = cropMin * 1;
                    if(counter >= 0) {
                        if(passText == null) {
                            passText = FARMING_OVERLAY_COLOR + "NONE";
                        }
                        if (cropsPerSecond <= 0) {
                            passText = FARMING_OVERLAY_COLOR + "NONE";
                        } else {
                            passText = decimalFormat.format(profittotal*1);
                        }
                    }
                    lineMap.put(5, "Crops/min:  "+
                            String.format("%.2f", cropMin));
                    lineMap.put(6, "Profit/h:  "+
                            String.format("%,.2f", profittotal*1));
                    new TextRenderer(mc, cropFarming + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(6) + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(5), MoveCommand.farmingTextXY[0] + 25, MoveCommand.farmingTextXY[1] + 8, scale);
                }
                if(Config.farmingtype == 3) {
                    cropFarming = FARMING_OVERLAY_COLOR + "Farming crop: " + FARMING_OVERLAY_COLOR + "Pumpkin";
                    minProfit = cropMin * 4;
                    if(counter >= 0) {
                        if(passText == null) {
                            passText = FARMING_OVERLAY_COLOR + "NONE";
                        }
                        if (cropsPerSecond <= 0) {
                            passText = FARMING_OVERLAY_COLOR + "NONE";
                        } else {
                            passText = formatCalc.format(profittotal*2);
                        }
                    }
                    lineMap.put(7, "Crops/min:  "+
                            String.format("%.2f", cropMin));
                    lineMap.put(8, "Profit/h:  "+
                            String.format("%,.2f", profittotal*2));
                    new TextRenderer(mc, cropFarming + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(8) + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(7), MoveCommand.farmingTextXY[0] + 25, MoveCommand.farmingTextXY[1] + 8, scale);
                }
                if(Config.farmingtype == 4) {
                    cropFarming = FARMING_OVERLAY_COLOR + "Farming crop: " + FARMING_OVERLAY_COLOR + "Melon";
                    minProfit = cropMin * 0.5f;
                    if(counter >= 0) {
                        if(passText == null) {
                            passText = FARMING_OVERLAY_COLOR + "NONE";
                        }
                        if (cropsPerSecond <= 0) {
                            passText = FARMING_OVERLAY_COLOR + "NONE";
                        } else {
                            passText = formatCalc.format(profittotal*2);
                        }
                    }
                    lineMap.put(9, "Crops/min:  "+
                            String.format("%.2f", cropMin));
                    lineMap.put(10, "Profit/h:  "+
                            String.format("%,.2f", profittotal*2));
                    new TextRenderer(mc, cropFarming + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(10) + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(9), MoveCommand.farmingTextXY[0] + 25, MoveCommand.farmingTextXY[1] + 8, scale);
                }
                if(Config.farmingtype == 5) {
                    cropFarming = FARMING_OVERLAY_COLOR + "Farming crop: " + FARMING_OVERLAY_COLOR + "Sugar cane";
                    minProfit = cropMin * 2;
                    if(counter >= 0) {
                        if(passText == null) {
                            passText = FARMING_OVERLAY_COLOR + "NONE";
                        }
                        if (cropsPerSecond <= 0) {
                            passText = FARMING_OVERLAY_COLOR + "NONE";
                        } else {
                            passText = decimalFormat.format(profittotal*2);
                        }
                    }
                    lineMap.put(11, "Crops/min:  "+
                            String.format("%.2f", cropMin));
                    lineMap.put(12, "Profit/h:  "+
                            String.format("%,.2f", profittotal*2));
                    new TextRenderer(mc, cropFarming + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(12) + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(11), MoveCommand.farmingTextXY[0] + 25, MoveCommand.farmingTextXY[1] + 8, scale);
                }

                GL11.glScaled(scaleReset, scaleReset, scaleReset);
            }
        }
    }
}

