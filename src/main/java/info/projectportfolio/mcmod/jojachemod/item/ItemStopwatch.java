package info.projectportfolio.mcmod.jojachemod.item;

import info.projectportfolio.mcmod.jojachemod.JojacheMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;

public class ItemStopwatch extends Item
{
    public static final String ITEM_NAME = "item_stopwatch";
    public static final String START_TIME = "StopwatchStartTime";

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
            playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Total World Time: " + TextFormatting.RED + worldIn.getTotalWorldTime()));
            /* // Perhaps we can't modify NBT from the client side, so we should send the tick long to the server to handle, and the clear event.
            NBTTagCompound stopwatchNbt = itemInHand.getTagCompound();

            if(stopwatchNbt.hasKey(START_TIME))
            {
                long startTime = stopwatchNbt.getLong(START_TIME);
                playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Ticks Elapsed: " + TextFormatting.RED + (worldIn.getTotalWorldTime() - startTime)));
                stopwatchNbt.removeTag(START_TIME);
            }
            else
            {
                stopwatchNbt.setLong(START_TIME, worldIn.getTotalWorldTime());
                playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Started timing..."));
            }

            itemInHand.setTagCompound(stopwatchNbt);
            */
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
