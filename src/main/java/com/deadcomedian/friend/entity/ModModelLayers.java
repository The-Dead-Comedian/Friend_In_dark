package com.deadcomedian.friend.entity;

import com.deadcomedian.friend.FriendInTheDark;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {

    public static final EntityModelLayer DWELLER =
            new EntityModelLayer(new Identifier(FriendInTheDark.MOD_ID, "dweller"), "main");
}
