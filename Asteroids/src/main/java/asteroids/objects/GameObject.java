package asteroids.objects;
import asteroids.game.Game;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class GameObject {
    
    protected double x;
    protected double y;
    protected Polygon object;
    protected Point2D velocity;
    
    public GameObject(double x, double y, Polygon object, Point2D velocity) {
        this.x = x;
        this.y = y;
        this.object = object;
        this.velocity = velocity;
    }
    
    public Polygon getObject() {
        return this.object;
    }
  
    public Point2D getLocation() {
        return new Point2D(this.x, this.y);
    }

    protected void updatePosition() {
        this.x += this.velocity.getX();
        this.y += this.velocity.getY();
        if (this.x < 0) {
            this.x = this.x + Game.WIDTH;
        }
        if (this.x > Game.WIDTH) {
            this.x = this.x - Game.WIDTH;
        }
        if (this.y < 0) {
            this.y = this.y + Game.HEIGHT;
        }
        if (this.y > Game.HEIGHT) {
            this.y = this.y - Game.HEIGHT;
        }
        this.object.setTranslateX(this.x);
        this.object.setTranslateY(this.y);
    }

    public boolean checkCollision(GameObject otherObject) {
        Shape tormaysalue = Shape.intersect(this.object, otherObject.getObject());
        return tormaysalue.getBoundsInLocal().getWidth() != -1;
    }
}