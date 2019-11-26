/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import asteroids.objects.Asteroid;
import asteroids.objects.Projectile;
import javafx.geometry.Point2D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yakuc
 */
public class ProjectileTest {
    
    public ProjectileTest() {
    }
    
    Projectile projectile;

    @Before
    public void setUp() {
        projectile = new Projectile(0, 0, new Point2D(1, 1));
    }
    
    @Test
    public void projectileSpawnsWhereItIsSupposedToSpawn(){
        assertEquals(new Point2D(0, 0), projectile.getLocation());
    }
    
    @Test
    public void projectileMovesAtRightDirection(){
        projectile.update();
        assertEquals(new Point2D(1, 1), projectile.getLocation());
    }
}
