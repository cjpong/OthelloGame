package othello;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * represents a single game piece in the othello game (a circle)
 */
public class Piece {
    private Circle circle;
    private Pane gamePane;
    private int pieceColor;
    private int row;
    private int col;

    /**
     * constructs a piece object at a specified location on the board
     * @param row
     * @param col
     * @param gamePane
     */
    public Piece(int row, int col, Pane gamePane){
        this.row = row;
        this.col = col;
        this.circle = new Circle(col*Constants.GRID_WIDTH+30, row*Constants.GRID_WIDTH+30, Constants.PIECE_RADIUS);
        this.gamePane = gamePane;
        this.pieceColor = 2;
    }

    /**
     * Adds piece to board and sets the color based on active player
     * @param activePlayer
     */
    public void addPiece(int activePlayer){
        if(activePlayer == Constants.WHITE){
            this.circle.setFill(Color.WHITE);
            this.pieceColor = Constants.WHITE;
        }
        else{
            this.circle.setFill(Color.BLACK);
            this.pieceColor = Constants.BLACK;
        }
        this.addToPane();
    }

    /**
     * changes piece color to the opposite color
     */
    public void flipPiece(){
        if(circle.getFill()==Color.WHITE){
            circle.setFill(Color.BLACK);
            this.pieceColor = Constants.BLACK;
        }
        else{
            circle.setFill(Color.WHITE);
            this.pieceColor = Constants.WHITE;
        }
    }

    /**
     * adds the circle graphically to the game pane
     */
    public void addToPane(){
        if(gamePane!= null){
            this.gamePane.getChildren().add(this.circle);
        }
    }

    /**
     * directly sets the color of the piece to either black or white
     * @param i
     */
    public void setPiece(int i){
        if(i == Constants.WHITE){
            circle.setFill(Color.WHITE);
            this.pieceColor = Constants.WHITE;
        }
        else{
            circle.setFill(Color.BLACK);
            this.pieceColor = Constants.BLACK;
        }
        this.addToPane();
    }

    /**
     * @return color of the piece (black, white, 2 if none);
     */
    public int getPieceColor(){
        return this.pieceColor;
    }

    /**
     * deepcopy of the piece without a pane
     * @return
     */
    public Piece copyPiece(){
         Piece temp = new Piece(this.row, this.col, null);
         temp.pieceColor = this.pieceColor;
         return temp;
    }

    /**
     * resets the piece to its original state, and removes it from the pane.
     */
    public void reset(){
        this.gamePane.getChildren().remove(this.circle);
        this.circle = null;
        this.circle = new Circle(col*Constants.GRID_WIDTH+30, row*Constants.GRID_WIDTH+30, Constants.PIECE_RADIUS);
        this.pieceColor = 2;
    }

}

