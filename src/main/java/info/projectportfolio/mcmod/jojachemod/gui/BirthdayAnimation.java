package info.projectportfolio.mcmod.jojachemod.gui;

import info.projectportfolio.mcmod.jojachemod.JojacheMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class BirthdayAnimation
{
    private final int ITEM_HEIGHT = 256;
    private final int ITEM_Y_OFFSET = 128;
    private final int FINAL_Y_POS = -2 - ITEM_HEIGHT - ITEM_Y_OFFSET;
    private final int ITEM_X_OFFSET = 150;

    private int yPos = Integer.MIN_VALUE;
    private int[] xPos = new int[2];

    public boolean drawFrame(ScaledResolution resolution)
    {
        if(yPos == Integer.MIN_VALUE)
        {
            int itemsCenterX = (resolution.getScaledWidth()/2)-50;
            yPos = resolution.getScaledHeight()+1;
            xPos[0] = itemsCenterX-(ITEM_X_OFFSET/2);
            xPos[1] = itemsCenterX+(ITEM_X_OFFSET/2);
            Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(
                    new ResourceLocation(JojacheMod.MODID, "cat_licking_birthday_cake"),
                    SoundCategory.MUSIC, 1.0f, 1.0f, false, 0,
                    ISound.AttenuationType.NONE, 0, 0, 0
            ));
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(
                new ResourceLocation(JojacheMod.MODID,"textures/gui/balloon1.png"));
        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(
                xPos[0], yPos, 0, 0, 256, 256);
        Minecraft.getMinecraft().renderEngine.bindTexture(
                new ResourceLocation(JojacheMod.MODID,"textures/gui/balloon2.png"));
        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(
                xPos[1], yPos + ITEM_Y_OFFSET, 0, 0, 256, 256);
        yPos-=2;

        return yPos <= FINAL_Y_POS;
    }
}
