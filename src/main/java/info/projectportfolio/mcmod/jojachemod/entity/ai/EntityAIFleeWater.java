package info.projectportfolio.mcmod.jojachemod.entity.ai;

import java.util.Random;
import javax.annotation.Nullable;

import info.projectportfolio.mcmod.jojachemod.capability.ICapabilityWetness;
import info.projectportfolio.mcmod.jojachemod.capability.ProviderCapabilityWetness;
import info.projectportfolio.mcmod.jojachemod.packet.PacketHandler;
import info.projectportfolio.mcmod.jojachemod.packet.PacketWetness;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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
        if (!this.world.containsAnyLiquid(this.creature.getEntityBoundingBox())
        )
        {
            return false;
        }
        else
        {
            ICapabilityWetness wetCap;

            wetCap = creature.getCapability(ProviderCapabilityWetness.CAPABILITY_WETNESS, null);
            if(wetCap != null)
            {
                if(wetCap.makeWet())
                {
                    PacketHandler.INSTANCE.sendToAllAround(new PacketWetness(creature.getEntityId(), wetCap),
                            new NetworkRegistry.TargetPoint(creature.dimension, creature.posX, creature.posY, creature.posZ, 400));
                }
            }
            Vec3d vec3d = this.findPossibleDryShelter();
            if(isCreeper)
            {
                ((EntityCreeper)creature).setCreeperState(-1);
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
        ICapabilityWetness wetCap;

        wetCap = creature.getCapability(ProviderCapabilityWetness.CAPABILITY_WETNESS, null);
        if(wetCap != null)
        {
            if(wetCap.makeWet())
            {
                PacketHandler.INSTANCE.sendToAllAround(new PacketWetness(creature.getEntityId(), wetCap),
                        new NetworkRegistry.TargetPoint(creature.dimension, creature.posX, creature.posY, creature.posZ, 400));
            }
        }
        if(isCreeper)
        {
            ((EntityCreeper)creature).setCreeperState(-1);
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
                    && this.creature.getBlockPathWeight(blockpos1) < 0.0F)
            {
                return new Vec3d((double)blockpos1.getX(), (double)blockpos1.getY(), (double)blockpos1.getZ());
            }
        }

        return null;
    }
}