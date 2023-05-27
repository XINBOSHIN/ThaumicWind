package xinboshin.thaumicwind.common.config;

import xinboshin.thaumicwind.common.Magistics;
import xinboshin.thaumicwind.common.config.base.ConfigBase;
import net.minecraftforge.common.config.Config;

@Config(modid = ThaumicWidn.ID, name = ThaumicWind.NAME + "/General")
public class ConfigGeneral extends ConfigBase {

	// only for general properties, like gui styles, difficulty settings, etc.

	@Config.LangKey("config.general.blight.severity")
	@Config.Comment("0 - n00b mode; 1 - TC3-4; 2 - TC2; 3 - aids")
	public static short BLIGHT_SEVERITY = 1;
}
