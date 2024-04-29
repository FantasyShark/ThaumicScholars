package fantasyshark.thaumicscholars.common.blocks;


import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;



@Mod.EventBusSubscriber
public class BlockRegistryHandler {

    public static final BlockRichSoil BLOCK_RICH_SOIL = new BlockRichSoil();
    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Block> iForgeRegistry) {

        IForgeRegistry<Block> registry = iForgeRegistry.getRegistry();
        /*registry.register(BLOCK_RICH_SOIL);*/

    }

}
