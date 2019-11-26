package asteroids.objects;

import asteroids.game.Game;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Projectile extends GameObject {
    
    public Projectile(double x, double y, Point2D vel) {
        super(x, y, new Polygon(0, 0, 0, 5, 3, 3), vel);
    }
    
    public void update() {
        updatePosition();
    }
}