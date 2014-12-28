package mpGuard.handler;

import mpGuard.MPGuardMain;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import shift.mceconomy2.api.MCEconomyAPI;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEventHandler {

	@SubscribeEvent
	public void onEntityItemPickupEvent(EntityItemPickupEvent event) {

		if(event.item.getEntityItem() != null && event.item.getEntityItem().getItem() == MPGuardMain.ItemMPFukuro)
		{
			ItemStack mpFukuro = event.item.getEntityItem();

	    	NBTTagCompound nbt = mpFukuro.getTagCompound();
	    	if(nbt == null)
	    	{
	        	nbt = new NBTTagCompound();
	        	nbt.setInteger("lostMP", 0);
	    	}

	    	MCEconomyAPI.addPlayerMP(event.entityPlayer, nbt.getInteger("lostMP"), false);
			//System.out.println("DEBUGDEBUG!!");

			event.item.setDead();
			event.setCanceled(true);
		}
	}
}
