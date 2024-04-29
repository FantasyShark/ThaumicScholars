package fantasyshark.thaumicscholars.common.crafting;

import fantasyshark.thaumicscholars.common.items.ItemsTS;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.items.ItemsTC;

public class FurnaceRecipeRegistryHandler {

    public static void register() {

        GameRegistry.addSmelting(new ItemStack(ItemsTS.clusters, 1, 0), new ItemStack(Items.COAL, 2, 0), 1.0F);
        GameRegistry.addSmelting(new ItemStack(ItemsTS.clusters, 1, 1), new ItemStack(Items.REDSTONE, 2, 0), 1.0F);
        GameRegistry.addSmelting(new ItemStack(ItemsTS.clusters, 1, 2), new ItemStack(Items.DYE, 2, 4), 1.0F);
        GameRegistry.addSmelting(new ItemStack(ItemsTS.clusters, 1, 3), new ItemStack(Items.DIAMOND, 2, 0), 1.0F);
        GameRegistry.addSmelting(new ItemStack(ItemsTS.clusters, 1, 4), new ItemStack(Items.EMERALD, 2, 0), 1.0F);
        GameRegistry.addSmelting(new ItemStack(ItemsTS.clusters, 1, 5), new ItemStack(ItemsTC.amber, 2, 0), 1.0F);
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTS.clusters, 1, 32767), new ItemStack(ItemsTC.nuggets, 1, 10), 0.02F);
    }
}
