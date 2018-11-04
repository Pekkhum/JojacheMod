package info.projectportfolio.mcmod.jojachemod.capability;

public class CapabilityWetness implements ICapabilityWetness
{
    public static final int maxWetness = 7;
    private long maxTicksToDry;
    private int wetness;
    private long ticksToDry;

    public CapabilityWetness() {}

    public CapabilityWetness(long maxTicksToDryIn, int wetnessIn, long ticksToDryIn)
    {
        this.maxTicksToDry = maxTicksToDryIn;
        this.wetness = wetnessIn;
        this.ticksToDry = ticksToDryIn;
    }

    @Override
    public int getMaxWetness() { return maxWetness; }

    @Override
    public long getMaxTicksToDry() { return this.maxTicksToDry; }

    @Override
    public void setMaxTicksToDry(long maxTicksToDryIn) { this.maxTicksToDry = maxTicksToDryIn; }

    @Override
    public int getWetness()
    {
        return this.wetness;
    }

    @Override
    public void setWetness(int wetnessIn)
    {
        this.wetness = wetnessIn;
    }

    @Override
    public long getTicksToDry()
    {
        return this.ticksToDry;
    }

    @Override
    public void setTicksToDry(long ticksToDryIn)
    {
        this.ticksToDry = ticksToDryIn;
    }

    @Override
    public boolean isWet()
    {
        return ticksToDry > 0;
    }

    @Override
    public boolean makeWet()
    {
        this.ticksToDry = this.maxTicksToDry;
        if (this.wetness != maxWetness)
        {
            this.wetness = maxWetness;
            return true;
        }
        return false;
    }

    @Override
    public boolean tickWetness()
    {
        int newWetness;

        if(ticksToDry > 0)
        {
            ticksToDry--;
        }
        newWetness = (int)(((double)ticksToDry/(double)maxTicksToDry)*maxWetness);
        if(newWetness != wetness)
        {
            wetness = newWetness;
            return true;
        }
        return false;
    }
}
