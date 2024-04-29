package fantasyshark.thaumicscholars.common.events;

import com.google.common.collect.Sets;
import fantasyshark.thaumicscholars.common.debug.Debug;
import fantasyshark.thaumicscholars.common.items.ItemsTS;
import fantasyshark.thaumicscholars.common.items.tools.*;
import fantasyshark.thaumicscholars.common.potion.PotionRegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
import thaumcraft.common.lib.events.PlayerEvents;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXScanSource;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.lib.utils.Utils;

import java.util.*;

@Mod.EventBusSubscriber
public class ToolEvents {

    private static final Set isEffective;
    static HashMap<Integer, EnumFacing> lastFaceClicked = new HashMap();
    static boolean blockDestructiveRecursion = false;

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (!entity.world.isRemote && entity instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) entity;
            ItemStack heldItemMain = player.getHeldItem(EnumHand.MAIN_HAND);
            ItemStack heldItemOff = player.getHeldItem(EnumHand.OFF_HAND);
            final UUID MODIFIER_ID = UUID.fromString("42b33eb8-4aa2-1e9c-e10c-84c86c8185af");
            AttributeModifier mod = new AttributeModifier(MODIFIER_ID, "my_modifier", 5.0, 1);
            IAttributeInstance attr = player.getAttributeMap().getAttributeInstance(player.SWIM_SPEED);

            if (heldItemMain.getItem() instanceof ItemAdElementalAxeBeta) {
                if (!attr.hasModifier(mod)) {
                    attr.applyModifier(mod);
                }
            }else if (attr.hasModifier(mod)){
                attr.removeModifier(mod);
            }

