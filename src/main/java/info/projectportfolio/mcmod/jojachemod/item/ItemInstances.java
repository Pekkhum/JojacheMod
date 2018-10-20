package info.projectportfolio.mcmod.jojachemod.item;


import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemInstances {
    @GameRegistry.ObjectHolder("jojachemod:item_stopwatch")
    public static ItemStopwatch itemStopwatch;

    public static void registerRenderers(ModelRegistryEvent event) {
        registerItemRenderer(ItemInstances.itemStopwatch);
    }

    private static void registerItemRenderer(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }
}