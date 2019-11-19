/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import asteroids.objects.Asteroid;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author yakuc
 */
public class AsteroidTest {
    
    public AsteroidTest() {
    }
    
    Asteroid asteroid;

    @Before
    public void setUp() {
        asteroid = new Asteroid(0, 0, 32, 1, new Point2D(1, 1));
    }
    
    @Test
    public void asteroidSpawnsWhereItIsSupposedToSpawn(){
        assertEquals(new Point2D(0, 0), asteroid.getLocation());
    }
    
    @Test
    public void asteroidMovesAtRightDirection(){
        asteroid.update();
        assertEquals(new Point2D(1, 1), asteroid.getLocation());
    }
}
