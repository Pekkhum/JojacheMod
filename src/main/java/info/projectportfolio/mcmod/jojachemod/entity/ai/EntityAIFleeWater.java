package info.projectportfolio.mcmod.jojachemod.entity.ai;

import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRain;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIFleeWater extends EntityAIBase
{
    private final EntityCreature creature;
    private final boolean isCreeper;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private final double movementSpeed;
    private final World world;

    public EntityAIFleeWater(EntityCreature theCreatureIn, double movementSpeedIn)
    {
        this.creature = theCreatureIn;
        isCreeper = this.creature instanceof EntityCreeper;
        this.movementSpeed = movementSpeedIn;
        this.world = theCreatureIn.world;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        //TODO: Wettable and not wet? (for now always true, but we need to reduce sky checks)
        //if (!this.creature.isBurning())
        //{
        //    return false;
        //}
        // else
        // Touching water or rain? ( rain removed as the rain check was constantly true?
        if (!this.world.containsAnyLiquid(this.creature.getEntityBoundingBox())
                // Including rain makes this expensive and less successful.
                //&& !(
                //        this.world.isRainingAt(new BlockPos(this.creature.posX, this.creature.getEntityBoundingBox().minY, this.creature.posZ))
                //                && this.world.canSeeSky(new BlockPos(this.creature.posX, this.creature.getEntityBoundingBox().minY, this.creature.posZ))
                //)
        )
        {
            return false;
        }
        else
        {
            Vec3d vec3d = this.findPossibleDryShelter();
            if(isCreeper)
            {
                ((EntityCreeper)creature).setCreeperState(-1);
                dripWater(5);
            }

            if (vec3d == null)
            {
                return false;
            }
            else
            {
                this.shelterX = vec3d.x;
                this.shelterY = vec3d.y;
                this.shelterZ = vec3d.z;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return !this.creature.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.creature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        if(isCreeper)
        {
            ((EntityCreeper)creature).setCreeperState(-1);
            dripWater(5);
        }
    }

    @Nullable
    private Vec3d findPossibleDryShelter()
    {
        Random random = this.creature.getRNG();
        BlockPos blockpos = new BlockPos(this.creature.posX, this.creature.getEntityBoundingBox().minY, this.creature.posZ);

        for (int i = 0; i < 100; ++i)
        {
            BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);

            if (!this.world.containsAnyLiquid(new AxisAlignedBB(blockpos1))
                    // Including rain makes this expensive and less successful.
                    //&& !(this.world.isRainingAt(blockpos1)
                    //    && this.world.canSeeSky(blockpos1)
                    //)
                    && this.creature.getBlockPathWeight(blockpos1) < 0.0F)
            {
                return new Vec3d((double)blockpos1.getX(), (double)blockpos1.getY(), (double)blockpos1.getZ());
            }
        }

        return null;
    }

    //TODO: Move this to the client! It won't work right here!
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