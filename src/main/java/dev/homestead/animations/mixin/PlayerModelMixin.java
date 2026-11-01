package dev.homestead.animations.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Additive rotations on vanilla player parts. We do not replace the renderer,
 * armor layers, Trinkets, Accessories, or backpack renderers.
 */
@Mixin(PlayerEntityModel.class)
public abstract class PlayerModelMixin<T extends LivingEntity> extends BipedEntityModel<T> {
    @Shadow public ModelPart leftSleeve;
    @Shadow public ModelPart rightSleeve;
    @Shadow public ModelPart leftPants;
    @Shadow public ModelPart rightPants;
    @Shadow public ModelPart jacket;

    protected PlayerModelMixin(ModelPart root) { super(root); }

    @Inject(method = "setAngles", at = @At("TAIL"))
    private void homestead$animate(T entity, float limbAngle, float limbDistance,
                                   float animationProgress, float headYaw, float headPitch,
                                   CallbackInfo ci) {
        if (entity.isSpectator()) return;
        float motion = MathHelper.clamp(limbDistance, 0.0F, 1.0F);
        float cycle = limbAngle * 0.6662F;
        float breathing = MathHelper.sin(animationProgress * 0.075F) * 0.018F;
        // Subtle locomotion lean, counter-rotation, and idle breathing.
        body.pitch += motion * 0.045F + breathing;
        body.roll += MathHelper.sin(cycle) * motion * 0.025F;
        head.pitch -= motion * 0.015F;
        rightArm.roll += MathHelper.sin(cycle) * motion * 0.035F;
        leftArm.roll -= MathHelper.sin(cycle) * motion * 0.035F;
        if (!entity.isOnGround() && !entity.isFallFlying() && !entity.isSwimming()) {
            rightLeg.pitch -= 0.08F;
            leftLeg.pitch += 0.08F;
        }
        // Keep skin overlays aligned with the adjusted base parts.
        leftSleeve.copyTransform(leftArm);
        rightSleeve.copyTransform(rightArm);
        leftPants.copyTransform(leftLeg);
        rightPants.copyTransform(rightLeg);
        jacket.copyTransform(body);
    }
}
