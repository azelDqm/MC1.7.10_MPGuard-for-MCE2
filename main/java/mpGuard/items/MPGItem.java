package mpGuard.items;

import mpGuard.MPGuardMain;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class MPGItem {

    public static Item ItemMPFukuro;
    public static Item ItemMPFukuro2;


	public MPGItem()
	{
    	ItemMPFukuro2 = new itemFukuro2().setCreativeTab(CreativeTabs.tabMisc)
				 .setUnlocalizedName("mceBank.MPFukuro2")
				 .setTextureName("mcebank:MPFukuro")
				 .setMaxStackSize(1);
    	GameRegistry.registerItem(ItemMPFukuro2, "itemMPFukuro2");

    	//死亡時に落とすアイテム登録
    	if(MPGuardMain.conf.cfgLostMode.getInt() != 0)
    	{
    		//System.out.println("ロスト用アイテム登録完了");
	    	ItemMPFukuro = new Item().setCreativeTab(CreativeTabs.tabMisc)
	    							 .setUnlocalizedName("mceBank.MPFukuro")
	    							 .setTextureName("mcebank:MPFukuro")
	    							 .setMaxStackSize(1);
	    	GameRegistry.registerItem(ItemMPFukuro, "itemMPFukuro");
    	}
	}
}
