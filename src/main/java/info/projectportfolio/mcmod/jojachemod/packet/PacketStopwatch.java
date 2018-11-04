package info.projectportfolio.mcmod.jojachemod.packet;

import info.projectportfolio.mcmod.jojachemod.JojacheMod;
import info.projectportfolio.mcmod.jojachemod.item.ItemInstances;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.StandardCharsets;

public class PacketStopwatch implements IMessage
{
    private long timeMark;
    private EnumHand hand;

    public PacketStopwatch(long timeMarkIn, EnumHand handIn)
    {
        this.timeMark = timeMarkIn;
        this.hand = handIn;
    }

    // An empty constructor is required on the receiving side.
    public PacketStopwatch()
    {
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        // Be VERY defensive, so invalid packets are unlikely to crash teh server.
        if(buf.readableBytes() >= 12)
        {
            int length;
            this.timeMark = buf.readLong();
            length = buf.readInt();
            if(buf.readableBytes() >= length)
            {
                this.hand = EnumHand.valueOf(buf.readCharSequence(length, StandardCharsets.UTF_8).toString());
            }
            else
                JojacheMod.getLogger().error("Malformed stopwatch request: string length greater than remaining bytes.");
        }
        else
            JojacheMod.getLogger().error("Malformed stopwatch request: Not enough bytes in packet.");

        if(this.hand == null)
            this.hand = EnumHand.MAIN_HAND;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        String handStr = this.hand.toString();
        // Encoding the position as a long is more efficient
        buf.writeLong(this.timeMark);
        buf.writeInt(handStr.length());
        buf.writeBytes(handStr.getBytes(StandardCharsets.UTF_8));
    }

    public static class Handler implements IMessageHandler<PacketStopwatch, IMessage>
    {
        @Override
        public IMessage onMessage(PacketStopwatch message, MessageContext ctx)
        {
            // Always use a construct like this to actually handle your message. This ensures that
            // your 'handle' code is run on the main Minecraft thread. 'onMessage' itself
            // is called on the networking thread so it is not safe to do a lot of things
            // here.
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketStopwatch message, MessageContext ctx) {
            // This code is run on the server side. So you can do server-side calculations here
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;

            ItemInstances.itemStopwatch.doTimerActivate(playerEntity, message.hand, message.timeMark);
        }
    }
}
