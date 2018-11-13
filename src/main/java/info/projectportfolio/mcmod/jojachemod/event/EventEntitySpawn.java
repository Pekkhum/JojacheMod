package info.projectportfolio.mcmod.jojachemod.event;

import info.projectportfolio.mcmod.jojachemod.JojacheMod;
import info.projectportfolio.mcmod.jojachemod.capability.ICapabilityWetness;
import info.projectportfolio.mcmod.jojachemod.capability.ProviderCapabilityWetness;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import info.projectportfolio.mcmod.jojachemod.Configuration;
import info.projectportfolio.mcmod.jojachemod.entity.ai.EntityAIFleeWater;
import info.projectportfolio.mcmod.jojachemod.entity.ai.EntityAIDryOut;

import java.time.LocalDate;
import java.util.Calendar;


@Mod.EventBusSubscriber
public class EventEntitySpawn {
    public static final ResourceLocation RESOURCE_LOC_CAPABILITY_WETNESS = new ResourceLocation(JojacheMod.MODID, "ICapabilityWetness");
    public static boolean clientCelebrated = false;

    @SubscribeEvent
    public static void onAddCapabilitiesEntity(AttachCapabilitiesEvent<Entity> evt)
    {
        if(evt.getObject() instanceof EntityCreeper)
        {
            evt.addCapability(RESOURCE_LOC_CAPABILITY_WETNESS, new ProviderCapabilityWetness());
        }
    }

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent e)
    {
        World world = e.getWorld();
        Entity entity = e.getEntity();


        if(entity instanceof EntityCreeper)
        {
            if(Configuration.creeperWetting && !world.isRemote)
                addMoistener((EntityCreeper) entity, Configuration.creeperDryingTicks);
        }
        else if(entity instanceof EntityPlayer)
        {
            LocalDate today = LocalDate.now();
            LocalDate bday = LocalDate.of(today.getYear(), 11, 18);

            //TODO: Configurable birthday logic
            if(Configuration.merryUnbirthday || (entity.getName().equals("Jojache") && today.equals(bday)))
            {
                if (e.getWorld().isRemote)
                {
                    if (!clientCelebrated)
                    {
                        clientCelebrated = true;
                        EventWorldTick.triggerBdayAnim();
                    }
                } else
                {
                    FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(
                            new TextComponentTranslation("message.jojachemod.birthday_joined", entity.getName())
                    );
                }
            }
        }
    }

    private static void addMoistener(EntityCreature creature, int dryingTicks)
    {
        ICapabilityWetness wetCap = creature.getCapability(ProviderCapabilityWetness.CAPABILITY_WETNESS, null);

        if(wetCap != null)
        {
            wetCap.setMaxTicksToDry(dryingTicks);
            creature.tasks.addTask(1, new EntityAIDryOut(creature));
            creature.tasks.addTask(0, new EntityAIFleeWater(creature, 1.25));
        }
    }
}