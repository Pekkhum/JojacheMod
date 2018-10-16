package info.projectportfolio.mcmod.jojachemod.entity.ai;

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
    /** How wet the entity is **/
    private long currentWetness = 0;


    public EntityAIDryOut(EntityCreature entityIn, long maxWetnessIn)
    {
        this.creature = entityIn;
        isCreeper = this.creature instanceof EntityCreeper;
        this.maxWetness = maxWetnessIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        // Entity is in water, so become wet, but don't start drying out.
        if(creature.world.containsAnyLiquid(this.creature.getEntityBoundingBox())) {
            if(isCreeper)
            {
                ((EntityCreeper)creature).setCreeperState(-1);
            }
            this.currentWetness = this.maxWetness;
            dripWater(2);
            return true;
        }
        // If we are dry, there is nothing to dry out.
        if(currentWetness == 0)
        {
            return false;
        }

        return true;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    //public void startExecuting()
    //{
    //}

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    //public void resetTask()
    //{
    //}

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        if(currentWetness > 0)
        {
            if(isCreeper)
            {
                ((EntityCreeper)creature).setCreeperState(-1);
            }
            //TODO: Particles! Client-side only!
            dripWater((int)((((double)currentWetness)/(double)maxWetness)*5));
            currentWetness--;
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
