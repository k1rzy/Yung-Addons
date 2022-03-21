package com.encodey.YungAddons.Core.gui;

import java.io.IOException;
import java.util.*;

import com.encodey.YungAddons.YungAddons;
import com.encodey.YungAddons.commands.Dankers.MoveCommand;
import com.encodey.YungAddons.commands.Dankers.ScaleCommmand;
import com.encodey.YungAddons.features.Mining.CrystalHollows.MiningPassTime;
import com.encodey.YungAddons.features.Misc.Timers.CenturyCake;
import com.encodey.YungAddons.features.Misc.Timers.WitherShield;
import com.encodey.YungAddons.handler.TimerHandler;
import com.encodey.YungAddons.utils.FontUtils;
import com.encodey.YungAddons.utils.UtilsDanker;
import net.minecraft.client.gui.*;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public class LocationEditGui extends GuiScreen {

        private String moving = null;
        private int lastMouseX = -1;
        private int lastMouseY = -1;

        private LocationButton miningPassTimer;
        private LocationButton cakeTimer;
        private LocationButton farmingText;
        private LocationButton waterAnswer;

        public List<Integer> farmingTextOver = new ArrayList<>(Arrays.asList(0, 1));

        @Override
        public boolean doesGuiPauseGame() {
            return false;
        }

        @Override
        public void initGui() {
            super.initGui();


            String farmingOverText = EnumChatFormatting.AQUA + "Farming crop: Carrot\n" +
                    EnumChatFormatting.AQUA + "Profit: N/A\n" +
                    EnumChatFormatting.AQUA + "Crops per min: N/A";

            String waterAnswerText = EnumChatFormatting.DARK_AQUA + "\n" +
                    EnumChatFormatting.DARK_PURPLE + "Purple: " + EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay\n" +
                    EnumChatFormatting.GOLD + "Orange: " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.DARK_GRAY + "Coal\n" +
                    EnumChatFormatting.BLUE + "Blue: " + EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.DARK_GRAY + "Coal, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay\n" +
                    EnumChatFormatting.GREEN + "Green: " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald\n" +
                    EnumChatFormatting.RED + "Red: " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.AQUA + "Diamond, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";
            cakeTimer = new LocationButton(0, MoveCommand.cakeTimerXY[0], MoveCommand.cakeTimerXY[1] + 5, 85 * ScaleCommmand.cakeTimerScale, 18 * ScaleCommmand.cakeTimerScale, ScaleCommmand.cakeTimerScale, CenturyCake.CAKE_COLOUR + "    Century cake: 11h16m", null, null);
            miningPassTimer = new LocationButton(1, MoveCommand.passTimerXY[0], MoveCommand.passTimerXY[1] + 5, 42 * ScaleCommmand.passTimerScale, 18 * ScaleCommmand.passTimerScale, ScaleCommmand.passTimerScale, MiningPassTime.PASS_COLOUR + "    Hollows pass: 4h2m", null, null);
            waterAnswer = new LocationButton(2, MoveCommand.waterAnswerXY[0], MoveCommand.waterAnswerXY[1], 190 * ScaleCommmand.waterAnswerScale, 54 * ScaleCommmand.waterAnswerScale, ScaleCommmand.waterAnswerScale, waterAnswerText, null, null);
            farmingText = new LocationButton(3, MoveCommand.farmingTextXY[0], MoveCommand.farmingTextXY[1] + 5, 50 * ScaleCommmand.farmingTextScale, 20 * ScaleCommmand.farmingTextScale, ScaleCommmand.farmingTextScale, farmingOverText, null, null);

            this.buttonList.add(cakeTimer);
            this.buttonList.add(miningPassTimer);
            this.buttonList.add(waterAnswer);
            this.buttonList.add(farmingText);
        }
        @Override
        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            FontUtils.drawBackground(this.width, this.height);
            mouseMoved(mouseX, mouseY);

            double cakeTimerScale = ScaleCommmand.cakeTimerScale;
            double cakeTimerScaleReset = Math.pow(cakeTimerScale, -1);
            GL11.glScaled(cakeTimerScale, cakeTimerScale, cakeTimerScale);
            mc.getTextureManager().bindTexture(CenturyCake.CAKE_ICON);
            Gui.drawModalRectWithCustomSizedTexture(MoveCommand.cakeTimerXY[0], MoveCommand.cakeTimerXY[1], 0, 0, 16, 16, 16, 16);
            GL11.glScaled(cakeTimerScaleReset, cakeTimerScaleReset, cakeTimerScaleReset);

            double passTimerScale = ScaleCommmand.passTimerScale;
            double passTimerScaleReset = Math.pow(passTimerScale, -1);
            GL11.glScaled(passTimerScale, passTimerScale, passTimerScale);
            mc.getTextureManager().bindTexture(MiningPassTime.GEMSTONE_ICON);
            Gui.drawModalRectWithCustomSizedTexture(MoveCommand.passTimerXY[0], MoveCommand.passTimerXY[1], 0, 0, 16, 16, 16, 16);
            GL11.glScaled(passTimerScaleReset, passTimerScaleReset, passTimerScaleReset);

            double farmingTextScale = ScaleCommmand.farmingTextScale;
            double farmingTextScaleReset = Math.pow(farmingTextScale, -1);
            GL11.glScaled(farmingTextScale, farmingTextScale, farmingTextScale);
            Gui.drawModalRectWithCustomSizedTexture(MoveCommand.farmingTextXY[0], MoveCommand.farmingTextXY[1], 0, 0, 16, 16, 16, 16);
            GL11.glScaled(farmingTextScaleReset, farmingTextScaleReset, farmingTextScaleReset);

            super.drawScreen(mouseX, mouseY, partialTicks);
        }

        private void mouseMoved(int mouseX, int mouseY) {
            int xMoved = mouseX - lastMouseX;
            int yMoved = mouseY - lastMouseY;

            if (moving != null) {
                switch (moving) {
                    case "cakeTimer":
                        MoveCommand.cakeTimerXY[0] += xMoved;
                        MoveCommand.cakeTimerXY[1] += yMoved;
                        cakeTimer.xPosition = MoveCommand.cakeTimerXY[0];
                        cakeTimer.yPosition = MoveCommand.cakeTimerXY[1];
                        break;
                    case "waterAnswer":
                        MoveCommand.waterAnswerXY[0] += xMoved;
                        MoveCommand.waterAnswerXY[1] += yMoved;
                        waterAnswer.xPosition = MoveCommand.waterAnswerXY[0];
                        waterAnswer.yPosition = MoveCommand.waterAnswerXY[1];
                        break;
                    case "miningPassTimer":
                        MoveCommand.passTimerXY[0] += xMoved;
                        MoveCommand.passTimerXY[1] += yMoved;
                        miningPassTimer.xPosition = MoveCommand.passTimerXY[0];
                        miningPassTimer.yPosition = MoveCommand.passTimerXY[1];
                        break;
                    case "farmingText":
                        MoveCommand.farmingTextXY[0] += xMoved;
                        MoveCommand.farmingTextXY[1] += yMoved;
                        farmingText.xPosition = MoveCommand.farmingTextXY[0];
                        farmingText.yPosition = MoveCommand.farmingTextXY[1];
                        break;
                }
                this.buttonList.clear();
                initGui();
            }

            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }

        @Override
        public void actionPerformed(GuiButton button) {
            if (button instanceof LocationButton) {
                if (button == cakeTimer) {
                    moving = "cakeTimer";
                } else if (button == waterAnswer) {
                    moving = "waterAnswer";
                } else if (button == miningPassTimer) {
                    moving = "miningPassTimer";
                } else if (button == farmingText) {
                    moving = "farmingText";
                }
            }
        }

        @Override
        public void mouseReleased(int mouseX, int mouseY, int state) {
            super.mouseReleased(mouseX, mouseY, state);
            moving = null;
            TimerHandler.writeIntConfig("locations", "cakeTimerX", MoveCommand.cakeTimerXY[0]);
            TimerHandler.writeIntConfig("locations", "cakeTimerY", MoveCommand.cakeTimerXY[1]);
            TimerHandler.writeIntConfig("locations", "waterAnswerX", MoveCommand.waterAnswerXY[0]);
            TimerHandler.writeIntConfig("locations", "waterAnswerY", MoveCommand.waterAnswerXY[1]);
            TimerHandler.writeIntConfig("locations", "miningPassTimerX", MoveCommand.passTimerXY[0]);
            TimerHandler.writeIntConfig("locations", "miningPassTimerY", MoveCommand.passTimerXY[1]);
            TimerHandler.writeIntConfig("locations", "farmingTextX", MoveCommand.farmingTextXY[0]);
            TimerHandler.writeIntConfig("locations", "farmingTextY", MoveCommand.farmingTextXY[1]);
        }

}
