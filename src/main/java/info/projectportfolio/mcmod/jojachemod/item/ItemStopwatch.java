package info.projectportfolio.mcmod.jojachemod.item;

import info.projectportfolio.mcmod.jojachemod.JojacheMod;
import info.projectportfolio.mcmod.jojachemod.packet.PacketHandler;
import info.projectportfolio.mcmod.jojachemod.packet.PacketStopwatch;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;

public class ItemStopwatch extends Item
{
    public static final String ITEM_NAME = "item_stopwatch";
    public static final String START_TIME_KEY = "StopwatchStartTime";

    public ItemStopwatch()
    {
        setRegistryName(ITEM_NAME);
        setUnlocalizedName(JojacheMod.MODID + "." + ITEM_NAME);
        setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemInHand = playerIn.getHeldItem(handIn);

        if(worldIn.isRemote && itemInHand.getItem() instanceof ItemStopwatch)
        {
            PacketHandler.INSTANCE.sendToServer(new PacketStopwatch(worldIn.getTotalWorldTime(), handIn));
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    /**
     * Called on the server-side by the PacketStopwatch handler to handle the onItemRightClick actions.
     * @param player The player using the stopwatch
     * @param handIn The hand they are holding the stopwatch in
     * @param timeMark The client-side totalWorldTime at which they used it
     */
    public void doTimerActivate(EntityPlayerMP player, EnumHand handIn, long timeMark)
    {
        ItemStack itemInHand = player.getHeldItem(handIn);

        if (timeMark > 0 && itemInHand != null)
        {
            NBTTagCompound stopwatchNbt;

            if (itemInHand.getItem() instanceof ItemStopwatch && itemInHand.hasTagCompound())
            {
                stopwatchNbt = itemInHand.getTagCompound();
            }
            else
            {
                stopwatchNbt = new NBTTagCompound();
            }

            if(stopwatchNbt.hasKey(START_TIME_KEY))
            {
                long startTime = stopwatchNbt.getLong(START_TIME_KEY);
                player.sendMessage(new TextComponentTranslation("message.jojachemod.item_stopwatch.ticks_elapsed", (timeMark - startTime)));
                stopwatchNbt.removeTag(START_TIME_KEY);
            }
            else
            {
                stopwatchNbt.setLong(START_TIME_KEY, timeMark);
                player.sendMessage(new TextComponentTranslation("message.jojachemod.item_stopwatch.timer_started"));
            }

            itemInHand.setTagCompound(stopwatchNbt);
        }
    }
}