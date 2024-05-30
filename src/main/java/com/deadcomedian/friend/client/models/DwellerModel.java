// Made with Blockbench 4.10.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.deadcomedian.friend.client.models;

import com.deadcomedian.friend.client.animations.ModAnimations;
import com.deadcomedian.friend.entity.custom.DwellerEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class DwellerModel <T extends DwellerEntity> extends SinglePartEntityModel<T> {
	private final ModelPart Dame;

	private final ModelPart Head;

	public DwellerModel(ModelPart root) {
		this.Dame = root.getChild("Dame");


		this.Head = root.getChild("Dame").getChild("Body").getChild("UpperBody").getChild("Neck").getChild("Neck2").getChild("Head");

	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Dame = modelPartData.addChild("Dame", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData Body = Dame.addChild("Body", ModelPartBuilder.create().uv(0, 25).cuboid(-5.0F, -2.5F, -3.5F, 10.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -17.5F, 0.5F));

		ModelPartData UpperBody = Body.addChild("UpperBody", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -15.5F, -4.5F, 12.0F, 16.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData RightArm = UpperBody.addChild("RightArm", ModelPartBuilder.create().uv(40, 42).cuboid(-2.0F, -1.4023F, -2.1166F, 4.0F, 13.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.25F, -9.9924F, 0.5868F));

		ModelPartData LowerRightArm = RightArm.addChild("LowerRightArm", ModelPartBuilder.create().uv(58, 28).mirrored().cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 13.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 11.5977F, -0.1166F));

		ModelPartData LeftArm = UpperBody.addChild("LeftArm", ModelPartBuilder.create().uv(40, 42).cuboid(-2.0F, -3.4304F, -1.9879F, 4.0F, 13.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(7.75F, -10.9235F, -1.8777F));

		ModelPartData LowerRightArm2 = LeftArm.addChild("LowerRightArm2", ModelPartBuilder.create().uv(58, 28).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 13.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 9.5696F, 0.0121F));

		ModelPartData Neck = UpperBody.addChild("Neck", ModelPartBuilder.create().uv(42, 0).cuboid(-3.0F, -6.75F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0174F, -14.5715F, -1.7815F));

		ModelPartData Neck2 = Neck.addChild("Neck2", ModelPartBuilder.create().uv(51, 54).cuboid(-2.5F, -8.0F, 0.0F, 5.0F, 8.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.75F, -2.5F));

		ModelPartData Head = Neck2.addChild("Head", ModelPartBuilder.create().uv(26, 29).cuboid(-4.0F, -6.0F, -8.0F, 8.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 4.0F));

		ModelPartData Jaw = Head.addChild("Jaw", ModelPartBuilder.create().uv(34, 17).cuboid(-4.0F, -1.0F, -8.0F, 8.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData RightLeg = Body.addChild("RightLeg", ModelPartBuilder.create().uv(0, 37).cuboid(-3.0F, 0.0F, -2.0F, 5.0F, 15.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.4F, 2.5F, -0.5F));

		ModelPartData LeftLeg = Body.addChild("LeftLeg", ModelPartBuilder.create().uv(0, 37).mirrored().cuboid(-1.0F, 0.0F, -2.0F, 5.0F, 15.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(1.4F, 2.5F, -0.5F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		Dame.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return Dame;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(headYaw, headPitch);

		this.animateMovement(ModAnimations.WALK, limbAngle, limbDistance, 2f, 2.5f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.IDLE, animationProgress, 1f);
		this.updateAnimation(entity.attackAnimationState, ModAnimations.ATTACK, animationProgress, 1f);
		//this.updateAnimation(entity.griddyAnimationState, ModAnimations.GRIDDY, animationProgress, 1f);
		//this.updateAnimation(entity.gangamAnimationState, ModAnimations.GANGAM, animationProgress, 1f);
		//this.updateAnimation(entity.dameAnimationState, ModAnimations.DAME, animationProgress, 1f);


	}
	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.Head.yaw = headYaw * 0.017453292F;
		this.Head.pitch = headPitch * 0.017453292F;
	}

}