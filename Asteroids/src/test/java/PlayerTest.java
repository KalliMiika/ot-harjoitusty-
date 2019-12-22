
import asteroids.game.Game;
import asteroids.objects.Player;
import asteroids.objects.Projectile;
import javafx.geometry.Point2D;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    
    public PlayerTest() {
    }
    
    Game dummyGame;
    Player player;

    @Before
    public void setUp() {
        dummyGame = new Game(true);
        player = dummyGame.getPlayer();
    }
   
    @Test
    public void playerSpawnsWhereItIsSupposedToSpawn(){
        assertEquals(new Point2D(Game.WIDTH/2, Game.HEIGHT/2), player.getLocation());
    }
    
    @Test
    public void playerRotatesCorrectly(){
        double startingRotation = player.getObject().getRotate();
        player.rotate(15);
        assertEquals(startingRotation+15, player.getObject().getRotate(), 0.0);
    }
    
    @Test
    public void playerAcceleratesInTheRightDirection(){
        double xVel = 0.05 * Math.cos(Math.toRadians(player.getObject().getRotate()));
        double yVel = 0.05 * Math.sin(Math.toRadians(player.getObject().getRotate()));
        Point2D playerLocation = player.getLocation().add(new Point2D(xVel, yVel));
        player.accelerate();
        player.update();
        assertEquals(playerLocation, player.getLocation());
    }
    
    @Test
    public void destroyedPlayerCannotRotate(){
        double rotation = player.getObject().getRotate();
        player.destroy();
        player.rotate(10);
        assertEquals(rotation, player.getObject().getRotate(), 0);
    }
    
    @Test
    public void destroyedPlayerCannotMove(){
        Point2D playerLocation = player.getLocation();
        player.destroy();
        player.accelerate();
        player.update();
        assertEquals(playerLocation, player.getLocation());
    }
    
    @Test
    public void projectileIsCreatedWhenPlayerFires(){
        player.fireBlanks();
        assertEquals(1, dummyGame.getProjectiles().size());
    }
}
