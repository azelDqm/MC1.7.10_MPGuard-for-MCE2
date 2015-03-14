package mpGuard;

import mpGuard.command.MPGCommand;
import mpGuard.enums.FuncEnum;
import mpGuard.handler.EntityEventHandler;
import mpGuard.items.MPGItem;
import mpGuard.items.MPGRecipes;
import mpGuard.playerData.PacketHandler;
import mpGuard.playerData.PlayerDataHandler;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "MPGuard", name = "MPGuardForMCEconomy2", version = "1.1.1",  useMetadata = true, dependencies = "after:mceconomy2")

public class MPGuardMain {

    @SidedProxy(clientSide = "mpGuard.ClientProxy", serverSide = "mpGuard.CommonProxy")
    public static CommonProxy proxy;

    @Instance("MPGuard")
    public static MPGuardMain instance;

    public static int DEBUG = 0;

    public static MPGuardConfig conf;
    public static MPGItem items;

    public static FuncEnum enums;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	PacketHandler.init();

    	conf = new MPGuardConfig();
    	items = new MPGItem();
    }

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		instance = this;

		MinecraftForge.EVENT_BUS.register(new PlayerDataHandler());
		MinecraftForge.EVENT_BUS.register(new EntityEventHandler());

		FMLCommonHandler.instance().bus().register(new PlayerDataHandler());
	}

	@Mod.EventHandler
	public void setver(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new MPGCommand());
	}

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	enums = new FuncEnum();
		new MPGRecipes();
    }
}