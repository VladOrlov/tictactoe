package tictactoe.conrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tictactoe.GameController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Orlov Vladislav on 05.01.2016.
 */
public class ModalWindowsController extends Stage implements Initializable{

    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public static void openInModalMode(URL url, String title) {

        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setResizable(false);
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void okButtonClickHandler(ActionEvent evt) {
        Button clickedButton = (Button) evt.getTarget();
        Stage stage = (Stage) clickedButton.getScene().getWindow();
        stage.close();
    }
}
