package io.github.bloepiloepi.particles;

import io.github.bloepiloepi.particles.shapes.ShapeOptions;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.utils.PacketUtils;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ParticleUtils {
    public static void drawParticle(Collection<Player> players, @NotNull Position position,
                                    @NotNull ShapeOptions options) {
        ParticlePacket packet = options.createPacket(position.getX(), position.getY(), position.getZ());
        PacketUtils.sendGroupedPacket(players, packet);
    }

    public static Position bezier(@NotNull Position[] points, double time) {
        double x = 0;
        double y = 0;
        double z = 0;

        int order = points.length - 1;
        for (int i = 0; i <= order; i++) {
            double preCompute = binomial(order, i) * Math.pow((1 - time), (order - i)) * Math.pow(time, i);

            x = x + (preCompute * points[i].getX());
            y = y + (preCompute * points[i].getY());
            z = z + (preCompute * points[i].getZ());
        }

        return new Position(x, y, z);
    }

    public static int binomial(int n, int k) {
        if (k > n - k) {
            k = n - k;
        }

        int binomial = 1;
        for (int i = 1; i <= k; i++) {
            binomial = binomial * (n + 1 - i) / i;
        }
        return binomial;
    }
}
