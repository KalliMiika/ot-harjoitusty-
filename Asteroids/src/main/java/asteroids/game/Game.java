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
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    
    public Map<KeyCode, Boolean> pressedButtons = new HashMap<>();
    
    Player player;
    Pane screen;
    Scene scene;
    Text text;
    AtomicInteger points = new AtomicInteger();
    
    ArrayList<Projectile> projectiles = new ArrayList<>();
    ArrayList<Asteroid> asteroids = new ArrayList<>();
    
    long firingCooldown = 0;
    long lastUpdate;
    int millis = 0;
    int seconds = 0;
    int minutes = 0;
    
    public void setup() {
        screen = new Pane();
        screen.setPrefSize(WIDTH, HEIGHT);
        text = new Text(10, 20, "Points: 0\nTime Survived: 00:00:000");
        screen.getChildren().add(text);
        scene = new Scene(screen);
        scene.setOnKeyPressed(event -> {
            pressedButtons.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedButtons.put(event.getCode(), Boolean.FALSE);
        });
        player = new Player();
        screen.getChildren().add(player.getObject());
        generateStartingAsteroids();
        lastUpdate = System.currentTimeMillis();
    }
    
    @Override
    public void start(Stage stage) {
        setup();
        new AnimationTimer() {
            @Override
            public void handle(long present) {
                handlePressedKeys();
                update();
                checkCollisions();
                updatePointsAndTime();
            }
        }.start();
        stage.setScene(scene);
        stage.show();
    }  
   
    private void update() {
        player.update();
        asteroids.forEach((as) -> as.update());
        List<Projectile> deadProjectiles = new ArrayList<>();
        projectiles.forEach((p) -> {
            if (p.update() <= 0) {
                deadProjectiles.add(p);
            }
        });
        removeDeadProjectiles(deadProjectiles);
    }
    
    private void updatePointsAndTime() {
        millis += System.currentTimeMillis() - lastUpdate;
        lastUpdate = System.currentTimeMillis();
        if (millis >= 1000) {
            millis -= 1000;
            seconds++;
            points.incrementAndGet();
        }
        if (seconds >= 60) {
            seconds -= 60;
            minutes++;
        }
        text.setText("Points: " + points + "\nTime Survived: " + minutes + ":" + seconds + ":" + millis);
    }

    private void removeDeadProjectiles(List<Projectile> deadProjectiles) {
        deadProjectiles.forEach(p -> {
            screen.getChildren().remove(p.getObject());
            projectiles.remove(p);
        });
    }
    
    private void checkCollisions() {
        asteroids.forEach(as -> {
            if (player.checkCollision(as)) {
                player.destroy();
            }
        });
        ArrayList<GameObject> collisions = new ArrayList<>();
        ArrayList<Asteroid> newAsteroids = new ArrayList<>();
        projectiles.forEach(p -> {
            asteroids.forEach(as -> {
                if (p.checkCollision(as)) {
                    collisions.add(p);
                    collisions.add(as);
                    as.shatter().forEach((newAs) -> {
                        newAsteroids.add(newAs);
                    });
                    points.addAndGet(as.getPoints());
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
        newAsteroids.forEach(as -> {
            asteroids.add(as);
            screen.getChildren().add(as.getObject());
        });
    }
    
    private void handlePressedKeys() {
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

    }
    
    private Asteroid generateAsteroid() {
        Random rnd = new Random();
        Point2D spawnLocation = randomizeStartingLocation();
        double size = ((rnd.nextDouble() - 0.5) * 10) + 30;
        int rotation = rnd.nextInt(10) - 5;
        double xVel = (rnd.nextDouble() - 0.5) * (rnd.nextInt(3) + 1);
        double yVel = (rnd.nextDouble() - 0.5) * (rnd.nextInt(3) + 1);
        return new Asteroid(spawnLocation.getX(), spawnLocation.getY(), size, 2, rotation, new Point2D(xVel, yVel));
    }
    
    private Point2D randomizeStartingLocation() {
        Random rnd = new Random();
        switch (rnd.nextInt(4) + 1) {
            case (1):
                double x = rnd.nextInt(WIDTH);
                double y = 0;
                return new Point2D(x, y);
            case (2):
                x = rnd.nextInt(WIDTH);
                y = HEIGHT;
                return new Point2D(x, y);
            case (3):
                x = 0;
                y = rnd.nextInt(HEIGHT);
                return new Point2D(x, y);
            case (4):
                x = WIDTH;
                y = rnd.nextInt(HEIGHT);
                return new Point2D(x, y);
            default:
                return new Point2D(0, 0);
        }
    }
    
    private void generateStartingAsteroids() {
        Random rnd = new Random();
        for (int i = 0; i < 5; i++) {
            asteroids.add(generateAsteroid());
        }
        asteroids.forEach((as)-> {
            screen.getChildren().add(as.getObject());
        });
    }
    
    public static void main(String args[]) {
        launch(args);
    }
}
