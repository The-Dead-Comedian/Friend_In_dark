package com.deadcomedian.friend.entity;

import com.deadcomedian.friend.FriendInTheDark;
import com.deadcomedian.friend.entity.custom.DwellerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<DwellerEntity> DWELLER = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(FriendInTheDark.MOD_ID, "dweller"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DwellerEntity::new)
                    .dimensions(EntityDimensions.fixed(1.5f, 2.7f)).build());



    public static void registerModEntities() {
        FriendInTheDark.LOGGER.info("Registering Entities for " + FriendInTheDark.MOD_ID);
    }
}

