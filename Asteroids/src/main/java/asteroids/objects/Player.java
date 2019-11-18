package asteroids.objects;

import asteroids.game.Game;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

public class Player extends GameObject{
    
    
    public Player(){
        super(Game.WIDTH/2, Game.HEIGHT/2, new Polygon(-5, -5, 10, 0, -5, 5), new Point2D(0, 0));
    }
    
    public void rotate(int rotation){
        super.object.setRotate(super.object.getRotate() + rotation);
    }
    
    public void accelerate(){
        double xAccel = 0.05 * Math.cos(Math.toRadians(super.object.getRotate()));
        double yAccel = 0.05 * Math.sin(Math.toRadians(super.object.getRotate()));
        super.velocity = super.velocity.add(xAccel, yAccel);
    }
    
    public Scene getScene(Pane screen) {
        Scene scene = new Scene(screen);
        Map<KeyCode, Boolean> pressedButtons = new HashMap<>();

        scene.setOnKeyPressed(event -> {
            pressedButtons.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedButtons.put(event.getCode(), Boolean.FALSE);
        });

        new AnimationTimer() {

            @Override
            public void handle(long present) {
                if (pressedButtons.getOrDefault(KeyCode.LEFT, false)) {
                    rotate(-5);
                }

                if (pressedButtons.getOrDefault(KeyCode.RIGHT, false)) {
                    rotate(5);
                }

                if (pressedButtons.getOrDefault(KeyCode.UP, false)) {
                    accelerate();
                }
                updatePosition();
            }
        }.start();

        return scene;
    }
}
