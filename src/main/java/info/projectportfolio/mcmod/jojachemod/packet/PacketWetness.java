package info.projectportfolio.mcmod.jojachemod.packet;

import info.projectportfolio.mcmod.jojachemod.JojacheMod;
import info.projectportfolio.mcmod.jojachemod.capability.ICapabilityWetness;
import info.projectportfolio.mcmod.jojachemod.capability.ProviderCapabilityWetness;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketWetness implements IMessage
{
    private int entityId;
    private long wetness;

    public PacketWetness(int entityIdIn, long wetnessIn)
    {
        this.entityId = entityIdIn;
        this.wetness = wetnessIn;
    }

    public PacketWetness()
    {
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        // Be VERY defensive, so invalid packets are unlikely to crash the client.
        if(buf.readableBytes() >= 12)
        {
            this.entityId = buf.readInt();
            this.wetness = buf.readLong();
        }
        else
            JojacheMod.getLogger().error("Malformed stopwatch request: Not enough bytes in packet.");
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        // Encoding the position as a long is more efficient
        buf.writeInt(this.entityId);
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
            Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketWetness message, MessageContext ctx) {
            // This code is run on the client Minecraft instance. So you can do Minecraft calculations here
            Entity ent = Minecraft.getMinecraft().world.getEntityByID(message.entityId);

            if(ent != null)
            {
                ICapabilityWetness wetCap = ent.getCapability(ProviderCapabilityWetness.CAPABILITY_WETNESS, null);

                if(wetCap != null)
                {
                    wetCap.setWetness(message.wetness);
                }
            }
        }
    }
}
