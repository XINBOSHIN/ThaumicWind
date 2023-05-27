package xinboshin.thaumicwind.common.config.base;

import xinboshin.thaumicwind.common.Magistics;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigBase {

	@EventBusSubscriber(modid = ThaumicWind.ID)
	protected static class ConfigHandler {

		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Magistics.ID)) {
				ConfigManager.load(Magistics.ID, Config.Type.INSTANCE);
			}
		}
	}
}
