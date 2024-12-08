package fantasyshark.thaumicscholars.common.events;

import fantasyshark.thaumicscholars.common.debug.Debug;
import fantasyshark.thaumicscholars.common.items.tools.ItemAdElementalAxeBeta;
import fantasyshark.thaumicscholars.common.potion.PotionRegistryHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PotionEvent {

    /*Use turbulence*/
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {

        DamageSource damageSource = event.getSource();
        EntityLivingBase target = event.getEntityLiving();
        World world = target.world;
        if (event.isCanceled() || world.isRemote) {
            return;
        }

        if(damageSource.getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) damageSource.getTrueSource();
            ItemStack heldItemMain = player.getHeldItemMainhand();
            Potion potion = PotionRegistryHandler.POTION_TURBULENCE;
            if(heldItemMain.getItem() instanceof ItemAdElementalAxeBeta) {
                if (target.isPotionActive(potion)) {
                    PotionEffect effect = target.getActivePotionEffect(potion);
                    event.setAmount(event.getAmount() + 2.0F * (effect.getAmplifier()));
                }
            }
        }
    }

    /*Apply turbulence*/
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {

        EntityPlayer player = event.getEntityPlayer();
        ItemStack heldItemMain = player.getHeldItemMainhand();
        Potion potion = PotionRegistryHandler.POTION_TURBULENCE;
        EntityLivingBase target = (EntityLivingBase)event.getTarget();

        if (!player.world.isRemote) {
            if(heldItemMain.getItem() instanceof ItemAdElementalAxeBeta && player.getCooledAttackStrength(0.0F) >= 0.8F) {
                if (!target.isPotionActive(potion)) {
                    target.addPotionEffect(new PotionEffect(potion, 2 * 20, 0));
                }else{
                    PotionEffect effect = target.getActivePotionEffect(potion);
                    target.addPotionEffect(new PotionEffect(potion, 2 * 20, effect.getAmplifier() + 1));
                }
            }
        }
    }
}
