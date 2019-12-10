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
    
    /**
     * GameObject-luokan konstruktori
     * 
     * @param x         GameObject-olion aloituspisteen x-koordinaatti
     * @param y         GameObject-olion aloituspisteen y-koordinaatti
     * @param object    GameObject-olion graafinen monikulmio
     * @param velocity  GameObject-olion liikettä kuvaava vektori
     */
    public GameObject(double x, double y, Polygon object, Point2D velocity) {
        this.x = x;
        this.y = y;
        this.object = object;
        this.velocity = velocity;
    }
    
    /**
     * Metodi palauttaa GameObject-olion graafisen monikulmion
     * 
     * @return Poligon-Olio joka kuvastaa GameObject-olion graafista monikulmiota
     */
    public Polygon getObject() {
        return this.object;
    }
  
    /**
     * Metodi palauttaa GameObject-olion sijainnin koordinaattipisteenä.
     * 
     * @return GameObject-olion x- ja y-koordinaatit Point2D-oliona
     */
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

    /**
     * Metodi tarkastaa törmäävätkö kaksi GameObject-oliota toisiinsa
     * 
     * @param otherObject   Toinen GameObject-olio jonka kanssa törmäystä tarkastellaan
     * @return false jos törmäystä ei tapahdu, true jos tapahtuu
     */
    public boolean checkCollision(GameObject otherObject) {
        Shape collisionArea = Shape.intersect(this.object, otherObject.getObject());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
}
