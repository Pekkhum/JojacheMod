package info.projectportfolio.mcmod.jojachemod;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = JojacheMod.MODID, name = JojacheMod.NAME, version = JojacheMod.VERSION)
public class JojacheMod
{
    public static final String MODID = "jojachemod";
    public static final String NAME = "Jojache Mod";
    public static final String VERSION = "0.0.1";

    @SidedProxy(clientSide = "info.projectportfolio.mcmod.jojachemod.ClientProxy", serverSide = "info.projectportfolio.mcmod.jojachemod.ServerProxy")
    public static CommonProxy proxy;

    private static Logger logger;

    public static Logger getLogger()
    {
        return logger;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        ConfigManager.load("JojacheMod", Config.Type.INSTANCE);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("{} finished initialization.", NAME);
    }
}
