package fantasyshark.thaumicscholars.common.items.tools;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import fantasyshark.thaumicscholars.ThaumicScholars;
import fantasyshark.thaumicscholars.common.items.ItemRegistryHandler;
import fantasyshark.thaumicscholars.common.items.ItemsTS;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.IThaumcraftItems;

import java.util.Iterator;
import java.util.Set;

public class ItemAdElementalShovelAlpha extends ItemTool implements IThaumcraftItems {
    private static final Set isEffective;

    public ItemAdElementalShovelAlpha(ToolMaterial enumtoolmaterial) {
        super(0, -3.0F, enumtoolmaterial, isEffective);
        this.setCreativeTab(ItemsTS.TABTS);
        this.setRegistryName("ad_elemental_shovel_alpha");
        this.setUnlocalizedName(ThaumicScholars.MODID + ".adElementalShovelAlpha");
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

    public boolean canHarvestBlock(IBlockState blockState) {
        ItemStack orestack = new ItemStack(blockState.getBlock(), 1, blockState.getBlock().getMetaFromState(blockState));
        for(int id : OreDictionary.getOreIDs(orestack)) {
            String s = OreDictionary.getOreName(id);
            if(s.matches("^ore[A-Z].+")) {
                return false;
            }
        }
        return blockState.getMaterial() != Material.WOOD && blockState.getMaterial() != Material.LEAVES && blockState.getMaterial() != Material.PLANTS ;
    }

    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("shovel", "pickaxe");
    }

    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.RARE;
    }

    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        return stack2.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0)) ? true : super.getIsRepairable(stack1, stack2);
    }

    private boolean isEffectiveAgainst(Block block) {
        Iterator var1 = isEffective.iterator();

        Object b;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            b = var1.next();
        } while(b != block);

        return true;
    }

    static {
        isEffective = Sets.newHashSet(new Block[]{Blocks.COBBLESTONE, Blocks.DOUBLE_STONE_SLAB, Blocks.STONE_SLAB, Blocks.STONE, Blocks.SANDSTONE, Blocks.MOSSY_COBBLESTONE, Blocks.ICE, Blocks.NETHERRACK, Blocks.RAIL, Blocks.DETECTOR_RAIL, Blocks.GOLDEN_RAIL, Blocks.ACTIVATOR_RAIL, Blocks.GRASS, Blocks.DIRT, Blocks.SAND, Blocks.GRAVEL, Blocks.SNOW_LAYER, Blocks.SNOW, Blocks.CLAY, Blocks.FARMLAND, Blocks.SOUL_SAND, Blocks.MYCELIUM});
    }
}
