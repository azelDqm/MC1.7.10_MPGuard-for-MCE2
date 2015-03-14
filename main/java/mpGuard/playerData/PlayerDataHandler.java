package mpGuard.playerData;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import mpGuard.MPGuardMain;
import mpGuard.enums.EnumMPGuard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import shift.mceconomy2.api.MCEconomyAPI;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class PlayerDataHandler {

	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<>();

	@SubscribeEvent
    /*IExtendedEntityPropertiesを登録する処理を呼び出す*/
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            ExtendedPlayerProperties.register((EntityPlayer)event.entity);
        }
    }

    @SubscribeEvent
    /*死亡時に呼ばれるイベント。ここで、IExtendedEntityPropertiesを保存する処理を呼び出す*/
    public void onLivingDeathEvent(LivingDeathEvent event)  {
        if (event.entityLiving instanceof EntityPlayer && !event.entity.worldObj.isRemote) {
        	EntityPlayer ep = (EntityPlayer)event.entityLiving;

        	if(MPGuardMain.conf.cfgLostMode.getInt(1) != 0)
        	{
        		int lostMP = MCEconomyAPI.getPlayerMP(ep);
        		int itemMP = 0;
	        	if(MPGuardMain.conf.cfgLostMode.getInt(1) == EnumMPGuard.ItemLost.Mode())
	        	{
	        		//アイテムロストの場合
	        		itemMP = lostMP;
	        		lostMP = 0;

	        	}else if(MPGuardMain.conf.cfgLostMode.getInt(1) == EnumMPGuard.RandomLost.Mode() ||
	        			 MPGuardMain.conf.cfgLostMode.getInt(1) == EnumMPGuard.RandomItem.Mode())
	        	{
	        		//ランダムロストの場合
	        		Random rand = new Random();
	        		int lostPer = rand.nextInt(MPGuardMain.conf.cfgLostPercent.getInt(50) + 1);
	        		itemMP = lostMP * lostPer / 100;
	        		lostMP = lostMP - itemMP;
	        	}

	        	//ロスト予定分をプレイヤーデータに格納
	        	ExtendedPlayerProperties.get(ep).setPlayerMP(lostMP);

	        	if(itemMP > 0 && (MPGuardMain.conf.cfgLostMode.getInt(1) == EnumMPGuard.ItemLost.Mode() ||
	        					  MPGuardMain.conf.cfgLostMode.getInt(1) == EnumMPGuard.RandomItem.Mode()))
	        	{
	        		//アイテムロストの場合 アイテムを落とす
	                ItemStack fukuro = new ItemStack(MPGuardMain.items.ItemMPFukuro, 1);

	                NBTTagCompound nbt = fukuro.getTagCompound();
	                if(nbt ==null)
	                {
	                	nbt = new NBTTagCompound();
	                	nbt.setInteger("lostMP", itemMP);

	                	fukuro.setTagCompound(nbt);
	                }
					ep.entityDropItem(fukuro, 0.0F);
	        	}
        	}

            NBTTagCompound playerData = new NBTTagCompound();
            (event.entity.getExtendedProperties(ExtendedPlayerProperties.EXT_PROP_NAME)).saveNBTData(playerData);
            storeEntityData(event.entity.getCommandSenderName(), playerData);
        }
    }

    @SubscribeEvent
    /*ワールドに入った時に呼ばれるイベント。ここでIExtendedEntityPropertiesを読み込む処理を呼び出す*/
    public void onEntityJoinWorld(EntityJoinWorldEvent event)  {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
        	EntityPlayer ep = (EntityPlayer)event.entity;

        	if(!ep.isDead && ep.getHealth() > 0)
        	{
	            NBTTagCompound playerData = getEntityData(event.entity.getCommandSenderName());
	            if (playerData != null) {
	                (event.entity.getExtendedProperties(ExtendedPlayerProperties.EXT_PROP_NAME)).loadNBTData(playerData);
	            }
	            ((ExtendedPlayerProperties)(event.entity.getExtendedProperties(ExtendedPlayerProperties.EXT_PROP_NAME))).loadProxyData((EntityPlayer)event.entity);
	            MCEconomyAPI.addPlayerMP(ep, ExtendedPlayerProperties.get(ep).getPlayerMP(), false);
	            ExtendedPlayerProperties.get(ep).setPlayerMP(0);
        	}else
        	{
            	if(MPGuardMain.conf.cfgLostMode.getInt(1) != 0)
            	{
            		int lostMP = MCEconomyAPI.getPlayerMP(ep);
            		int itemMP = 0;
    	        	if(MPGuardMain.conf.cfgLostMode.getInt(1) == EnumMPGuard.ItemLost.Mode())
    	        	{
    	        		//アイテムロストの場合
    	        		itemMP = lostMP;
    	        		lostMP = 0;

    	        	}else if(MPGuardMain.conf.cfgLostMode.getInt(1) == EnumMPGuard.RandomLost.Mode() ||
    	        			 MPGuardMain.conf.cfgLostMode.getInt(1) == EnumMPGuard.RandomItem.Mode())
    	        	{
    	        		//ランダムロストの場合
    	        		Random rand = new Random();
    	        		int lostPer = rand.nextInt(MPGuardMain.conf.cfgLostPercent.getInt(50) + 1);
    	        		itemMP = lostMP * lostPer / 100;
    	        		lostMP = lostMP - itemMP;
    	        	}

    	        	//ロスト予定分をプレイヤーデータに格納
    	        	ExtendedPlayerProperties.get(ep).setPlayerMP(lostMP);
            	}

        		NBTTagCompound playerData = new NBTTagCompound();
                (event.entity.getExtendedProperties(ExtendedPlayerProperties.EXT_PROP_NAME)).saveNBTData(playerData);
                storeEntityData(event.entity.getCommandSenderName(), playerData);
        	}
        }
    }

    @SubscribeEvent
    /*リスポーン時に呼ばれるイベント。Serverとの同期を取る*/
    public void respawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.player.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendTo(new MessagePlayerProperties(event.player), (EntityPlayerMP)event.player);
            MCEconomyAPI.addPlayerMP(event.player, ExtendedPlayerProperties.get(event.player).getPlayerMP(), false);
            ExtendedPlayerProperties.get(event.player).setPlayerMP(0);
        }
    }

    @SubscribeEvent
    /*Dimensionを移動した時に呼ばれるイベント。Serverとの同期を取る*/
    public void changedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.player.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendTo(new MessagePlayerProperties(event.player), (EntityPlayerMP)event.player);
        }
    }

    /*PlayerのIExtendedEntityPropertiesをMapに保存*/
    public static void storeEntityData(String name, NBTTagCompound compound) {
        extendedEntityData.put(name, compound);
    }

    /*PlayerのIExtendedEntityPropertiesをMapから読み込み*/
    public static NBTTagCompound getEntityData(String name) {
        return extendedEntityData.remove(name);
    }

}
