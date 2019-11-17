package asteroids.game;

import asteroids.objects.Player;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game extends Application{

    public static int WIDTH = 1200;
    public static int HEIGHT = 800;
    
    @Override
    public void init() throws Exception {
      // ...
    }
    
    @Override
    public void start(Stage stage) {
        Pane screen = new Pane();
        screen.setPrefSize(WIDTH, HEIGHT);
        
        Player player = new Player();
        screen.getChildren().add(player.getSpaceship());

        


        stage.setScene(player.getScene(screen));
        stage.show();
    }  
    
    public static void main(String args[]){
        launch(args);
    }
}
