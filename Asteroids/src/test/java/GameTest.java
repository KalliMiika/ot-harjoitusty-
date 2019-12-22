

import asteroids.game.Game;
import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static sun.audio.AudioPlayer.player;

public class GameTest {
    
    public GameTest() {
    }
    
    Game dummyGame;
    
    @Before
    public void setUp() {
        dummyGame = new Game(true);
    }
    
    @Test
    public void nothingHappensWhenGameIsOver(){
        dummyGame.getPlayer().accelerate();
        dummyGame.update();
        Point2D playerLocation = dummyGame.getPlayer().getLocation();
        dummyGame.dummyEndGame();
        dummyGame.update();
        dummyGame.update();
        assertEquals(playerLocation, dummyGame.getPlayer().getLocation());
    }
}
