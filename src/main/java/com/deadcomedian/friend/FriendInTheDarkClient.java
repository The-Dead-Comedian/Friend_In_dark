package com.deadcomedian.friend;

import com.deadcomedian.friend.client.models.DwellerModel;
import com.deadcomedian.friend.client.renderer.DwellerRenderer;
import com.deadcomedian.friend.entity.ModEntities;
import com.deadcomedian.friend.entity.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FriendInTheDarkClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(){
        EntityRendererRegistry.register(ModEntities.DWELLER, DwellerRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DWELLER, DwellerModel::getTexturedModelData);

    }
}