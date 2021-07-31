package io.github.bloepiloepi.particles.shapes;

import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public abstract class ParticleIterator<T extends ParticleShape> {
    protected final T shape;
    protected final ShapeOptions options;

    protected ParticleIterator(T shape, @NotNull ShapeOptions options) {
        this.shape = shape;
        this.options = options;
    }

    public void draw(@NotNull Instance instance, @NotNull Position start) {
        draw(instance.getPlayers(), start, options.getPatternIterator());
    }

    public void draw(@NotNull Collection<Player> players, @NotNull Position start) {
        draw(players, start, options.getPatternIterator());
    }

    public abstract void draw(@NotNull Collection<Player> players, @NotNull Position start, @NotNull LinePattern.Iterator pattern);
}
