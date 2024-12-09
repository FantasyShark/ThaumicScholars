package fantasyshark.thaumicscholars.common.crafting;

import fantasyshark.thaumicscholars.common.items.ItemsTS;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.config.ConfigItems;

public class InfusionRecipeRegistryHandler {

    public static ItemStack SENSES_CRYSTAL = ThaumcraftApiHelper.makeCrystal(Aspect.SENSES);

    public static void initializeInfusionRecipes() {
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumicscholars:AdElementalPickaxeBeta"), new InfusionRecipe("ADELEMENTALPICKB", new ItemStack(ItemsTS.adelementalpickaxebeta), 10, (new AspectList()).add(Aspect.FIRE, 120).add(Aspect.ORDER, 120).add(Aspect.METAL, 60), new ItemStack(ItemsTC.elementalPick, 1, 32767), new Object[]{ConfigItems.FIRE_CRYSTAL, ConfigItems.ORDER_CRYSTAL, new ItemStack(ItemsTC.nuggets, 1, 10), new ItemStack(ItemsTC.nuggets, 1, 10), new ItemStack(ItemsTC.clusters, 1, 0), new ItemStack(ItemsTC.clusters, 1, 1), new ItemStack(ItemsTC.clusters, 1, 6), new ItemStack(ItemsTC.clusters, 1, 7)}));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumicscholars:AdElementalPickaxeAlpha"), new InfusionRecipe("ADELEMENTALPICKA", new ItemStack(ItemsTS.adelementalpickaxealpha), 10, (new AspectList()).add(Aspect.SENSES, 120).add(Aspect.METAL, 60), new ItemStack(ItemsTC.elementalPick, 1, 32767), new Object[]{SENSES_CRYSTAL, SENSES_CRYSTAL, new ItemStack(ItemsTC.morphicResonator), new ItemStack(BlocksTC.arcaneEar)}));
    }
}
