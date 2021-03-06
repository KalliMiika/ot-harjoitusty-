package asteroids.objects;

import asteroids.game.Game;
import asteroids.ui.Asteroids;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Player extends GameObject {

    private boolean destroyed = false;
    private long firingCooldown = 0;

    /**
     * Player-olion konstruktori
     */
    public Player() {
        super(Game.WIDTH / 2, Game.HEIGHT / 2, new Polygon(-5, -5, 10, 0, -5, 5), new Point2D(0, 0));
    }

    /**
     * Metodi päivittää pelaajahahmon sijainnin jos pelaajahahmo ei ole
     * tuhoutunut
     */
    public void update() {
        if (destroyed) {
            return;
        }
        updatePosition();
    }

    /**
     * Metodi tuhoaa pelaajahahmon.
     */
    public void destroy() {
        this.destroyed = true;
    }

    /**
     * Metodi pyörittää pelaajahahmoa annetun parametrin verran
     *
     * @param rotation pelaajahahmon pyörityksen määrä
     */
    public void rotate(int rotation) {
        if (destroyed) {
            return;
        }
        super.object.setRotate(super.object.getRotate() + rotation);
    }

    /**
     * metodi kiihdyttää pelaajahahmon vauhtia.
     */
    public void accelerate() {
        double xAccel = 0.05 * Math.cos(Math.toRadians(super.object.getRotate()));
        double yAccel = 0.05 * Math.sin(Math.toRadians(super.object.getRotate()));
        super.velocity = super.velocity.add(xAccel, yAccel);
    }

    /**
     * Metodi luo uuden ammuksen pelaajahahmon sijainnista
     */
    public void fire() {
        if (firingCooldown + 1000 < System.currentTimeMillis()) {
            double xVel = 3 * Math.cos(Math.toRadians(super.object.getRotate()));
            double yVel = 3 * Math.sin(Math.toRadians(super.object.getRotate()));
            Projectile p = new Projectile(super.x, super.y, new Point2D(xVel, yVel));
            Game.getInstance().getProjectiles().add(p);
            Asteroids.getInstance().addObject(p.getObject());
            firingCooldown = System.currentTimeMillis();
        }
    }
    
    /**
     * Testimetodi
     * Metodi luo uuden ammuksen pelaajahahmon sijainnista
     */
    public void fireBlanks() {
        if (firingCooldown + 1000 < System.currentTimeMillis()) {
            double xVel = 3 * Math.cos(Math.toRadians(super.object.getRotate()));
            double yVel = 3 * Math.sin(Math.toRadians(super.object.getRotate()));
            Projectile p = new Projectile(super.x, super.y, new Point2D(xVel, yVel));
            Game.getInstance().getProjectiles().add(p);
            firingCooldown = System.currentTimeMillis();
        }
    }
}
