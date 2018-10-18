package info.projectportfolio.mcmod.jojachemod;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;

@Config(modid="JojacheMod", name="JojacheMod", type=Type.INSTANCE, category="General  ")
public class Configuration {
    @Comment({
            "This decides whether or not creepers 'become wet' when they touch fluids, ",
            "causing them to be unable to detonate for a time. Rain is ignored, due to performance issues."
    })
    @Name("Creepers Get Wet")
    public static boolean creeperWetting = true;

    @Comment({
            "How many ticks should creepers take to dry out?",
            "0 means they won't take time to dry out, but touching fluids will cancel detonation."
    })
    @Name("Ticks to Dry Creepers")
    @RangeInt(min=0)
    public static int creeperDryingTicks = 50;
}