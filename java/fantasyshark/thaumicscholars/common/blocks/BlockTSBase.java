package fantasyshark.thaumicscholars.common.blocks;

import fantasyshark.thaumicscholars.common.items.ItemRegistryHandler;
import fantasyshark.thaumicscholars.common.items.ItemsTS;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTSBase extends Block {
    public BlockTSBase(Material material, String name) {
        super(material);
        this.setUnlocalizedName(name);
        this.setRegistryName("thaumcraft", name);
        this.setCreativeTab(ItemsTS.TABTS);
        this.setResistance(2.0F);
        this.setHardness(1.5F);
    }
}
