package info.projectportfolio.mcmod.jojachemod.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProviderCapabilityWetness implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(ICapabilityWetness.class)
    public static final Capability<ICapabilityWetness> CAPABILITY_WETNESS = null;

    private ICapabilityWetness instance = CAPABILITY_WETNESS.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CAPABILITY_WETNESS;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == CAPABILITY_WETNESS ? CAPABILITY_WETNESS.cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return CAPABILITY_WETNESS.getStorage().writeNBT(CAPABILITY_WETNESS, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        CAPABILITY_WETNESS.getStorage().readNBT(CAPABILITY_WETNESS, this.instance, null, nbt);
    }
}
