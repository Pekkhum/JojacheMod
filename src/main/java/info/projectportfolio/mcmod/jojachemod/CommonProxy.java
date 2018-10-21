package info.projectportfolio.mcmod.jojachemod;

import info.projectportfolio.mcmod.jojachemod.capability.CapabilityWetness;
import info.projectportfolio.mcmod.jojachemod.capability.CapabilityWetnessFactory;
import info.projectportfolio.mcmod.jojachemod.capability.ICapabilityWetness;
import info.projectportfolio.mcmod.jojachemod.packet.PacketHandler;
import info.projectportfolio.mcmod.jojachemod.storage.StorageWetness;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import info.projectportfolio.mcmod.jojachemod.item.ItemStopwatch;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber
public class CommonProxy {
    public static Side proxySide = null;

    public void preInit(FMLPreInitializationEvent e)
    {
        ConfigManager.load(JojacheMod.MODID, Config.Type.INSTANCE);
        // Initialize our packet handler. Make sure the name is
        // 20 characters or less!
        PacketHandler.registerMessages();
        CapabilityManager.INSTANCE.register(ICapabilityWetness.class, new StorageWetness(), new CapabilityWetnessFactory());
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
