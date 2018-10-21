package info.projectportfolio.mcmod.jojachemod.entity.ai;

import info.projectportfolio.mcmod.jojachemod.capability.ICapabilityWetness;
import info.projectportfolio.mcmod.jojachemod.capability.WetnessProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRain;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIDryOut extends EntityAIBase {

    /** The entity that is drying. */
    private final EntityCreature creature;
    private final boolean isCreeper;
    /** How wet the entity is **/
    private final long maxWetness;


    public EntityAIDryOut(EntityCreature entityIn, long maxWetnessIn)
    {
        this.creature = entityIn;
        isCreeper = this.creature instanceof EntityCreeper;
        this.maxWetness = maxWetnessIn;
        this.setMutexBits(1); // This needs to block chasing the player and detonation, so movement bit is used.
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        boolean execute = false;
        // Entity is in water, so become wet, but don't start drying out.
        if(creature.world.containsAnyLiquid(this.creature.getEntityBoundingBox())) {
            ICapabilityWetness wetCap;
            if(isCreeper)
            {
                ((EntityCreeper)creature).setCreeperState(-1);
            }
            wetCap = creature.getCapability(WetnessProvider.CAPABILITY_WETNESS, null);
            if(wetCap != null)
            {
                wetCap.setWetness(this.maxWetness);
                dripWater(2);
            }
            return true;
        }
        // If we are dry, there is nothing to dry out.
        return execute;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        ICapabilityWetness wetCap = creature.getCapability(WetnessProvider.CAPABILITY_WETNESS, null);


        if(wetCap != null)
        {
            long wetness = wetCap.getWetness();
            if(wetness > 0)
            {
                if (isCreeper)
                {
                    ((EntityCreeper) creature).setCreeperState(-1);
                }
                //TODO: Particles! Client-side only!
                dripWater((int) ((((double) wetness) / (double) maxWetness) * 5));
                wetCap.setWetness(wetness - 1);
            }
        }
    }

    private ParticleRain.Factory dropMaker = new ParticleRain.Factory();
    private void dripWater(int dropCount)
    {
        AxisAlignedBB creatureBB = this.creature.getEntityBoundingBox();

        for(int count = 0 ; count < dropCount; count ++) {
            double spawnX = creatureBB.minX + (creatureBB.maxX - creatureBB.minX) * Math.random();
            double spawnY = creatureBB.minY + (creatureBB.maxY - creatureBB.minY) * Math.random();
            double spawnZ = creatureBB.minZ + (creatureBB.maxZ - creatureBB.minZ) * Math.random();
            Particle drop = dropMaker.createParticle(0, creature.world, spawnX, spawnY, spawnZ, 0,-10,0);
            Minecraft.getMinecraft().effectRenderer.addEffect(drop);
        }
    }
}
