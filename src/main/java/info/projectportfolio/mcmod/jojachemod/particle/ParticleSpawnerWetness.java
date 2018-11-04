package info.projectportfolio.mcmod.jojachemod.particle;

import info.projectportfolio.mcmod.jojachemod.capability.ICapabilityWetness;
import info.projectportfolio.mcmod.jojachemod.capability.ProviderCapabilityWetness;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRain;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ParticleSpawnerWetness
{
    private final ParticleRain.Factory dropMaker = new ParticleRain.Factory();
    private final List<Integer> entityIdList = new ArrayList<>();
    private long dryingTicks;


    public ParticleSpawnerWetness(int dryingTicks)
    {
        this.dryingTicks = dryingTicks;
    }

    public boolean trackEntity(Entity entity)
    {
        if(entity.hasCapability(ProviderCapabilityWetness.CAPABILITY_WETNESS, null))
        {
            synchronized(entityIdList)
            {
                return entityIdList.add(entity.getEntityId());
            }
        }
        return false;
    }

    public boolean untrackEntity(Entity entity)
    {
        synchronized(entityIdList)
        {
            return entityIdList.remove(new Integer(entity.getEntityId()));
        }
    }

    public void tickEntities()
    {

        synchronized(entityIdList)
        {
            Iterator<Integer> entityIdIterator = entityIdList.iterator();
            Integer entId;

            while (entityIdIterator.hasNext() && null != (entId = entityIdIterator.next()))
            {
                Entity ent = Minecraft.getMinecraft().world.getEntityByID(entId);
                if (ent != null)
                {
                    ICapabilityWetness wetCap = ent.getCapability(ProviderCapabilityWetness.CAPABILITY_WETNESS, null);

                    if (wetCap == null)
                        entityIdIterator.remove();
                    else
                        dripWater(ent, wetCap.getWetness());
                }
                else
                    entityIdIterator.remove();
            }
        }
    }

    private void dripWater(Entity ent, int dropCount)
    {
        AxisAlignedBB creatureBB = ent.getEntityBoundingBox();

        for(int count = 0 ; count < dropCount; count ++) {
            double spawnX = creatureBB.minX + (creatureBB.maxX - creatureBB.minX) * Math.random();
            double spawnY = creatureBB.minY + (creatureBB.maxY - creatureBB.minY) * Math.random();
            double spawnZ = creatureBB.minZ + (creatureBB.maxZ - creatureBB.minZ) * Math.random();
            Particle drop = dropMaker.createParticle(0, ent.world, spawnX, spawnY, spawnZ, 0,-10,0);
            Minecraft.getMinecraft().effectRenderer.addEffect(drop);
        }
    }
}
