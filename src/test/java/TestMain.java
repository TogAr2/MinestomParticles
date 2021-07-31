import io.github.bloepiloepi.particles.shapes.LinePattern;
import io.github.bloepiloepi.particles.shapes.ParticleCircle;
import io.github.bloepiloepi.particles.shapes.ParticleShape;
import io.github.bloepiloepi.particles.shapes.ShapeOptions;
import io.github.bloepiloepi.particles.shapes.builder.CircleBuilder;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.particle.Particle;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.world.DimensionType;

public class TestMain {
	public static void main(String[] args) {
		MinecraftServer server = MinecraftServer.init();
		
		InstanceManager instanceManager = MinecraftServer.getInstanceManager();
		ChunkGeneratorDemo chunkGeneratorDemo = new ChunkGeneratorDemo();
		InstanceContainer instanceContainer = instanceManager.createInstanceContainer(DimensionType.OVERWORLD);
		instanceContainer.enableAutoChunkLoad(true);
		instanceContainer.setChunkGenerator(chunkGeneratorDemo);
		
		MojangAuth.init();
		
		MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerLoginEvent.class, event -> {
			event.setSpawningInstance(instanceContainer);
			event.getPlayer().setRespawnPoint(new Position(0, 41, 0));
		});
		
		server.start("localhost", 25565);
		
		// PARTICLE TEST
		
		//MultiPolygon shape = ParticleShape.cube(new Position(0, 41, 0), 10, 5, 14);
        /*MultiPolygon shape = ParticleShape.multiPolygon()
                .lineStart(new Position(0, 41, 0))
                .lineTo(new Position(-1.5, 43.5, 0))
                .lineTo(new Position(0, 45, 0))
                .lineTo(new Position(1.5, 43.5, 0))

                .lineTo(new Position(0, 41, 0))
                .lineTo(new Position(0, 43.5, -1.5))
                .lineTo(new Position(0, 45, 0))
                .lineTo(new Position(0, 43.5, 1.5))
                .lineTo(new Position(0, 41, 0))

                .jumpTo(new Position(-1.5, 43.5, 0))
                .lineTo(new Position(0, 43.5, -1.5))
                .lineTo(new Position(1.5, 43.5, 0))
                .lineTo(new Position(0, 43.5, 1.5))
                .lineTo(new Position(-1.5, 43.5, 0))

                .build();*/
		//BezierLine shape = ParticleShape.bezier(new Position(0, 41, 0), new Position(10, 45, 5))
		//        .addControlPoint(new Position(-5, 50, 2))
		//        .addControlPoint(new Position(3, 45, -3))
		//        .step(0.02)
		//        .build();
		CircleBuilder circleBuilder = ParticleShape.circle(new Position(0, 45, 0)).radius(3);
		
		ParticleShape shape = ParticleShape.multiPolygon()
				.addShape(circleBuilder.facing(ParticleCircle.Facing.X).build())
				//.addShape(circleBuilder.facing(ParticleCircle.Facing.Y).build())
				//.addShape(circleBuilder.facing(ParticleCircle.Facing.Z).build())
				.build();
		
		var ref = new Object() {
			LinePattern linePattern = LinePattern.of("-              ");
		};
		
		ShapeOptions.Builder shapeOptionsBuilder = ShapeOptions.builder(Particle.FLAME).particleCount(60);
		
		MinecraftServer.getSchedulerManager().buildTask(() -> {
			ref.linePattern = ref.linePattern.withOffset(1);
			
			shape.draw(shapeOptionsBuilder.linePattern(ref.linePattern).build(), instanceContainer, new Position(0, 0, 0));
		}).repeat(3, TimeUnit.TICK).schedule();
	}
}
