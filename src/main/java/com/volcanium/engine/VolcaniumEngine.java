package com.volcanium.engine;

import net.minecraft.entity.Entity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
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
        
        // Fixed: Use getBoundingBox() instead of getVisibilityBoundingBox()
        Box box = entity.getBoundingBox();
        if (box == null) return true;

        return frustum.testAab(
            (float) box.minX,
            (float) box.minY,
            (float) box.minZ,
            (float) box.maxX,
            (float) box.maxY,
            (float) box.maxZ
        );
    }

    public boolean shouldRender(BlockEntity blockEntity) {
        if (blockEntity == null) return true;
        BlockPos pos = blockEntity.getPos();
        if (pos == null) return true;
        
        return frustum.testPoint((float) pos.getX(), (float) pos.getY(), (float) pos.getZ());
    }

    public boolean shouldRender(Particle particle, BlockPos pos) {
        if (particle == null || pos == null) return true;
        
        return frustum.testPoint((float) pos.getX(), (float) pos.getY(), (float) pos.getZ());
    }
}
