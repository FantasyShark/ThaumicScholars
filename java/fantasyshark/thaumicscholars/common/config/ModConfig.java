package fantasyshark.thaumicscholars.common.config;

import fantasyshark.thaumicscholars.common.events.UtilsEvent;
import fantasyshark.thaumicscholars.common.items.ItemsTS;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.utils.Utils;

public class ModConfig {

    public ModConfig() {
    }

    public static void postInitMisc() {

        /*add mining result different from the pickaxe of the core*/
        UtilsEvent.addSpecialMiningResult(new ItemStack(Items.COAL), new ItemStack(ItemsTS.clusters, 1, 0), 1.0F);
        UtilsEvent.addSpecialMiningResult(new ItemStack(Items.REDSTONE), new ItemStack(ItemsTS.clusters, 1, 1), 1.0F);
        UtilsEvent.addSpecialMiningResult(new ItemStack(Items.DYE, 1, 4), new ItemStack(ItemsTS.clusters, 1, 2), 1.0F);
        UtilsEvent.addSpecialMiningResult(new ItemStack(Items.DIAMOND), new ItemStack(ItemsTS.clusters, 1, 3), 1.0F);
        UtilsEvent.addSpecialMiningResult(new ItemStack(Items.EMERALD), new ItemStack(ItemsTS.clusters, 1, 4), 1.0F);
        UtilsEvent.addSpecialMiningResult(new ItemStack(ItemsTC.amber), new ItemStack(ItemsTS.clusters, 1, 5), 1.0F);

    }
}
