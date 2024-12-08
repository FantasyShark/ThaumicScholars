package fantasyshark.thaumicscholars.common.creativetab;

import fantasyshark.thaumicscholars.common.items.ItemsTS;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabThaumicScholars extends CreativeTabs {

    public TabThaumicScholars() {

        super("thaumicscholars");
    }

    @Override
    public ItemStack getTabIconItem() {

        return new ItemStack(ItemsTS.adelementalpickaxebeta);
    }
}
