package we.are.project.cipher;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BukkitTests {

    static final ServerMock serverMock = MockBukkit.mock();
    ProjectCipher plugin;
    World world;

    @BeforeAll
    void setup() {
        plugin = MockBukkit.load(ProjectCipher.class);
        world = serverMock.addSimpleWorld("world");
    }

    @AfterAll
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void testDoubleHealth() {
        LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 0,100,0), EntityType.ZOMBIE);
        double health = zombie.getHealth();
        plugin.on(new EntitySpawnEvent(zombie));
        assertEquals(zombie.getHealth(), health * 2);
    }

}
