package fantasyshark.thaumicscholars.common.events;

import net.minecraft.item.ItemStack;

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
}
