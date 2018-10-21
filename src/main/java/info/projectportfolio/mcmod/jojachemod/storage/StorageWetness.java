package info.projectportfolio.mcmod.jojachemod.storage;

import info.projectportfolio.mcmod.jojachemod.capability.ICapabilityWetness;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class StorageWetness implements Capability.IStorage<ICapabilityWetness>
{
    public static final String WETNESS_NBT_KEY = "";

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ICapabilityWetness> capability, ICapabilityWetness instance, EnumFacing side)
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setLong(WETNESS_NBT_KEY, instance.getWetness());

        return nbt;
}

    @Override
    public void readNBT(Capability<ICapabilityWetness> capability, ICapabilityWetness instance, EnumFacing side, NBTBase nbt)
    {
        if(nbt != null && nbt instanceof NBTTagCompound && ((NBTTagCompound) nbt).hasKey(WETNESS_NBT_KEY))
        {
            instance.setWetness(((NBTTagCompound) nbt).getLong(WETNESS_NBT_KEY));
        }
    }
}
