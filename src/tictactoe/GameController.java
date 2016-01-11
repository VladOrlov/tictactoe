package tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tictactoe.conrollers.GameDifficultyController;
import tictactoe.conrollers.GameModeController;
import tictactoe.conrollers.ModalWindowsController;
import tictactoe.conrollers.RoundResultController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author Orlov Vladislav on 05.01.2016.
 */
public class GameController {

    @FXML
    Button btn0_0;
    @FXML
    Button btn0_1;
    @FXML
    Button btn0_2;
    @FXML
    Button btn1_0;
    @FXML
    Button btn1_1;
    @FXML
    Button btn1_2;
    @FXML
    Button btn2_0;
    @FXML
    Button btn2_1;
    @FXML
    Button btn2_2;

    private GameBoard gameBoard;
    private Turn turn;
    private GameMode gameMode;
    private ComputerPlayer computerPlayer;
    private ModalWindowsController mwc;

    public enum GameMode {PvP, PvC}

    public GameController() {
        gameBoard  = new GameBoard();
        gameBoard.initialize();

        turn = new Turn(1, Turn.Move.X);

        gameMode = GameMode.PvC;
        computerPlayer = new ComputerPlayer(gameBoard, Turn.Move.O);

        mwc = new ModalWindowsController();
        mwc.setGameController(this);
    }

    public void buttonClickHandler(MouseEvent evt){

        Button clickedButton = (Button) evt.getTarget();
        String buttonID = clickedButton.getId();

        Cell cell = gameBoard.getCellByButtonID(buttonID);

        if(cell == null || !cell.isFree()){
            return;
        }

        processPlayerMove(cell, clickedButton);

    }

    public void menuClickHandler(ActionEvent evt){

        MenuItem clickedMenu = (MenuItem) evt.getTarget();
        String menuID = clickedMenu.getId();

        if (menuID.equals("newGame")){
            newGame();
        }else if(menuID.equals("difLevel")){
            GameDifficultyController.openInModalMode(getClass().getResource("/fxml/difficulty_selection.fxml"), computerPlayer.getGameDifficulty(), this);
        }else if(menuID.equals("gameMod")){
            GameModeController.openInModalMode(getClass().getResource("/fxml/game_mode.fxml"), gameMode, this);
        }else if(menuID.equals("rules")){
            ModalWindowsController.openInModalMode(getClass().getResource("/fxml/rules.fxml"), "Game rules");
        }else if(menuID.equals("about")){
            ModalWindowsController.openInModalMode(getClass().getResource("/fxml/about.fxml"), "About");
        }else if(menuID.equals("quit")){
            System.exit(0);
        }
    }

    private void processPlayerMove(Cell cell, Button clickedButton){

        if (turn.moveIsX()){
            cell.takeACell(gameBoard, 1);
            clickedButton.setGraphic(new ImageView("images/x75dpi.png"));
            System.out.println("X player move: column - "+ cell.getColumn() +", row - "+cell.getRow()+ ", value - "+ cell.getValue());
        } else if(turn.moveIsO()){
            cell.takeACell(gameBoard, -1);
            clickedButton.setGraphic(new ImageView("images/o75dpi.png"));
            System.out.println("O player move: column - "+ cell.getColumn() +", row - "+cell.getRow()+ ", value - "+ cell.getValue());
        }

        if(!turn.setNextMove()){
            endGame();
        }else{
            if (gameMode == GameMode.PvC & computerPlayer.getPlayFor() == turn.getCurrentMove()) {
                computerMove();
            }
        }

    }

