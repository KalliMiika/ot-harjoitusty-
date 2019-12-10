package asteroids.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HighScore {

    private static HighScore instance;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private Stage stage;
    private Scene menu;

    public HighScore(Stage stage) {
        this.stage = stage;
        instance = this;
    }

    public static HighScore getInstance() {
        return instance;
    }

    private void setup() {
        VBox menu = new VBox(5);
        menu.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("HighScore");
        title.setStyle("-fx-font-size: 80");
        title.setPadding(new Insets(100, 10, 50, 10));
        menu.getChildren().add(title);

        GridPane scoreBoard = new GridPane();
        scoreBoard.setAlignment(Pos.TOP_CENTER);
        scoreBoard.setGridLinesVisible(true);
        scoreBoard.setMaxSize(600, 400);
        scoreBoard.setMinSize(600, 400);
        scoreBoard.add(new Label("Nickname"), 1, 1);
        scoreBoard.add(new Label("Timestamp"), 2, 1);
        scoreBoard.add(new Label("Asteroids Destroyed"), 3, 1);
        scoreBoard.add(new Label("Time Survived"), 4, 1);
        scoreBoard.add(new Label("Points Earned"), 5, 1);
        fillScoreBoard(scoreBoard);
        menu.getChildren().add(scoreBoard);

        Button backButton = new Button("Back to Menu");
        backButton.setStyle("-fx-font-size: 25");
        backButton.setPadding(new Insets(10, 10, 10, 10));
        backButton.setOnAction(e -> {
            MainMenu.getInstance().start();
        });
        menu.getChildren().add(backButton);

        this.menu = new Scene(menu, WIDTH, HEIGHT);
    }

    private void fillScoreBoard(GridPane scoreBoard) {
        String line = null;
        String path = "highscores.txt";
        int row = 2;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            while ((line = bufferedReader.readLine()) != null) {
                String[] score = line.split(";");
                String nickname = score[0];
                String timestamp = score[1];
                String asteroidsDestroyed = score[2];
                String timeSurvived = score[3] + ":" + score[4] + ":" + score[5];
                String points = score[6];
                scoreBoard.add(new Label(nickname), 1, row);
                scoreBoard.add(new Label(timestamp), 2, row);
                scoreBoard.add(new Label(asteroidsDestroyed), 3, row);
                scoreBoard.add(new Label(timeSurvived), 4, row);
                scoreBoard.add(new Label(points), 5, row);
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodi tuo näkyviin Huipputuloslistaus -näkymän
     */
    public void start() {
        setup();
        stage.setScene(menu);
        stage.show();
    }

}
