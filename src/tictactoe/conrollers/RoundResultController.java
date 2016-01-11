package tictactoe.conrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tictactoe.GameController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Orlov Vladislav on 05.01.2016.
 */
public class RoundResultController extends Stage implements Initializable{

    @FXML
    TextArea roundResult;

    private GameController gameController;

    public static void openInModalMode(URL url, String message, GameController gameController) {

        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            RoundResultController rrc = (RoundResultController)loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Round result");
            stage.setResizable(false);
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            rrc.setRoundResult(message);
            rrc.setGameController(gameController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setRoundResult(String roundResult) {
        this.roundResult.setText(roundResult);
    }

    public void clickButtonHandler(ActionEvent evt){

        Button clickedButton = (Button)evt.getTarget();
        String buttonID = clickedButton.getId();

        if (buttonID.equals("newGame")) {
            Stage stage = (Stage)clickedButton.getScene().getWindow();
            stage.close();
            gameController.newGame();
        }else if(buttonID.equals("quit")){
            System.exit(0);
        }

    }

}
