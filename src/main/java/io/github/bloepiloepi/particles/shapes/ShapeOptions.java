package io.github.bloepiloepi.particles.shapes;

import net.minestom.server.particle.data.ParticleData;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;

//TODO add distance mode (like stretch, continue on other lines, etc.)
public class ShapeOptions {
    private final ParticleData particleData;
    private final LinePattern linePattern;
    private final int particleDistance;
    private final int particleCount;
    private final float particleSpeed;

    public ShapeOptions(@NotNull ParticleData particleData,
                        @NotNull LinePattern linePattern, int particleDistance,
                        int particleCount, float particleSpeed) {
        this.particleData = particleData;
        this.particleCount = particleCount;
        this.linePattern = linePattern;
        this.particleDistance = particleDistance;
        this.particleSpeed = particleSpeed;
    }

    public @NotNull ParticleData getParticleData() {
        return particleData;
    }

    public @NotNull LinePattern.Iterator getPatternIterator() {
        return linePattern.iterator();
    }

    public boolean hasParticleCount() {
        return particleCount != -1;
    }

    public int getParticleDistance() {
        return particleDistance;
    }

    public int getParticleCount() {
        return particleCount;
    }

    public float getParticleSpeed() {
        return particleSpeed;
    }

    public static @NotNull Builder builder(@NotNull ParticleData particle) {
        return new Builder(particle);
    }

    public static class Builder {
        private ParticleData particleData;
        private LinePattern linePattern = LinePattern.empty();
        private int particleDistance = -1;
        private int particleCount = -1;
        private float particleSpeed = 0;

        private Builder(@NotNull ParticleData particleData) {
            this.particleData = particleData;
        }

        public @NotNull Builder particle(@NotNull ParticleData data) {
            this.particleData = data;
            return this;
        }

        public @NotNull Builder linePattern(@NotNull LinePattern linePattern) {
            this.linePattern = linePattern;
            return this;
        }

        public @NotNull Builder particleDistance(int particleDistance) {
            Check.stateCondition(particleCount != -1,
                    "Cannot use particleCount and particleDistance at the same time");
            this.particleDistance = particleDistance;
            return this;
        }

        public @NotNull Builder particleCount(int particleCount) {
            Check.stateCondition(particleDistance != -1,
                    "Cannot use particleCount and particleDistance at the same time");
            this.particleCount = particleCount;
            return this;
        }

        public @NotNull Builder particleSpeed(float particleSpeed) {
            this.particleSpeed = particleSpeed;
            return this;
        }

        public @NotNull ShapeOptions build() {
            if (particleCount == -1 && particleDistance == -1) {
                particleDistance = 1;
            }

            return new ShapeOptions(particleData, linePattern, particleDistance, particleCount, particleSpeed);
        }
    }
}
