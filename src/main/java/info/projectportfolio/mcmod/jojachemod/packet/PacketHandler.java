package info.projectportfolio.mcmod.jojachemod.packet;

import info.projectportfolio.mcmod.jojachemod.JojacheMod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
    private static int packetId = 0;

    public static SimpleNetworkWrapper INSTANCE = null;

    public PacketHandler() {
    }

    public static int nextID() {
        return packetId++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(JojacheMod.MODID);
        registerServerMessages();
        registerClientMessages();
    }

    private static void registerServerMessages() {
        // Register messages which are sent from the client to the server here:
        INSTANCE.registerMessage(PacketStopwatch.Handler.class, PacketStopwatch.class, nextID(), Side.SERVER);
    }

    private static void registerClientMessages() {
        // Register messages which are sent from the server to the client here:
        INSTANCE.registerMessage(PacketWetness.Handler.class, PacketWetness.class, nextID(), Side.CLIENT);
    }
}
