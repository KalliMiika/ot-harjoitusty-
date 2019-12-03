package asteroids.objects;

import asteroids.utils.PolygonFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;

public class Asteroid extends GameObject {
    
    private int rotation;
    private double size;
    private int lives;
    
    public Asteroid(double x, double y, double size, int lives, int rotation, Point2D velocity) {
        super(x, y, new PolygonFactory().createRandomPolygon(size), velocity);
        this.size = size;
        this.lives = lives;
        this.rotation = rotation;
    }
    
    public List<Asteroid> shatter() {
        ArrayList<Asteroid> ret = new ArrayList<>();
        if (this.lives == 0) {
            return ret;
        }
        int shatterAngle = 120;
        double x = (Math.cos(shatterAngle) * super.velocity.getX()) - (Math.sin(shatterAngle) * super.velocity.getY());
        double y = (Math.sin(shatterAngle) * super.velocity.getX()) + (Math.cos(shatterAngle) * super.velocity.getY());
        Point2D vel = new Point2D(x, y);
        double x2 = (Math.cos(shatterAngle) * super.velocity.getX()) + (Math.sin(shatterAngle) * super.velocity.getY());
        double y2 = (-Math.sin(shatterAngle) * super.velocity.getX()) + (Math.cos(shatterAngle) * super.velocity.getY());
        Point2D vel2 = new Point2D(x2, y2);
        ret.add(new Asteroid(super.x, super.y, this.size / 1.5, this.lives - 1, this.rotation, vel));
        ret.add(new Asteroid(super.x, super.y, this.size / 1.5, this.lives - 1, this.rotation, vel2));
        return ret;
    }
    
    public void update() {
        super.object.setRotate(super.object.getRotate() + rotation);
        super.updatePosition();
    }
    
    public int getPoints() {
        return 100 * (this.lives + 1);
    }
}
