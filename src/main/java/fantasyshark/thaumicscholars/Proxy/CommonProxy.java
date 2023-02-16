package fantasyshark.thaumicscholars.Proxy;

import fantasyshark.thaumicscholars.common.config.ModConfig;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;


public class CommonProxy {

    public CommonProxy() {
    }

    public void postInit(FMLPostInitializationEvent event) {

        ModConfig.postInitMisc();

    }
}
