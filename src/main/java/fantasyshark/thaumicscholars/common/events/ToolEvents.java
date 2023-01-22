package fantasyshark.thaumicscholars.common.events;

import fantasyshark.thaumicscholars.common.items.tools.ItemAdElementalPickaxe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXScanSource;

import java.util.List;

@Mod.EventBusSubscriber
public class ToolEvents {

    //炽心镐回声升级
    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {

        EntityLivingBase entity = event.getEntityLiving();
        if(!entity.world.isRemote && entity instanceof EntityPlayer) {

            ItemStack heldItemMain = entity.getHeldItem(EnumHand.MAIN_HAND);
            ItemStack heldItemOff = entity.getHeldItem(EnumHand.OFF_HAND);
            if((heldItemMain.getItem() instanceof ItemAdElementalPickaxe || heldItemOff.getItem() instanceof ItemAdElementalPickaxe) && ((EntityPlayer) entity).world.getTotalWorldTime() % 60 == 0) {

                    ((EntityPlayer) entity).world.playSound(null, ((EntityPlayer) entity).posX + 0.5D, ((EntityPlayer) entity).posY + 0.5D, ((EntityPlayer) entity).posZ + 0.5D, SoundsTC.wandfail, SoundCategory.BLOCKS, 0.2F, 0.2F + ((EntityPlayer) entity).world.rand.nextFloat() * 0.2F);
                    PacketHandler.INSTANCE.sendTo(new PacketFXScanSource(entity.getPosition(), 2), (EntityPlayerMP)event.getEntity());

                final List<Entity> targets = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entity.posX - 12, entity.posY - 12, entity.posZ - 12, entity.posX + 12, entity.posY + 12, entity.posZ + 12));
                if (targets.size() > 0) {

                    for (int count = 0; count < targets.size(); ++count) {

                        final Entity reflector = targets.get(count);
                        if (reflector instanceof EntityLivingBase && !(reflector instanceof EntityPlayer)) {

                            ((EntityLivingBase)reflector).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 100));
                        }
                    }
                }
            }
        }
    }

}
