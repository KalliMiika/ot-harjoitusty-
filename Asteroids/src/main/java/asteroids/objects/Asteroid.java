package asteroids.objects;

import asteroids.utils.PolygonFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;

public class Asteroid extends GameObject {
    
    private int rotation;
    private double size;
    private int lives;
    
    /**
     * Asteroid luokan konstruktori
     * 
     * @param x         Asteroidin aloituspisteen x-koordinaatti
     * @param y         Asteroidin aloituspisteen y-koordinaatti
     * @param size      Asteroidin koko
     * @param lives     Asteroidin jäljellä olevien hajoamiskertojen määrä
     * @param rotation  Asteroidin pyörimisvauhti
     * @param velocity  Asteroidin liikettä kuvaava vektori
     */
    public Asteroid(double x, double y, double size, int lives, int rotation, Point2D velocity) {
        super(x, y, new PolygonFactory().createRandomPolygon(size), velocity);
        this.size = size;
        this.lives = lives;
        this.rotation = rotation;
    }
    
    /**
     * Metodi luo kaksi uutta asteroidia hajoitetun asteroidin tilalle, jotka aloittavat
     * samasta pisteestä kuin juuri tuhoutunut asteroidi, mutta menevät noin 30 astetta sivuun
     * siitä suunnasta mihin alkuperäinen asteroidi oli menossa.
     * 
     * Jos tuhotun Asteroidin hajoamiskertojen lukumäärä oli 0, ei uusia asteroideja luoda.
     * 
     * @return lista luoduista asteroideista.
     */
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
    
    /**
     * Päivittää asteroidin sijaintia ja pyörittää sitä
     */
    public void update() {
        super.object.setRotate(super.object.getRotate() + rotation);
        super.updatePosition();
    }
    
    /**
     * Metodi antaa pisteitä asteroidin koon perusteella.
     * 
     * @return asteroidin arvoa kuvastava luku
     */
    public int getPoints() {
        return 100 * (this.lives + 1);
    }
}
