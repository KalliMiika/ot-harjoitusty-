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
    
    private Polygon Spaceship;
    
    public Player(){
        super(Game.WIDTH/2, Game.HEIGHT/2, new Point2D(0, 0));
        this.Spaceship = new Polygon(-5, -5, 10, 0, -5, 5);
    }
    
    public void update(){
        super.updatePosition();
        this.Spaceship.setTranslateX(super.x);
        this.Spaceship.setTranslateY(super.y);
    }
    
    public Polygon getSpaceship(){
        return this.Spaceship;
    }
    
    public void rotate(int rotation){
        this.Spaceship.setRotate(this.Spaceship.getRotate() + rotation);
    }
    
    public void accelerate(){
        double xAccel = 0.05 * Math.cos(Math.toRadians(this.Spaceship.getRotate()));
        double yAccel = 0.05 * Math.sin(Math.toRadians(this.Spaceship.getRotate()));
        super.velocity = super.velocity.add(xAccel, yAccel);
    }
    
    public Scene getScene(Pane screen) {
        Scene scene = new Scene(screen);
        Map<KeyCode, Boolean> painetutNapit = new HashMap<>();

        scene.setOnKeyPressed(event -> {
            painetutNapit.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            painetutNapit.put(event.getCode(), Boolean.FALSE);
        });

        new AnimationTimer() {

            @Override
            public void handle(long nykyhetki) {
                if (painetutNapit.getOrDefault(KeyCode.LEFT, false)) {
                    rotate(-5);
                }

                if (painetutNapit.getOrDefault(KeyCode.RIGHT, false)) {
                    rotate(5);
                }

                if (painetutNapit.getOrDefault(KeyCode.UP, false)) {
                    accelerate();
                }
                update();
            }
        }.start();

        return scene;
    }
}
