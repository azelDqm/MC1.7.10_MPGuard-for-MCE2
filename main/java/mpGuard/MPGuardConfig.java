package mpGuard;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class MPGuardConfig {

	public static Property cfgLostMode;
	public static Property cfgLostPercent;
	public static Property cfgMoneyRate;

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

		config.save();
	}
}
