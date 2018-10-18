package info.projectportfolio.mcmod.jojachemod;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = JojacheMod.MODID, name = JojacheMod.NAME, version = JojacheMod.VERSION)
public class JojacheMod
{
    public static final String MODID = "jojachemod";
    public static final String NAME = "Jojache Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    public static Logger getLogger()
    {
        return logger;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        net.minecraftforge.common.config.ConfigManager.load("JojacheMod", Config.Type.INSTANCE);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("{} finished initialization.", NAME);
    }
}
