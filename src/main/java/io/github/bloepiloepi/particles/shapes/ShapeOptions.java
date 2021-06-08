package io.github.bloepiloepi.particles.shapes;

import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;
import net.minestom.server.utils.binary.BinaryWriter;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

//TODO add distance mode (like stretch, continue on other lines, etc.)
public class ShapeOptions {
    private final Particle particle;
    private final boolean visibleFromDistance;
    private final float particleSpeed;
    private final Consumer<BinaryWriter> dataWriter;

    private final LinePattern linePattern;
    private final int particleDistance;
    private final int particleCount;

    public ShapeOptions(@NotNull Particle particle, boolean visibleFromDistance, float particleSpeed,
                        @Nullable Consumer<BinaryWriter> dataWriter, @NotNull LinePattern linePattern,
                        int particleDistance, int particleCount) {
        this.particle = particle;
        this.visibleFromDistance = visibleFromDistance;
        this.particleSpeed = particleSpeed;
        this.dataWriter = dataWriter;

        this.linePattern = linePattern;
        this.particleCount = particleCount;
        this.particleDistance = particleDistance;
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

    public @NotNull ParticlePacket createPacket(double x, double y, double z) {
        return ParticleCreator.createParticlePacket(particle, visibleFromDistance, x, y, z,
                0, 0, 0, particleSpeed, 1, dataWriter);
    }

    public static @NotNull Builder builder(@NotNull Particle particle) {
        return new Builder(particle);
    }

    public static @NotNull ShapeOptions of(@NotNull Particle particle) {
        return builder(particle).build();
    }

    public static class Builder {
        private Particle particle;
        private boolean visibleFromDistance;
        private float particleSpeed = 0;
        private Consumer<BinaryWriter> dataWriter;

        private LinePattern linePattern = LinePattern.empty();
        private int particleDistance = -1;
        private int particleCount = -1;

        private Builder(@NotNull Particle particle) {
            this.particle = particle;
        }

        public @NotNull Builder particle(@NotNull Particle data) {
            this.particle = data;
            return this;
        }

        public @NotNull Builder visibleFromDistance(boolean visibleFromDistance) {
            this.visibleFromDistance = visibleFromDistance;
            return this;
        }

        public @NotNull Builder particleSpeed(float particleSpeed) {
            this.particleSpeed = particleSpeed;
            return this;
        }

        public @NotNull Builder dataWriter(@Nullable Consumer<BinaryWriter> dataWriter) {
            this.dataWriter = dataWriter;
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

        public @NotNull ShapeOptions build() {
            if (particleCount == -1 && particleDistance == -1) {
                particleDistance = 1;
            }

            return new ShapeOptions(particle, visibleFromDistance, particleSpeed, dataWriter, linePattern, particleDistance, particleCount);
        }
    }
}
