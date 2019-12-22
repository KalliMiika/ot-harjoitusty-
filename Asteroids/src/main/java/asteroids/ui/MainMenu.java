
package asteroids.ui;

import asteroids.game.Game;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {
    
    private static MainMenu instance;
    
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    
    private Stage stage;
    private Scene menu;
    
    public MainMenu(Stage stage){
        this.stage = stage;
        instance = this;
    }
    
    public static MainMenu getInstance(){
        return instance;
    }
    
    private void setup() {
        VBox menu = new VBox(5);
        menu.setAlignment(Pos.TOP_CENTER);
        
        Label title = new Label("Asteroids");
        title.setStyle("-fx-font-size: 80");
        title.setPadding(new Insets(100, 10, 100, 10));
        menu.getChildren().add(title);
        
        Button start = new Button("Start");
        start.setStyle("-fx-font-size: 25");
        start.setPadding(new Insets(10, 10, 10, 10));
        start.setOnAction(e->{
            Asteroids asteroids = new Asteroids(stage);
            asteroids.start();
        });
        menu.getChildren().add(start);
        
        Button highscore = new Button("Highscore");
        highscore.setStyle("-fx-font-size: 25");
        highscore.setPadding(new Insets(10, 10, 10, 10));
        highscore.setOnAction(e->{
            HighScore hs = new HighScore(stage);
            hs.start();
        });
        menu.getChildren().add(highscore);
        
        Button exit = new Button("Exit");
        exit.setStyle("-fx-font-size: 25");
        exit.setPadding(new Insets(10, 10, 10, 10));
        exit.setOnAction(e->{
            Platform.exit();
            System.exit(0);
        });
        menu.getChildren().add(exit);
        
        this.menu = new Scene(menu, WIDTH, HEIGHT);
    }
    
    /**
     * Metodi tuo esille Päävalikko-näkymän
     */
    public void start() {
        setup();
        stage.setScene(menu);
        stage.show();
    }
    
}
