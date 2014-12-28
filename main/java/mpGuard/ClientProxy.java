package mpGuard;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy  extends CommonProxy{

	@Override
	public File getDir()
	{
		return Minecraft.getMinecraft().mcDataDir;
	}

    @Override
    public EntityPlayer getEntityPlayerInstance() {
    	return Minecraft.getMinecraft().thePlayer;
    }

}
