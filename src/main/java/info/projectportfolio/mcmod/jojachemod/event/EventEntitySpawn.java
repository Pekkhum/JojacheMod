package info.projectportfolio.mcmod.jojachemod.event;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import info.projectportfolio.mcmod.jojachemod.Configuration;
import info.projectportfolio.mcmod.jojachemod.entity.ai.EntityAIFleeWater;
import info.projectportfolio.mcmod.jojachemod.entity.ai.EntityAIDryOut;

@Mod.EventBusSubscriber
public class EventEntitySpawn {

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent e)
    {
        if(e.getWorld().isRemote) return;

        if(e.getEntity() instanceof EntityCreeper)
        {
            if(Configuration.creeperWetting)
                addMoistener((EntityCreeper) e.getEntity(), Configuration.creeperDryingTicks);
        }
    }

    private static void addMoistener(EntityCreature creature, int dryingTicks)
    {
        creature.tasks.addTask(1, new EntityAIDryOut(creature, dryingTicks));
        creature.tasks.addTask(0, new EntityAIFleeWater(creature, 1.25));
    }
}