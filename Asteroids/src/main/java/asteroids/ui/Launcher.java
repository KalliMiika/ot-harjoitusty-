package asteroids.ui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Asteroids");
        MainMenu menu = new MainMenu(stage);
        menu.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
