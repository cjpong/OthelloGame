package othello;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * the referee class controls the game flow of Othello, handling player turns, move checks etc.
 */
public class Referee {
    private Player white;
    private Player black;
    private Board board;
    private Player activePlayer;
    private Timeline timeline;
    private Pane gamePane;
    private Label scoreLabel;
    private Label instructionsLabel;

    /**
     * referee constructur initializes with game board and other ui elements
     * @param board
     * @param game
     * @param scoreLabel
     */
    public Referee(Board board, Pane game, Label scoreLabel){
        this.scoreLabel = scoreLabel;
        this.gamePane = game;
        this.board = board;
        this.gamePane.setFocusTraversable(true);
        this.gamePane.requestFocus();
        this.gamePane.setOnMouseClicked((MouseEvent e) -> makeMove(e));

    }

    /**
     * updates score label with the current count of black and white pieces
     */
    public void updateScore(){
        this.scoreLabel.setText("White: "+ this.board.getWhiteCount() + "   Black: " + this.board.getBlackCount());
    }

    /**
     * switches to the next player's turn and handles endgame
     * @param skip if skip, the program skips the switching to the next player's turn
     */
    private void nextPlayerTurn(boolean skip){
        this.board.resetBoardColor();
        updateScore();
        if(this.board.checkEndGame(activePlayer.getColor())){
            gameOver();
            return;
        }
        if(!skip){
            if(activePlayer == white){
                activePlayer = black;
            }
            else{
                activePlayer = white;
            }
        }
        if(activePlayer.getColor() == Constants.WHITE){
            this.instructionsLabel.setText("White to move");
        }
        else{
            this.instructionsLabel.setText("Black to move");
        }
        if(activePlayer.isComputer()){
            int[] moveLocation = activePlayer.makeMove();
            if(moveLocation == null){
                gameOver();
                return;
            }
            this.board.clickGrid(moveLocation, activePlayer.getColor());
            this.board.flipSquares(moveLocation, activePlayer.getColor());
            playTimeline();
        }
        else{
            showLegalMoves();
        }
    }

    /**
     * brief animation before turn ends to transition to the next player.
     */
    public void playTimeline(){
        KeyFrame KF = new KeyFrame(Duration.seconds(.1));
        this.timeline = new Timeline(KF);
        this.timeline.setCycleCount(1);
        this.timeline.play();
        this.timeline.setOnFinished(actionEvent -> nextPlayerTurn(false));
    }

    /**
     * handles mouse click events for making a move, checks if it is valid
     * also makes a computer move if it's the computer's turn
     * @param e
     */
    public void makeMove(MouseEvent e){
        if(!(white == null || black == null)){
            if(!activePlayer.isComputer()){
                int row = (int) e.getY()/Constants.GRID_WIDTH;
                int col = (int) e.getX()/Constants.GRID_WIDTH;
                int[] moveLocation = new int[]{row, col};
                boolean validMove = this.board.checkValidMove(moveLocation, activePlayer.getColor());
                if (validMove && !this.board.checkEndGame(activePlayer.getColor())) {
                    this.board.clickGrid(moveLocation, activePlayer.getColor());
                    this.board.flipSquares(moveLocation, activePlayer.getColor());
                    playTimeline();
                }
            }
            else{
                int[] moveLocation = this.activePlayer.makeMove();
                if(moveLocation == null){
                    gameOver();
                    return;
                }
                this.board.clickGrid(moveLocation, activePlayer.getColor());
                this.board.flipSquares(moveLocation, activePlayer.getColor());
                playTimeline();
            }
        }
    }

    /**
     * displays all possible moves for a player of a given color
     */
    public void showLegalMoves(){
        ArrayList<Move> moves = this.board.getPossibleMoves(activePlayer.getColor());
        for(Move m: moves){
            this.board.setGridColor(m.getLocation(), Color.GRAY);
        }
    }

    /**
     * setter method that allows the referee to access and modify the instruction label
     * @param i
     */
    public void addInstructionLabel(Label i){
        this.instructionsLabel = i;
    }


    /**
     * method to set the players given a player 1 and player 2, and also determines the current active player
     * if there is no active player, then white is the first to move
     * @param p1
     * @param p2
     */
    public void setPlayers(Player p1, Player p2){
        int activeColor = Constants.WHITE;
        if(activePlayer!= null){
            activeColor = this.activePlayer.getColor();
        }
        this.white = p1;
        this.black = p2;
        if(activeColor == Constants.WHITE){
            this.activePlayer = this.white;
        }
        else{
            this.activePlayer = this.black;
        }
        nextPlayerTurn(true);
    }

    /**
     * method to handle the end of the game, displays the winner or tie message, and disables the board from being
     * clicked
     */
    private void gameOver(){
        String winner = "Tie";
        if(this.board.getWhiteCount() > this.board.getBlackCount()){
            winner = "white wins";
        }
        else if(this.board.getBlackCount() > this.board.getWhiteCount()){
            winner = "black wins";
        }
        this.instructionsLabel.setText("Game Over, " + winner);
        this.gamePane.setFocusTraversable(false);
    }

    /**
     * resets the game state for a new game
     */
    public void reset(){
        white = null;
        black = null;
        this.activePlayer = null;
        //calls the board's reset method
        this.board.reset();
        //makes sure that all the squares are green
        this.board.resetBoardColor();
        //resets score to 0, 0
        this.updateScore();
        this.instructionsLabel.setText("Select options, then press Apply Settings");
    }
}
