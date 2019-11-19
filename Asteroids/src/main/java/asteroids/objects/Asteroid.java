package asteroids.objects;

import javafx.geometry.Point2D;

public class Asteroid extends GameObject {
    
    private int rotation;
    
    public Asteroid(double x, double y, double size, int rotation, Point2D velocity){
        super(x, y, new PolygonFactory().createRandomPolygon(size), velocity);
        this.rotation = rotation;
    }
    
    public Point2D getLocation(){
        return new Point2D(super.x, super.y);
    }
    
    public void update(){
        super.object.setRotate(super.object.getRotate()+rotation);
        super.updatePosition();
    }
}
