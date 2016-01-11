package tictactoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Orlov Vladislav on 05.01.2016.
 */
public class GameBoard {

    private static int[][] gameBoard;
    private static List<WinLine> winLines;
    private static WinLine winLine;
    private Map<String, Cell> boardCells;

    public static class WinLineBuilder{

        private Cell firstCell;
        private Cell secondCell;
        private Cell thirdCell;

        public WinLineBuilder() {
        }

        public WinLineBuilder setNextCell(Cell cell){
            if(this.firstCell == null){
                this.firstCell = cell;
            }else if(this.secondCell == null){
                this.secondCell = cell;
            }else if(this.thirdCell == null){
                this.thirdCell = cell;
            }
            return this;
        }

        public GameBoard.WinLine build(){
            return new WinLine(this.firstCell, this.secondCell, this.thirdCell);
        }

        public void clear(){
            this.firstCell = null;
            this.secondCell= null;
            this.thirdCell = null;
        }
    }

    public static class WinLine{

        private final Cell firstCell;
        private final Cell secondCell;
        private final Cell thirdCell;
        private int checkSum;

        private WinLine() {
            firstCell = null;
            secondCell = null;
            thirdCell = null;
        }

        public WinLine(Cell firstCell, Cell secondCell, Cell thirdCell) {
            this.firstCell = firstCell;
            this.secondCell = secondCell;
            this.thirdCell = thirdCell;
        }

        public WinLine checkForWinner(){
            checkSum = firstCell.getValue()+secondCell.getValue()+thirdCell.getValue();

            if(checkSum == 3 || checkSum == -3) {
                setWinLine(this);
            }

            return null;
        }

        public Cell getFirstCell() {
            return firstCell;
        }

        public Cell getSecondCell() {
            return secondCell;
        }

        public Cell getThirdCell() {
            return thirdCell;
        }

        public int getCheckSum() {
            return checkSum;
        }

        public Cell getFreeCell(){
            if(firstCell.isFree()){
                return firstCell;
            }else if(secondCell.isFree()){
                return secondCell;
            }else if(thirdCell.isFree()){
                return thirdCell;
            }
            return null;
        }

        @Override
        public String toString() {
            return "WinLine{" +
                    "firstCell=" + firstCell +
                    ", secondCell=" + secondCell +
                    ", thirdCell=" + thirdCell +
                    '}';
        }
    }

    public static void setWinLine(WinLine line) {
        winLine = line;
    }

    public static WinLine getWinLine() {
        return winLine;
    }

    public static boolean isWinner(){
        return winLine != null;
    }

    public static List<WinLine> getWinLines() {
        return winLines;
    }

    public Cell getCellByButtonID(String id){
        return boardCells.get(id);
    }

    public void newGame(){
        resetGameBoard();
        for (Map.Entry<String, Cell> entry : boardCells.entrySet()) {
            entry.getValue().reset();
        }
        setWinLine(null);
    }

    public void initialize() {
        setGameBoard(new int[3][3]);
        initializeBoard();
        initializeWinLines();
    }

    private void initializeBoard(){

        boardCells = new HashMap<>();
        Cell currentCell;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                currentCell = new Cell(i, j);
                boardCells.put(GameController.getButtonName(currentCell), currentCell);
            }
        }

    }

    private void initializeWinLines(){

        List<GameBoard.WinLine> lines = new ArrayList<>();
        WinLineBuilder vhLineBuilder = new WinLineBuilder();
        WinLineBuilder d1LineBuilder = new WinLineBuilder();
        WinLineBuilder d2LineBuilder = new WinLineBuilder();

        for(int i = 0, k = gameBoard.length-1; i < gameBoard.length; i++, k--){

            d1LineBuilder.setNextCell(findCellByIndexInBoard(i, i));
            d2LineBuilder.setNextCell(findCellByIndexInBoard(k, i));

            for (int j = 0; j < gameBoard.length; j++){
                vhLineBuilder.setNextCell(findCellByIndexInBoard(i, j));
            }
            lines.add(vhLineBuilder.build());
            vhLineBuilder.clear();

            for (int j = 0; j < gameBoard.length; j++){
                vhLineBuilder.setNextCell(findCellByIndexInBoard(j, i));
            }
            lines.add(vhLineBuilder.build());
            vhLineBuilder.clear();
        }

        lines.add(d1LineBuilder.build());
        lines.add(d2LineBuilder.build());

        setWinLines(lines);

    }

    public Cell findCellByIndexInBoard(int column, int row){
       return boardCells.get(GameController.getButtonName(new Cell(column, row)));
    }

    public void setGameBoard(int[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setWinLines(List<WinLine> linesList) {
        winLines = linesList;
    }

    public void resetGameBoard(){
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard[i][j] = 0;
            }
        }
    }

    public void makeMove(Cell cell){
        gameBoard[cell.getColumn()][cell.getRow()] = cell.getValue();
        notifyWinLines();
    }

    private void notifyWinLines() {
        for (WinLine line : winLines) {
            if(winLine != null){
                break;
            }
            line.checkForWinner();
        }
    }

    public List<Cell> getAvailableMoves() {
        List<Cell>availableMoves = new ArrayList<>();

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (gameBoard[i][j] == 0) {
                    availableMoves.add(findCellByIndexInBoard(i, j));
                }
            }
        }
        return availableMoves;
    }

    public String getWinner(){
        if(winLine != null){
            return "Winner is "+ (winLine.getCheckSum() > 0 ? "Player X" : "Player O");
        }else{
            return "Round DRAW";
        }
    }

    public Map<String, Cell> getBoardCells() {
        return boardCells;
    }

    public void setBoardCells(Map<String, Cell> boardCells) {
        this.boardCells = boardCells;
    }
}

