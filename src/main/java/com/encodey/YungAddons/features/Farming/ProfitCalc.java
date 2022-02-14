package com.encodey.YungAddons.features.Farming;

import com.encodey.YungAddons.Config;
import com.encodey.YungAddons.Core.gui.LocationEditGui;
import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.commands.Dankers.MoveCommand;
import com.encodey.YungAddons.commands.Dankers.ScaleCommmand;
import com.encodey.YungAddons.handler.TextRenderer;
import com.encodey.YungAddons.utils.MathUtils.LerpUtils;
import com.encodey.YungAddons.utils.MathUtils.Position;
import com.encodey.YungAddons.utils.OverlayStyle;
import com.encodey.YungAddons.utils.TextOverlay;
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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class ProfitCalc extends TextOverlay {
    private long lastUpdate = -1;
    private int counterLast = -1;
    private int counter = -1;
    private float cropsPerSecondLast = 0;
    private float cropsPerSecond = 0;
    public static float minProfit;
    public static String cropFarming;
    private LinkedList<Integer> counterQueue = new LinkedList<>();

    public static final ResourceLocation INVISIBLE_ICON = new ResourceLocation("yungaddons", "icons/invisicon.png");

    private float lastTotalXp = -1;
    private boolean isFarming = false;
    private LinkedList<Float> xpGainQueue = new LinkedList<>();
    private float xpGainHourLast = -1;
    private float xpGainHour = -1;

    public static String FARMING_OVERLAY_COLOR;

    private int xpGainTimer = 0;

    public static String passText;

    private String skillType = "Farming";
    public static String crops;

    public ProfitCalc() {
        super();
    }

    private float interp(float now, float last) {
        float interp = now;
        if(last >= 0 && last != now) {
            float factor = (System.currentTimeMillis()-lastUpdate)/1000f;
            factor = LerpUtils.clampZeroOne(factor);
            interp = last + (now - last) * factor;
        }
        return interp;
    }
    @Override
    public void update() {
        if(!Config.profitfarming) {
            counter = -1;
            return;
        }

        lastUpdate = System.currentTimeMillis();
        counterLast = counter;
        xpGainHourLast = xpGainHour;
        counter = -1;

        if(Minecraft.getMinecraft().thePlayer == null) return;

        ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        if(stack != null && stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();

            if(tag.hasKey("ExtraAttributes", 10)) {
                NBTTagCompound ea = tag.getCompoundTag("ExtraAttributes");

                if(ea.hasKey("mined_crops", 99)) {
                    counter = ea.getInteger("mined_crops");
                    counterQueue.add(0, counter);
                } else if(ea.hasKey("farmed_cultivating", 99)) {
                    counter = ea.getInteger("farmed_cultivating");
                    counterQueue.add(0, counter);
                }
            }
        }

        while(counterQueue.size() >= 4) {
            counterQueue.removeLast();
        }

        if(counterQueue.isEmpty()) {
            cropsPerSecond = -1;
            cropsPerSecondLast = 0;
        }
        else {
            cropsPerSecondLast = cropsPerSecond;
            int last = counterQueue.getLast();
            int first = counterQueue.getFirst();

            cropsPerSecond = (first - last)/3f;
        }
        if(counter != -1) {
            overlayStrings = new ArrayList<>();
        } else {
            overlayStrings = null;
        }
    }
    @Override
    public void updateFrequent() {
        super.updateFrequent();
        if(counter < 0) {
            overlayStrings = null;
        } else {
            HashMap<Integer, String> lineMap = new HashMap<>();

            NumberFormat format = NumberFormat.getIntegerInstance();

            if(counter >= 0) {
                int counterInterp = (int)interp(counter, counterLast);

            }

            if(counter >= 0) {
                if(cropsPerSecondLast == cropsPerSecond && cropsPerSecond <= 0) {

                } else {
                    float cpsInterp = interp(cropsPerSecond, cropsPerSecondLast);

                    lineMap.put(1, EnumChatFormatting.AQUA+"Crops/m: "+EnumChatFormatting.YELLOW+
                            String.format("%.2f", cpsInterp*60));
                }
            }

            float xpInterp = xpGainHour;
            if(xpGainHourLast == xpGainHour && xpGainHour <= 0) {
                lineMap.put(5, EnumChatFormatting.AQUA+"XP/h: "+EnumChatFormatting.YELLOW+"N/A");
            } else {
                xpInterp = interp(xpGainHour, xpGainHourLast);

                lineMap.put(5, EnumChatFormatting.AQUA+"XP/h: "+EnumChatFormatting.YELLOW+
                        format.format(xpInterp)+(isFarming ? "" : EnumChatFormatting.RED + " (PAUSED)"));
            }

            float yaw = Minecraft.getMinecraft().thePlayer.rotationYawHead;
            yaw %= 360;
            if(yaw < 0) yaw += 360;
            if(yaw > 180) yaw -= 360;
            for(int strIndex : YungAddons.locEdit.farmingTextOver) {
                if(lineMap.get(strIndex) != null) {
                    overlayStrings.add(lineMap.get(strIndex));
                    crops = "Cr: " + lineMap.get(strIndex);
                }
            }
            if(overlayStrings != null && overlayStrings.isEmpty()) overlayStrings = null;
        }
    }
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if(!Config.profitfarming) return;
        if(Config.profitfarming) {
            if(event.type == RenderGameOverlayEvent.ElementType.ALL) {
                HashMap<Integer, String> lineMap = new HashMap<>();
                float cpsInterp = interp(cropsPerSecond, cropsPerSecondLast);
                float cropMin = cpsInterp * 60;
                float profittotal = cropMin * 60;
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
                            passText = String.format("%.2f", minProfit*60);
                        }
                    }
                    lineMap.put(1, "Crops/min:  "+
                            String.format("%.2f", cpsInterp*60));
                    lineMap.put(2, "Profit/h:  "+
                            String.format("%.2f", profittotal*2));
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
                            passText = String.format("%.2f", minProfit*60);
                        }
                    }
                    lineMap.put(3, "Crops/min:  "+
                            String.format("%.2f", cpsInterp*60));
                    lineMap.put(4, "Profit/h:  "+
                            String.format("%.2f", profittotal*1));
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
                            passText = String.format("%.2f", minProfit*60);
                        }
                    }
                    lineMap.put(5, "Crops/min:  "+
                            String.format("%.2f", cpsInterp*60));
                    lineMap.put(6, "Profit/h:  "+
                            String.format("%.2f", profittotal*1));
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
                            passText = String.format("%.2f", minProfit*60);
                        }
                    }
                    lineMap.put(7, "Crops/min:  "+
                            String.format("%.2f", cpsInterp*60));
                    lineMap.put(8, "Profit/h:  "+
                            String.format("%.2f", profittotal*2));
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
                            passText = String.format("%.2f", minProfit*60);
                        }
                    }
                    lineMap.put(9, "Crops/min:  "+
                            String.format("%.2f", cpsInterp*60));
                    lineMap.put(10, "Profit/h:  "+
                            String.format("%.2f", profittotal*2));
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
                            passText = String.format("%.2f", minProfit*60);
                        }
                    }
                    lineMap.put(11, "Crops/min:  "+
                            String.format("%.2f", cpsInterp*60));
                    lineMap.put(12, "Profit/h:  "+
                            String.format("%.2f", profittotal*2));
                    new TextRenderer(mc, cropFarming + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(12) + "\n" + FARMING_OVERLAY_COLOR + lineMap.get(11), MoveCommand.farmingTextXY[0] + 25, MoveCommand.farmingTextXY[1] + 8, scale);
                }

                GL11.glScaled(scaleReset, scaleReset, scaleReset);
            }
        }
    }
}

