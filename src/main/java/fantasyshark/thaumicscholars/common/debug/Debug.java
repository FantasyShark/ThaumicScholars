package fantasyshark.thaumicscholars.common.debug;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class Debug {

    public Debug(){

    }

    public static void chat(EntityLivingBase player, String string) {

        TextComponentString text = new TextComponentString(string);
        player.sendMessage(text);
    }
}
