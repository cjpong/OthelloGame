package othello;

import javafx.scene.layout.Pane;

/**
 * represents a player
 * abstract class that both humanplayer and computerplayer inherit from
 */
public class Player {
    protected int playerColor;
    protected Board board;
    protected Pane gamePane;
    protected Boolean isActive;
    protected Referee referee;
    protected boolean isComputer;

    /**
     * constructs a player object
     * @param board
     * @param game
     * @param color
     * @param referee
     */
    public Player(Board board, Pane game, int color, Referee referee){
        this.referee = referee;
        this.isActive = false;
        this.board = board;
        this.gamePane = game;
        this.playerColor = color;
        if(this.playerColor == Constants.WHITE){
            this.isActive = true;
        }
    }

    /**
     * makes a move on the board, overriden by subclasses
     * @return
     */
    public int[] makeMove(){
        return new int[0];
    };

    /**
     * returns player color
     * @return
     */
    public int getColor(){
        return this.playerColor;
    }


    /**
     * returns if this player is a computer or not
     * @return
     */
    public boolean isComputer(){
        return isComputer;
    }

}
