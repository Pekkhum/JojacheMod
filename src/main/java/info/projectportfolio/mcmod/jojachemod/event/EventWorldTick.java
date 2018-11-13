package info.projectportfolio.mcmod.jojachemod.event;

import info.projectportfolio.mcmod.jojachemod.gui.BirthdayAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class EventWorldTick
{
     private static BirthdayAnimation bdayAnim = null;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent evt)
    {
        if(evt.phase == TickEvent.Phase.END
                && !Minecraft.getMinecraft().isGamePaused()
                && Minecraft.getMinecraft().player != null)
        {
            EventHandlerClient.wetnessParticleSpawner.tickEntities();
        }
    }

    @SubscribeEvent
    public static void drawOverlays(RenderGameOverlayEvent.Post event)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL
                || mc.gameSettings.showDebugInfo || mc.currentScreen instanceof GuiChat)
        {
            return;
        }

        if(bdayAnim != null)
        {
            if(bdayAnim.drawFrame(event.getResolution()))
            {
                bdayAnim = null;
            }
        }
    }

    public static void triggerBdayAnim()
    {
        bdayAnim = new BirthdayAnimation();
    }
}
