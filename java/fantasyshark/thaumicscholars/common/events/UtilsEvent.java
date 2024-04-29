package fantasyshark.thaumicscholars.common.events;

import fantasyshark.thaumicscholars.common.blocks.BlockRichSoil;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class UtilsEvent {

    public static HashMap<List<Object>, ItemStack> specialMiningResult = new HashMap();
    public static HashMap<List<Object>, Float> specialMiningChance = new HashMap();

    public UtilsEvent() {
    }

    public static void addSpecialMiningResult(ItemStack in, ItemStack out, float chance) {
        specialMiningResult.put(Arrays.asList(in.getItem(), in.getItemDamage()), out);
        specialMiningChance.put(Arrays.asList(in.getItem(), in.getItemDamage()), chance);
    }

    public static ItemStack findSpecialMiningResult(ItemStack is, float chance, Random rand) {
        ItemStack dropped = is.copy();
        float r = rand.nextFloat();
        List ik = Arrays.asList(is.getItem(), is.getItemDamage());
        if (specialMiningResult.containsKey(ik) && r <= chance * ((Float)specialMiningChance.get(ik)).floatValue()) {
            dropped = ((ItemStack)specialMiningResult.get(ik)).copy();
            dropped.setCount(dropped.getCount() * is.getCount());
        }

        return dropped;
    }

    @SubscribeEvent
    public static void onBlockBrocken(BlockEvent.BreakEvent event) {
        if (!event.getWorld().isRemote) {
            IBlockState state = event.getState();
            World world = event.getWorld();
            BlockPos pos = event.getPos();
            if (state.getBlock() instanceof BlockCrops) {
                if (world.getBlockState(pos.down()).getBlock() instanceof BlockRichSoil) {
                    if (!event.getPlayer().capabilities.isCreativeMode) {
                        for (ItemStack itemStack : (state.getBlock()).getDrops( world, pos, state, 0)){
                            world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
                        }
                    }
                }
            }
        }
    }
}
