package info.projectportfolio.mcmod.jojachemod.packet;

import info.projectportfolio.mcmod.jojachemod.JojacheMod;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketWetness implements IMessage
{
    private long wetness;

    public PacketWetness(long wetnessIn)
    {
        this.wetness = wetnessIn;
    }

    public PacketWetness()
    {
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        // Be VERY defensive, so invalid packets are unlikely to crash teh server.
        if(buf.readableBytes() >= 8)
        {
            int length;
            this.wetness = buf.readLong();
        }
        else
            JojacheMod.getLogger().error("Malformed stopwatch request: Not enough bytes in packet.");
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        // Encoding the position as a long is more efficient
        buf.writeLong(this.wetness);
    }

    public static class Handler implements IMessageHandler<PacketWetness, IMessage>
    {
        @Override
        public IMessage onMessage(PacketWetness message, MessageContext ctx)
        {
            // Always use a construct like this to actually handle your message. This ensures that
            // your 'handle' code is run on the main Minecraft thread. 'onMessage' itself
            // is called on the networking thread so it is not safe to do a lot of things
            // here.
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketWetness message, MessageContext ctx) {
            // This code is run on the server side. So you can do server-side calculations here
        }
    }
}
