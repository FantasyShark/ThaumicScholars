package fantasyshark.thaumicscholars.common.config;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import thaumcraft.api.items.ItemsTC;

public class ConfigMaterials {
    public static Item.ToolMaterial TOOLMAT_ADELEMENTAL;

    public ConfigMaterials() {
    }

    static {
        TOOLMAT_ADELEMENTAL = EnumHelper.addToolMaterial("THAUMIUM_ADELEMENTAL", 3, 3000, 9.0F, 3.0F, 18).setRepairItem(new ItemStack(ItemsTC.ingots));
    }
}