            if ((heldItemMain.getItem() instanceof ItemAdElementalPickaxeAlpha || heldItemOff.getItem() instanceof ItemAdElementalPickaxeAlpha) && player.world.getTotalWorldTime() % 60 == 0) {

                player.world.playSound(null, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, SoundsTC.wandfail, SoundCategory.BLOCKS, 0.2F, 0.2F + player.world.rand.nextFloat() * 0.2F);
                PacketHandler.INSTANCE.sendTo(new PacketFXScanSource(player.getPosition(), 2), (EntityPlayerMP) event.getEntity());

                final List<Entity> targets = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX - 12, player.posY - 12, player.posZ - 12, player.posX + 12, player.posY + 12, player.posZ + 12));
                if (targets.size() > 0) {

                    for (int count = 0; count < targets.size(); ++count) {

                        final Entity reflector = targets.get(count);
                        if (reflector instanceof EntityLivingBase && !(reflector instanceof EntityPlayer)) {

                            ((EntityLivingBase) reflector).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 100));
                        }
                    }
                }
            }

            if (heldItemMain.getItem() instanceof ItemAdElementalShovelBeta) {

                int aa = -1;
                int bb = -1;
                int xx, yy, zz = 0;
                for (xx = -2; xx <= 2; xx++) {
                    for (yy = 0; yy <= 2 ; yy++) {
                        for (zz = -2; zz <= 2; zz++) {
                            BlockPos blockPos = new BlockPos(player.getPosition().add(xx, yy, zz));
                            if (isEffectiveAgainst(player.world.getBlockState(blockPos).getBlock())) {
                                player.world.destroyBlock(blockPos, false);
                            }
                        }
                    }
                }
                if (player.isSneaking()) {
                    for (xx = -2; xx <= 2; xx++) {
                        for (zz = -2; zz <= 2; zz++) {
                            BlockPos blockPos = new BlockPos(player.getPosition().add(xx, -1, zz));
                            if (isEffectiveAgainst(player.world.getBlockState(blockPos).getBlock())) {
                                player.world.destroyBlock(blockPos, false);
                            }
                        }
                    }
                }
                /*BlockPos downPosition = new BlockPos(player.posX, player.posY - 0.1f, player.posZ);
                BlockPos upPosition = new BlockPos(player.posX, player.posY + 0.1f, player.posZ);

                if (isEffectiveAgainst(player.world.getBlockState(downPosition).getBlock())) {
                    Debug.chat(player, "Dirt!");
                    if (player.isSneaking()) {
                        player.world.destroyBlock(downPosition, false);
                    }

                }*/
            }

        }
    }

    /*Purification*/
    @SubscribeEvent(priority = EventPriority.HIGH)
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
            if (heldItem.getItem() instanceof ItemAdElementalAxeAlpha) {
                InventoryUtils.dropHarvestsAtPos(event.getWorld(), event.getPos(), event.getDrops(), true, 10, event.getHarvester());
                event.getDrops().clear();
            }

            if (!blockDestructiveRecursion && heldItem.getItem() instanceof ItemAdElementalShovelAlpha && !event.getHarvester().isSneaking()) {
                blockDestructiveRecursion = true;
                EnumFacing face = (EnumFacing)lastFaceClicked.get(event.getHarvester().getEntityId());
                if (face == null) {
                    face = EnumFacing.getDirectionFromEntityLiving(event.getPos(), event.getHarvester());
                }

                int aa = -2;
                int bb = -2;
                int xx = 0;
                int yy = 0;
                int zz = 0;

                while(true) {
                    if (aa > 2) {
                        blockDestructiveRecursion = false;
                        break;
                    }

                    if (face.ordinal() <= 1) {
                        for(bb = -2; bb <= 2; ++bb) {
                            if (aa != 0 || bb != 0) {
                                xx = aa;
                                zz = bb;
                            }
                            IBlockState bl = event.getWorld().getBlockState(event.getPos().add(xx, yy, zz));
                            if (bl.getBlockHardness(event.getWorld(), event.getPos().add(xx, yy, zz)) >= 0.0F && (ForgeHooks.isToolEffective(event.getWorld(), event.getPos().add(xx, yy, zz), heldItem) || heldItem.getItem() instanceof ItemTool && ((ItemTool)heldItem.getItem()).getDestroySpeed(heldItem, bl) > 1.0F)) {
                                if (event.getHarvester().getName().equals("FakeThaumcraftBore")) {
                                    ++event.getHarvester().xpCooldown;
                                } else {
                                    heldItem.damageItem(1, event.getHarvester());
                                }

                                ItemStack orestack = new ItemStack(bl.getBlock(), 1, bl.getBlock().getMetaFromState(bl));
                                for(int id : OreDictionary.getOreIDs(orestack)) {
                                    String s = OreDictionary.getOreName(id);
                                    if(!s.matches("^ore[A-Z].+")) {
                                        BlockUtils.harvestBlock(event.getWorld(), event.getHarvester(), event.getPos().add(xx, yy, zz));
                                    }
                                }

                            }
                        }
                    }else {
                        for(bb = -1; bb <= 3; ++bb) {
                            if (aa != 0 || bb != 0) {
                                if (face.ordinal() <= 3) {
                                    xx = aa;
                                    yy = bb;
                                } else {
                                    zz = aa;
                                    yy = bb;
                                }
                            }
                            IBlockState bl = event.getWorld().getBlockState(event.getPos().add(xx, yy, zz));
                            if (bl.getBlockHardness(event.getWorld(), event.getPos().add(xx, yy, zz)) >= 0.0F && (ForgeHooks.isToolEffective(event.getWorld(), event.getPos().add(xx, yy, zz), heldItem) || heldItem.getItem() instanceof ItemTool && ((ItemTool)heldItem.getItem()).getDestroySpeed(heldItem, bl) > 1.0F)) {
                                if (event.getHarvester().getName().equals("FakeThaumcraftBore")) {
                                    ++event.getHarvester().xpCooldown;
                                } else {
                                    heldItem.damageItem(1, event.getHarvester());
                                }

                                ItemStack orestack = new ItemStack(bl.getBlock(), 1, bl.getBlock().getMetaFromState(bl));
                                for(int id : OreDictionary.getOreIDs(orestack)) {
                                    String s = OreDictionary.getOreName(id);
                                    if(!s.matches("^ore[A-Z].+")) {
                                        BlockUtils.harvestBlock(event.getWorld(), event.getHarvester(), event.getPos().add(xx, yy, zz));
                                    }
                                }
                            }
                        }
                    }
                    ++aa;
                }
            }
        }
    }

    @SubscribeEvent
    public static void playerInteract(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntityPlayer() != null) {
            lastFaceClicked.put(event.getEntityPlayer().getEntityId(), event.getFace());
        }

    }

    private static boolean isEffectiveAgainst(Block block) {
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
        isEffective = Sets.newHashSet(new Block[]{Blocks.GRASS, Blocks.DIRT, Blocks.SAND, Blocks.GRAVEL, Blocks.SNOW_LAYER, Blocks.SNOW, Blocks.CLAY, Blocks.FARMLAND, Blocks.SOUL_SAND, Blocks.MYCELIUM});
    }

}