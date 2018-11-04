package info.projectportfolio.mcmod.jojachemod.capability;

public interface ICapabilityWetness
{
    int getMaxWetness();

    long getMaxTicksToDry();
    void setMaxTicksToDry(long maxTicksToDryIn);

    int getWetness();
    void setWetness(int wetnessIn);

    long getTicksToDry();
    void setTicksToDry(long ticksToDryIn);

    boolean isWet();
    boolean makeWet();
    boolean tickWetness();
}
