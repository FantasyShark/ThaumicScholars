package fantasyshark.thaumicscholars.common.items.tools;

import fantasyshark.thaumicscholars.ThaumicScholars;
import fantasyshark.thaumicscholars.common.items.ItemRegistryHandler;
import fantasyshark.thaumicscholars.common.items.ItemsTS;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.blocks.world.plants.BlockLogsTC;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.IThaumcraftItems;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
import thaumcraft.common.lib.events.ServerEvents;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumcraft.common.lib.utils.Utils;

import java.util.Iterator;
import java.util.List;

public class ItemAdElementalAxeAlpha extends ItemAxe implements IThaumcraftItems {

    public ItemAdElementalAxeAlpha(ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial, 8.0F, -3.0F);
        this.setCreativeTab(ItemsTS.TABTS);
        this.setRegistryName("ad_elemental_axe_alpha");
        this.setUnlocalizedName(ThaumicScholars.MODID + ".adElementalAxeAlpha");
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

    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.BOW;
    }

    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        playerIn.setActiveHand(hand);
        return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
    }

    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        if (!player.isSneaking()) {
            List<EntityItem> stuff = EntityUtils.getEntitiesInRange(player.world, player.posX, player.posY, player.posZ, player, EntityItem.class, 10.0D);
            List<Entity> targets = player.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX - 10, player.posY - 10, player.posZ - 10, player.posX + 10, player.posY + 10, player.posZ + 10));
            if (stuff != null && stuff.size() > 0) {
                Iterator var5 = stuff.iterator();

                while (var5.hasNext()) {
                    EntityItem e = (EntityItem) var5.next();
                    if (!e.isDead) {
                        double d6 = e.posX - player.posX;
                        double d8 = e.posY - player.posY + (double) (player.height / 2.0F);
                        double d10 = e.posZ - player.posZ;
                        double d11 = (double) MathHelper.sqrt(d6 * d6 + d8 * d8 + d10 * d10);
                        d6 /= d11;
                        d8 /= d11;
                        d10 /= d11;
                        double d13 = 0.3D;
                        e.motionX -= d6 * d13;
                        e.motionY -= d8 * d13 - 0.1D;
                        e.motionZ -= d10 * d13;
                        if (e.motionX > 0.25D) {
                            e.motionX = 0.25D;
                        }

                        if (e.motionX < -0.25D) {
                            e.motionX = -0.25D;
                        }

                        if (e.motionY > 0.25D) {
                            e.motionY = 0.25D;
                        }

                        if (e.motionY < -0.25D) {
                            e.motionY = -0.25D;
                        }

                        if (e.motionZ > 0.25D) {
                            e.motionZ = 0.25D;
                        }

                        if (e.motionZ < -0.25D) {
                            e.motionZ = -0.25D;
                        }

                        if (player.world.isRemote) {
                            FXDispatcher.INSTANCE.crucibleBubble((float) e.posX + (player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.2F, (float) e.posY + e.height + (player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.2F, (float) e.posZ + (player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.2F, 0.33F, 0.33F, 1.0F);
                        }
                    }
                }
            }
            if (!player.world.isRemote) {
                if (targets.size() > 0) {
                    for (count = 0; count < targets.size(); ++count) {
                        final Entity entity = targets.get(count);
                        if (!(entity == player) && entity instanceof EntityLivingBase) {
                            final Vec3d p = new Vec3d(player.posX, player.posY, player.posZ);
                            final Vec3d t = new Vec3d(entity.posX, entity.posY, entity.posZ);
                            final double distance = p.distanceTo(t) + 0.1;
                            final Vec3d r = new Vec3d(t.x - p.x, t.y - p.y, t.z - p.z);
                            final Entity entity2 = entity;
                            entity2.motionX -= r.x / 10.0 / distance;
                            final Entity entity3 = entity;
                            entity3.motionY -= r.y / 5.0 / distance;
                            final Entity entity4 = entity;
                            entity4.motionZ -= r.z / 10.0 / distance;

                            FXDispatcher.INSTANCE.crucibleBubble((float) entity.posX + (player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.2F, (float) entity.posY + entity.height + (player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.2F, (float) entity.posZ + (player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.2F, 0.33F, 0.33F, 1.0F);
                        }


                    }
                }
            }
        }else {
            int xx = 0;
            int zz = 0;
            if(player.world.getTotalWorldTime() % 20 == 0) {
                for(int aa = -5; aa <= 4; ++aa) {
                    for(int bb = -5; bb <= 5; ++bb) {
                        xx = aa;
                        zz = bb;
                        BlockPos blockPos = new BlockPos(player.posX + xx, player.posY, player.posZ + zz);
                        IBlockState bl = player.world.getBlockState(blockPos);
                        if (Utils.isWoodLog(player.world, blockPos)) {
                            stack.damageItem(1, player);
                            BlockUtils.breakFurthestBlock(player.world, blockPos, bl, (EntityPlayer)player);
                        }
                    }
                }
            }

        }
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab != ItemsTS.TABTS && tab != CreativeTabs.SEARCH) {
            super.getSubItems(tab, items);
        } else {
            ItemStack w1 = new ItemStack(this);
            EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.BURROWING, 1);
            EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.COLLECTOR, 1);
            items.add(w1);
        }

    }
}
