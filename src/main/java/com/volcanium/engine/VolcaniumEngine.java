package com.volcanium.engine;

import net.minecraft.entity.Entity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.BlockPos;
import org.joml.Matrix4f;
import org.joml.FrustumIntersection;

public class VolcaniumEngine {
    public static final VolcaniumEngine INSTANCE = new VolcaniumEngine();
    private final FrustumIntersection frustum = new FrustumIntersection();

    private VolcaniumEngine() {}

    public void updateFrustum(Matrix4f combinedMatrix) {
        frustum.set(combinedMatrix);
    }

    public boolean shouldRender(Entity entity) {
        if (entity == null) return true;
        // Check if the entity's hitbox intersects with the phone screen's view field
        return frustum.testAab(
            (float) entity.getVisibilityBoundingBox().minX,
            (float) entity.getVisibilityBoundingBox().minY,
            (float) entity.getVisibilityBoundingBox().minZ,
            (float) entity.getVisibilityBoundingBox().maxX,
            (float) entity.getVisibilityBoundingBox().maxY,
            (float) entity.getVisibilityBoundingBox().maxZ
        );
    }

    public boolean shouldRender(BlockEntity blockEntity) {
        if (blockEntity == null) return true;
        BlockPos pos = blockEntity.getPos();
        // Check if the 1x1x1 block tile entity boundaries are inside the screen field
        return frustum.testAab(
            pos.getX(), pos.getY(), pos.getZ(), 
            pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1
        );
    }

    public boolean shouldRender(Particle particle) {
        if (particle == null) return true;
        // Fast single-point check for individual particle effects to reduce micro-stutters
        return frustum.testPoint(
            (float) particle.getBoundingBox().minX,
            (float) particle.getBoundingBox().minY,
            (float) particle.getBoundingBox().minZ
        );
    }
}

