package asteroids.utils;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.shape.Polygon;

public class PolygonFactory {

    public Polygon createRandomPolygon(double size) {
        Random rnd = new Random();

        int pointCount = 15;
        double degreeBeetweenPoints = 360 / pointCount;
        
        Polygon polygon = new Polygon();
        ArrayList<Double> points = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int deviation = rnd.nextInt(10) - 5;
            double x = size * Math.cos(Math.toRadians(degreeBeetweenPoints * i));
            double y = size * Math.sin(Math.toRadians(degreeBeetweenPoints * i));
            points.add(x + deviation);
            points.add(y + deviation);
        }
        polygon.getPoints().addAll(points);

        return polygon;
    }
}
