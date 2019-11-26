package asteroids.objects;

import asteroids.game.Game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

public class Player extends GameObject {
    
    private boolean destroyed;
    
    public Player() {
        super(Game.WIDTH / 2, Game.HEIGHT / 2, new Polygon(-5, -5, 10, 0, -5, 5), new Point2D(0, 0));
        this.destroyed = false;
    }
    
    public void update() {
        if (!destroyed) {
            updatePosition();
        }
    }
    
    public void destroy() {
        this.destroyed = true;
    }
    
    public void rotate(int rotation) {
        if (!destroyed) {
            super.object.setRotate(super.object.getRotate() + rotation);
        }
    }
    
    public void accelerate() {
        double xAccel = 0.05 * Math.cos(Math.toRadians(super.object.getRotate()));
        double yAccel = 0.05 * Math.sin(Math.toRadians(super.object.getRotate()));
        super.velocity = super.velocity.add(xAccel, yAccel);
    }
    
    public Projectile fire() {
        double xVel = 3 * Math.cos(Math.toRadians(super.object.getRotate()));
        double yVel = 3 * Math.sin(Math.toRadians(super.object.getRotate()));
        return new Projectile(super.x, super.y, new Point2D(xVel, yVel));
    }
}
