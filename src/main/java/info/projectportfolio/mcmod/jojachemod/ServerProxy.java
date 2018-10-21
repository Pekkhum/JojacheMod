package info.projectportfolio.mcmod.jojachemod;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ServerProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        this.proxySide = Side.SERVER;
        super.preInit(e);
    }
}