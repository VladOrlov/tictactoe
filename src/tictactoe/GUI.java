package tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Orlov Vladislav on 05.01.2016.
 */
public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/application.fxml"));
            primaryStage.setTitle("Tic tac toe");

            Scene scene = new Scene(root, 600, 625);
            scene.getStylesheets().add(0, "styles/application.css");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.setMinHeight(325);
            primaryStage.setMinWidth(300);
            primaryStage.show();
        }catch (Throwable e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
