package othello;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Represents the logical structure of the Othello board
 * handles important game methods such as evaluating the board, flipping pieces, validating moves
 * and other helping methods for gameplay
 * board is represented by a 10x10 array of "Grids", the inner 8x8 grid is actually used for gameplay
 * while the outer layer is a border.
 */
public class Board {
    private Grid[][] board;
    private Pane gamePane;
    private int whiteCount;
    private int blackCount;

    public Board(Pane game){
        this.gamePane = game;
        this.board = new Grid[10][10];
        //each player starts with two pieces
        this.whiteCount = 2;
        this.blackCount = 2;
    }
    /**
     * initializes each Grid object within the board array, and sets those which are border to border and
     * makes them red, while playable grids are green.
     * Sets up the initial four pieces
     */
    public void setUpBoard(){
        for(int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                Color temp = Color.GREEN;
                boolean isBorder = false;
                if(i==0 || i == 9|| k==0 ||  k == 9){
                    temp = Color.RED;
                    isBorder = true;
                }
                this.board[i][k] = new Grid(i, k, this.gamePane, temp, isBorder);
            }
        }
        this.board[4][4].setPiece(Constants.WHITE);
        this.board[4][5].setPiece(Constants.BLACK);
        this.board[5][4].setPiece(Constants.BLACK);
        this.board[5][5].setPiece(Constants.WHITE);

    }

    /**
     *simulates a click on the grid object at the mouse's click location and adds a piece of the curernt player's
     * color to the board
     * updates the piece count for each color
     * @param location array that contians the row and column of the intented grid
     * @param activeColor the color of the active player
     */
    public void clickGrid(int[] location, int activeColor){
        int row = location[0];
        int col = location[1];
        this.board[row][col].click(activeColor);
        if(activeColor == 0){
            this.whiteCount += 1;
        }
        else{
            this.blackCount += 1;
        }
    }

    /**
     * checks if the clicked location is a valid move for the current player
     * move is valid as long as it is on an unclaimed square, not a border, and sandwiches at least one opponent piece.
     * @param location
     * @param activeColor
     * @return
     */
    public boolean checkValidMove(int[] location, int activeColor){
        int row = location[0];
        int col = location[1];
        //checks for border click
        if(row == 0 || row == 9 || col == 0 || col == 9){
            return false;
        }
        //checks if the game is over
        else if(checkEndGame(activeColor)){
            return false;
        }
        //checks if the clicked square is taken already
        if(this.board[row][col].isFilled()){
            return false;
        }
        boolean valid = false;
        int i = 0;
        //checks
        while(!valid && i < 8){
            int[] directions = directionFinder(i);
            valid = checkSandwich(row, col, directions,false, activeColor);
            i++;
        }
        return valid;
    }

    /**
     *recursive method to check if placing a piece sandwiches an opponent piece
     * @param row
     * @param col
     * @param directions array that specifys the direction in which to check (row direction, column direction)
     * @param seenOpponent condition if an opponent piece has already been seen
     * @param activeColor color of active player
     * @return true if a sandwich is found, false if otherwise
     */
    private boolean checkSandwich(int row, int col, int[] directions, boolean seenOpponent, int activeColor){
        //gets the next square in the direction of checking
        Grid nextSquare = this.board[row+directions[0]][col + directions[1]];
        //runs this if opponent hasn't been seen yet
        if(!seenOpponent){
            if(nextSquare.isBorder()||nextSquare.getPieceColor()==2){
                return false;
            }
            else if (nextSquare.getPieceColor() != activeColor){
                return checkSandwich(row + directions[0], col + directions[1], directions, true, activeColor);
            }
            else{
                return false;
            }
        }
        //runs if opponent has been seen already
        else{
            if(nextSquare.isBorder()||nextSquare.getPieceColor()==2){
                return false;
            }
            else if(nextSquare.getPieceColor() == activeColor){
                return true;
            }
            else{
                //continues to check in the direction specified
                return checkSandwich(row + directions[0], col + directions[1], directions, true, activeColor);
            }
        }
    }

    /**
     * flips the opponents pieces to the active player's color if a valid move is made
     * @param location
     * @param activeColor
     */
    public void flipSquares(int[] location, int activeColor){
        int row = location[0];
        int col = location[1];
        int i = 0;
        //iterates through the 8 directions to check for sandwiches in all directions
        while(i<8){
            int[] directions = directionFinder(i);
            if(checkSandwich(row, col, directions, false, activeColor)){
                flipSquaresHelper(row, col, directions, activeColor);
            }
            i++;
        }
    }

    /**
     * recursively flips opponent pieces in a given directions
     * @param row
     * @param col
     * @param directions
     * @param activeColor
     */
    private void flipSquaresHelper(int row, int col, int[] directions, int activeColor){
        int nextRow = row+directions[0];
        int nextCol = col + directions[1];
        Grid nextSquare = this.board[nextRow][nextCol];
        if(!(nextSquare.getPieceColor() == activeColor || nextSquare.isBorder()||nextSquare.getPieceColor() == 2)){
            nextSquare.flipPiece();
            if(activeColor == 0){
                this.whiteCount += 1;
                this.blackCount -= 1;
            }
            else{
                this.blackCount += 1;
                this.whiteCount -= 1;
            }
            flipSquaresHelper(nextRow, nextCol, directions, activeColor);
        }
    }

    /**
     * checks if the game has ended for the current player, by first checking if the board
     * is filled, then if there are still possible moves
     * @param activeColor
     * @return
     */
    public boolean checkEndGame(int activeColor){
        for(Grid[] row: this.board){
            for(Grid g: row){
                if(g.getPieceColor() != 2){
                    return false;
                }
            }
        }
        if(getPossibleMoves(activeColor)!= null){
            return false;
        }
        return true;
    }

    /**
     * getter method for number of white pieces on the board currently
     * @return
     */
    public int getWhiteCount(){
        return whiteCount;
    }

    /**
     * getter method for number of black pieces on the board currently
     * @return
     */
    public int getBlackCount(){
        return blackCount;
    }

    /**
     * helper method that converts a direction (1-8) into vectors that other methods can easily use.
     * @param i
     * @return
     */
    private int[] directionFinder(int i){
        switch(i){
            case 0:
                return new int[]{-1, 0};
            case 1:
                return new int[]{-1, 1};
            case 2:
                return new int[]{0, 1};
            case 3:
                return new int[]{1, 1};
            case 4:
                return new int[]{1, 0};
            case 5:
                return new int[]{1, -1};
            case 6:
                return new int[]{0, -1};
            case 7:
                return new int[]{-1, -1};
            default:
                return new int[]{1,1};
        }
    }

    /**
     * evaluates the board's score based on the predefined weights for grid positions
     * a higher score indicates a favorable board state for the current player
     * @param playerColor
     * @return
     */
    public int evaluateBoard(int playerColor){
        int whiteScore = 0;
        int blackScore = 0;
        int eval = 0;
        for(int i = 1; i < 8; i++){
            for (int k = 1; k < 8; k++) {
                if(!board[i][k].isBorder() && board[i][k].getPieceColor() == Constants.WHITE){
                    whiteScore += Constants.BOARD_WEIGHTS[i][k];
                }
                else if(!board[i][k].isBorder() && board[i][k].getPieceColor() == Constants.BLACK){
                    blackScore += Constants.BOARD_WEIGHTS[i][k];
                }
            }
        }
        if(playerColor == Constants.WHITE){
            eval = whiteScore - blackScore;
        }
        else{
            eval = blackScore - whiteScore;
        }
        return eval;
    }

    /**
     * returns ArrayList of all possible moves for active player by iterating through the board and checking
     * if each grid is a valid move
     * @param activeColor
     * @return
     */
    public ArrayList<Move> getPossibleMoves(int activeColor){
        ArrayList<Move> possibleMoves = new ArrayList();
        for(int r = 1; r < 9; r++){
            for(int c = 1; c < 9; c++){
                int[] move = new int[]{r, c};
                if(checkValidMove(move, activeColor)){
                    possibleMoves.add(new Move(move));
                }
            }
        }
        return possibleMoves;
    }

    /**
     * getter method for the 2d array of grids
     * @return
     */
    public Grid[][] getBoard(){
        return this.board;
    }

    /**
     * creates a deep copy of current board state excluding the graphical parts, for our
     * computer player to use
     * @return a new board with identical value
     */
    public Board copyBoard(){
        Board temp = new Board(null);
        for(int i = 0; i < 10; i++){
            for(int k = 0; k < 10; k++){
                //deep copy method from grid class
                temp.board[i][k] = this.board[i][k].copyGrid();
            }
        }
        temp.whiteCount = this.whiteCount;
        temp.blackCount = this.blackCount;
        return temp;
    }

    /**
     * sets the color of a specific grid square
     * @param location
     * @param c
     */
    public void setGridColor(int[] location, Color c){
        int row = location[0];
        int col = location[1];
        this.board[row][col].setGridColor(c);
    }

    /**
     * sets color of all non border squares to green
     */
    public void resetBoardColor(){
        for(Grid[] gRow: this.board){
            for(Grid g: gRow){
                if(!g.isBorder()){
                    g.setGridColor(Color.GREEN);
                }
            }
        }
    }

    /**
     * resets board to initial state, clearing all placed pieces and putting back the starting four
     * pieces
     */
    public void reset(){
        this.whiteCount = 2;
        this.blackCount = 2;
        for(int i = 1; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                this.board[i][k].reset();
            }
        }
        this.board[4][4].setPiece(Constants.WHITE);
        this.board[4][5].setPiece(Constants.BLACK);
        this.board[5][4].setPiece(Constants.BLACK);
        this.board[5][5].setPiece(Constants.WHITE);
    }
}
