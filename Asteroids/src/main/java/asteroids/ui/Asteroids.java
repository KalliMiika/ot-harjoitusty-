package asteroids.ui;

import asteroids.game.Game;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Asteroids {

    private static Asteroids instance;

    private Map<KeyCode, Boolean> pressedButtons;

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private Stage stage;
    private Pane screen;
    private Scene scene;
    private Text text;

    private Game game;

    public Asteroids(Stage stage) {
        this.stage = stage;
        instance = this;
    }

    public static Asteroids getInstance() {
        return instance;
    }

    private void setup() {
        screen = new Pane();
        pressedButtons = new HashMap<>();
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
        game = new Game();
    }

    /**
     * Metodi rakentaa näkymän kutsumalla setup() metodia, jonka jälkeen se
     * käynnistää pelin.
     */
    public void start() {
        setup();
        new AnimationTimer() {
            @Override
            public void handle(long present) {
                if (game.isPaused()) {
                    return;
                }
                if (game.isGameOver()) {
                    game.update();
                    return;
                }
                handlePressedKeys();
                game.update();
                text.setText("Points: " + game.getPoints() + "\nAsteroids Destroyed: " + game.getAsteroidsDestroyed() + "\nTime Survived: " + game.getTime());
            }
        }.start();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodi luo loppuruutunäkymän
     */
    public void setupScoreScreen() {
        VBox menu = new VBox(5);
        menu.setAlignment(Pos.CENTER);

        Label title = new Label("Game Over");
        title.setStyle("-fx-font-size: 80");
        title.setPadding(new Insets(100, 10, 50, 10));
        menu.getChildren().add(title);

        Label pointsGained = new Label("Points: " + game.getPoints());
        pointsGained.setStyle("-fx-font-size: 50");
        pointsGained.setPadding(new Insets(50, 10, 50, 10));
        menu.getChildren().add(pointsGained);

        Button playAgain = new Button("Play Again");
        playAgain.setStyle("-fx-font-size: 25");
        playAgain.setPadding(new Insets(10, 10, 10, 10));
        playAgain.setOnAction(e -> {
            instance = new Asteroids(stage);
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
            game.unpause();
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

    private void handlePressedKeys() {
        if (pressedButtons.getOrDefault(KeyCode.LEFT, false)) {
            game.getPlayer().rotate(-5);
        }
        if (pressedButtons.getOrDefault(KeyCode.RIGHT, false)) {
            game.getPlayer().rotate(5);
        }
        if (pressedButtons.getOrDefault(KeyCode.UP, false)) {
            game.getPlayer().accelerate();
        }
        if (pressedButtons.getOrDefault(KeyCode.SPACE, false)) {
            game.getPlayer().fire();
        }
        if (pressedButtons.getOrDefault(KeyCode.ESCAPE, false)) {
            game.pause();
            setupPauseScreen();
        }
    }
    
    /**
     * Metodi joka lisää parametrina annetun objectin ruudulle
     * @param object Lisättävä objekti
     */
    public void addObject(Polygon object) {
        screen.getChildren().add(object);
    }
    
    /**
     * Metodi joka poistaa parametrina annetun objektin ruudulta
     * @param object Poistettava objekti.
     */
    public void removeObject(Polygon object) {
        screen.getChildren().remove(object);
    }
}
