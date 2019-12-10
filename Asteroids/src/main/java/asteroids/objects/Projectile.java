package asteroids.objects;

import asteroids.game.Game;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Projectile extends GameObject {
    
    private int lifetime;
    
    /**
     * Projectile-olion konstruktori
     * 
     * @param x     Projectile-olion aloituspisteen x-koordinaatti
     * @param y     Projectile-olion aloituspisteen y-koordinaatti
     * @param vel   Projectile-olion liikettä kuvaava vektori
     */
    public Projectile(double x, double y, Point2D vel) {
        super(x, y, new Polygon(0, 0, 0, 5, 3, 3), vel);
        lifetime = 300;
    }
    
    /**
     * Metodi joka päivittää Projectile-olion sijaintia ja elinikää
     * @return Palauttaa Projectile-olion elintiän päivityksen jälkeen
     */
    public int update() {
        updatePosition();
        return lifetime--;
    }
}
