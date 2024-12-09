package fantasyshark.thaumicscholars;

import fantasyshark.thaumicscholars.common.config.ConfigItems;
import fantasyshark.thaumicscholars.proxies.CommonProxy;
import fantasyshark.thaumicscholars.common.config.ConfigResearch;
import fantasyshark.thaumicscholars.common.config.ModConfig;
import fantasyshark.thaumicscholars.common.crafting.FurnaceRecipeRegistryHandler;
import fantasyshark.thaumicscholars.common.crafting.InfusionRecipeRegistryHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ThaumicScholars.MODID, name = ThaumicScholars.NAME, version = ThaumicScholars.VERSION)
public class ThaumicScholars
{
    public static final String MODID = "thaumicscholars";
    public static final String NAME = "Thaumic Scholars";
    public static final String VERSION = "1.0";

    private static Logger logger;
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FurnaceRecipeRegistryHandler.register();
        InfusionRecipeRegistryHandler.initializeInfusionRecipes();
        ConfigResearch.init();
        ConfigItems.init();

    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        ModConfig.postInitMisc();

    }

}
