
import asteroids.game.Game;
import asteroids.objects.Player;
import javafx.geometry.Point2D;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    
    public PlayerTest() {
    }
    Player player;

    @Before
    public void setUp() {
        player = new Player();
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
}
