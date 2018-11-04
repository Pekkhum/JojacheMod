package info.projectportfolio.mcmod.jojachemod.entity.ai;

import info.projectportfolio.mcmod.jojachemod.capability.ICapabilityWetness;
import info.projectportfolio.mcmod.jojachemod.capability.ProviderCapabilityWetness;
import info.projectportfolio.mcmod.jojachemod.packet.PacketHandler;
import info.projectportfolio.mcmod.jojachemod.packet.PacketWetness;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class EntityAIDryOut extends EntityAIBase {

    /** The entity that is drying. */
    private final EntityCreature creature;
    private final boolean isCreeper;


    public EntityAIDryOut(EntityCreature entityIn)
    {
        this.creature = entityIn;
        isCreeper = this.creature instanceof EntityCreeper;
        this.setMutexBits(1); // This needs to block chasing the player and detonation, so movement bit is used.
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        boolean execute;
        ICapabilityWetness wetCap;

        wetCap = creature.getCapability(ProviderCapabilityWetness.CAPABILITY_WETNESS, null);
        if(wetCap == null)
            return false;

        // Entity is in water, so become wet, but don't start drying out.
        if(creature.world.containsAnyLiquid(this.creature.getEntityBoundingBox())) {

            if(wetCap.makeWet())
            {
                    PacketHandler.INSTANCE.sendToAllAround(new PacketWetness(creature.getEntityId(), wetCap),
                            new NetworkRegistry.TargetPoint(creature.dimension, creature.posX, creature.posY, creature.posZ, 400));
            }
            execute = false;
        }
        else
        {
            execute = wetCap.getWetness() > 0;
        }

        if(execute && isCreeper)
        {
            ((EntityCreeper)creature).setCreeperState(-1);
        }
        // If we are dry, there is nothing to dry out.
        return execute;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        ICapabilityWetness wetCap = creature.getCapability(ProviderCapabilityWetness.CAPABILITY_WETNESS, null);

        if(wetCap != null)
        {
            if(wetCap.isWet())
            {
                if (isCreeper)
                {
                    ((EntityCreeper) creature).setCreeperState(-1);
                }
                if(wetCap.tickWetness())
                {
                    PacketHandler.INSTANCE.sendToAllAround(new PacketWetness(creature.getEntityId(), wetCap),
                            new NetworkRegistry.TargetPoint(creature.dimension, creature.posX, creature.posY, creature.posZ, 400));
                }
            }
        }
    }
}
