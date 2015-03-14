package mpGuard;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class MPGuardConfig {

	public static Property cfgLostMode;
	public static Property cfgLostPercent;
	public static Property cfgMoneyRate;

	public static Property cfgComPerm_SEND;
	public static Property cfgComPerm_ADD;
	public static Property cfgComPerm_REMOVE;
	public static Property cfgComPerm_SET;
	public static Property cfgComPerm_LIST;

	public static Property cfgFukuro2_enable;
	public static Property cfgFukuro2_maxValue;

	public static Configuration cfgMain;

	public MPGuardConfig()
	{
		cfgMain = new Configuration(new File(MPGuardMain.proxy.getDir(), "config/MPGuard.cfg"));

		getConfigMain(cfgMain);
	}

	public void getConfigMain(Configuration config)
	{
		config.load();
		config.setCategoryComment("MCEBankMain", "MCEBank main settings");
		cfgLostMode = config.get("MCEBankMain","LostMode", 1 ,"mode 0:AllLost 1:NotLost 2:change item and lost 3:random value lost 4:random value and item");
		cfgLostPercent = config.get("MCEBankMain","MaxLostPercent", 50 ,"LostMode 3 or 4, lost value 0% to this setting Valie");

		config.setCategoryComment("MCEBankCommand", "MCEBank command permission settings");
		cfgComPerm_SEND = config.get("MCEBankCommand","MPG_SEND_PERMISSION", 1 ,"0:not use  1:ALL player  2:OP only");
		cfgComPerm_ADD = config.get("MCEBankCommand","MPG_ADD_PERMISSION", 0 ,"0:not use  1:ALL player  2:OP only");
		cfgComPerm_REMOVE = config.get("MCEBankCommand","MPG_REMOVE_PERMISSION", 0 ,"0:not use  1:ALL player  2:OP only");
		cfgComPerm_SET = config.get("MCEBankCommand","MPG_SET_PERMISSION", 0 ,"0:not use  1:ALL player  2:OP only");
		cfgComPerm_LIST = config.get("MCEBankCommand","MPG_LIST_PERMISSION", 2 ,"0:not use  1:ALL player  2:OP only");

		config.setCategoryComment("MCEBankMakeMPfukuro", "MCEBank Fukuro2 setting");
		cfgFukuro2_enable = config.get("MCEBankMakeMPfukuro","enable", 1 ,"0:not use  1:use");
		cfgFukuro2_maxValue = config.get("MCEBankMakeMPfukuro","MPset MAX VALUE", 6 ,"1: 1MP 2:10MP 3:100MP 4:1,000MP 5:10,000MP 6:100,000MP 7:1,000,000MP 8:10,000.000MP");

		config.save();
	}
}
