package com.deadcomedian.friend;

import com.deadcomedian.friend.entity.ModEntities;
import com.deadcomedian.friend.entity.custom.DwellerEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FriendInTheDark implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID ="friend-in-the-dark";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModEntities.registerModEntities();
		FabricDefaultAttributeRegistry.register(ModEntities.DWELLER, DwellerEntity.createDwellerAttributes());

		LOGGER.info("Hello Fabric world!");
	}
}