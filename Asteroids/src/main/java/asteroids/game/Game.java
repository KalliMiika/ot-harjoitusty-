package asteroids.game;

import asteroids.objects.Asteroid;
import asteroids.objects.GameObject;
import asteroids.objects.Player;
import asteroids.objects.Projectile;
import asteroids.ui.Launcher;
import asteroids.ui.MainMenu;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game {

    private static Game instance;

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    public Map<KeyCode, Boolean> pressedButtons;

    Player player;
    Stage stage;
    Pane screen;
    Scene scene;
    Text text;
    AtomicInteger points;
    AtomicInteger asteroidsDestroyed;

    ArrayList<Projectile> projectiles;
    ArrayList<Asteroid> asteroids;

    long firingCooldown;
    long lastUpdate;
    int spawnTimer;
    int millis;
    int seconds;
    int minutes;
    boolean paused;
    boolean gameOver;

    public Game(Stage stage) {
        this.instance = this;
        this.stage = stage;
    }

    public static Game getInstance() {
        return instance;
    }

    private void setup() {
        screen = new Pane();
        player = new Player();
        pressedButtons = new HashMap<>();
        projectiles = new ArrayList<>();
        asteroids = new ArrayList<>();
        points = new AtomicInteger();
        asteroidsDestroyed = new AtomicInteger();
        firingCooldown = 0;
        lastUpdate = 0;
        spawnTimer = 2000;
        millis = 0;
        seconds = 0;
        minutes = 0;
        paused = false;
        gameOver = false;
        screen.setPrefSize(WIDTH, HEIGHT);
        text = new Text(10, 20, "Points: 0\nAsteroids Destroyed: 0\nTime Survived: 00:00:000");
        screen.getChildren().add(text);
        scene = new Scene(screen);
        scene.setOnKeyPressed(event -> {
            pressedButtons.put(event.getCode(), Boolean.TRUE);
        });
        scene.setOnKeyReleased(event -> {
            pressedButtons.put(event.getCode(), Boolean.FALSE);
        });
        screen.getChildren().add(player.getObject());
        generateStartingAsteroids();
        lastUpdate = System.currentTimeMillis();
    }


    private void setupScoreScreen() {
        VBox menu = new VBox(5);
        menu.setAlignment(Pos.CENTER);

        Label title = new Label("Game Over");
        title.setStyle("-fx-font-size: 80");
        title.setPadding(new Insets(100, 10, 50, 10));
        menu.getChildren().add(title);

        Label pointsGained = new Label("Points: " + points);
        pointsGained.setStyle("-fx-font-size: 50");
        pointsGained.setPadding(new Insets(50, 10, 50, 10));
        menu.getChildren().add(pointsGained);

        Button playAgain = new Button("Play Again");
        playAgain.setStyle("-fx-font-size: 25");
        playAgain.setPadding(new Insets(10, 10, 10, 10));
        playAgain.setOnAction(e -> {
            instance = new Game(stage);
            instance.start();
        });
        menu.getChildren().add(playAgain);

        Button mainMenu = new Button("Main Menu");
        mainMenu.setStyle("-fx-font-size: 25");
        mainMenu.setPadding(new Insets(10, 10, 10, 10));
        mainMenu.setOnAction(e -> {
            MainMenu.getInstance().start();
        });
        menu.getChildren().add(mainMenu);

        Button quit = new Button("Quit");
        quit.setStyle("-fx-font-size: 25");
        quit.setPadding(new Insets(10, 10, 10, 10));
        quit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
        menu.getChildren().add(quit);
        screen.getChildren().add(menu);
    }

    private void endGame() {
        this.gameOver = true;
        player.destroy();
        saveScore();
        setupScoreScreen();
    }

    private void saveScore() {
        System.out.println(points);
        String nickname = "Pelaaja";
        String path = "highscores.txt";
        try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8"))) {
            output.append(nickname + ";" + new SimpleDateFormat("dd-MM-yyyy hh:mm").format(new Date()) + ";" + asteroidsDestroyed + ";" + minutes + ";" + seconds + ";" + millis + ";" + points + "\r\n");
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupPauseScreen() {
        VBox menu = new VBox(5);
        menu.setAlignment(Pos.CENTER);

        Label title = new Label("Paused");
        title.setStyle("-fx-font-size: 80");
        title.setPadding(new Insets(100, 10, 100, 10));
        menu.getChildren().add(title);

        Button resumeGame = new Button("Resume Game");
        resumeGame.setStyle("-fx-font-size: 25");
        resumeGame.setPadding(new Insets(10, 10, 10, 10));
        resumeGame.setOnAction(e -> {
            screen.getChildren().remove(menu);
            this.paused = false;
        });
        menu.getChildren().add(resumeGame);

        Button mainMenu = new Button("Main Menu");
        mainMenu.setStyle("-fx-font-size: 25");
        mainMenu.setPadding(new Insets(10, 10, 10, 10));
        mainMenu.setOnAction(e -> {
            MainMenu.getInstance().start();
        });
        menu.getChildren().add(mainMenu);

        Button quit = new Button("Quit");
        quit.setStyle("-fx-font-size: 25");
        quit.setPadding(new Insets(10, 10, 10, 10));
        quit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
        menu.getChildren().add(quit);
        screen.getChildren().add(menu);
    }

    private void pause() {
        this.paused = true;
        setupPauseScreen();
    }

    /**
     * Metodi rakentaa näkymän kutsumalla setup() metodia, 
     * jonka jälkeen se käynnistää pelin.
     */
    public void start() {
        setup();
        new AnimationTimer() {
            @Override
            public void handle(long present) {
                if (paused) {
                    return;
                }
                if (gameOver) {
                    update();
                    return;
                }
                handlePressedKeys();
                update();
                checkCollisions();
                updatePointsAndTime();
            }
        }.start();
        stage.setScene(scene);
        stage.show();
    }

    private void spawnNewAsteroid() {
        Asteroid as = generateAsteroid();
        asteroids.add(as);
        screen.getChildren().add(as.getObject());
        spawnTimer = 2000;
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
        if (spawnTimer == 0) {
            spawnNewAsteroid();
        }
        spawnTimer--;
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
        text.setText("Points: " + points + "\nAsteroids Destroyed: " + asteroidsDestroyed + "\nTime Survived: " + minutes + ":" + seconds + ":" + millis);
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
                endGame();
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
                    asteroidsDestroyed.incrementAndGet();
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
                addNewProjectile(player.fire());
            }
        }
        if (pressedButtons.getOrDefault(KeyCode.ESCAPE, false)) {
            pause();
        }
    }

    private void addNewProjectile(Projectile p) {
        screen.getChildren().add(p.getObject());
        projectiles.add(p);
        firingCooldown = System.currentTimeMillis();
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
                return getNorthSpawnPoint();
            case (2):
                return getEastSpawnPoint();
            case (3):
                return getSouthSpawnPoint();
            case (4):
                return getSouthSpawnPoint();
            default:
                return new Point2D(0, 0);
        }
    }

    private Point2D getNorthSpawnPoint() {
        Random rnd = new Random();
        return new Point2D(rnd.nextInt(WIDTH), 0);
    }

    private Point2D getSouthSpawnPoint() {
        Random rnd = new Random();
        return new Point2D(rnd.nextInt(WIDTH), HEIGHT);
    }

    private Point2D getWestSpawnPoint() {
        Random rnd = new Random();
        return new Point2D(0, rnd.nextInt(HEIGHT));
    }

    private Point2D getEastSpawnPoint() {
        Random rnd = new Random();
        return new Point2D(WIDTH, rnd.nextInt(HEIGHT));
    }

    private void generateStartingAsteroids() {
        Random rnd = new Random();
        for (int i = 0; i < 5; i++) {
            asteroids.add(generateAsteroid());
        }
        asteroids.forEach((as) -> {
            screen.getChildren().add(as.getObject());
        });
    }

    /**
     * Metodi palauttaa listan, joka sisältää pelissä olemassa olevat projectile-oliot
     * 
     * @return Olemassa olevat Projectile-oliot
     */
    public List<Projectile> getProjectiles() {
        return this.projectiles;
    }

    /**
     * Metodi palauttaa listan, joka sisältää pelissä olemassa olevat asteroid-oliot
     * 
     * @return Olemassa olevat Asteroid-oliot
     */
    public List<Asteroid> getAsteroids() {
        return this.asteroids;
    }

    /**
     * Metodi palauttaa pelaajahahmo-olion
     * @return Pelaajahahmo-olio
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Metodi palauttaa Pane-elementin, joka sisältää kaikki GameObject-olioiden kuviot
     * 
     * @return Pane-elementti johon peli visualisoidaan.
     */
    public Pane getScreen() {
        return this.screen;
    }
}
