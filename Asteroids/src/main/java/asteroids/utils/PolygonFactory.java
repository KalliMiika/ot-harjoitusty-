package asteroids.utils;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.shape.Polygon;

public class PolygonFactory {

    /**
     * Metodi randomgeneroi satunnaisen muotoisen 12-kulmion
     * luomalla 12 pistettä täydellisen ympyrän muotoon
     * ja sitten heittämällä jokaista pistettä hieman sivuun
     * @param size  Luotavan monikulmion koko
     * @return      Randomgeneroitu monikulmio
     */
    public Polygon createRandomPolygon(double size) {
        Random rnd = new Random();

        int pointCount = 12;
        double degreeBetweenPoints = 360 / pointCount;
        
        Polygon polygon = new Polygon();
        ArrayList<Double> points = new ArrayList<>();
        for (int i = 0; i < pointCount; i++) {
            int deviation = rnd.nextInt(6) - 3;
            double x = size * Math.cos(Math.toRadians(degreeBetweenPoints * i));
            double y = size * Math.sin(Math.toRadians(degreeBetweenPoints * i));
            points.add(x + deviation);
            points.add(y + deviation);
        }
        polygon.getPoints().addAll(points);

        return polygon;
    }
}
