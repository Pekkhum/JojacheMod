package info.projectportfolio.mcmod.jojachemod.event;

import info.projectportfolio.mcmod.jojachemod.JojacheMod;
import info.projectportfolio.mcmod.jojachemod.capability.ProviderCapabilityWetness;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import info.projectportfolio.mcmod.jojachemod.Configuration;
import info.projectportfolio.mcmod.jojachemod.entity.ai.EntityAIFleeWater;
import info.projectportfolio.mcmod.jojachemod.entity.ai.EntityAIDryOut;

@Mod.EventBusSubscriber
public class EventEntitySpawn {
    public static final ResourceLocation RESOURCE_LOC_CAPABILITY_WETNESS = new ResourceLocation(JojacheMod.MODID, "ICapabilityWetness");

    @SubscribeEvent
    public static void onAddCapabilitiesEntity(AttachCapabilitiesEvent<Entity> evt)
    {
        if(evt.getObject() instanceof EntityCreeper)
        {
            evt.addCapability(RESOURCE_LOC_CAPABILITY_WETNESS, new ProviderCapabilityWetness());
        }
    }

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent e)
    {
        World world = e.getWorld();
        Entity entity = e.getEntity();


        if(e.getEntity() instanceof EntityCreeper)
        {
            if(Configuration.creeperWetting && !world.isRemote)
                addMoistener((EntityCreeper) e.getEntity(), Configuration.creeperDryingTicks);
        }
    }

    private static void addMoistener(EntityCreature creature, int dryingTicks)
    {
        creature.tasks.addTask(1, new EntityAIDryOut(creature, dryingTicks));
        creature.tasks.addTask(0, new EntityAIFleeWater(creature, 1.25));
    }
}