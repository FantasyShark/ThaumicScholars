package fantasyshark.thaumicscholars.common.config;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;

public class ConfigResearch {

    public ConfigResearch () {

    }

    public static void init() {
        ResearchCategories.registerCategory("THAUMICSCHOLARS", null, new AspectList(), new ResourceLocation("thaumicscholars","textures/gui/cat_auromancy.png"), new ResourceLocation("thaumicscholars","textures/gui/gui_research_back_1.jpg"));

        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumicscholars", "research/tool.json"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumicscholars", "research/base.json"));
    }
}
