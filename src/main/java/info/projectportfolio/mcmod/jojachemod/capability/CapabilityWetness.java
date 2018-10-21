package info.projectportfolio.mcmod.jojachemod.capability;

import info.projectportfolio.mcmod.jojachemod.JojacheMod;

public class CapabilityWetness implements ICapabilityWetness
{
    private long wetness;

    public CapabilityWetness() {}

    public CapabilityWetness(long wetnessIn)
    {
        this.wetness = wetnessIn;
    }

    @Override
    public long getWetness()
    {
        return this.wetness;
    }

    @Override
    public void setWetness(long wetnessIn)
    {
        this.wetness = wetnessIn;
    }
}
