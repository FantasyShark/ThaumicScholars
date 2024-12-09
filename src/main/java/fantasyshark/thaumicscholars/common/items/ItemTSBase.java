package fantasyshark.thaumicscholars.common.items;

import fantasyshark.thaumicscholars.ThaumicScholars;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.IThaumcraftItems;

public class ItemTSBase extends Item implements IThaumcraftItems {

    protected final String BASE_NAME;
    protected String[] VARIANTS;
    protected int[] VARIANTS_META;

    public ItemTSBase(String name, String... variants) {

        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(ItemsTS.TABTS);
        this.setNoRepair();
        this.setHasSubtypes(variants.length > 1);
        this.BASE_NAME = name;
        if (variants.length == 0) {
            this.VARIANTS = new String[]{name};
        } else {
            this.VARIANTS = variants;
        }

        this.VARIANTS_META = new int[this.VARIANTS.length];

        for(int m = 0; m < this.VARIANTS.length; this.VARIANTS_META[m] = m++) {
            ;
        }

        ItemRegistryHandler.ITEM_VARIANT_HOLDERS.add(this);
    }

    public String getUnlocalizedName(ItemStack itemStack) {

        return this.hasSubtypes && itemStack.getMetadata() < this.VARIANTS.length && this.VARIANTS[itemStack.getMetadata()] != this.BASE_NAME ? String.format(super.getUnlocalizedName() + ".%s", this.VARIANTS[itemStack.getMetadata()]) : super.getUnlocalizedName(itemStack);

    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {

        if (tab == ItemsTS.TABTS || tab == CreativeTabs.SEARCH) {
            if (!this.getHasSubtypes()) {
                super.getSubItems(tab, items);
            } else {
                for(int meta = 0; meta < this.VARIANTS.length; ++meta) {
                    items.add(new ItemStack(this, 1, meta));
                }
            }
        }

    }

    public Item getItem() {
        return this;
    }

    public String[] getVariantNames() {
        return this.VARIANTS;
    }

    public int[] getVariantMeta() {
        return this.VARIANTS_META;
    }

    public ItemMeshDefinition getCustomMesh() {
        return null;
    }

    public ModelResourceLocation getCustomModelResourceLocation(String variant) {

        return variant.equals(this.BASE_NAME) ? new ModelResourceLocation("thaumicscholars:" + this.BASE_NAME) : new ModelResourceLocation("thaumicscholars:" + this.BASE_NAME, variant);
    }

}