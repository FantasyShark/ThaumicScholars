package fantasyshark.thaumicscholars.common.items.casters.foci;

import fantasyshark.thaumicscholars.ThaumicScholars;
import fantasyshark.thaumicscholars.common.items.ItemRegistryHandler;
import fantasyshark.thaumicscholars.common.items.ItemsTS;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.casters.ItemFocus;

public class ItemFocusCreative extends ItemFocus {

    public ItemFocusCreative () {
        super("focus_creative", 999);
        this.maxStackSize = 1;
        this.setMaxDamage(0);
        this.setCreativeTab(ItemsTS.TABTS);
        this.setUnlocalizedName(ThaumicScholars.MODID + ".focuscreative");
        /*ConfigItems.ITEM_VARIANT_HOLDERS.remove(this);*/
        ItemRegistryHandler.ITEM_VARIANT_HOLDERS.add(this);
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == ItemsTS.TABTS || tab == CreativeTabs.SEARCH) {
            items.add(new ItemStack(this, 1, 0));
        }
    }

}
