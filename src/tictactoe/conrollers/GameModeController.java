package tictactoe.conrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tictactoe.GameController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Orlov Vladislav on 05.01.2016.
 */
public class GameModeController extends Stage implements Initializable {

    @FXML
    RadioButton pvp;
    @FXML
    RadioButton pvc;

    private GameController gameController;

    public static void openInModalMode(URL url, GameController.GameMode gameMode, GameController gameController) {

        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            GameModeController gmc = (GameModeController) loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Game mode");
            stage.setResizable(false);
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            gmc.setGameController(gameController);
            gmc.setGameMode(gameMode);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setGameMode(GameController.GameMode gameMode) {
        if (gameMode == GameController.GameMode.PvP) {
            this.pvp.setSelected(true);
        } else if (gameMode == GameController.GameMode.PvC) {
            this.pvc.setSelected(true);
        }
    }

    public void clickButtonHandler(ActionEvent evt) {

        Button clickedButton = (Button) evt.getTarget();
        String buttonID = clickedButton.getId();

        if (buttonID.equals("ok")) {
            if (pvp.isSelected()) {
                gameController.changeGameMode(GameController.GameMode.PvP);
            } else if (pvc.isSelected()) {
                gameController.changeGameMode(GameController.GameMode.PvC);
            }
        }
        Stage stage = (Stage) clickedButton.getScene().getWindow();
        stage.close();

    }

}
