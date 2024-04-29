package fantasyshark.thaumicscholars.common.potion;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class PotionRegistryHandler {

    public static final Potion POTION_TURBULENCE = new PotionTurbulence();

    @SubscribeEvent
    public static void onPotionRegistry(RegistryEvent.Register<Potion> event) {

        IForgeRegistry<Potion> registry = event.getRegistry();
        registry.register(POTION_TURBULENCE);
    }
}
