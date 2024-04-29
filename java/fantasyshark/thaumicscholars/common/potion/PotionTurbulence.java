package fantasyshark.thaumicscholars.common.potion;

import fantasyshark.thaumicscholars.ThaumicScholars;
import net.minecraft.potion.Potion;

public class PotionTurbulence extends Potion {

    public PotionTurbulence () {

        super(true, 0x51255255);
        this.setRegistryName(ThaumicScholars.MODID + ":turbulence");
        this.setPotionName("effect." + ThaumicScholars.MODID + ".turbulence");
    }
}
