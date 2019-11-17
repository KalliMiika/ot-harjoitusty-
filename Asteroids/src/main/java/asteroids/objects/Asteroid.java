package asteroids.objects;

import javafx.geometry.Point2D;

public class Asteroid extends GameObject {
    
    private int size;
    
    public Asteroid(double x, double y, int size, Point2D velocity){
        super(x, y, velocity);
        this.size = size;
    }
}
