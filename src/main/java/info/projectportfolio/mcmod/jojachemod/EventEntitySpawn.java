package info.projectportfolio.mcmod.jojachemod;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
            addMoistener((EntityCreeper) e.getEntity());
        }
    }

    private static void addMoistener(EntityCreeper creep)
    {
        //TODO: Enable function via properties file.
        creep.tasks.addTask(1, new EntityAIDryOut(creep, 50)); //TODO: Set with properties file.
        creep.tasks.addTask(0, new EntityAIFleeWater(creep, 1.0));
    }
}
