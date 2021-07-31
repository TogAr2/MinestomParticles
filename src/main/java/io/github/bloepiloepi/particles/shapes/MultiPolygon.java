package io.github.bloepiloepi.particles.shapes;

import net.minestom.server.entity.Player;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

//TODO better name
public class MultiPolygon extends ParticleShape {
    private final ParticleShape[] shapes;

    public MultiPolygon(@NotNull ParticleShape[] shapes) {
        this.shapes = shapes;
    }

    @Override
    public @NotNull ParticleIterator<?> iterator(@NotNull ShapeOptions options) {
        return new MultiPolygonIterator(this, options);
    }

    public static class MultiPolygonIterator extends ParticleIterator<MultiPolygon> implements Iterator<ParticleShape> {
        private int index = 0;

        protected MultiPolygonIterator(MultiPolygon shape, ShapeOptions options) {
            super(shape, options);
        }

        @Override
        public boolean hasNext() {
            return index < shape.shapes.length;
        }

        @Override
        public ParticleShape next() {
            ParticleShape result = shape.shapes[index];

            index++;

            return result;
        }

        @Override
        public void draw(@NotNull Collection<Player> players, @NotNull Position start, @NotNull LinePattern.Iterator pattern) {
            while (hasNext()) {
                ParticleShape shape = next();
                shape.iterator(options).draw(players, start, pattern.reset());
            }
        }
    }

    @Override
    public String toString() {
        return "MultiPolygon{" +
                "shapes=" + Arrays.toString(shapes) +
                '}';
    }
}
