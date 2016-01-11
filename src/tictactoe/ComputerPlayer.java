package tictactoe;

import java.util.List;

/**
 * @author Orlov Vladislav on 05.01.2016.
 */
public class ComputerPlayer{

    private static int mistakesDone = 0;
    private GameBoard gameBoard;
    private GameDifficulty gameDifficulty;
    private GameController.Turn.Move playFor;

    public enum GameDifficulty{APRENTICE(4), ADEPT(2), MASTER(1), GRANDMASTER(0);
        private int mistakeChance;

        GameDifficulty(int value){
            mistakeChance = value;
        }

        public boolean makeMistake(){
            return (int)(Math.random() * (mistakeChance-mistakesDone)) !=0 ? true : false;
        }

    }

    public ComputerPlayer(GameBoard gameBoard, GameController.Turn.Move playFor) {
        this.gameBoard      = gameBoard;
        this.playFor        = playFor;
        this.gameDifficulty = GameDifficulty.ADEPT;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
        reset();
    }

    public Cell getMove(){

        if(gameDifficulty.makeMistake()){
            mistakesDone++;
            List<Cell> availableMoves = gameBoard.getAvailableMoves();
            int selectedMove = (int)(Math.random()*(availableMoves.size()-1));
            return availableMoves.get(selectedMove);
        }

        List<GameBoard.WinLine> winLines = gameBoard.getWinLines();

        for (GameBoard.WinLine winLine : winLines) {
            if (winLine.getCheckSum() == 2) {
                return winLine.getFreeCell();
            }
        }

        for (GameBoard.WinLine winLine : winLines) {
            if (winLine.getCheckSum() == -2) {
                return winLine.getFreeCell();
            }
        }

        Cell cellToMove = getSuitableStandardMove();
        if(cellToMove != null){
            return cellToMove;
        }

        for (GameBoard.WinLine winLine : winLines) {
            if (winLine.getCheckSum() >= -1) {
                return winLine.getFreeCell();
            }
        }

        return null;
    }

    public Cell getSuitableStandardMove(){

        Cell cellToMove = null;
        cellToMove = gameBoard.findCellByIndexInBoard(1,1);
        if(cellToMove.isFree()){
            return cellToMove;
        }
        cellToMove = gameBoard.findCellByIndexInBoard(0,0);
        if(cellToMove.isFree()){
            return cellToMove;
        }
        cellToMove = gameBoard.findCellByIndexInBoard(0,2);
        if(cellToMove.isFree()){
            return cellToMove;
        }
        cellToMove = gameBoard.findCellByIndexInBoard(2,0);
        if(cellToMove.isFree()){
            return cellToMove;
        }
        cellToMove = gameBoard.findCellByIndexInBoard(2,2);
        if(cellToMove.isFree()){
            return cellToMove;
        }

        return null;

    }

    public void reset(){
        mistakesDone = 0;
    }

    public GameController.Turn.Move getPlayFor() {
        return playFor;
    }

    public void setPlayFor(GameController.Turn.Move playFor) {
        this.playFor = playFor;
    }
}
