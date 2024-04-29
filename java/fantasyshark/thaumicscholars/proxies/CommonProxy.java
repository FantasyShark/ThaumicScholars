package fantasyshark.thaumicscholars.proxies;

import fantasyshark.thaumicscholars.common.config.ConfigItems;
import fantasyshark.thaumicscholars.common.config.ModConfig;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;


public class CommonProxy {

    public CommonProxy() {
    }

    public void postInit(FMLPostInitializationEvent event) {

        ModConfig.postInitMisc();

    }

    public void init(FMLInitializationEvent event) {

        ConfigItems.init();

    }
}
