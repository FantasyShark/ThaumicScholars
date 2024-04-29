package fantasyshark.thaumicscholars.common.entity;

import fantasyshark.thaumicscholars.ThaumicScholars;
import fantasyshark.thaumicscholars.common.entity.foci.EntitySpellGolem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.Thaumcraft;

@Mod.EventBusSubscriber
public class EntityRegistryHandler {

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        int id = 0;
        event.getRegistry().register(EntityEntryBuilder.create().entity(EntitySpellGolem.class).id(
                new ResourceLocation(ThaumicScholars.MODID, "spell_golem"), id++).name(
                ThaumicScholars.MODID + ".spell_golem").tracker(64, 3, false).build());
    }

}
