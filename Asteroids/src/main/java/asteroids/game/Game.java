package asteroids.game;

import asteroids.objects.Asteroid;
import asteroids.objects.GameObject;
import asteroids.objects.Player;
import asteroids.objects.Projectile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game extends Application {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    
    long firingCooldown = 0;
    
    @Override
    public void start(Stage stage) {
        Pane screen = new Pane();
        screen.setPrefSize(WIDTH, HEIGHT);
        Scene scene = new Scene(screen);
        
        Player player = new Player();
        ArrayList<Projectile> projectiles = new ArrayList<>();
        screen.getChildren().add(player.getObject());

        ArrayList<Asteroid> asteroids = generateStartingAsteroids();
        asteroids.forEach((as)-> {
            screen.getChildren().add(as.getObject());
        });
        
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
                    player.rotate(-5);
                }

                if (pressedButtons.getOrDefault(KeyCode.RIGHT, false)) {
                    player.rotate(5);
                }

                if (pressedButtons.getOrDefault(KeyCode.UP, false)) {
                    player.accelerate();
                }
                
                if (pressedButtons.getOrDefault(KeyCode.SPACE, false)) {
                    if (System.currentTimeMillis() > firingCooldown + 1000) {
                        Projectile p = player.fire();
                        screen.getChildren().add(p.getObject());
                        projectiles.add(p);
                        firingCooldown = System.currentTimeMillis();
                    }
                }
                player.update();
                asteroids.forEach((as) -> as.update());
                projectiles.forEach((p) -> p.update());
                asteroids.forEach(as -> {
                    if (player.checkCollision(as)) {
                        player.destroy();
                    }
                });
                ArrayList<GameObject> collisions = new ArrayList<>();
                projectiles.forEach(p -> {
                    asteroids.forEach(as -> {
                        if (p.checkCollision(as)) {
                            collisions.add(p);
                            collisions.add(as);
                        }
                    });
                });
                collisions.forEach(col -> {
                    screen.getChildren().remove(col.getObject());
                    if (asteroids.contains(col)) {
                        asteroids.remove(col);
                    }
                    if (projectiles.contains(col)) {
                        projectiles.remove(col);
                    }
                });
            }
        }.start();

        stage.setScene(scene);
        stage.show();
    }  
    
    ArrayList<Asteroid> generateStartingAsteroids() {
        ArrayList<Asteroid> asteroids = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < 5; i++) {
            double x;
            double y;
            double size;
            int rotation;
            double xVel;
            double yVel;
            int side = rnd.nextInt(4) + 1;
            switch (side) {
                case(1):
                    x = rnd.nextInt(WIDTH);
                    y = 0;
                    break;
                case(2):
                    x = rnd.nextInt(WIDTH);
                    y = HEIGHT;
                    break;
                case(3):
                    x = 0;
                    y = rnd.nextInt(HEIGHT);
                    break;
                case(4):
                    x = WIDTH;
                    y = rnd.nextInt(HEIGHT);
                    break;
                default:
                    x = 0;
                    y = 0;
                    break;
            }
            //Size varies beetween 25-35
            size = ((rnd.nextDouble() - 0.5) * 10) + 30;
            rotation = rnd.nextInt(10) - 5;
            xVel = (rnd.nextDouble() - 0.5) * (rnd.nextInt(5) + 1);
            yVel = (rnd.nextDouble() - 0.5) * (rnd.nextInt(5) + 1);
            asteroids.add(new Asteroid(x, y, size, rotation, new Point2D(xVel, yVel)));
        }
        return asteroids;
    }
    
    public static void main(String args[]) {
        launch(args);
    }
}
