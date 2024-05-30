package com.deadcomedian.friend.client.renderer;

import com.deadcomedian.friend.FriendInTheDark;
import com.deadcomedian.friend.client.models.DwellerModel;
import com.deadcomedian.friend.entity.ModModelLayers;
import com.deadcomedian.friend.entity.custom.DwellerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DwellerRenderer  extends MobEntityRenderer<DwellerEntity, DwellerModel<DwellerEntity>> {
    private static final Identifier TEXTURE = new Identifier(FriendInTheDark.MOD_ID, "textures/entity/dweller.png");

    public DwellerRenderer(EntityRendererFactory.Context context) {
        super(context, new DwellerModel<>(context.getPart(ModModelLayers.DWELLER)), 0.6f);
    }

    @Override
    public Identifier getTexture(DwellerEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(DwellerEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {


        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

}
