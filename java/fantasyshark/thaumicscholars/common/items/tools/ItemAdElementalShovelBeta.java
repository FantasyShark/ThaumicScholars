package fantasyshark.thaumicscholars.common.items.tools;

import com.google.common.collect.ImmutableSet;
import fantasyshark.thaumicscholars.ThaumicScholars;
import fantasyshark.thaumicscholars.common.items.ItemRegistryHandler;
import fantasyshark.thaumicscholars.common.items.ItemsTS;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.EnumFacing;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.IThaumcraftItems;

import java.util.Set;

public class ItemAdElementalShovelBeta extends ItemSpade implements IThaumcraftItems {
    private static final Block[] isEffective;
    EnumFacing side;

    public ItemAdElementalShovelBeta(ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial);
        this.setCreativeTab(ItemsTS.TABTS);
        this.side = EnumFacing.DOWN;
        this.setRegistryName("ad_elemental_shovel_beta");
        this.setUnlocalizedName(ThaumicScholars.MODID + ".adElementalShovelBeta");
        ItemRegistryHandler.ITEM_VARIANT_HOLDERS.add(this);
    }

    public Item getItem() {
        return this;
    }

    public String[] getVariantNames() {
        return new String[]{"normal"};
    }

    public int[] getVariantMeta() {
        return new int[]{0};
    }

    public ItemMeshDefinition getCustomMesh() {
        return null;
    }

    public ModelResourceLocation getCustomModelResourceLocation(String variant) {
        return new ModelResourceLocation("thaumicscholars:" + variant);
    }

    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("shovel");
    }

    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.RARE;
    }

    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        return stack2.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0)) ? true : super.getIsRepairable(stack1, stack2);
    }

    static {
        isEffective = new Block[]{Blocks.GRASS, Blocks.DIRT, Blocks.SAND, Blocks.GRAVEL, Blocks.SNOW_LAYER, Blocks.SNOW, Blocks.CLAY, Blocks.FARMLAND, Blocks.SOUL_SAND, Blocks.MYCELIUM};
    }
}
