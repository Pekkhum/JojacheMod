package info.projectportfolio.mcmod.jojachemod;

import info.projectportfolio.mcmod.jojachemod.packet.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import info.projectportfolio.mcmod.jojachemod.item.ItemStopwatch;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e)
    {
        ConfigManager.load(JojacheMod.MODID, Config.Type.INSTANCE);
        // Initialize our packet handler. Make sure the name is
        // 20 characters or less!
        PacketHandler.registerMessages("jojachemod");
    }

    public void init(FMLInitializationEvent e)
    {
    }

    public void postInit(FMLPostInitializationEvent e)
    {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(new ItemStopwatch());
    }
}
