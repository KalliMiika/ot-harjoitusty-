/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import asteroids.objects.Asteroid;
import java.util.List;
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
        asteroid = new Asteroid(0, 0, 32, 2, 1, new Point2D(1, 1));
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
    
    @Test
    public void asteroidSpawnsNewAsteroidsWhenShattered(){
        List<Asteroid> newAsteroids = asteroid.shatter();
        assertEquals(2, newAsteroids.size());
    }
    
    @Test
    public void asteroidYieldsCorrectAmountOfPoints(){
        int points = 0;
        points += asteroid.getPoints();
        List<Asteroid> newAsteroids = asteroid.shatter();
        points += newAsteroids.get(0).getPoints();
        newAsteroids = newAsteroids.get(0).shatter();
        points += newAsteroids.get(0).getPoints();
        assertEquals(600, points);
    }
    
    @Test
    public void asteroidCanOnlyBeShatteredThreeTimes(){
        Asteroid as = asteroid.shatter().get(0);
        as = as.shatter().get(0);
        assertEquals(0, as.shatter().size());
    }
}
