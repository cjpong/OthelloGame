package othello;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
/**
 * setupgame class initializes and manages the game setup, such as creating the board, setting players, and delegating
 * ui components
 */
public class SetupGame {
    private Board board;
    private Pane gamePane;
    private Referee referee;

    private Label scoreLabel;

    /**
     * initializes the game setup by creating the board and referee
     */
    public SetupGame(){
        this.gamePane = new Pane();
        this.gamePane.setPrefWidth(Constants.BOARD_WIDTH);
        this.gamePane.setPrefHeight(Constants.BOARD_WIDTH);
        this.board = new Board(this.gamePane);
        this.scoreLabel = new Label("White: 2   " + "Black: 2");
        this.referee = new Referee(this.board, this.gamePane, scoreLabel);
        this.board.setUpBoard();
    }

    /**
     * returns the scorelabel, which is supposed to display the game's current score
     * @return
     */
    public Label getScoreLabel(){
        return this.scoreLabel;
    }

    /**
     * creates the players based on a player's choice from the control panel, and hands them over to the referee
     * @param p1mode
     * @param p2mode
     */
    public void setPlayers(int p1mode, int p2mode){
        Player p1;
        Player p2;
        switch(p1mode){
            case 0:
                p1 = new HumanPlayer(this.board, this.gamePane, Constants.WHITE, this.referee);
                break;
            case 1:
                p1 = new ComputerPlayer(this.board, this.gamePane, Constants.WHITE, this.referee, 1);
                break;
            case 2:
                p1 = new ComputerPlayer(this.board, this.gamePane, Constants.WHITE, this.referee, 2);
                break;
            case 3:
                p1 = new ComputerPlayer(this.board, this.gamePane, Constants.WHITE, this.referee, 3);
                break;
            default:
                p1 = new HumanPlayer(this.board, this.gamePane, Constants.WHITE, this.referee);
        }
        switch(p2mode){
            case 0:
                p2 = new HumanPlayer(this.board, this.gamePane, Constants.BLACK, this.referee);
                break;
            case 1:
                p2 = new ComputerPlayer(this.board, this.gamePane, Constants.BLACK, this.referee, 1);
                break;
            case 2:
                p2 = new ComputerPlayer(this.board, this.gamePane, Constants.BLACK, this.referee, 2);
                break;
            case 3:
                p2 = new ComputerPlayer(this.board, this.gamePane, Constants.BLACK, this.referee, 3);
                break;
            default:
                p2 = new HumanPlayer(this.board, this.gamePane, Constants.BLACK, this.referee);
        }
        this.referee.setPlayers(p1, p2);
    }

    /**
     * returns the game pane where board is displayed
     * @return
     */
    public Pane getGamePane(){
        return this.gamePane;
    }

    /**
     * returns the referee handling players
     * @return
     */
    public Referee getReferee(){return this.referee;}


}

