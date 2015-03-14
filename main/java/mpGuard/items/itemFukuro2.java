package mpGuard.items;

import java.util.List;

import mpGuard.MPGuardMain;
import mpGuard.playerData.ExtendedPlayerProperties;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import shift.mceconomy2.api.MCEconomyAPI;

public class itemFukuro2 extends Item{

	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(ExtendedPlayerProperties.get(par3EntityPlayer).getFukuroSetMP() < 1)
		{
			ExtendedPlayerProperties.get(par3EntityPlayer).setFukuroSetMP(1);
		}

		int setValEP = ExtendedPlayerProperties.get(par3EntityPlayer).getFukuroSetMP();

		if(par3EntityPlayer.isSneaking())
		{
			if(setValEP >= MPGuardMain.conf.cfgFukuro2_maxValue.getInt())
			{
				setValEP = 1;
			}else
			{
				setValEP = setValEP + 1;
			}

			if(!par3EntityPlayer.worldObj.isRemote)
			{
				par3EntityPlayer.addChatMessage(new ChatComponentTranslation(
						"mpg.itemMessage.fukuro2.setting.txt",new Object[] {MPGuardMain.enums.getFukuroValueFromId(setValEP).getValue()}));
			}

			ExtendedPlayerProperties.get(par3EntityPlayer).setFukuroSetMP(setValEP);
		}else
		{

			if(MPGuardMain.DEBUG == 1 ||
					MCEconomyAPI.getPlayerMP(par3EntityPlayer) >= MPGuardMain.enums.getFukuroValueFromId(setValEP).getValue())
			{
	            NBTTagCompound nbt = par1ItemStack.getTagCompound();
	            if(nbt ==null)
	            {
	            	nbt = new NBTTagCompound();
	            	nbt.setInteger("settingMP", 0);
	            }

	            int oldMP = nbt.getInteger("settingMP");
	            int newMP = oldMP + MPGuardMain.enums.getFukuroValueFromId(setValEP).getValue();
	            nbt.setInteger("settingMP", newMP);

            	par1ItemStack.setTagCompound(nbt);
            	ItemStack dummy = new ItemStack(par1ItemStack.getItem(), 1);


    			par1ItemStack.setStackDisplayName(dummy.getDisplayName() + " (" + newMP + "MP)");
    			if(!par3EntityPlayer.worldObj.isRemote)
    			{
    				par3EntityPlayer.worldObj.playSoundAtEntity(par3EntityPlayer, "mcebank:player.mpin", 0.5F, 1.0F);
    				par3EntityPlayer.addChatMessage(new ChatComponentTranslation(
    						"mpg.itemMessage.fukuro2.mpin.txt",new Object[] {MPGuardMain.enums.getFukuroValueFromId(setValEP).getValue(), newMP}));
    			}
			}else
			{
				par3EntityPlayer.addChatMessage(new ChatComponentTranslation(
						"mpg.itemMessage.fukuro2.lowmp",new Object[] {}));
			}

			//System.out.println(par1ItemStack.getUnlocalizedName());
		}

		return par1ItemStack;
    }


    @Override
  	 public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
    	p_77624_3_.add("§f" + I18n.format("mpg.itemInfo.fukuro2.1.txt"));
    	p_77624_3_.add("§f" + I18n.format("mpg.itemInfo.fukuro2.2.txt"));
    	p_77624_3_.add("§f" + I18n.format("mpg.itemInfo.fukuro2.3.txt"));
    	p_77624_3_.add("§f" + I18n.format("mpg.itemInfo.fukuro2.4.txt"));
    	p_77624_3_.add("§f" + I18n.format("mpg.itemInfo.fukuro2.5.txt"));
    	p_77624_3_.add("§f" + I18n.format("mpg.itemInfo.fukuro2.6.txt"));
    	p_77624_3_.add("§f" + I18n.format("mpg.itemInfo.fukuro2.7.txt"));
    	p_77624_3_.add("§f" + I18n.format("mpg.itemInfo.fukuro2.8.txt"));

    	super.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
