package mpGuard;

import mpGuard.handler.EntityEventHandler;
import mpGuard.playerData.PacketHandler;
import mpGuard.playerData.PlayerDataHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "MPGuard", name = "MPGuardForMCEconomy2", version = "1.0.1",  useMetadata = true, dependencies = "after:mceconomy2")

public class MPGuardMain {

    @SidedProxy(clientSide = "mpGuard.ClientProxy", serverSide = "mpGuard.CommonProxy")
    public static CommonProxy proxy;

    @Instance("MPGuard")
    public static MPGuardMain instance;

    public static Item ItemMPFukuro;
    public static MPGuardConfig conf;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	PacketHandler.init();

    	conf = new MPGuardConfig();
    	//Loader.isModLoaded("DQMIIINext");

    	//死亡時に落とすアイテム登録
    	if(conf.cfgLostMode.getInt() != 0)
    	{
    		//System.out.println("ロスト用アイテム登録完了");
	    	ItemMPFukuro = new Item().setCreativeTab(CreativeTabs.tabMisc)
	    							 .setUnlocalizedName("mceBank.MPFukuro")
	    							 .setTextureName("mcebank:MPFukuro")
	    							 .setMaxStackSize(1);
	    	GameRegistry.registerItem(ItemMPFukuro, "itemMPFukuro");
    	}
    }

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		instance = this;

		MinecraftForge.EVENT_BUS.register(new PlayerDataHandler());
		MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
		FMLCommonHandler.instance().bus().register(new PlayerDataHandler());
	}

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}