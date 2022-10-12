/*
 * File updated ~ 8 - 10 - 2022 ~ Leaf
 */

package leaf.cosmere.feruchemy.common;

import leaf.cosmere.api.CosmereAPI;
import leaf.cosmere.api.IModModule;
import leaf.cosmere.api.ISpiritwebSubmodule;
import leaf.cosmere.api.Version;
import leaf.cosmere.common.Cosmere;
import leaf.cosmere.feruchemy.common.capabilities.FeruchemySpiritwebSubmodule;
import leaf.cosmere.feruchemy.common.registries.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Feruchemy.MODID)
public class Feruchemy implements IModModule
{
	public static final String MODID = "feruchemy";

	public static Feruchemy instance;

	public final Version versionNumber;

	public Feruchemy()
	{
		Cosmere.addModule(instance = this);

		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		modBus.addListener(this::commonSetup);
		modBus.addListener(this::onConfigLoad);
		FeruchemyItems.ITEMS.register(modBus);
		FeruchemyEffects.EFFECTS.register(modBus);
		FeruchemyAttributes.ATTRIBUTES.register(modBus);
		FeruchemyManifestations.MANIFESTATIONS.register(modBus);
		FeruchemyLootFunctions.LOOT_FUNCTIONS.register(modBus);
		FeruchemyLootModifiers.LOOT_MODIFIERS.register(modBus);

		//Set our version number to match the mods.toml file, which matches the one in our build.gradle
		versionNumber = new Version(ModLoadingContext.get().getActiveContainer());
	}

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(Feruchemy.MODID, path);
	}

	public void commonSetup(FMLCommonSetupEvent event)
	{
		CosmereAPI.logger.info("Cosmere: Feruchemy module Version {} initializing...", versionNumber);
		MinecraftForge.EVENT_BUS.register(this);

	}

	@Override
	public Version getVersion()
	{
		return versionNumber;
	}

	@Override
	public String getName()
	{
		return "Feruchemy";
	}

	@Override
	public ISpiritwebSubmodule makeSubmodule()
	{
		return new FeruchemySpiritwebSubmodule();
	}

	private void onConfigLoad(ModConfigEvent configEvent)
	{
		//Note: We listen to both the initial load and the reload, to make sure that we fix any accidentally
		// cached values from calls before the initial loading
		ModConfig config = configEvent.getConfig();
		//Make sure it is for the same modid as us
		if (config.getModId().equals(MODID))
		{

		}
	}
}