package fantasyshark.thaumicscholars.common.items.tools;

import fantasyshark.thaumicscholars.ThaumicScholars;
import fantasyshark.thaumicscholars.common.entity.EntityStreamVortex;
import fantasyshark.thaumicscholars.common.items.ItemRegistryHandler;
import fantasyshark.thaumicscholars.common.items.ItemsTS;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.items.IThaumcraftItems;
import thaumcraft.common.lib.utils.EntityUtils;

import java.util.Iterator;
import java.util.List;

public class ItemAdElementalAxeBeta extends ItemAxe implements IThaumcraftItems {

    public ItemAdElementalAxeBeta(ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial, 1.0F, -3.0F);
        this.setCreativeTab(ItemsTS.TABTS);
        this.setRegistryName("ad_elemental_axe_beta");
        this.setUnlocalizedName(ThaumicScholars.MODID + ".adElementalAxeBeta");
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

    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.RARE;
    }

    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        return stack2.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0)) ? true : super.getIsRepairable(stack1, stack2);
    }


}
