package fantasyshark.thaumicscholars.common.events;

import fantasyshark.thaumicscholars.common.items.ItemsTS;
import fantasyshark.thaumicscholars.common.items.tools.ItemAdElementalPickaxeAlpha;
import fantasyshark.thaumicscholars.common.items.tools.ItemAdElementalPickaxeBeta;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXScanSource;
import thaumcraft.common.lib.utils.Utils;

import java.util.List;

@Mod.EventBusSubscriber
public class ToolEvents {

    /*Echo*/
    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {

        EntityLivingBase entity = event.getEntityLiving();
        if (!entity.world.isRemote && entity instanceof EntityPlayer) {

            ItemStack heldItemMain = entity.getHeldItem(EnumHand.MAIN_HAND);
            ItemStack heldItemOff = entity.getHeldItem(EnumHand.OFF_HAND);
            if ((heldItemMain.getItem() instanceof ItemAdElementalPickaxeAlpha || heldItemOff.getItem() instanceof ItemAdElementalPickaxeAlpha) && ((EntityPlayer) entity).world.getTotalWorldTime() % 60 == 0) {

                ((EntityPlayer) entity).world.playSound(null, ((EntityPlayer) entity).posX + 0.5D, ((EntityPlayer) entity).posY + 0.5D, ((EntityPlayer) entity).posZ + 0.5D, SoundsTC.wandfail, SoundCategory.BLOCKS, 0.2F, 0.2F + ((EntityPlayer) entity).world.rand.nextFloat() * 0.2F);
                PacketHandler.INSTANCE.sendTo(new PacketFXScanSource(entity.getPosition(), 2), (EntityPlayerMP) event.getEntity());

                final List<Entity> targets = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entity.posX - 12, entity.posY - 12, entity.posZ - 12, entity.posX + 12, entity.posY + 12, entity.posZ + 12));
                if (targets.size() > 0) {

                    for (int count = 0; count < targets.size(); ++count) {

                        final Entity reflector = targets.get(count);
                        if (reflector instanceof EntityLivingBase && !(reflector instanceof EntityPlayer)) {

                            ((EntityLivingBase) reflector).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 100));
                        }
                    }
                }
            }
        }
    }

    /*Purification*/
    @SubscribeEvent
    public static void harvestBlockEvent(final BlockEvent.HarvestDropsEvent event) {
        if (!event.getWorld().isRemote && event.getHarvester() != null) {
            ItemStack heldItem = event.getHarvester().getHeldItem(event.getHarvester().getActiveHand());
            if (heldItem != null && !heldItem.isEmpty()&heldItem.getItem() instanceof ItemAdElementalPickaxeBeta) {

                if (!event.isSilkTouching() || ForgeHooks.isToolEffective(event.getWorld(), event.getPos(), heldItem) || heldItem.getItem() instanceof ItemTool && ((ItemTool) heldItem.getItem()).getDestroySpeed(heldItem, event.getState()) > 1.0F) {
                    int xx;
                    boolean s = false;
                    for (xx = 0; xx < event.getDrops().size(); ++xx) {
                        ItemStack is = (ItemStack) event.getDrops().get(xx);
                        ItemStack smr = Utils.findSpecialMiningResult(is, 1, event.getWorld().rand);
                        ItemStack smr2 = UtilsEvent.findSpecialMiningResult(is, 1, event.getWorld().rand);

                        if (!is.isItemEqual(smr)) {
                            event.getDrops().set(xx, smr);
                            s = true;
                        }else if (!is.isItemEqual(smr2)) {
                            event.getDrops().set(xx, smr2);
                            s = true;
                        }

                        if (s) {
                            event.getWorld().playSound((EntityPlayer) null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.7F + event.getWorld().rand.nextFloat() * 0.2F);
                        }
                    }
                }
            }
        }
    }
}