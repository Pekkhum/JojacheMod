package info.projectportfolio.mcmod.jojachemod.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class EventWorldTick
{
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
}
