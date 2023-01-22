package fantasyshark.thaumicscholars.common.items;

import fantasyshark.thaumicscholars.common.items.tools.ItemAdElementalPickaxe;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftMaterials;

@Mod.EventBusSubscriber
public class ItemRegistryHandler {

    public static final ItemAdElementalPickaxe AD_ELEMENTAL_PICKAXE = new ItemAdElementalPickaxe(ThaumcraftMaterials.TOOLMAT_ELEMENTAL);

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Item> event) {

        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(AD_ELEMENTAL_PICKAXE);

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {

        ModelLoader.setCustomModelResourceLocation(AD_ELEMENTAL_PICKAXE,0, new ModelResourceLocation(AD_ELEMENTAL_PICKAXE.getRegistryName(), "inventory"));

    }

}
