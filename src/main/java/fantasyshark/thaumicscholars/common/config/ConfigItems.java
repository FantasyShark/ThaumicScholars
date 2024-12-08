package fantasyshark.thaumicscholars.common.config;

import fantasyshark.thaumicscholars.ThaumicScholars;
import fantasyshark.thaumicscholars.common.items.casters.foci.FocusEffectLightning;
import fantasyshark.thaumicscholars.common.items.casters.foci.FocusMediumGolem;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.FocusEngine;

public class ConfigItems {

    public static void init() {
        FocusEngine.registerElement(FocusMediumGolem.class, new ResourceLocation(ThaumicScholars.MODID, "textures/foci/golem.png"), Aspect.MECHANISM.getColor());
        FocusEngine.registerElement(FocusEffectLightning.class, new ResourceLocation(ThaumicScholars.MODID, "textures/foci/golem.png"), Aspect.ENERGY.getColor());

    }
}
