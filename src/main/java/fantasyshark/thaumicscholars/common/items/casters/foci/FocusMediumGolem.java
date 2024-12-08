package fantasyshark.thaumicscholars.common.items.casters.foci;

import fantasyshark.thaumicscholars.ThaumicScholars;
import fantasyshark.thaumicscholars.common.entity.foci.EntitySpellGolem;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.FocusMedium;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.entities.monster.EntitySpellBat;

public class FocusMediumGolem extends FocusMedium {
    public FocusMediumGolem() {
    }

    public String getResearch()  {
        return "UNLOCKSCHOLARS";
    }

    public String getKey() {
        return ThaumicScholars.MODID + ".GOLEM";
    }

    public int getComplexity() {
        return 2;
    }

    public EnumSupplyType[] willSupply() {
        return new EnumSupplyType[]{EnumSupplyType.TARGET};
    }

    public Aspect getAspect() {
        return Aspect.MECHANISM;
    }

    public boolean execute(Trajectory trajectory) {
        EntitySpellGolem golem = new EntitySpellGolem(this.getRemainingPackage(), this.getSettingValue("target") == 1);
        golem.setPosition(trajectory.source.x, trajectory.source.y, trajectory.source.z);
        return this.getPackage().getCaster().world.spawnEntity(golem);
    }

    public boolean hasIntermediary() {
        return true;
    }

    public float getPowerMultiplier() {
        return 0.5F;
    }
}