    private void computerMove(){

       Cell cellToMove = computerPlayer.getMove();

        try {
            processPlayerMove(cellToMove, getButtonInstance(cellToMove));
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

    }

    private void clearGameBoardPane(){

        Class<? extends GameController> aClass = this.getClass();

        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field field : declaredFields) {
            Annotation[] annotations = field.getAnnotations();
            if(field.isAnnotationPresent(FXML.class)){
                try {
                    Button btn = (Button) aClass.getMethod("get" + field.getName().replaceFirst("b","B")).invoke(this);
                    btn.setGraphic(new ImageView());
                    btn.setStyle("-fx-background-color:ghostwhite");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void highlightWinLine(GameBoard.WinLine line){

        try {
            Button firstBtn  = getButtonInstance(line.getFirstCell());
            Button secondBtn = getButtonInstance(line.getSecondCell());
            Button thirdBtn  = getButtonInstance(line.getThirdCell());

            highlightButtons(firstBtn, secondBtn, thirdBtn);
        } catch (Exception e) {
           e.printStackTrace();
        }

    }

    private Button getButtonInstance(Cell cell) throws Exception {
        if (cell == null){
            return null;
        }
        Class<? extends GameController> clazz = this.getClass();
        return (Button) clazz.getMethod("get" + getButtonName(cell).replaceFirst("b","B")).invoke(this);
    }

    private void highlightButtons(Button first, Button second, Button third){
        first.setStyle("-fx-background-color:lightsteelblue");
        second.setStyle("-fx-background-color:lightsteelblue");
        third.setStyle("-fx-background-color:lightsteelblue");
    }

    public static String getButtonName(Cell cell){
        return new StringBuilder("btn").append(cell.getColumn()).append("_").append(cell.getRow()).toString();
    }

    public void newGame(){
        clearGameBoardPane();
        gameBoard.newGame();
        turn.reset();
        computerPlayer.reset();
    }

    public void endGame(){
        if(GameBoard.getWinLine() != null){
            highlightWinLine(GameBoard.getWinLine());
        }
        showRoundResult(gameBoard.getWinner());
    }

    public void showRoundResult(String message){

        RoundResultController.openInModalMode(getClass().getResource("/fxml/round_result.fxml"), gameBoard.getWinner(), this);
    }

    public void changeGameDifficulty(ComputerPlayer.GameDifficulty gameDifficulty){
        computerPlayer.setGameDifficulty(gameDifficulty);
        newGame();
    }

    public void changeGameMode(GameMode gameMode){
        setGameMode(gameMode);
        newGame();
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public Button getBtn0_0() {
        return btn0_0;
    }

    public Button getBtn0_1() {
        return btn0_1;
    }

    public Button getBtn0_2() {
        return btn0_2;
    }

    public Button getBtn1_0() {
        return btn1_0;
    }

    public Button getBtn1_1() {
        return btn1_1;
    }

    public Button getBtn1_2() {
        return btn1_2;
    }

    public Button getBtn2_0() {
        return btn2_0;
    }

    public Button getBtn2_1() {
        return btn2_1;
    }

    public Button getBtn2_2() {
        return btn2_2;
    }

    public static class Turn {

        private int moveNumber;
        private Move currentMove;

        enum Move {O, X, E}

        public Turn(int moveNumber, Move currentMove) {
            this.moveNumber = moveNumber;
            this.currentMove = currentMove;
        }


        public int getMoveNumber() {
            return moveNumber;
        }

        public void setMoveNumber(int moveNumber) {
            this.moveNumber = moveNumber;
        }

        public Move getCurrentMove() {
            return currentMove;
        }

        public void setCurrentMove(Move currentMove) {
            this.currentMove = currentMove;
        }

        public boolean moveIsX(){
            return currentMove == Move.X;
        }

        public boolean moveIsO(){
            return currentMove == Move.O;
        }

        public boolean setNextMove(){

            if(GameBoard.isWinner() || moveNumber >= 9){
                this.currentMove = Move.E;
                return false;
            }else{
                this.currentMove = (this.currentMove == Move.X) ? Move.O : Move.X;
                this.moveNumber++;
                return true;
            }

        }

        public void setEndGame(){
            this.currentMove = Move.E;
        }

        public void reset(){
            moveNumber = 1;
            currentMove = Move.X;
        }
    }

}
