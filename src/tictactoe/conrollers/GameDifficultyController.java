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
import tictactoe.ComputerPlayer;
import tictactoe.GameController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Orlov Vladislav on 05.01.2016.
 */
public class GameDifficultyController extends Stage implements Initializable{

    @FXML
    RadioButton apprentice;
    @FXML
    RadioButton adept;
    @FXML
    RadioButton master;
    @FXML
    RadioButton grandmaster;

    private GameController gameController;

    public static void openInModalMode(URL url, ComputerPlayer.GameDifficulty gameDifficulty, GameController gameController) {

        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            GameDifficultyController gdc = (GameDifficultyController)loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Game difficulty");
            stage.setResizable(false);
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            gdc.setGameController(gameController);
            gdc.setGameDifficulty(gameDifficulty);
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

    public void setGameDifficulty(ComputerPlayer.GameDifficulty difficulty) {
        if(difficulty == ComputerPlayer.GameDifficulty.ADEPT){
            adept.setSelected(true);
        }else if(difficulty == ComputerPlayer.GameDifficulty.APRENTICE){
            apprentice.setSelected(true);
        }else if(difficulty == ComputerPlayer.GameDifficulty.MASTER){
            master.setSelected(true);
        }else if(difficulty == ComputerPlayer.GameDifficulty.GRANDMASTER){
            grandmaster.setSelected(true);
        }

    }

    public void clickButtonHandler(ActionEvent evt){

        Button clickedButton = (Button)evt.getTarget();
        String buttonID = clickedButton.getId();

        if (buttonID.equals("ok")) {
            if(apprentice.isSelected()){
                gameController.changeGameDifficulty(ComputerPlayer.GameDifficulty.APRENTICE);
            }else if(adept.isSelected()){
                gameController.changeGameDifficulty(ComputerPlayer.GameDifficulty.ADEPT);
            }else if(master.isSelected()){
                gameController.changeGameDifficulty(ComputerPlayer.GameDifficulty.MASTER);
            }else if(grandmaster.isSelected()){
                gameController.changeGameDifficulty(ComputerPlayer.GameDifficulty.GRANDMASTER);
            }
        }

        Stage stage = (Stage)clickedButton.getScene().getWindow();
        stage.close();

    }

}
