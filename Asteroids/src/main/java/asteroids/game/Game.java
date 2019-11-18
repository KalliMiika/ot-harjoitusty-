package asteroids.game;

import asteroids.objects.Asteroid;
import asteroids.objects.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.*;
import javafx.application.Application;
import javafx.geometry.Point2D;
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
        screen.getChildren().add(player.getObject());

        Asteroid as = new Asteroid(0.0, 0.0, 32, -10, new Point2D(1.0, 1.0));
        screen.getChildren().add(as.getObject());
        new AnimationTimer() {
            
            @Override
            public void handle(long present) {
                as.update();
            }
        }.start();


        stage.setScene(player.getScene(screen));
        stage.show();
    }  
    
    public static void main(String args[]){
        launch(args);
    }
}
