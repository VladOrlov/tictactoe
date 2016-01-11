package tictactoe;

/**
 * @author Orlov Vladislav on 05.01.2016.
 */
public class Cell {

    private int column;
    private int row;
    private int value;

    public Cell(int column, int row) {
        this.column = column;
        this.row = row;
        this.value = 0;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isFree(){
        return value == 0;
    }

    public void takeACell(GameBoard gameBoard, int value){
        if(this.value != 0){
            System.out.println("Wrong cell!!! current:"+value);
        }
        setValue(value);
        gameBoard.makeMove(this);
    }

    public void reset(){
        setValue(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;

        Cell cell = (Cell) o;

        if (getColumn() != cell.getColumn()) return false;
        return getRow() == cell.getRow();

    }

    @Override
    public int hashCode() {
        int result = getColumn();
        result = 17 * result + 31 * getRow();
        return result;
    }
}
