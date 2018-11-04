package info.projectportfolio.mcmod.jojachemod.storage;

import info.projectportfolio.mcmod.jojachemod.capability.ICapabilityWetness;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class StorageWetness implements Capability.IStorage<ICapabilityWetness>
{
    public static final String WETNESS_NBT_KEY = "jojachemod_wetness";
    public static final String TICKS_TO_DRY_NBT_KEY = "jojachemod_ticks_to_dry";

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ICapabilityWetness> capability, ICapabilityWetness instance, EnumFacing side)
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setInteger(WETNESS_NBT_KEY, instance.getWetness());
        nbt.setLong(TICKS_TO_DRY_NBT_KEY, instance.getTicksToDry());

        return nbt;
    }

    @Override
    public void readNBT(Capability<ICapabilityWetness> capability, ICapabilityWetness instance, EnumFacing side, NBTBase nbt)
    {
        if(nbt instanceof NBTTagCompound && ((NBTTagCompound) nbt).hasKey(WETNESS_NBT_KEY))
        {
            instance.setWetness(((NBTTagCompound) nbt).getInteger(WETNESS_NBT_KEY));
            instance.setTicksToDry(((NBTTagCompound) nbt).getLong(TICKS_TO_DRY_NBT_KEY));
        }
    }
}
