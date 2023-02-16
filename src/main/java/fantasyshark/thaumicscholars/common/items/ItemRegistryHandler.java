package fantasyshark.thaumicscholars.common.items;

import fantasyshark.thaumicscholars.common.items.tools.ItemAdElementalPickaxeAlpha;
import fantasyshark.thaumicscholars.common.items.tools.ItemAdElementalPickaxeBeta;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.IThaumcraftItems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Mod.EventBusSubscriber
public class ItemRegistryHandler {

    public static final List<IThaumcraftItems> ITEM_VARIANT_HOLDERS = new ArrayList();

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Item> iForgeRegistry) {

        IForgeRegistry<Item> registry = iForgeRegistry.getRegistry();
        registry.register(ItemsTS.elementalpickaxealpha = new ItemAdElementalPickaxeAlpha(ThaumcraftMaterials.TOOLMAT_ELEMENTAL));
        registry.register(ItemsTS.elementalpickaxebeta = new ItemAdElementalPickaxeBeta(ThaumcraftMaterials.TOOLMAT_ELEMENTAL));
        registry.register(ItemsTS.clusters= new ItemTSBase("cluster", new String[]{"coal", "redstone", "lapis", "diamond", "emerald", "amber"}));

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {

        /*ModelLoader.setCustomModelResourceLocation(AD_ELEMENTAL_PICKAXE_ALPHA,0, new ModelResourceLocation(AD_ELEMENTAL_PICKAXE_ALPHA.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(AD_ELEMENTAL_PICKAXE_BETA,0, new ModelResourceLocation(AD_ELEMENTAL_PICKAXE_BETA.getRegistryName(), "inventory"));*/



        Iterator var0 = ITEM_VARIANT_HOLDERS.iterator();

        while(var0.hasNext()) {
            IThaumcraftItems itemVariantHolder = (IThaumcraftItems)var0.next();
            initModelAndVariants(itemVariantHolder);
            System.out.println(66666666);
        }

    }

    @SideOnly(Side.CLIENT)
    private static void initModelAndVariants(IThaumcraftItems item) {
        int i;
        if (item.getCustomMesh() != null) {
            ModelLoader.setCustomMeshDefinition(item.getItem(), item.getCustomMesh());

            for(i = 0; i < item.getVariantNames().length; ++i) {
                ModelBakery.registerItemVariants(item.getItem(), new ResourceLocation[]{item.getCustomModelResourceLocation(item.getVariantNames()[i])});
                System.out.println(11111);
            }
        } else if (item.getItem() == ItemsTC.seals) {
            for(i = 0; i < item.getVariantNames().length; ++i) {
                ModelLoader.setCustomModelResourceLocation(item.getItem(), item.getVariantMeta()[i], new ModelResourceLocation(item.getItem().getRegistryName() + "_" + item.getVariantNames()[i], (String)null));
                System.out.println(22222);
            }
        } else if (!item.getItem().getHasSubtypes()) {
            ModelLoader.setCustomModelResourceLocation(item.getItem(), 0, new ModelResourceLocation(item.getItem().getRegistryName(), "inventory"));
            System.out.println(item.getItem().getRegistryName());
        } else {
            for(i = 0; i < item.getVariantNames().length; ++i) {
                ModelLoader.setCustomModelResourceLocation(item.getItem(), item.getVariantMeta()[i], item.getCustomModelResourceLocation(item.getVariantNames()[i]));

            }
        }

    }


}
