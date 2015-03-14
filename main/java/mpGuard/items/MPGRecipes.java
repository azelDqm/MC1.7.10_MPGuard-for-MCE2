package mpGuard.items;

import mpGuard.MPGuardMain;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class MPGRecipes {

	public MPGRecipes()
	{
        GameRegistry.addRecipe(new ItemStack(MPGuardMain.items.ItemMPFukuro2 , 1), new Object[]
                {
                    "BAB",
                    "BBB",
                    'A', Items.bowl, 'B', Items.wheat
                });
	}
}
