package othello;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * grid class represents a single square on the game board, as either a playable space
 * or as a border
 * contains information about its piece and visual representation
 */
public class Grid {
    private int pieceType;
    private boolean isBorder;
    private int row;
    private int column;
    private Rectangle square;
    private Piece piece;
    private Pane gamePane;

    /**
     * constructs a grid object
     * @param r row index of grid
     * @param c column index of grid
     * @param pane gamepane where grid should show
     * @param squareColor color of grid square
     * @param isBorder true if grid is a border
     */
    public Grid(int r, int c, Pane pane, Color squareColor, boolean isBorder){
        this.gamePane = pane;
        this.isBorder = isBorder;
        pieceType = 0;
        this.row = r;
        this.column = c;
        this.square = new Rectangle(this.column*Constants.GRID_WIDTH + 2, this.row*Constants.GRID_WIDTH + 2, Constants.GRID_WIDTH - 1, Constants.GRID_WIDTH - 1);
        this.square.setFill(squareColor);
        if (this.gamePane != null) {
            this.gamePane.getChildren().addAll(square);
        }
        this.piece = new Piece(this.row, this.column, gamePane);
    }

    /**
     * handles a click on the grid, adds a piece if it is not a border square, and updates the
     * piecetype to indicate that the grid has a piece now
     * @param activeColor
     */
    public void click(int activeColor){
        if(!(pieceType != 0 || this.isBorder)){
            this.piece.addPiece(activeColor);
            this.pieceType = 1;
        }
    }

    /**
     * puts a piece on the grid
     * @param i
     */
    public void setPiece(int i){
        this.piece.setPiece(i);
        this.pieceType = 1;
    }

    /**
     * checks if the grid already has a piece in it
     * @return
     */
    public boolean isFilled(){
        if(pieceType == 1){
            return true;
        }
        return false;
    }

    /**
     * gets if the grid is a border piece
     * @return
     */
    public boolean isBorder(){
        return this.isBorder;
    }

    /**
     * returns piece color if there is a piece; returns 2 if no piece
     * @return
     */
    public int getPieceColor(){
        if(pieceType == 0){
            return 2;
        }
        else{
            return piece.getPieceColor();
        }
    }

    /**
     * changes piece color to opposing piece
     */
    public void flipPiece(){
        this.piece.flipPiece();
    }

    /**
     * changes the color of the grid square
     * @param c
     */
    public void setGridColor(Color c){
        this.square.setFill(c);
    }

    /**
     * deep copy of the grid, including all of its information and the piece
     * @return
     */
    public Grid copyGrid(){
        Grid temp = new Grid(this.row, this.column, null, Color.BLACK, this.isBorder);
        temp.pieceType = this.pieceType;
        temp.piece = this.piece.copyPiece();
        return temp;
    }

    /**
     * resets the grid to initial state, removing pieces.
     */
    public void reset(){
        pieceType = 0;
        this.piece.reset();
    }
}
