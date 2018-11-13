package info.projectportfolio.mcmod.jojachemod.event;

import info.projectportfolio.mcmod.jojachemod.Configuration;
import info.projectportfolio.mcmod.jojachemod.particle.ParticleSpawnerWetness;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class EventHandlerClient
{
    public static ParticleSpawnerWetness wetnessParticleSpawner = new ParticleSpawnerWetness(Configuration.creeperDryingTicks);
    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking evt)
    {
        Entity entity = evt.getTarget();

        if(entity instanceof EntityCreeper)
        {
            if(Configuration.creeperWetting)
            {
                wetnessParticleSpawner.trackEntity(entity);
            }
        }
    }

    @SubscribeEvent
    public static void onStopTracking(PlayerEvent.StopTracking evt)
    {
        Entity entity = evt.getTarget();

        if(entity instanceof EntityCreeper)
        {
            if(Configuration.creeperWetting)
            {
                wetnessParticleSpawner.untrackEntity(entity);
            }
        }
    }

}
