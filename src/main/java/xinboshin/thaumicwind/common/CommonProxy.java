package xinboshin.thaumicwind.common;

import xinboshin.thaumicwind.api.back.IProxy;
import xinboshin.thaumicwind.api.front.MagisticsApi;
import xinboshin.thaumicwind.api.research.ResearchCategory;
import xinboshin.thaumicwind.api.research.ResearchEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {}

	@Override
	public void init(FMLInitializationEvent event) {}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		ResearchCategory info = new ResearchCategory("thaumicwind.research_category.info", new ResourceLocation(ThaumicWind.ID, "textures/gui/research/r_thaumaturgy.png"), new ResourceLocation(ThaumicWind.ID, "textures/gui/research/bg.png"));
		ResearchEntry test = new ResearchEntry("thaumicwind.research_entry.test", info, 4, 4, new ResourceLocation(ThaumicWind.ID, "textures/gui/research/r_crucible.png"));
		ThaumicWindApi.registerResearch(info);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
