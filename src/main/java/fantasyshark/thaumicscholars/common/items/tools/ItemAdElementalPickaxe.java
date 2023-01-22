package fantasyshark.thaumicscholars.common.items.tools;

import fantasyshark.thaumicscholars.ThaumicScholars;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.config.ConfigItems;

//直接继承炽心镐会有RegistryName重复的问题
public class ItemAdElementalPickaxe extends ItemPickaxe {

    public ItemAdElementalPickaxe(ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial);
        this.setCreativeTab(ConfigItems.TABTC);
        super.setRegistryName("ad_elemental_pickaxe");
        super.setUnlocalizedName(ThaumicScholars.MODID + ".adElementalPickaxe");
    }

    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.RARE;
    }

    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        return stack2.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0)) ? true : super.getIsRepairable(stack1, stack2);
    }
}
