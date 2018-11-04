package info.projectportfolio.mcmod.jojachemod.capability;

import java.util.concurrent.Callable;

public class CapabilityWetnessFactory implements Callable<ICapabilityWetness>
{
    @Override
    public ICapabilityWetness call() {
        return new CapabilityWetness();
    }
}
