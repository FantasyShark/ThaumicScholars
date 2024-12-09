package fantasyshark.thaumicscholars.common.items;

import fantasyshark.thaumicscholars.common.blocks.BlockRegistryHandler;
import fantasyshark.thaumicscholars.common.config.ConfigMaterials;
import fantasyshark.thaumicscholars.common.items.casters.foci.ItemFocusCreative;
import fantasyshark.thaumicscholars.common.items.tools.*;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
        registry.register(ItemsTS.focuscreative = new ItemFocusCreative());
        registry.register(ItemsTS.adelementalpickaxealpha = new ItemAdElementalPickaxeAlpha(ThaumcraftMaterials.TOOLMAT_ELEMENTAL));
        registry.register(ItemsTS.adelementalpickaxebeta = new ItemAdElementalPickaxeBeta(ThaumcraftMaterials.TOOLMAT_ELEMENTAL));
        registry.register(ItemsTS.adelementalaxealpha = new ItemAdElementalAxeAlpha(ThaumcraftMaterials.TOOLMAT_ELEMENTAL));
        registry.register(ItemsTS.adelementalaxebeta = new ItemAdElementalAxeBeta(ThaumcraftMaterials.TOOLMAT_ELEMENTAL));
        registry.register(ItemsTS.adelementalshovelalpha = new ItemAdElementalShovelAlpha(ConfigMaterials.TOOLMAT_ADELEMENTAL));
        registry.register(ItemsTS.adelementalshovelbeta = new ItemAdElementalShovelBeta(ThaumcraftMaterials.TOOLMAT_ELEMENTAL));
        registry.register(ItemsTS.adelementalhoealpha = new ItemAdElementalHoeAlpha(ThaumcraftMaterials.TOOLMAT_ELEMENTAL));
        registry.register(ItemsTS.adelementalhoebeta = new ItemAdElementalHoeBeta(ThaumcraftMaterials.TOOLMAT_ELEMENTAL));

        registry.register(ItemsTS.clusters= new ItemTSBase("cluster", new String[]{"coal", "redstone", "lapis", "diamond", "emerald", "amber"}));

        /*registry.register(ItemsTS.richsoil = new ItemBlock(BlockRegistryHandler.BLOCK_RICH_SOIL));*/
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {

        Iterator var0 = ITEM_VARIANT_HOLDERS.iterator();

        while(var0.hasNext()) {
            IThaumcraftItems itemVariantHolder = (IThaumcraftItems)var0.next();
            initModelAndVariants(itemVariantHolder);
        }

    }

    @SideOnly(Side.CLIENT)
    private static void initModelAndVariants(IThaumcraftItems item) {
        int i;
        if (item.getCustomMesh() != null) {
            ModelLoader.setCustomMeshDefinition(item.getItem(), item.getCustomMesh());

            for(i = 0; i < item.getVariantNames().length; ++i) {
                ModelBakery.registerItemVariants(item.getItem(), new ResourceLocation[]{item.getCustomModelResourceLocation(item.getVariantNames()[i])});
            }
        } else if (item.getItem() == ItemsTC.seals) {
            for(i = 0; i < item.getVariantNames().length; ++i) {
                ModelLoader.setCustomModelResourceLocation(item.getItem(), item.getVariantMeta()[i], new ModelResourceLocation(item.getItem().getRegistryName() + "_" + item.getVariantNames()[i], (String)null));
            }
        } else if (!item.getItem().getHasSubtypes()) {
            ModelLoader.setCustomModelResourceLocation(item.getItem(), 0, new ModelResourceLocation(item.getItem().getRegistryName(), "inventory"));
/*            System.out.println(item.getItem().getRegistryName());*/
        } else {
            for(i = 0; i < item.getVariantNames().length; ++i) {
                ModelLoader.setCustomModelResourceLocation(item.getItem(), item.getVariantMeta()[i], item.getCustomModelResourceLocation(item.getVariantNames()[i]));

            }
        }

    }


}
