package asteroids.game;

import asteroids.objects.Asteroid;
import asteroids.objects.GameObject;
import asteroids.objects.Player;
import asteroids.objects.Projectile;
import asteroids.ui.Asteroids;
import static java.awt.SystemColor.text;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game {

    private static Game instance;

    public static final int WIDTH = 1200; //
    public static final int HEIGHT = 800; //

    private Player player;
    private AtomicInteger points;
    private AtomicInteger asteroidsDestroyed;

    private ArrayList<Projectile> projectiles;
    private ArrayList<Asteroid> asteroids;

    private long lastUpdate;
    private int spawnTimer;
    private int millis;
    private int seconds;
    private int minutes;
    private boolean paused;
    private boolean gameOver;

    public Game() {
        this.instance = this;
        player = new Player();
        projectiles = new ArrayList<>();
        asteroids = new ArrayList<>();
        points = new AtomicInteger();
        asteroidsDestroyed = new AtomicInteger();
        lastUpdate = 0;
        spawnTimer = 2000;
        millis = 0;
        seconds = 0;
        minutes = 0;
        paused = false;
        gameOver = false;
        Asteroids.getInstance().addObject(player.getObject());
        generateStartingAsteroids();
        lastUpdate = System.currentTimeMillis();
    }
    
    /**
     * Konstruktori testiluokkia varten
     * @param DummyGameConstructor 
     */
    public Game(boolean DummyGameConstructor) {
        this.instance = this;
        player = new Player();
        projectiles = new ArrayList<>();
        asteroids = new ArrayList<>();
        points = new AtomicInteger();
        asteroidsDestroyed = new AtomicInteger();
        lastUpdate = 0;
        spawnTimer = 2000;
        millis = 0;
        seconds = 0;
        minutes = 0;
        paused = false;
        gameOver = false;
        lastUpdate = System.currentTimeMillis();
    }

    public static Game getInstance() {
        return instance;
    }

    private void endGame() {
        this.gameOver = true;
        player.destroy();
        saveScore();
        Asteroids.getInstance().setupScoreScreen();
    }
    
    /**
     * Metodi joka päättää pelin testiolosuhteissa
     */
    public void dummyEndGame() {
        this.gameOver = true;
        player.destroy();
        saveScore();
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

    /**
     * Metodi laittaa pelin pause-tilaan.
     */
    public void pause() {
        this.paused = true;
    }

    /**
     * Metodi pääättää pelin pause-tilan.
     */
    public void unpause() {
        this.paused = false;
    }

    /**
     * Metodi joka rakentaa pelin seuraavan framen
     */
    public void update() {
        if (gameOver) {
            updateLocations();
            return;
        }
        updateLocations();
        checkCollisions();
        updatePointsAndTime();
    }

    private void spawnNewAsteroid() {
        Asteroid as = generateAsteroid();
        asteroids.add(as);
        Asteroids.getInstance().addObject(as.getObject());
        spawnTimer = 2000;
    }

    private void updateLocations() {
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
    }

    private void removeDeadProjectiles(List<Projectile> deadProjectiles) {
        deadProjectiles.forEach(p -> {
            Asteroids.getInstance().removeObject(p.getObject());
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
            Asteroids.getInstance().removeObject(col.getObject());
            if (asteroids.contains(col)) {
                asteroids.remove(col);
            }
            if (projectiles.contains(col)) {
                projectiles.remove(col);
            }
        });
        newAsteroids.forEach(as -> {
            asteroids.add(as);
            Asteroids.getInstance().addObject(as.getObject());
        });
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
            Asteroids.getInstance().addObject(as.getObject());
        });
    }

    /**
     * Metodi palauttaa listan, joka sisältää pelissä olemassa olevat
     * projectile-oliot
     *
     * @return Olemassa olevat Projectile-oliot
     */
    public List<Projectile> getProjectiles() {
        return this.projectiles;
    }

    /**
     * Metodi palauttaa listan, joka sisältää pelissä olemassa olevat
     * asteroid-oliot
     *
     * @return Olemassa olevat Asteroid-oliot
     */
    public List<Asteroid> getAsteroids() {
        return this.asteroids;
    }

    /**
     * Metodi palauttaa pelaajahahmo-olion
     *
     * @return Pelaajahahmo-olio
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Metodi palauttaa pelin pause-statuksen
     *
     * @return True, jos peli on pausettu, muutoin false
     */
    public boolean isPaused() {
        return this.paused;
    }

    /**
     * Metodi palauttaa pelin loppu-statuksen
     *
     * @return True, jos pelin on loppunut, muutoin false
     */
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * Metodi palauttee kertyneiden pisteiden lukumäärän
     *
     * @return Pisteiden lukumäärä AtomicInteger muuttujana
     */
    public AtomicInteger getPoints() {
        return this.points;
    }

    /**
     * Metodi palauttaa kuluneen ajan pelin alusta
     *
     * @return String-tyypin muuttuja joka sisältää kuluneen ajan pelin alusta
     */
    public String getTime() {
        return minutes + ":" + seconds + ":" + millis;
    }

    /**
     * Metodi palauttaa tuhottujen asteroidien lukumäärän
     *
     * @return AtomicInteger-tyypin muuttuja joka sisältää tuhottujen
     * asteroidien lukumäärän
     */
    public AtomicInteger getAsteroidsDestroyed() {
        return this.asteroidsDestroyed;
    }
}
